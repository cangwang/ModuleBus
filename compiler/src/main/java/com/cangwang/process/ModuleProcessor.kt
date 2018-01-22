package com.cangwang.process

import com.cangwang.annotation.ModuleGroup
import com.cangwang.annotation.ModuleUnit
import com.cangwang.bean.ModuleUnitBean
import com.cangwang.model.ICWModule
import com.cangwang.model.IModuleFactory
import com.cangwang.utils.Logger
import com.cangwang.utils.ModuleUtil
import com.google.auto.service.AutoService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.kotlinpoet.*

import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.MapUtils

import java.io.IOException
import java.util.Collections
import java.util.HashMap
import java.util.LinkedHashSet
import java.util.LinkedList

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
/**
 * Created by cangwang on 2017/12/6.
 */
@AutoService(Processor::class)
class ModuleProcessor : AbstractProcessor() {
    private lateinit var mFiler: Filer      // File util, write class file into disk.
    private lateinit var logger: Logger
    private lateinit var types: Types
    private lateinit var elements: Elements
    private lateinit var applicationName: String
    private lateinit var moduleName: String

    private val map = HashMap<String, LinkedList<ModuleUnitBean>>()

    @Synchronized override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        println("------------------------------")
        println("ModuleProcess init")

        mFiler = processingEnv.filer                  // Generate class.
        logger = Logger(processingEnv.messager)   // Package the log utils.
        ModuleUtil.logger = logger
        types = processingEnv.typeUtils            // Get type utils.
        elements = processingEnv.elementUtils      // Get class meta.
        val options = processingEnv.options
        if (MapUtils.isNotEmpty(options)) {
//            applicationName = options["applicationName"]
            if(options.get("applicationName")!=null) {
                applicationName = options.get("applicationName")!!
                println("applicationName = " + applicationName)
                initAppJson()
            }else if (options.get("moduleName")!=null){
//                moduleName = options["moduleName"]
                moduleName = options.get("moduleName")!!
                println("moduleName = " + moduleName)
            }
        }
    }


    override fun getSupportedAnnotationTypes(): Set<String> {
        val annotations = LinkedHashSet<String>()
        annotations.add(ModuleUnit::class.java!!.getCanonicalName())
        annotations.add(ModuleGroup::class.java!!.getCanonicalName())
        //        annotations.add(InjectBean.class.getCanonicalName());
        return annotations
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        if (CollectionUtils.isNotEmpty(set)) {
            val moduleUnitElements = roundEnvironment.getElementsAnnotatedWith(ModuleUnit::class.java)
            val moduleGroupElements = roundEnvironment.getElementsAnnotatedWith(ModuleGroup::class.java)
            //            Set<? extends Element> injectBeanElements = roundEnvironment.getElementsAnnotatedWith(InjectBean.class);
            try {
                //遍历注解
                logger!!.info(">>> Found moduleUnit, start get jsonArray <<<")
                val units = ModuleUnitProcessor.parseModules(moduleUnitElements, logger!!, mFiler!!, elements!!)
                logger!!.info(">>> Found moduleGroup, start get jsonArray <<<")
                val groups = ModuleGroupProcessor.parseModulesGroup(moduleGroupElements, logger!!, mFiler!!, elements!!)

                if (moduleName != null) {
                    val moduleArrary = JsonArray()
                    if (units != null)
                        moduleArrary.addAll(units)
                    if (groups != null)
                        moduleArrary.addAll(groups)
                    if (moduleArrary.size() > 0) {
                        //写入每个json文件
                        ModuleUtil.createCenterJson(moduleName!!)
                        ModuleUtil.writeJsonFile(ModuleUtil.getJsonAddress(moduleName!!), moduleArrary.toString())
                    }
                    logger!!.info(moduleArrary.toString())
                }

                //                InjectJarProcessor.parseModulesGroup(injectBeanElements, logger, mFiler, elements,types);
            } catch (e: Exception) {
                logger!!.error(e)
            }

        }

        return true
    }

    private fun initAppJson() {
        if (applicationName != null) {
            val path = ModuleUtil.getJsonAddress(applicationName!!)
            try {
                //先清理app center.json缓存数据
                ModuleUtil.createCenterJson(applicationName!!)
                //获取引用module的列表
                val moduleNameList = ModuleUtil.readSetting()
                val jsonArray = JsonArray()
                if (moduleNameList != null) {
                    for (name in moduleNameList) {
                        //读取module center.json列表
                        val json = ModuleUtil.readJsonFile(ModuleUtil.getJsonAddress(name))
                        if (json.isEmpty()) continue
                        jsonArray.addAll(ModuleUtil.parserJsonArray(json)!!)
                    }
                }
                logger!!.info("读取" + path + " module center.json列表:" + jsonArray.toString())
                //转换为对象做类型排序
                //                Map<String,LinkedList<ModuleUnitBean>> map =new HashMap<>();
                for (i in 0 until jsonArray.size()) {
                    val o = jsonArray.get(i).asJsonObject
                    val bean = ModuleUtil.gson.fromJson<ModuleUnitBean>(o, ModuleUnitBean::class.java!!)
                    if (map.containsKey(bean.templet)) {
                        map[bean.templet]!!.add(bean)
                    } else {
                        val list = object : LinkedList<ModuleUnitBean>() {
                            init {
                                add(bean)
                            }
                        }
                        map.put(bean.templet, list)
                    }
                }
                if (!map.isEmpty()) {
                    val o = JsonObject()
                    for ((key, value) in map) {
                        //排列层级
                        Collections.sort(value)
                        o.add(key, ModuleUtil.listToJson(value))
                    }
                    //写入到center.json
                    ModuleUtil.writeJsonFile(path, o.toString())
                }
                parseCenter(null, logger, mFiler, elements)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    @Throws(IOException::class)
    fun parseCenter(modulesElements: Set<Element>?, logger: Logger, mFiler: Filer?, elements: Elements?) {

        logger.info("init factory")

        val templateMap = ParameterizedTypeName.get(ClassName.get(HashMap::class),
                ClassName.get(String::class.java!!),
                ParameterizedTypeName.get(ClassName.get(List::class), ClassName.get(ICWModule::class)))

        val fieldMapBuilder = PropertySpec.builder("moduleMap",templateMap, KModifier.PRIVATE)
        fieldMapBuilder.initializer("new HashMap<>()")
        val fieldInstanceBuilder = PropertySpec.builder("sInstance", IModuleFactory::class, KModifier.PRIVATE)
        //添加loadInto方法
        val getInstanceBuilder = FunSpec.builder(ModuleUtil.METHOD_FACTROY_GET_INSTANCE)
                .returns(IModuleFactory::class.java!!)
                .addModifiers(KModifier.PUBLIC)
                .beginControlFlow("if (null == sInstance)")
                .addStatement("sInstance = new ModuleCenterFactory()")
                .endControlFlow()
                .addStatement("return sInstance")

        val templateList = ParameterizedTypeName.get(ClassName.get(List::class), ClassName.get(ICWModule::class))
        val templateName = ParameterSpec.builder("templateName",String::class).build()

        val getModuleListBuilder = FunSpec.builder(ModuleUtil.METHOD_FACTROY_GET_TEMPLE_LIST)
                .addAnnotation(Override::class)
                .addParameter(templateName)
                .returns(templateList)
                .addStatement("return moduleMap.get(templateName)")

        val code = CodeBlock.builder()
        //        code.addStatement("List<ICWModule> list = new $T<>()",LinkedList.class);

        var index = 0
        try {
            for ((key, value) in map) {
                //排列层级
                Collections.sort(value)
                code.addStatement("List<ICWModule> list\$L = new \$T<>()", index,LinkedList::class)
                for (b in value) {
                    logger.info(b.path)
                    code.addStatement("list\$L.add(new \$T().getModule())", index, ClassName.get(ModuleUtil.FACADE_PACKAGE, ModuleUtil.MODULE_UNIT + ModuleUtil.SEPARATOR + b.title))
                }
                code.addStatement("moduleMap.put(\$S,list\$L)", key, index)
                index++
                //                code.addStatement("list.clear();");
            }
        } catch (e: Exception) {
            logger.info(e.toString())
        }


        //构造java文件
        KotlinFile.builder(ModuleUtil.FACADE_PACKAGE,"ModuleCenterFactory")
                .addType(TypeSpec.classBuilder("ModuleCenterFactory")
                        .addKdoc(ModuleUtil.WARNING_TIPS)
                        .addSuperinterface(ClassName.get(elements!!.getTypeElement(ModuleUtil.IMODULE_FACTORY)))
                        .addModifiers(KModifier.PUBLIC)
                        .addProperty(fieldInstanceBuilder.build())
                        .addProperty(fieldMapBuilder.build())
//                        .addStaticBlock(code.build())
                        .addFun(getInstanceBuilder.build())
                        .addFun(getModuleListBuilder.build())
                        .build()
        ).build().writeTo(mFiler!!)
    }

}

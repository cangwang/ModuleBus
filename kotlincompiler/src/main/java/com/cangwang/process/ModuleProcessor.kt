package com.cangwang.process

import com.google.auto.service.AutoService
import javax.lang.model.util.Elements
import com.cangwang.bean.ModuleUnitBean
import com.cangwang.annotation.ModuleUnit
import com.cangwang.annotation.ModuleGroup
import javax.lang.model.SourceVersion
import com.cangwang.model.ICWModule
import com.cangwang.model.IModuleFactory
import com.cangwang.utils.Logger
import com.cangwang.utils.ModuleUtil
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.MapUtils
import java.io.IOException
import java.lang.Exception
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Types
import kotlin.collections.HashMap

/**
 * Created by cangwang on 2017/12/6.
 */
@AutoService(Processor::class)
class ModuleProcessor : AbstractProcessor() {
    private lateinit var mFiler: Filer // File util, write class file into disk.
    private lateinit var logger: Logger
    private lateinit var types: Types
    private lateinit var elements: Elements
    private var applicationName: String? = null
    private var moduleName: String? = null
    private val map: MutableMap<String, LinkedList<ModuleUnitBean>> = HashMap()
    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        println("------------------------------")
        println("ModuleProcess init")
        mFiler = processingEnv.filer // Generate class.
        logger = Logger(processingEnv.messager) // Package the log utils.
        ModuleUtil.logger = logger
        types = processingEnv.typeUtils // Get type utils.
        elements = processingEnv.elementUtils // Get class meta.
        val options = processingEnv.options
        if (MapUtils.isNotEmpty(options)) {
            applicationName = options["applicationName"]
            if (applicationName == null) {
                moduleName = options["moduleName"]
                println("moduleName = $moduleName")
            } else {
                println("applicationName = $applicationName")
                initAppJson()
            }
        }
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val annotations: MutableSet<String> = LinkedHashSet()
        annotations.add(ModuleUnit::class.java.canonicalName)
        annotations.add(ModuleGroup::class.java.canonicalName)
        //        annotations.add(InjectBean.class.getCanonicalName());
        return annotations
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(set: Set<TypeElement?>, roundEnvironment: RoundEnvironment): Boolean {
        if (CollectionUtils.isNotEmpty(set)) {
            val moduleUnitElements = roundEnvironment.getElementsAnnotatedWith(ModuleUnit::class.java)
            val moduleGroupElements = roundEnvironment.getElementsAnnotatedWith(ModuleGroup::class.java)
            //            Set<? extends Element> injectBeanElements = roundEnvironment.getElementsAnnotatedWith(InjectBean.class);
            try {
                //遍历注解
                logger!!.info(">>> Found moduleUnit, start get jsonArray <<<")
                val units = ModuleUnitProcessor.parseModules(moduleUnitElements, logger, mFiler, elements)
                logger!!.info(">>> Found moduleGroup, start get jsonArray <<<")
                val groups = ModuleGroupProcessor.parseModulesGroup(moduleGroupElements, logger, mFiler, elements)
                if (moduleName != null) {
                    val moduleArrary = JsonArray()
                    if (units != null) moduleArrary.addAll(units)
                    if (groups != null) moduleArrary.addAll(groups)
                    if (moduleArrary.size() > 0) {
                        //写入每个json文件
                        ModuleUtil.createCenterJson(moduleName)
                        ModuleUtil.writeJsonFile(ModuleUtil.getJsonAddress(moduleName), moduleArrary.toString())
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
            val path = ModuleUtil.getJsonAddress(applicationName)
            try {
                //先清理app center.json缓存数据
                ModuleUtil.createCenterJson(applicationName)
                //获取引用module的列表
                val moduleNameList = ModuleUtil.readSetting()
                logger!!.info("读取 module list:$moduleNameList")
                val jsonArray = JsonArray()
                if (moduleNameList != null) {
                    for (name in moduleNameList) {
                        //读取module center.json列表
                        val json = ModuleUtil.readJsonFile(ModuleUtil.getJsonAddress(name))
                        if (json.isEmpty()) continue
                        jsonArray.addAll(ModuleUtil.parserJsonArray(json))
                    }
                }
                logger!!.info("读取$path module center.json列表:$jsonArray")
                //转换为对象做类型排序
//                Map<String,LinkedList<ModuleUnitBean>> map =new HashMap<>();
                for (i in 0 until jsonArray.size()) {
                    val o = jsonArray[i].asJsonObject
                    val bean = ModuleUtil.gson.fromJson(o, ModuleUnitBean::class.java)
                    if (map.containsKey(bean.templet)) {
                        map[bean.templet]!!.add(bean)
                    } else {
                        val list: LinkedList<ModuleUnitBean> = object : LinkedList<ModuleUnitBean>() {
                            init {
                                add(bean)
                            }
                        }
                        map[bean.templet] = list
                    }
                }
                if (map.isNotEmpty()) {
                    val o = JsonObject()
                    for ((key, value) in map) {
                        //排列层级
                        value.sort()
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
    fun parseCenter(modulesElements: Set<Element?>?, logger: Logger?, mFiler: Filer?, elements: Elements?) {
        if (mFiler == null) return
        logger!!.info("init factory")
        val templateMap = HashMap::class.asClassName().parameterizedBy(
                String::class.asClassName(),
                MutableList::class.asClassName().parameterizedBy(
                ICWModule::class.asClassName()
        ))
        val fieldMapBuilder: PropertySpec.Builder = PropertySpec.builder("moduleMap", templateMap,  KModifier.PRIVATE)
        fieldMapBuilder.initializer("HashMap()")
        val fieldInstanceBuilder: PropertySpec.Builder = PropertySpec.builder("sInstance", IModuleFactory::class.java,  KModifier.PRIVATE, KModifier.COMPANION)
        //添加loadInto方法
        val getInstanceBuilder: FunSpec.Builder = FunSpec.builder(ModuleUtil.METHOD_FACTROY_GET_INSTANCE)
                .returns(IModuleFactory::class.java)
                .addModifiers(KModifier.PUBLIC, KModifier.COMPANION)
                .beginControlFlow("if (null == sInstance)")
                .addStatement("sInstance = ModuleCenterFactory()")
                .endControlFlow()
                .addStatement("return sInstance")
        val templateList = MutableList::class.asClassName().parameterizedBy(ICWModule::class.asClassName()).copy(nullable = true)

        val code: CodeBlock.Builder = CodeBlock.builder()
        //        code.addStatement("List<ICWModule> list = new $T<>()",LinkedList.class);
        var index = 0
        try {
            code.addStatement("val moduleMap: %T<String, List<%T>> = HashMap()", HashMap::class.java, ICWModule::class.java)
            for ((key, value) in map) {
                //排列层级
                value.sort()
                code.addStatement("val list%L: MutableList<ICWModule> = %T()", index,LinkedList::class.java)
                for (b in value) {
                    logger.info(b.path)
                    code.addStatement("list%L.add(%T().getModule())", index, ClassName(ModuleUtil.FACADE_PACKAGE, ModuleUtil.MODULE_UNIT + ModuleUtil.SEPARATOR + b.title))
                }
                code.addStatement("moduleMap.put(%S,list%L)", key, index)
                index++
                //                code.addStatement("list.clear();");
            }
        } catch (e: Exception) {
            logger.info(e.toString())
        }

        val templateName: ParameterSpec = ParameterSpec.builder("templateName", String::class.asTypeName().copy(nullable = true)).build()
        val getModuleListBuilder: FunSpec.Builder = FunSpec.builder(ModuleUtil.METHOD_FACTROY_GET_TEMPLE_LIST)
                .addModifiers(KModifier.PUBLIC, KModifier.OVERRIDE)
                .addCode(code.build())
                .addParameter(templateName)
                .returns(templateList)
                .addStatement("return moduleMap.get(templateName)")



        //构造java文件
        FileSpec.builder(ModuleUtil.FACADE_PACKAGE,"ModuleCenterFactory")
                .addType(TypeSpec.classBuilder("ModuleCenterFactory")
                        .addKdoc(ModuleUtil.WARNING_TIPS)
                        .addSuperinterface(elements!!.getTypeElement(ModuleUtil.IMODULE_FACTORY).asClassName())
//                        .addModifiers(KModifier.PUBLIC)
//                        .addProperty(fieldInstanceBuilder.build())
//                        .addProperty(fieldMapBuilder.build())
//                        .addFunction(getInstanceBuilder.build())
                        .addFunction(getModuleListBuilder.build())
                        .build()
        ).build().writeTo(mFiler)
    }
}
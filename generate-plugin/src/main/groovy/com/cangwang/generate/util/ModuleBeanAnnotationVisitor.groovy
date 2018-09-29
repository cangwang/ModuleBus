package com.cangwang.generate.util


import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by cangwang on 2018/9/13.
 */
class ModuleBeanAnnotationVisitor extends ClassVisitor {
    private boolean needCopy
    private String[] interfaces
    private String classname

    private final static String MODULE_BEAN_ANNOTATION_BYTECODE = "Lcom/com/cangwang/annotation/ModuleBean;"
    private final static String MODULE_BEAN_INTERFACE_BYTECODE = "com/com/cangwang/core/bean/IModuleBean"

    ModuleBeanAnnotationVisitor(){
        super(Opcodes.ASM4)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(i, i1, s, s1, s2, strings)
        this.classname = name
        this.interfaces = interfaces
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (MODULE_BEAN_ANNOTATION_BYTECODE == desc || interfaces.contains(MODULE_BEAN_INTERFACE_BYTECODE)){
            needCopy= true
        }
        return super.visitAnnotation(s, b)
    }

    boolean isNeedCopy(){
        return isNeedCopy()
    }

    @Override
    void visitEnd() {
        super.visitEnd()
//        GenerateUtil.addCopyInfo(new CopyInfo(classname))
    }
}
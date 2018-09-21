package com.cangwang.generate

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor

/**
 * Created by cangwang on 2018/9/13.
 */
class ModuleAnnotationVisitor extends ClassVisitor{
    private final static String MODULE_BEAN_ANNOTATION_BYTECODE = "Lcom/cangwang/common/annotation/ModuleBean;"
    private final static String MODULE_BEAN_INTERFACE_BYTECODE = "Lcom/cangwang/core/bean/IModuleBean;"
    ModuleAnnotationVisitor(int i) {
        super(i)
    }

    ModuleAnnotationVisitor(int i, ClassVisitor classVisitor) {
        super(i, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        if (interfaces.contains(MODULE_BEAN_INTERFACE_BYTECODE)){

        }
    }

    @Override
    AnnotationVisitor visitAnnotation(String s, boolean b) {
        if (s == MODULE_BEAN_ANNOTATION_BYTECODE){

        }
        return super.visitAnnotation(s, b)
    }

}
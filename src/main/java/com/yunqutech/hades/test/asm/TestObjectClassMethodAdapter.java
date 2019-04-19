package com.yunqutech.hades.test.asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestObjectClassMethodAdapter extends MethodAdapter {
    public TestObjectClassMethodAdapter(MethodVisitor methodVisitor) {
        super(methodVisitor);
    }

    @Override
    public void visitCode() {
        System.out.println("do insert ");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("this is insert method");
        mv.visitTypeInsn(Opcodes.NEW, "com/yunqutech/hades/business/major/DefaultFileLog");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/yunqutech/hades/business/major/DefaultFileLog", "<init>", "()V");
        mv.visitVarInsn(Opcodes.ASTORE, 2);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/yunqutech/hades/business/major/PrintLog", "doPrintLog", "(Ljava/lang/String;)V");
        System.out.println("do insert finish");
    }
}

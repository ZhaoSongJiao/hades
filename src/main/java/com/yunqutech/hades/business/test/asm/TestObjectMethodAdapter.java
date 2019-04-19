package com.yunqutech.hades.business.test.asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TestObjectMethodAdapter extends MethodAdapter {
    public TestObjectMethodAdapter(MethodVisitor methodVisitor) {
        super(methodVisitor);
    }

    @Override
    public void visitCode() {
        mv.visitTypeInsn(Opcodes.NEW, "com/yunqutech/hades/business/major/EachSQLAFileLog");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/yunqutech/hades/business/major/EachSQLAFileLog", "<init>", "()V");
        mv.visitVarInsn(Opcodes.ASTORE, 2);
        mv.visitVarInsn(Opcodes.ALOAD, 2);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "com/yunqutech/hades/business/major/PrintLog", "doPrintLog", "(Ljava/lang/String;)V");
    }
}

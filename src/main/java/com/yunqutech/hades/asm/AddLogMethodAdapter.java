package com.yunqutech.hades.asm;


import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddLogMethodAdapter extends MethodAdapter {


    public AddLogMethodAdapter(MethodVisitor methodVisitor) {
        super(methodVisitor);
    }

    public void visitCode() {
               visitMethodInsn(Opcodes.INVOKESTATIC,"InsertLog","doLog","");
    }
}

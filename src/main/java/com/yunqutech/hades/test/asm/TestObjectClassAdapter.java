package com.yunqutech.hades.test.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class TestObjectClassAdapter extends ClassAdapter {
    public TestObjectClassAdapter(ClassVisitor classVisitor) {
        super(classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
//        System.out.println("name:" + name + " access:" + access + " desc:" + desc + " signature:" + signature);

        //这里进行控制
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (methodVisitor == null) {
            return methodVisitor;
        }
        if (!name.equals("testMethod")) {
            return methodVisitor;
        }
        if (!desc.equals("(Ljava/lang/String;)V")) {
            return methodVisitor;
        }
        System.out.println("do adapter");
        MethodVisitor wapperdMethodVisitor = new TestObjectClassMethodAdapter(methodVisitor);
        return wapperdMethodVisitor;
    }
}

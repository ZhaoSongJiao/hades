package com.yunqutech.hades.business.connection.asm;

import com.yunqutech.hades.test.asm.TestObjectClassMethodAdapter;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.HashSet;
import java.util.Set;

public class ConnectionClassAdapter extends ClassAdapter {

    public static Set<String> methodNames = new HashSet<>();

    static {
        methodNames.add("prepareStatement");
        methodNames.add("prepareCall");
        methodNames.add("nativeSQL");
    }

    public ConnectionClassAdapter(ClassVisitor classVisitor) {
        super(classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {

        //这里进行控制
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (methodVisitor == null) {
            return methodVisitor;
        }
        //不在监控序列中
        if (!methodNames.contains(name)) {
            return methodVisitor;
        }
        //        System.out.println("name:" + name + " access:" + access + " desc:" + desc + " signature:" + signature);
        //如果不是一个参数的
        if (!desc.equals("(Ljava/lang/String;)V")) {
            return methodVisitor;
        }
        MethodVisitor wapperdMethodVisitor = new TestObjectClassMethodAdapter(methodVisitor);
        return wapperdMethodVisitor;
    }
}

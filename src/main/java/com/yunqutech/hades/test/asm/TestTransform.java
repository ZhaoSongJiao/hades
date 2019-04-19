package com.yunqutech.hades.test.asm;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TestTransform implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        byte[] data = doTransform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        return data;
    }

    public byte[] doTransform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("className:" + className);
        try {
            if (className.contains("yunqutech")) {
                System.out.println("this is correct");
                String newName = new String(className.replaceAll("/", "."));
                if ("com.yunqutech.hades.test.TestObject".equals(newName)) {
                    System.out.println("this is our target object");
                    ClassReader reader = new ClassReader(classfileBuffer);
                    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    ClassAdapter adapter = new TestObjectClassAdapter(writer);
                    reader.accept(adapter, ClassReader.SKIP_DEBUG);
                    return writer.toByteArray();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return classfileBuffer;
    }

}

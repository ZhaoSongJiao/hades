package com.yunqutech.hades.test.asm;

import com.yunqutech.hades.test.TestObject;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import sun.swing.StringUIClientPropertyKey;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class TestObjectASMTransformer implements ClassFileTransformer {
    private String checkClassName;

    public TestObjectASMTransformer() {
        this.checkClassName = TestObject.class.getName().replaceAll("/", ".");
        System.out.println("check :" + checkClassName);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if (StringUtils.isBlank(className))
            return null;

        if (className.contains("yunqutech")) {
            System.out.println("class Name:" + className);
        } else {
            return null;
        }


        try {
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            System.out.println("do adapter");
            ClassAdapter adapter = new TestObjectClassAdapter(writer);
            reader.accept(adapter, ClassReader.SKIP_DEBUG);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}

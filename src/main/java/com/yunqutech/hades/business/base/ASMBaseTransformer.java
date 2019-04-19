package com.yunqutech.hades.business.base;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.List;

public class ASMBaseTransformer extends AbstractTransformer {
    @Override
    public void init() {

    }

    @Override
    public void doPreTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public void doAfterTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public byte[] doTransClass(String className, byte[] source) {
        ClassReader classReader = new ClassReader(source);
        ClassWriter writer = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.SKIP_DEBUG);
        List<MethodNode> nodes = classNode.methods;
        for(MethodNode method:nodes)
        {

        }

        return new byte[0];
    }


}

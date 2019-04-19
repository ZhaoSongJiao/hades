package com.yunqutech.hades.business.base;

import com.yunqutech.hades.business.JudgeClassAdapter;
import com.yunqutech.hades.test.asm.TestObjectClassAdapter;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.List;

public abstract class ASMBaseTransformer extends AbstractTransformer {

    public abstract ClassAdapter getExecuteAdapter(ClassWriter writer);


    @Override
    public void doPreTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public void doAfterTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public byte[] doTransClass(String className, byte[] source) {
        ClassReader judgeReader = new ClassReader(source);
        ClassWriter judgeWriter = new ClassWriter(judgeReader, ClassWriter.COMPUTE_MAXS);
        ClassAdapter judgeAdapter = new JudgeClassAdapter(judgeWriter);
        judgeReader.accept(judgeAdapter, ClassReader.SKIP_DEBUG);
        String[] interfaces = judgeReader.getInterfaces();
        for (String inter : interfaces) {
            if (inter.replace("/", ".").equals(watchingClassName)) {
                ClassReader doReader = new ClassReader(source);
                ClassWriter doWriter = new ClassWriter(doReader, ClassWriter.COMPUTE_MAXS);
                ClassAdapter doAdapter = this.getExecuteAdapter(doWriter);
                doReader.accept(doAdapter, ClassReader.SKIP_DEBUG);
                return doWriter.toByteArray();
            }
        }
        return source;
    }


}

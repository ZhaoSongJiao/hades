package com.yunqutech.hades.business.test.asm;

import com.yunqutech.hades.business.base.ASMBaseTransformer;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassWriter;

public class TestObjectASMTransformer extends ASMBaseTransformer {
    @Override
    public ClassAdapter getExecuteAdapter(ClassWriter writer) {
        return new TestObjectClassAdapter(writer);
    }

    @Override
    public void init() {
        this.watchingClassName = "com.yunqutech.hades.test.TestInterface";
    }
}

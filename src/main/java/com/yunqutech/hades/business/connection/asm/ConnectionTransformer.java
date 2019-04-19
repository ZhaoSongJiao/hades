package com.yunqutech.hades.business.connection.asm;

import com.yunqutech.hades.business.base.ASMBaseTransformer;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassWriter;

public class ConnectionTransformer extends ASMBaseTransformer {
    @Override
    public ClassAdapter getExecuteAdapter(ClassWriter writer) {
        return new ConnectionClassAdapter(writer);
    }

    @Override
    public void init() {
        this.watchingClassName = "java.sql.Connection";
    }
}

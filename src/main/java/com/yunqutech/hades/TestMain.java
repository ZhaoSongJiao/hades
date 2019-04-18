package com.yunqutech.hades;


import com.yunqutech.hades.asm.AddLogAdapter;
import com.yunqutech.hades.test.TestObject;
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;

public class TestMain {
    public static void main(String[] args) throws Exception {
        ClassReader reader = new ClassReader(TestObject.class.getName());
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassAdapter adapter = new AddLogAdapter(writer);
        reader.accept(adapter, ClassReader.SKIP_DEBUG);
        byte[] data = writer.toByteArray();
        File file = new File("TestObject$Proxy.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }
}

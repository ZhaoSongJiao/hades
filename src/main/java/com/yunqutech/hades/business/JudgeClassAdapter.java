package com.yunqutech.hades.business;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;

public class JudgeClassAdapter extends ClassAdapter {
    public JudgeClassAdapter(ClassVisitor classVisitor) {
        super(classVisitor);
    }
}

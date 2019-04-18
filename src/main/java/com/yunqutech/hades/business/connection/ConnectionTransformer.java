package com.yunqutech.hades.business.connection;

import javassist.CtClass;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class ConnectionTransformer implements ClassFileTransformer {

    private static Set<String> connectionInterfaceList = new HashSet<>();

    static {
        connectionInterfaceList.add(Connection.class.getName());
    }

    private boolean isInWatch(CtClass[] classes) {
        if (connectionInterfaceList.size() == 0)
            return false;

        for (CtClass clazz : classes) {
            if (connectionInterfaceList.contains(clazz.getName()))
                return true;
        }
        return false;
    }



    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return new byte[0];
    }
}

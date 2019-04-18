package com.yunqutech.hades.instrument.doInstrument;

import com.yunqutech.hades.test.TestInterface;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import org.apache.commons.lang3.StringUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

public class Transformer implements ClassFileTransformer {

    private static Set<String> interFaceList = new HashSet<>();

    static {
        interFaceList.add(TestInterface.class.getName());
    }

    private boolean isInWatch(CtClass[] classes) {
        for (int i = 0; i < classes.length; i++) {
            if (interFaceList.contains(classes[i].getName()))
                return true;
        }
        return false;
    }

    private byte[] doTransClass(String className, byte[] classfileBuffer) {
        try {
            if (StringUtils.isBlank(className))
                return null;
            String currentClassName = className.replaceAll("/", ".");
            CtClass currentClass = ClassPool.getDefault().get(currentClassName);
            CtClass[] interfaces = currentClass.getInterfaces();
            if (!isInWatch(interfaces)) {
                return null;
            }
            //引入需要使用的class对应的包
            ClassPool.getDefault().importPackage("com.yunqutech.hades.bussiness");
            CtBehavior[] methods = currentClass.getMethods();
            for (CtBehavior method : methods) {
                String methodName = method.getName();
                if ("testMethod".equals(methodName)) {
                    CtClass[] paramsType = method.getParameterTypes();
                    for (CtClass type : paramsType) {
                        String typeName = type.getName();
                        System.out.println("param type:" + typeName);
                        if ((String.class.getName().replaceAll("/", ".")).equals(typeName)) {
                            System.out.println(" this is correct ");

                            //静态类进行设置编码
                            method.insertAt(0, " InsertLog.doLog($1);");
                            break;
                        }
                    }
                }
                //finish method
            }
            return currentClass.toBytecode();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("doTransFormClass:" + className);
        return this.doTransClass(className, classfileBuffer);
    }
}

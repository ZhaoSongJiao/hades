package com.yunqutech.hades.business.base;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.security.ProtectionDomain;
import java.util.Objects;

public class BaseTransformer extends AbstractTransformer {
    @Override
    public void doPreTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public void doAfterTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public byte[] doTransClass(String className) {
        if (isNotWatchClass(className)) {
            return null;
        }
        try {
            ClassPool.getDefault().importPackage(this.getPackageName());
            CtClass ctClass = this.getWatchingClass();
            CtMethod[] methods = ctClass.getMethods();
            for (CtMethod method : methods) {
                //如果是监听的对象的话执行某个操作
                if (this.getWatchingMethodList().contains(method.getName())) {
                    realDoing(method);
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public void realDoing(CtMethod method) {
        try {
            CtClass[] paramsType = method.getParameterTypes();
            for (CtClass type : paramsType) {
                String typeName = type.getName();
                //       System.out.println("param type:" + typeName);
                if ((String.class.getName().replaceAll("/", ".")).equals(typeName)) {
                    //         System.out.println(" this is correct ");
                    //静态类进行设置编码
                    //todo change the thing that the log real instance
                    method.insertAt(0, " EachSQLAFileLog.doStaticPrintLog($1);");
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

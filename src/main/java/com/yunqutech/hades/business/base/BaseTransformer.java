package com.yunqutech.hades.business.base;

import com.yunqutech.hades.business.major.EachSQLAFileLog;
import com.yunqutech.hades.business.major.PrintLog;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Objects;

public class BaseTransformer extends AbstractTransformer {
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
    public byte[] doTransClass(String className) {
        if (isNotWatchClass(className)) {
            return null;
        }
        className = className.replaceAll("/", ".");
        try {
            ClassPool.getDefault().importPackage(this.getPackageName());
            CtClass ctClass = ClassPool.getDefault().get(className);
            CtMethod[] methods = ctClass.getMethods();
            for (CtMethod method : methods) {
                //如果是监听的对象的话执行某个操作
                if (this.getWatchingMethodList().contains(method.getName())) {
                    realDoing(method);
                }
            }
            return ctClass.toBytecode();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void realDoing(CtMethod method) {
        try {
            CtClass[] paramsType = method.getParameterTypes();
            for (CtClass type : paramsType) {
                String typeName = type.getName();
                if ((String.class.getName().replaceAll("/", ".")).equals(typeName)) {
                    //   System.out.println(" this is correct method:" + method.getName() + " param Type:" + typeName);
                    PrintLog log = this.getPrintLog();
                    String className = log.getClass().getSimpleName();
                    String insertData = className + " print = new " + className + "(); print.doPrintLog($1);";
                    //静态类进行设置编码 //"EachSQLAFileLog.doStaticPrintLog($1);"
                    method.insertAt(0, insertData);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}

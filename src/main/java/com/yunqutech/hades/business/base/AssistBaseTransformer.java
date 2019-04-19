package com.yunqutech.hades.business.base;

import com.yunqutech.hades.business.major.EachSQLAFileLog;
import com.yunqutech.hades.business.major.PrintLog;
import javassist.*;
import org.apache.commons.lang3.StringUtils;

import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Objects;

public class AssistBaseTransformer extends AbstractTransformer {
    @Override
    public void init() {

    }

    @Override
    public void doPreTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        System.out.println("class Name:" + className);
    }

    @Override
    public void doAfterTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {

    }

    @Override
    public byte[] doTransClass(String className, byte[] source) {
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
            byte[] data = ctClass.toBytecode();
            ctClass.detach();
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

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
                    insertData += "System.out.println(\"-----------------------------------------------------------------\");";
                    //静态类进行设置编码 //"EachSQLAFileLog.doStaticPrintLog($1);"
                    method.insertAt(0, insertData);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * is our watching class
     *
     * @param className
     * @return
     */
    public boolean isWatchingClass(String className) {
        if (StringUtils.isBlank(this.getWatchingClassName())) {
            return false;
        }
        if (StringUtils.isBlank(className)) {
            return false;
        }
        className = className.replaceAll("/", ".");
        try {
            CtClass currentClass = getCtClassByName(className);
            if (currentClass == null) {
                return false;
            }
            CtClass[] interfaces = currentClass.getInterfaces();
            for (CtClass inter : interfaces) {
                String interName = inter.getName();
                if (interName.equals(this.getWatchingClassName())) {
                    System.out.println("---->realClassName:" + currentClass.getName());
                    currentClass.detach();
                    return true;
                }
            }
            currentClass.detach();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private CtClass getCtClassByName(String className) {
        try {
            return ClassPool.getDefault().get(className);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * is not our watching class
     *
     * @param className
     * @return
     */
    public boolean isNotWatchClass(String className) {
        return !isWatchingClass(className);
    }


    public boolean addNewMethodName(String methodName) {

        CtClass ctClass = null;
        try {
            ctClass = ClassPool.getDefault().get(this.watchingClassName);
        } catch (Exception ex) {
            return false;
        }
        if (ctClass == null) {
            return false;
        }

        CtMethod[] methods = ctClass.getMethods();
        if (methods == null || methods.length == 0)
            return false;

        for (int i = 0; i < methods.length; i++) {
            CtMethod method = methods[i];
            if (method.getName().equals(methodName)) {
                System.out.println("watching------>class:" + this.getWatchingClassName() + "\tmethod:" + method.getName());
                ctClass.detach();
                return this.getWatchingMethodList().add(methodName);
            }
        }
        ctClass.detach();
        return false;
    }


}

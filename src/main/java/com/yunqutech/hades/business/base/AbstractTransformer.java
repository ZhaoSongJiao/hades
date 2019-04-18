package com.yunqutech.hades.business.base;

import com.yunqutech.hades.business.major.DefaultFileLog;
import com.yunqutech.hades.business.major.PrintLog;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang3.StringUtils;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ZhaoSongJiao
 */
public abstract class AbstractTransformer implements ClassFileTransformer {


    private String watchingClassName;
    /**
     * 被监听的类
     */
    private CtClass watchingClass;

    /**
     * 被监听的类型
     */
    private Set<String> watchingMethodList;

    private PrintLog printLog;

    /**
     * 日志的包名称
     */
    private String packageName;

    public AbstractTransformer() {
        printLog = new DefaultFileLog();
        watchingMethodList = new HashSet<>();
        this.putPackageName(printLog);
        init();
    }

    public AbstractTransformer(PrintLog printLog) {
        this.printLog = printLog;
        watchingMethodList = new HashSet<>();
        this.putPackageName(printLog);
        init();
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
            CtClass[] interfaces = currentClass.getInterfaces();
            for (CtClass inter : interfaces) {
                String interName = inter.getName();
                if (interName.equals(this.getWatchingClassName())) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
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

    public abstract void init();

    /**
     * when you want pre do something please finish this work
     *
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     */
    public abstract void doPreTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer);

    /**
     * when you have finish the transwork what will you do
     *
     * @param loader              classLoader
     * @param className           the class Name
     * @param classBeingRedefined have definedClass
     * @param protectionDomain    domain
     * @param classfileBuffer     byte
     */
    public abstract void doAfterTrans(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer);


    public abstract byte[] doTransClass(String className);

    /**
     * when you find your need Method  this is what will you do
     *
     * @param method the method you have found
     */
    public abstract void realDoing(CtMethod method);

    /**
     * this is the method from the
     * interface ClassFileTransformer
     *
     * @param loader
     * @param className
     * @param classBeingRedefined
     * @param protectionDomain
     * @param classfileBuffer
     * @return
     * @throws IllegalClassFormatException
     */
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        this.doPreTrans(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        byte[] data = this.doTransClass(className);
        this.doAfterTrans(loader, className, classBeingRedefined, protectionDomain, data);
        return data;
    }


    private CtClass getCtClassByName(String className) throws Exception {
        return ClassPool.getDefault().get(className);
    }

    private void putPackageName(PrintLog printLog) {
        packageName = printLog.getClass().getPackage().getName().replaceAll("/", ".");
    }

    public String getWatchingClassName() {
        return watchingClassName;
    }

    public void setWatchingClassName(String watchingClassName) throws Exception {
        this.watchingClass = this.getCtClassByName(watchingClassName);
        this.watchingClassName = watchingClassName;
    }


    public Set<String> getWatchingMethodList() {
        return watchingMethodList;
    }

    public boolean addNewMethodName(String methodName) {
        if (this.watchingClass == null) {
            throw new RuntimeException("please set WatchingClass first");
        }

        CtMethod[] methods = this.watchingClass.getMethods();
        if (methods == null || methods.length == 0)
            return false;

        for (int i = 0; i < methods.length; i++) {
            CtMethod method = methods[i];
            if (method.getName().equals(methodName)) {
                System.out.println("watching------>class:" + this.getWatchingClassName() + "\tmethod:" + method.getName());

                return watchingMethodList.add(methodName);

            }
        }
        return false;
    }

    protected String getPackageName() {
        if (StringUtils.isNotBlank(packageName)) {
            return packageName.replaceAll("/", ".");
        }
        return "";
    }

    protected CtClass getWatchingClass() {
        return this.watchingClass;
    }

    protected PrintLog getPrintLog() {
        return printLog;
    }
}

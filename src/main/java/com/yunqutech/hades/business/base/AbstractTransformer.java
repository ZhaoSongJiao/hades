package com.yunqutech.hades.business.base;

import com.yunqutech.hades.business.major.DefaultFileLog;
import com.yunqutech.hades.business.major.PrintLog;
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


    protected String watchingClassName;

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


    public abstract byte[] doTransClass(String className,byte[] classBinarySource);


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
        byte[] data = this.doTransClass(className,classfileBuffer);
        this.doAfterTrans(loader, className, classBeingRedefined, protectionDomain, data);
        return data;
    }



    private void putPackageName(PrintLog printLog) {
        packageName = printLog.getClass().getPackage().getName().replaceAll("/", ".");
    }

    public String getWatchingClassName() {
        return watchingClassName;
    }

    public void putWatchingClassName(String watchingClassName) {
        this.watchingClassName = watchingClassName;
    }


    public Set<String> getWatchingMethodList() {
        return watchingMethodList;
    }



    protected String getPackageName() {
        if (StringUtils.isNotBlank(packageName)) {
            return packageName.replaceAll("/", ".");
        }
        return "";
    }

    protected PrintLog getPrintLog() {
        return printLog;
    }
}

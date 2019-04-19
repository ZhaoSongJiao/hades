package com.yunqutech.hades.test;

import com.yunqutech.hades.business.major.DefaultFileLog;
import com.yunqutech.hades.business.major.PrintLog;

public class TestObject implements TestInterface {
    public TestObject() {
        System.out.println("doing implemention");
    }

    @Override
    public void testMethod() {
        System.out.println("empty method");

        System.out.println("empty method end");
    }

    @Override
    public void testMethod(String name) {
        System.out.println("String method");
        System.out.println(name);


        System.out.println(name);

        System.out.println("String method end");
    }

    public void testMethod1(String name) {
        System.out.println("String method1");
        System.out.println(name);

        PrintLog printLog = new DefaultFileLog();
        printLog.doPrintLog(name);
        System.out.println(name);
        System.out.println("String method2 end");
    }

    @Override
    public void testMethod(int name) {
        System.out.println("int method");

        System.out.println("int method end");
    }

    @Override
    public void testMethod(float name) {
        System.out.println("float method");

        System.out.println("float method end");
    }

    @Override
    public void testMethod(String name, String desc) {
        System.out.println("String,String method");

        System.out.println("String,String method end");
    }
}

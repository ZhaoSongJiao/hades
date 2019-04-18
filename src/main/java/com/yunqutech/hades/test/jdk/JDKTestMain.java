package com.yunqutech.hades.test.jdk;

import com.yunqutech.hades.test.TestInterface;
import com.yunqutech.hades.test.TestObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JDKTestMain {
    public static void main(String[] args) {
        TestInterface object = new TestObject();
        InvocationHandler handler = new DynamicTestObjectProxy(object);
        TestInterface subject = (TestInterface) Proxy.newProxyInstance(handler.getClass().getClassLoader(), object.getClass().getInterfaces(), handler);
        System.out.println(subject.getClass().getName());
        subject.testMethod();
        subject.testMethod("name");
    }
}

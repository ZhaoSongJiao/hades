package com.yunqutech.hades.jdk;

import com.yunqutech.hades.test.TestInterface;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DynamicTestObjectProxy implements InvocationHandler {

    private TestInterface obj;

    public DynamicTestObjectProxy(TestInterface object) {
        this.obj = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("--------------------------------------------------------");
        System.out.println("before do invoke");
        System.out.println("method :" + method);

        Parameter[] params = method.getParameters();
        if (null == params || params.length == 0) {
            System.out.println("No parameter");
            System.out.println("--------------------------------------------------------\n\n");
            return null;
        }
        for (int i = 0; i < params.length; i++) {
            Parameter param = params[i];
            System.out.println("\t\t|param:" + param.getName());
            System.out.println("\t\t\t|->type:"+param.getType());
            System.out.println("\t\t\t|->annotations:"+param.getAnnotations());
            System.out.println("\t\t\t|->value");
        }

        method.invoke(obj, args);

        System.out.println("after do invoke");
        System.out.println("--------------------------------------------------------\n\n");

        return null;
    }
}

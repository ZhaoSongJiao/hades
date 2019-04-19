package com.yunqutech.hades.test;

public class TestInterfaceImpl implements TestInterface {
    @Override
    public void testMethod() {
        System.out.println("IMPL:");

    }

    @Override
    public void testMethod(String name) {
        System.out.println("IMPL:" + name);
    }

    @Override
    public void testMethod(int name) {
        System.out.println("IMPL:" + name);

    }

    @Override
    public void testMethod(float name) {
        System.out.println("IMPL:" + name);

    }

    @Override
    public void testMethod(String name, String desc) {
        System.out.println("IMPL:" + name);

    }
}

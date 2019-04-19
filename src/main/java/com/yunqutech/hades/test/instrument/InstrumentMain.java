package com.yunqutech.hades.test.instrument;

import com.yunqutech.hades.test.TestObject;

public class InstrumentMain {
    public static void main(String[] args) {
        TestObject object = new TestObject();
        System.out.println("------------------------------");
        object.testMethod("jzs");
        System.out.println("------------------------------");
        object.testMethod(1);
        object.testMethod(1.0f);
        System.out.println("------------------------------");
        object.testMethod("jzs", "desc");
        System.out.println("------------------------------");
        object.testMethod("zj test desc");

    }
}

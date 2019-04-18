package com.yunqutech.hades.instrument;

import com.yunqutech.hades.test.TestObject;

public class InstrumentMain {
    public static void main(String[] args) {
        TestObject object = new TestObject();
        object.testMethod("jzs");
        object.testMethod(1);
        object.testMethod(1.0f);
        object.testMethod("jzs", "desc");
    }
}

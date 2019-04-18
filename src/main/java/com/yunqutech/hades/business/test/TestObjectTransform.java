package com.yunqutech.hades.business.test;

import com.yunqutech.hades.business.base.BaseTransformer;
import com.yunqutech.hades.test.TestInterface;

public class TestObjectTransform extends BaseTransformer {
    @Override
    public void init() {
        super.init();
        try {
            this.putWatchingClassName(TestInterface.class.getName());
            this.addNewMethodName("testMethod");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

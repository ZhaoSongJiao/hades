package com.yunqutech.hades.test.instrument;

import com.yunqutech.hades.business.connection.ConnectionTransformer;
import com.yunqutech.hades.business.test.TestObjectTransform;
import com.yunqutech.hades.test.instrument.doInstrument.Transformer;

import java.lang.instrument.Instrumentation;

public class InstrumentPreMain extends Object {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("instrumentPreMain is calling");
        //inst.addTransformer(new Transformer());
        TestObjectTransform testTransform = new TestObjectTransform();
        inst.addTransformer(testTransform);
        ConnectionTransformer connectionTransformer = new ConnectionTransformer();
        inst.addTransformer(connectionTransformer);
//        inst.removeTransformer(connectionTransformer);
//        inst.removeTransformer(testTransform);

    }
}

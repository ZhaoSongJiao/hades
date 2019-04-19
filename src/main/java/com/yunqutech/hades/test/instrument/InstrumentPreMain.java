package com.yunqutech.hades.test.instrument;

import com.yunqutech.hades.business.test.TestObjectTransform;
import com.yunqutech.hades.test.asm.TestObjectASMTransformer;
import com.yunqutech.hades.test.instrument.doInstrument.Transformer;

import java.lang.instrument.Instrumentation;

public class InstrumentPreMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("instrumentPreMain is calling");

        TestObjectTransform testTransform = new TestObjectTransform();
        inst.addTransformer(testTransform);

        //        inst.addTransformer(new TestObjectASMTransformer());

//        inst.addTransformer(new Transformer());
//        inst.addTransformer(new Transformer());
//        ConnectionTransformerAssist connectionTransformer = new ConnectionTransformerAssist();
//        inst.addTransformer(connectionTransformer);


    }
}

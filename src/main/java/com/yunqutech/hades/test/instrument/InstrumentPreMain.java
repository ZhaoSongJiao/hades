package com.yunqutech.hades.test.instrument;

import com.yunqutech.hades.business.connection.ConnectionTransformerAssist;
import com.yunqutech.hades.business.test.TestObjectTransform;
import com.yunqutech.hades.business.test.asm.TestObjectASMTransformer;
import com.yunqutech.hades.test.asm.TestTransform;
import com.yunqutech.hades.test.instrument.doInstrument.Transformer;

import java.lang.instrument.Instrumentation;

public class InstrumentPreMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        //千万不要在preMain中使用反射会导致不平衡的
        System.out.println("instrumentPreMain is calling");
//        inst.addTransformer(new ConnectionTransformerAssist());
//        inst.addTransformer(new TestTransform());
        inst.addTransformer(new TestObjectASMTransformer());
    }
}

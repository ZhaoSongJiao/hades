package com.yunqutech.hades.instrument;

import com.yunqutech.hades.instrument.doInstrument.Transformer;
import java.lang.instrument.Instrumentation;
public class InstrumentPreMain extends Object {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("instrumentPreMain is calling");
        inst.addTransformer(new Transformer());
    }
}

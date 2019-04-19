package com.yunqutech.hades.business.statement;

import com.yunqutech.hades.business.base.AssistBaseTransformer;

import java.sql.CallableStatement;

public class PreparedStatementTransformerAssist extends AssistBaseTransformer {
    @Override
    public void init() {
        super.init();
        try {
            this.putWatchingClassName(CallableStatement.class.getName());
            this.addNewMethodName("prepareStatement");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

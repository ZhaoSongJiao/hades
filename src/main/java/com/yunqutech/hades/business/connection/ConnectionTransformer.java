package com.yunqutech.hades.business.connection;


import com.yunqutech.hades.business.base.BaseTransformer;

import java.sql.Connection;

public class ConnectionTransformer extends BaseTransformer {


    @Override
    public void init() {
        super.init();
        try {
            this.putWatchingClassName(Connection.class.getName());
            this.addNewMethodName("prepareStatement");
            this.addNewMethodName("prepareCall");
            this.addNewMethodName("nativeSQL");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

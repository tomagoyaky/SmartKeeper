package com.tomagoyaky.smart.keeper.handler;

import com.tomagoyaky.smart.keeper.SmartApplication;
import com.tomagoyaky.smart.keeper.SmartContext;

/**
 * @author tomagoyaky
 * @date 2018/3/6
 */

public abstract class AbstractHandler {

    protected SmartContext smartContext;
    public AbstractHandler(SmartContext smartContext){
        this.smartContext = smartContext;
    }
    public abstract void register(SmartApplication smartApplication);
}

package com.tomagoyaky.smart.keeper.handler;

import com.tomagoyaky.smart.keeper.SmartApplication;
import com.tomagoyaky.smart.keeper.SmartContext;

/**
 * Created by admin on 2018/3/6.
 */

public class RunnableHandler extends AbstractHandler {

    private static RunnableHandler runnableHandler;
    private Thread thread;
    public RunnableHandler(SmartContext smartContext) {
        super(smartContext);
    }

    public static RunnableHandler INSTANCE(SmartContext smartContext) {
        if(runnableHandler == null){
            runnableHandler = new RunnableHandler(smartContext);
        }
        return runnableHandler;
    }

    @Override
    public void register(SmartApplication smartApplication) {
    }

    public void execute(Runnable runnable){
        thread = new Thread(runnable);
        thread.start();
    }
}

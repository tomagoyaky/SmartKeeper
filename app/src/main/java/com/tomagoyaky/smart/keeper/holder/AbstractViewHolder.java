package com.tomagoyaky.smart.keeper.holder;

import android.app.Activity;

/**
 * @author tomagoyaky
 * @date 2018/3/6
 */

public abstract class AbstractViewHolder {

    private Activity activity;
    public AbstractViewHolder(Activity activity){
        this.activity = activity;
    }
}

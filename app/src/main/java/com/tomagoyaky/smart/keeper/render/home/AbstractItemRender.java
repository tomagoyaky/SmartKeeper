package com.tomagoyaky.smart.keeper.render.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.SmartContext;

/**
 * Created by admin on 2018/3/6.
 */

public abstract class AbstractItemRender {

    public SmartContext smartContext;
    public Activity activity;

    public AbstractItemRender(Activity activity, SmartContext smartContext){
        this.activity = activity;
        this.smartContext = smartContext;
    }
}

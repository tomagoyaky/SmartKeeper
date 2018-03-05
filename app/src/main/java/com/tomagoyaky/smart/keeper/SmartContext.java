package com.tomagoyaky.smart.keeper;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Typeface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tomagoyaky.smart.keeper.util.AssetsUtil;

/**
 * @author tomagoyaky
 * @date 2018/3/5
 */

public class SmartContext extends ContextWrapper {

    public final Typeface tfRegular;
    public final Typeface tfLight;
    public Context context;

    public JSONObject homeitemJson;

    public SmartContext(Context base) throws Exception{
        super(base);
        context = base;

        /**
         * 字体文件初始化
         * */
        tfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        /**
         *
         * */
        String homeitemJson = AssetsUtil.getAssetsJson(context, "homeitem.json");
        this.homeitemJson = JSON.parseObject(homeitemJson);
    }
}

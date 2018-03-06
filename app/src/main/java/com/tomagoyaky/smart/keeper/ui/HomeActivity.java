package com.tomagoyaky.smart.keeper.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.render.HomeViewRender;

/**
 * @author tomagoyaky
 */
public class HomeActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        new HomeViewRender(this);
    }
}

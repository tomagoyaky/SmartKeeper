package com.tomagoyaky.smart.keeper.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.holder.HomeViewHolder;

/**
 * @author tomagoyaky
 */
public class HomeActivity extends Activity {

    private Context context;
    private HomeViewHolder homeViewHolder;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        homeViewHolder = new HomeViewHolder(this);
    }
}

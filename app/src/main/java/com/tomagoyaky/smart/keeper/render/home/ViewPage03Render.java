package com.tomagoyaky.smart.keeper.render.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomagoyaky.smart.keeper.Logger;
import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.SmartApplication;
import com.tomagoyaky.smart.keeper.SmartContext;

/**
 * Created by admin on 2018/3/6.
 */

public class ViewPage03Render extends AbstractItemRender{

    private static ViewPage03Render viewPage03Render;
    public ViewPage03Render(Activity activity, SmartContext smartContext) {
        super(activity, smartContext);
    }

    public static ViewPage03Render INSTANCE(Activity activity, SmartContext smartContext) {
        if(viewPage03Render == null){
            viewPage03Render = new ViewPage03Render(activity, smartContext);
        }
        return viewPage03Render;
    }

    public View rendering(ViewGroup container) {
        View view = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.item_vp_list, null, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecycleAdapter(activity.getBaseContext()));
        container.addView(view);
        return view;
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        private JSONArray data;
        private Context context;
        public RecycleAdapter(Context context) {
            this.context = context;
            this.data = SmartApplication.getSmartContext().homeitemJson.getJSONArray("homeItems");
        }

        @Override
        public RecycleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            return new RecycleAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecycleAdapter.ViewHolder holder, final int position) {

            final String content = ((JSONObject)data.get(position)).getString("name");
            holder.txt.setText(content);
            holder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "" + SmartApplication.get().smartService.count, Toast.LENGTH_SHORT).show();
                    Logger.err(">>>> count=" + SmartApplication.get().smartService.count);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView txt;
            public ViewHolder(final View itemView) {
                super(itemView);
                txt = (TextView) itemView.findViewById(R.id.txt_vp_item_list);
            }
        }
    }
}

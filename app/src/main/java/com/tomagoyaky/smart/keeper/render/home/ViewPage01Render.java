package com.tomagoyaky.smart.keeper.render.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.SmartContext;
import com.tomagoyaky.smart.keeper.render.home.charts.ChartFileSystemRender;

/**
 * Created by admin on 2018/3/6.
 */

public class ViewPage01Render extends AbstractItemRender{

    private static ViewPage01Render viewPage01Render;
    public ViewPage01Render(Activity activity, SmartContext smartContext) {
        super(activity, smartContext);
    }

    public static ViewPage01Render INSTANCE(Activity activity, SmartContext smartContext) {
        if(viewPage01Render == null){
            viewPage01Render = new ViewPage01Render(activity, smartContext);
        }
        return viewPage01Render;
    }

    public View rendering(ViewGroup container) {
        View view = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.tab_01_chart, null, false);

        /**
         * also make a 'RecyclerView' adapt to the 'AppBarLayout' widget
         * */
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_tab_01_chart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecycleAdapter(activity.getBaseContext()));
        container.addView(view);
        return view;
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        private Context context;
        public RecycleAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecycleAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
            final View view = LayoutInflater.from(context).inflate(R.layout.tab_01_chart_item, viewGroup, false);
            return new RecycleAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecycleAdapter.ViewHolder holder, final int position) {

            /**
             * 0x01 add piechart for fileSystem
             * */
            final PieChart piechart_filesystem = (PieChart) holder.linearLayout.findViewById(R.id.piechart_filesystem);
            ChartFileSystemRender.INSTANCE(smartContext).rendering(piechart_filesystem);

            /**
             * 0x02 add piechart for fileSystem
             * */

        }

        @Override
        public int getItemCount() {
            return 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout linearLayout;
            public ViewHolder(final View itemView) {
                super(itemView);
                this.linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_chart_item);
            }
        }
    }
}

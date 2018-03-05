package com.tomagoyaky.smart.keeper.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.SmartApplication;

import java.util.ArrayList;
import java.util.Random;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * @author tomagoyaky
 * @date 2018/3/6
 */

public class HomeViewHolder extends AbstractViewHolder {

    private ViewPager viewPager;
    private NavigationTabBar navigationTabBar;
    private CoordinatorLayout coordinatorLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;

    private final TabModel[] modelArray = new TabModel[]{
            new TabModel("Heart", R.drawable.ic_first),
            new TabModel("Cup", R.drawable.ic_second),
            new TabModel("Diploma", R.drawable.ic_third),
            new TabModel("Flag", R.drawable.ic_fourth),
            new TabModel("Medal", R.drawable.ic_fifth)
    };

    public HomeViewHolder(Activity activity) {
        super(activity);
        this.viewPager = (ViewPager) activity.findViewById(R.id.vp_horizontal_ntb);
        this.navigationTabBar = (NavigationTabBar) activity.findViewById(R.id.ntb_horizontal);
        this.coordinatorLayout = (CoordinatorLayout) activity.findViewById(R.id.parent);
        this.collapsingToolbarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar);
        this.floatingActionButton = (FloatingActionButton) activity.findViewById(R.id.fab_convenient);

        this.viewPager.setAdapter(new ViewPagerAdapter(activity));
        this.initTabBar(activity);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final String title = String.valueOf(new Random().nextInt(100));
                            if (!model.isBadgeShowed()) {
                                model.setBadgeTitle(title);
                                model.showBadge();
                            } else {
                                model.updateBadgeTitle(title);
                            }
                        }
                    }, i * 100);
                }

                coordinatorLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Snackbar snackbar = Snackbar.make(navigationTabBar, "Coordinator NTB", Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(Color.parseColor("#9b92b3"));
                        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text))
                                .setTextColor(Color.parseColor("#423752"));
                        snackbar.show();
                    }
                }, 1000);
            }
        });

        this.collapsingToolbarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#009F90AF"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#9f90af"));
    }

    private void initTabBar(Context context) {
        final String[] colors = context.getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        for (int i = 0; i < modelArray.length; i++) {
            TabModel model = modelArray[i];
            models.add(new NavigationTabBar.Model.Builder(
                    context.getResources().getDrawable(model.drawableID),
                    Color.parseColor(colors[i]))
                    .title(model.name)
                    .build()
            );
        }

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    private class ViewPagerAdapter extends PagerAdapter {

        private Activity activity;
        public ViewPagerAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return modelArray.length;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(final View container, final int position, final Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            View view = null;
            switch (position) {
                case 0:
                    view = LayoutInflater.from(activity.getBaseContext()).inflate(R.layout.item_vp_list, null, false);
                    final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(activity.getBaseContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(new RecycleAdapter(activity.getBaseContext()));
                    container.addView(view);
                    break;
                case 1:
                    break;
                case 2:

                    break;
                default:
                    break;
            }
            return view;
        }
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
                    Toast.makeText(context, "" + data.get(position), Toast.LENGTH_SHORT).show();
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

    private class TabModel {
        public String name;
        public int drawableID;
        public TabModel(String name, int id) {
            this.name = name;
            this.drawableID = id;
        }
    }
}

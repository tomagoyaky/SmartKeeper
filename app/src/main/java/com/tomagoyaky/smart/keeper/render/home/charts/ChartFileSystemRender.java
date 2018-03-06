package com.tomagoyaky.smart.keeper.render.home.charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.tomagoyaky.smart.keeper.R;
import com.tomagoyaky.smart.keeper.SmartContext;

import java.util.ArrayList;

/**
 * Created by admin on 2018/3/6.
 */

public class ChartFileSystemRender {

    private static ChartFileSystemRender chartFileSystemRender;
    private SmartContext smartContext;
    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    public ChartFileSystemRender(SmartContext smartContext) {
        this.smartContext = smartContext;
    }

    public static ChartFileSystemRender INSTANCE(SmartContext smartContext){
        if(chartFileSystemRender == null){
            chartFileSystemRender = new ChartFileSystemRender(smartContext);
        }
        return chartFileSystemRender;
    }

    public void rendering(PieChart piechart_filesystem) {
        piechart_filesystem.setUsePercentValues(true);
        piechart_filesystem.getDescription().setEnabled(false);
        piechart_filesystem.setExtraOffsets(5, 10, 5, 5);

        piechart_filesystem.setDragDecelerationFrictionCoef(0.95f);

        piechart_filesystem.setCenterTextTypeface(smartContext.tfLight);
        piechart_filesystem.setCenterText(generateCenterSpannableText());

        piechart_filesystem.setDrawHoleEnabled(true);
        piechart_filesystem.setHoleColor(Color.WHITE);

        piechart_filesystem.setTransparentCircleColor(Color.WHITE);
        piechart_filesystem.setTransparentCircleAlpha(110);

        piechart_filesystem.setHoleRadius(58f);
        piechart_filesystem.setTransparentCircleRadius(61f);

        piechart_filesystem.setDrawCenterText(true);

        piechart_filesystem.setRotationAngle(0);
        // enable rotation of the chart by touch
        piechart_filesystem.setRotationEnabled(true);
        piechart_filesystem.setHighlightPerTapEnabled(true);

        // piechart_filesystem.setUnit(" €");
        // piechart_filesystem.setDrawUnitsInChart(true);

        setData(piechart_filesystem,4, 100);

        piechart_filesystem.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // piechart_filesystem.spin(2000, 0, 360);

        Legend l = piechart_filesystem.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        piechart_filesystem.setEntryLabelColor(Color.WHITE);
        piechart_filesystem.setEntryLabelTypeface(smartContext.tfRegular);
        piechart_filesystem.setEntryLabelTextSize(12f);
    }

    private void setData(PieChart piechart_filesystem, int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    smartContext.getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(smartContext.tfLight);
        piechart_filesystem.setData(data);

        // undo all highlights
        piechart_filesystem.highlightValues(null);
        piechart_filesystem.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
}

package co.raveblue.teacherassistant;


import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.vision.text.Line;

import org.apache.poi.xslf.usermodel.LineDash;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class second_fragment extends Fragment{

    private LineChart[] mCharts = new LineChart[1];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_fragment, container, false);

        mCharts[0] = (LineChart) v.findViewById(R.id.chart1);

        for (int i=0; i< mCharts.length; i++)
        {
            LineData data = getData(7,2);
            setupChart(mCharts[i], data, mColors[i]);
        }


        return v;
    }

    private void setupChart(LineChart chart, LineData data, int color){

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleColorHole(color);

        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

        chart.setViewPortOffsets(10, 0, 10, 0);

        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.setData(data);

    }

    private LineData getData(int count, int range)
    {
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i <count; i++)
        {
            int val = 0;
            if (i > 4)
            val = 1;
            yVals.add(new Entry(i, val));
        }

        LineDataSet set1 = new LineDataSet(yVals, "Data Set");

        set1.setLineWidth(3f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);

        set1.setDrawCircles(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);

        set1.setDrawFilled(false);
        set1.setFillColor(Color.rgb(17, 153, 142));
        set1.setFillAlpha(80);

        return new LineData(set1);
    }

    private int[] mColors = new int[]{
            Color.rgb(17,153,142)
    };

    public static second_fragment newInstance(String text){

        second_fragment f = new second_fragment();

        Bundle b = new Bundle();

        return f;
    }

}

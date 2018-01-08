package com.example.administrator.traffic.fragment;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.traffic.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by Administrator on 2018/1/8.
 */

public class CustomMarkerView extends MarkerView {
    private TextView tvcontent;
    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvcontent= (TextView) findViewById(R.id.tvcontent);
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        tvcontent.setText(""+entry.getVal());
    }

    @Override
    public int getXOffset() {
        return -25;
    }

    @Override
    public int getYOffset() {
        return -getHeight();
    }
}

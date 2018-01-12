package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyPagerAdpter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class Fragment_7 extends Fragment {
    private ViewPager vp;
    private LinearLayout llayout;
    private ArrayList<View> arrayList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_7, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        PieChart pieChart=new PieChart(getContext());
        arrayList.add(pieChart);
        HorizontalBarChart horizontalBarChart=new HorizontalBarChart(getContext());
        arrayList.add(horizontalBarChart);
        BarChart barChart=new BarChart(getContext());
        arrayList.add(barChart);
        vp.setAdapter(new MyPagerAdpter(arrayList));
    }

    private void initView(View view) {
        vp = (ViewPager) view.findViewById(R.id.vp);
        llayout = (LinearLayout) view.findViewById(R.id.llayout);
    }
}

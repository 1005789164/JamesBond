package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.traffic.MainActivity;
import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyPagerAdpter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class Fragment_7 extends Fragment {
    //,,,,,
    private ViewPager vp;
    private LinearLayout llayout;
    private ArrayList<View> arrayList = new ArrayList<>();
    private ImageView[] imageViews = new ImageView[3];
    private SlidingMenu slidingMenu;
    private TextView tvtext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_7, null);
        initView(view);
        initData();
        slidingMenu = ((MainActivity) getActivity()).slidingMenu;
        slidingMenu.addIgnoredView(vp);
        return view;
    }

    private void initData() {
        PieChart pieChart = new PieChart(getContext());
        arrayList.add(pieChart);
        HorizontalBarChart horizontalBarChart = new HorizontalBarChart(getContext());
        arrayList.add(horizontalBarChart);
        BarChart barChart = new BarChart(getContext());
        arrayList.add(barChart);
        vp.setAdapter(new MyPagerAdpter(arrayList));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (30, 30);
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                llayout.addView((imageViews[i] = new ImageView(getContext())), layoutParams);
                imageViews[i].setBackgroundResource(R.drawable.fragment_7_selector);
                imageViews[i].setSelected(true);
            } else {
                layoutParams.setMargins(15, 0, 0, 0);
                llayout.addView((imageViews[i] = new ImageView(getContext())), layoutParams);
                imageViews[i].setBackgroundResource(R.drawable.fragment_7_selector);
            }
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViews.length; i++) {
                    if (i == position)
                        imageViews[i].setSelected(true);
                    else
                        imageViews[i].setSelected(false);
                }
                switch (position) {
                    case 0:
                        tvtext.setText("平台上有违章和无违章车辆占比统计");
                        break;
                    case 1:
                        tvtext.setText("有无“重复违章记录车辆”的占比统计");
                        break;
                    case 2:
                        tvtext.setText("违章车辆的违章次数占比统计图");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView(View view) {
        vp = (ViewPager) view.findViewById(R.id.vp);
        llayout = (LinearLayout) view.findViewById(R.id.llayout);
        tvtext = (TextView) view.findViewById(R.id.tvtext);
    }
}

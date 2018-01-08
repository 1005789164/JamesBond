package com.example.administrator.traffic.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MyPagerAdpter extends PagerAdapter {
    ArrayList<View> list;

    public MyPagerAdpter(ArrayList<View> list){
        this.list=list;
    }

    //返回 页卡 的数量
    public int getCount() {
        return list.size();
    }

   // 判断 是 view 是否来自对象
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    // 添加一个 页卡 (实例化)
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    //销毁 一个 页卡
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

}

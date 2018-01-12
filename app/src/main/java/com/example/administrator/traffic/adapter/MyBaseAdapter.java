package com.example.administrator.traffic.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/2.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    public  ArrayList<T> list;
    public MyBaseAdapter(ArrayList<T> list){
        Log.d("tag","list大小======"+list.size());
        this.list=list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setView(position,convertView,parent);
    }

    public abstract View setView(int position, View convertView, ViewGroup parent);

}

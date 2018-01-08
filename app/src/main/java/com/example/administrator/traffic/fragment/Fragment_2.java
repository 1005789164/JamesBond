package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.bean.BusBean;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_2 extends Fragment {

    private View inflate;
    private ExpandableListView ex_bus_list_view;
    private ArrayList<BusBean> busBeen;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_2, null);
        initView();
        initData();
        return inflate;
    }

    private void initData() {
        for (int i = 1; i < 6; i++) {
            Net1(i);
        }
    }

    private void Net1(int id) {
        HttpThread httpThread = new HttpThread(HttpHelper.Get_Bus,""+id, handler);
        httpThread.start();
    }

    private void initView() {
        busBeen = new ArrayList<>();

        ex_bus_list_view = (ExpandableListView) inflate.findViewById(R.id.ex_bus_list_view);
        ex_bus_list_view.setAdapter(new MyAdapter());
    }


    private class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return 0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}

package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.bean.BusBean;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_2 extends Fragment {
    public final static int fragment_2_handler_1=201;

    private String[] BusStance_Name = new String[]{
            "中医医院", "联想大道"
    };
    private View inflate;
    private ExpandableListView ex_bus_list_view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == fragment_2_handler_1) {
                LoadBean(msg);
             //   busStanceList=temList;
                if (busStanceList.size() >= 2) {
                    for (int i = 0; i < busStanceList.size(); i++) {
                        ArrayList<BusBean> busBeen = busStanceList.get(i);
                        Collections.sort(busBeen,new MySort());
                    }
                    if (myAdapter == null) {
                        myAdapter = new MyAdapter();
                        ex_bus_list_view.setAdapter(myAdapter);
                    } else {
                        myAdapter.notifyDataSetChanged();
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
        }
    };
    private MyAdapter myAdapter;
    private List<ArrayList<BusBean>> busStanceList;
    private ArrayList<ArrayList<BusBean>> temList;
    private Timer timer;

    /**
     * 加载bean
     * @param msg
     */
    private void LoadBean(Message msg) {
        /**
         * BusId : 1
         * Busload : 60
         * Distance : 975
         */

        try {
            JSONObject jo = new JSONObject(msg.obj.toString());
            JSONArray ja = jo.getJSONArray("StationInfo");
//            BusBean busBean = gson.fromJson(ja., BusBean.class);
            Log.d("tag","ja=========="+ja.length());
            ArrayList<BusBean> busBeenlist = new ArrayList<>();
            for (int i = 0; i <ja.length() ; i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                BusBean busBean = new BusBean();
                busBean.setBusId(Integer.parseInt(jo1.getString("BusId")));
                busBean.setBusload(Integer.parseInt(jo1.getString("Busload")));
                busBean.setDistance(Integer.parseInt(jo1.getString("Distance")));
                Log.d("tag","busBean=========="+busBean.toString());
                busBeenlist.add(busBean);

            }
           // temList.add(busBeenlist);
            busStanceList=temList;
            busStanceList.add(busBeenlist);
           // Log.d("tag","busStanceList=========="+busStanceList.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_2, null);
        initView();
        initData();
       initTimer();
        return inflate;
    }

    private void initTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                initData();
            }
        };
        timer.schedule(timerTask,300,3000);
    }


    private void initData() {

        for (int i = 1; i < 3; i++) {
            if(i==1)temList.clear();
            Net1(i);
        }
        temList.clear();
    }

    private void Net1(int id) {
        HttpThread httpThread = new HttpThread(HttpHelper.Get_Bus, "{BusStationId:" + id + "}", handler,fragment_2_handler_1);
        httpThread.start();
    }

    private void initView() {
    //    busBeenlist = new ArrayList<>();//装busbean集合
        //临时装站台
        temList = new ArrayList<>();

        busStanceList = new ArrayList<>();//装站台
        ex_bus_list_view = (ExpandableListView) inflate.findViewById(R.id.ex_bus_list_view);
        View bus_head_layout = View.inflate(getActivity(), R.layout.bus_head_layout, null);
        View bt_bus_des = bus_head_layout.findViewById(R.id.bt_bus_des);
        bt_bus_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        ex_bus_list_view.addHeaderView(bus_head_layout);
        //  ex_bus_list_view.setAdapter(new MyAdapter());
    }

    private void ShowDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        View inflate = View.inflate(getActivity(), R.layout.bus_tab_layout, null);
        ListView lv_bus_people = (ListView) inflate.findViewById(R.id.lv_bus_people);
        View inflate1 = View.inflate(getActivity(), R.layout.bus_tab_head_layout, null);
        lv_bus_people.addHeaderView(inflate1);
        MyTabAdapter myTabAdapter = new MyTabAdapter(busStanceList.get(0));
        lv_bus_people.setAdapter(myTabAdapter);
        dialog.setView(inflate);
        dialog.show();

        Button bt_bus_back = (Button) inflate.findViewById(R.id.bt_bus_back);
        bt_bus_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


//        lv_bus_people.addHeaderView();
//        dialog.setView();
    }


    private class MyAdapter extends BaseExpandableListAdapter {
        private TextView tv_bus_car_number;
        private TextView tv_bus_stance_time;
        private TextView tv_bus_stance_distance;

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ArrayList<BusBean> busBean = busStanceList.get(groupPosition);
            Log.d("tag","busbean===="+busBean.size());
            return busBean.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            Log.e("tag","group============"+busStanceList.size());
            return  busStanceList.get(groupPosition);

        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            BusBean busBean = busStanceList.get(groupPosition).get(childPosition);
            return busBean;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.bus_stance_name_layout, null);
            TextView tv_bus_stance_name = (TextView) view.findViewById(R.id.tv_bus_stance_name);
            tv_bus_stance_name.setText(BusStance_Name[groupPosition]);
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View inflate = View.inflate(getActivity(), R.layout.bus_stance_child_layout, null);

            tv_bus_car_number = (TextView) inflate.findViewById(R.id.tv_bus_car_number);
            tv_bus_stance_time = (TextView) inflate.findViewById(R.id.tv_bus_stance_time);
            tv_bus_stance_distance = (TextView) inflate.findViewById(R.id.tv_bus_stance_distance);
            BusBean busBean = (BusBean) getChild(groupPosition, childPosition);
            tv_bus_car_number.setText(busBean.getBusId() + "   ("+ busBean.getBusload() + ")");
            tv_bus_stance_distance.setText("距离站台" + busBean.getDistance() + "米");
            tv_bus_stance_time.setText(busBean.getDistance() / 333 + "分钟到站");
            return inflate;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    private class MyTabAdapter extends MyBaseAdapter {

        private TextView tv_bus_tab_number;
        private TextView tv_bus_tab_id;
        private TextView tv_bus_tab_people;

        public MyTabAdapter(ArrayList list) {
            super(list);
        }

        @Override
        public View setView(int position, View convertView, ViewGroup parent) {
            View inflate = View.inflate(getActivity(), R.layout.bus_tab_item_layout, null);
            tv_bus_tab_number = (TextView) inflate.findViewById(R.id.tv_bus_tab_number);
            tv_bus_tab_id = (TextView) inflate.findViewById(R.id.tv_bus_tab_id);
            tv_bus_tab_people = (TextView) inflate.findViewById(R.id.tv_bus_tab_people);
            int number = position+1; //记录公交序号
            tv_bus_tab_number.setText(number+"");
            BusBean item = (BusBean) getItem(position);
            tv_bus_tab_id.setText(item.getBusId()+"");
            tv_bus_tab_people.setText(item.getBusload()+"");
            return inflate;
        }
    }


    private class MySort implements Comparator< BusBean> {

        @Override
        public int compare(BusBean o1, BusBean o2) {
            return o1.getDistance()-o2.getDistance();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}

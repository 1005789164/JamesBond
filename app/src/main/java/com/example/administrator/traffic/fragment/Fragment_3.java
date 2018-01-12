package com.example.administrator.traffic.fragment;

import android.content.DialogInterface;
import android.content.Entity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.ShowableListMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.traffic.MainActivity;
import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.bean.LightBean;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;
import com.example.administrator.traffic.util.SpUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_3 extends Fragment  {

    public final static int fragment_3_handler_1=301;
    public  final static int fragment_3_handler_2=302;

    private int crossing = 1;
    private Spinner sp_light;
    private Button bt_light_check;
    private Button bt_light_batch;
    private ListView lv_light;
    private ArrayList<LightBean> light_list;
    private boolean isSetTraffic = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==fragment_3_handler_1) {
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);
                    LightBean lightBean = new LightBean();
                    lightBean.RedTime = Integer.parseInt(jo.getString("RedTime"));
                    lightBean.YellowTime = Integer.parseInt(jo.getString("YellowTime"));
                    lightBean.GreenTime = Integer.parseInt(jo.getString("GreenTime"));
                    light_list.add(lightBean);
                    if(light_list.size()==5){
                        for (int i = 0; i < light_list.size(); i++) {
                            LightBean lightBean1 = light_list.get(i);
                            lightBean1.crossing=i+1;
                        }
                    }
                    if (light_list.size() != 0 && myAdapter == null) {
                        Log.e("tag","你会不会刷新==============");
                        myAdapter = new MyAdapter(light_list);
                        lv_light.setAdapter(myAdapter);
                    } else if (myAdapter != null) {
                        myAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==fragment_3_handler_2){
                Log.e("tag","执行到了handler=============");
                if(msg.obj.toString().contains("成功")){
                    if(myAdapter!=null){
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };
    private MyAdapter myAdapter;
    private View inflate;
    private ArrayList<Integer> light_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.light_layout, null);
        initView(inflate);
        return inflate;
    }


    public void initView(View inflate) {
        light_id = new ArrayList<>();
        light_list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Net(i);
        }
        sp_light = (Spinner) inflate.findViewById(R.id.sp_light);

        bt_light_check = (Button) inflate.findViewById(R.id.bt_light_check);
        bt_light_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LightSort();
            }
        });
        bt_light_batch = (Button) inflate.findViewById(R.id.bt_light_batch);
        bt_light_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(light_id.size()!=0){

                        showSetLightDialog();
                        if(isSetTraffic){
                            light_id.clear();

                    }
                }else {
                    Toast.makeText(getActivity(),"请勾选设置小灯",Toast.LENGTH_SHORT).show();
                }
            }
        });
        lv_light = (ListView) inflate.findViewById(R.id.lv_light);
        //设置light的头布局
        View lightView = View.inflate(getActivity(), R.layout.light_head_item, null);
        lv_light.addHeaderView(lightView);


    }

    private void Net(int id) {
        HttpThread httpThread = new HttpThread(HttpHelper.Get_Light, "{'TrafficLightId':" + id + "}", handler,fragment_3_handler_1);
        httpThread.start();
    }


    private void LightSort() {

        String item = (String) sp_light.getSelectedItem();
        switch (item) {
            case "路口升序":
                Collections.sort(light_list, new MyLightSort(0));
                break;
            case "路口降序":
                Collections.sort(light_list, new MyLightSort(1));
                break;
            case "红灯升序":
                Collections.sort(light_list, new MyLightSort(2));
                break;
            case "红灯降序":
                Collections.sort(light_list, new MyLightSort(3));
                break;
            case "绿灯升序":
                Collections.sort(light_list, new MyLightSort(4));
                break;
            case "绿灯降序":
                Collections.sort(light_list, new MyLightSort(5));
                break;
            case "黄灯升序":
                Collections.sort(light_list, new MyLightSort(6));
                break;
            case "黄灯降序":
                Collections.sort(light_list, new MyLightSort(7));
                break;
        }
        if(myAdapter!=null){
            for (int i = 0; i < light_list.size(); i++) {
                LightBean lightBean = light_list.get(i);
                Log.e("sort","排序后的数组========"+lightBean.toString());
            }
//            myAdapter.updateItemsData(light_list);
            myAdapter.notifyDataSetChanged();
        }

    }


    private class MyAdapter extends MyBaseAdapter {
        public MyAdapter(ArrayList data) {
            super(data);
        }

        @Override
        public View setView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getActivity(), R.layout.light_item, null);
            TextView tv_light_item1 = (TextView) convertView.findViewById(R.id.tv_light_item1);
            TextView tv_light_item2 = (TextView) convertView.findViewById(R.id.tv_light_item2);
            TextView tv_light_item3 = (TextView) convertView.findViewById(R.id.tv_light_item3);
            TextView tv_light_item4 = (TextView) convertView.findViewById(R.id.tv_light_item4);
            Button bt_light_set = (Button) convertView.findViewById(R.id.bt_light_set);
            CheckBox ck_light = (CheckBox) convertView.findViewById(R.id.ck_light);

            //---------------------------------------------------------------------------

            final LightBean lightBean = light_list.get(position);
            tv_light_item1.setText("路口" + lightBean.crossing);
            tv_light_item2.setText(lightBean.RedTime+"");
            tv_light_item3.setText(lightBean.YellowTime+"");
            tv_light_item4.setText(lightBean.GreenTime+"");
            bt_light_set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(light_id.size()!=0){
                         if(light_id.size()>1){
                            Toast.makeText(getActivity(),"请选择批量设置",Toast.LENGTH_SHORT).show();
                         }else {
                             showSetLightDialog();
                             if (isSetTraffic) {
                                 light_id.clear();
                             }
                         }
                    }else {
                        Toast.makeText(getActivity(),"请勾选设置小灯",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //----------------------------------------------
            ck_light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                            light_id.add(lightBean.crossing);
                    }else {
                        for (int i = 0; i < light_id.size(); i++) {
                         if(light_id.get(i).equals(lightBean.getCrossing())){
                                light_id.remove(i);
                            }
                        }
                    }
                }
            });
            return convertView;
        }


    }

    private void showSetLightDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = View.inflate(getActivity(), R.layout.light_time_item, null);
        final EditText et_light_time1 = (EditText) inflate.findViewById(R.id.et_light_time1);
        final EditText et_light_time2 = (EditText) inflate.findViewById(R.id.et_light_time2);
        final EditText et_light_time3 = (EditText) inflate.findViewById(R.id.et_light_time3);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String string1 = et_light_time1.getText().toString();
                String string2= et_light_time2.getText().toString();
                String string3 = et_light_time3.getText().toString();
                if(TextUtils.isEmpty(string1)&&TextUtils.isEmpty(string2)&&TextUtils.isEmpty(string3)){
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    Net2(string1,string2,string3);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setView(inflate);
        dialog.show();
    }

    private void Net2(String string1, String string2, String string3) {
        String string = SpUtil.getString(getActivity(), "user_name", "admin");
        String[] str = new String[]{string1,string2,string3};
        Log.e("tag","灯的id=========="+light_id.toString());

        for (int j = 0; j <light_id.size() ; j++) {
            for (int i = 0; i < 3; i++) {
                Log.e("tag","灯的id=========="+light_id.get(j).toString());

                HttpThread httpThread = new HttpThread(HttpHelper.Set_Light_Time,
                        "{TrafficLightId:"+light_id.get(j)+"-1,Status:Red,Time:"+str[i]+", UserName:"+string+"}",handler2,fragment_3_handler_2);
                httpThread.start();

            }
        }
        isSetTraffic=true;

    }


    private class MyLightSort implements java.util.Comparator<LightBean> {
        private int position;

        public MyLightSort(int position) {
            this.position = position;
        }

        @Override
        public int compare(LightBean o1, LightBean o2) {
            switch (position) {
                case 0:
                    return o1.crossing - o2.crossing;
                case 1:
                    return -(o1.crossing - o2.crossing);
                case 2:
                    return o1.RedTime - o2.RedTime;
                case 3:
                    return -(o1.RedTime - o2.RedTime);
                case 4:
                    return o1.YellowTime - o2.YellowTime;
                case 5:
                    return -(o1.YellowTime - o2.YellowTime);
                case 6:
                    return o1.GreenTime - o2.GreenTime;
                case 7:
                    return -(o1.GreenTime - o2.GreenTime);
            }
            return 0;
        }
    }
}

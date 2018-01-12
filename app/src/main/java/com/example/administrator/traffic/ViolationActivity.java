package com.example.administrator.traffic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.fragment.Fragment_5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/11.
 */

public class ViolationActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_violation_add;
    private ListView lv_violation_1;
    private ListView lv_violation_2;
    private String str = "";
    private ArrayList<String> violation;
    private MyViolationDesAdapter myViolationDesAdapter;
    private MyViolationAdapter myViolationAdapter;
    private ArrayList<ArrayList<JSONObject>> violation_des_list;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("违章查询");
        setContentView(R.layout.violation_layout);
        initView();
        initData();
    }

    private void initData() {
        //罚款详情
        violation_des_list = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null){
            violation = extras.getStringArrayList("violation");
            Log.d("tag","violation.size>>"+violation.size());
        }
        //默认加载1违章小车详情信息
        for (int i = 0; i < violation.size(); i++) {
            String s = violation.get(i);
            try {
                //
                ArrayList<JSONObject> ja = new ArrayList<>();//存放当个违章所有违章详情
                JSONObject jo = new JSONObject(s);
                JSONArray lists = new JSONArray(jo.getString("lists"));
                for (int j = 0; j < lists.length(); j++) {
                    JSONObject jsonObject = lists.getJSONObject(j);
                    ja.add(jsonObject);
                }
                violation_des_list.add(ja);
                Log.d("tag","violation_des_list========="+violation_des_list.size()
                        +"==========violation_des===========" +violation_des_list.get(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(violation.size()!=0&&violation_des_list.size()!=0){
        myViolationAdapter = new MyViolationAdapter();
        myViolationDesAdapter = new MyViolationDesAdapter(violation_des_list.get(0));
        lv_violation_1.setAdapter(myViolationAdapter);
        lv_violation_2.setAdapter(myViolationDesAdapter);
        }
        //跳转到违章照片
        lv_violation_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jo = (JSONObject) myViolationDesAdapter.getItem(position);
                try {
                    String photo = jo.getString("photo");
                    Intent intent1 = new Intent(getApplicationContext(),ViolationPhotoActivity.class);
                    intent1.putExtra("photo",photo);
                    startActivity(intent1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initView() {
        iv_violation_add = (ImageView) findViewById(R.id.iv_violation_add);
        lv_violation_1 = (ListView) findViewById(R.id.lv_violation_1);
        lv_violation_2 = (ListView) findViewById(R.id.lv_violation_2);
        iv_violation_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_violation_add:
                finish();
                break;
        }
    }

    private class MyViolationAdapter extends BaseAdapter {
        private TextView tv_vl_name;
        private TextView tv_vl_undisposed;
        private TextView tv_vl_points;
        private TextView tv_vl_gold;
        private ImageView iv_vl_remove;
        private LinearLayout ll_violation_item;


        @Override
        public int getCount() {
            return violation.size();
        }

        @Override
        public Object getItem(int position) {
            return violation.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.e("tag", "刷新界面====");
            convertView = View.inflate(getApplicationContext(), R.layout.violation_item_layout, null);
            tv_vl_name = (TextView) convertView.findViewById(R.id.tv_vl_name);
            tv_vl_undisposed = (TextView) convertView.findViewById(R.id.tv_vl_undisposed);
            tv_vl_points = (TextView) convertView.findViewById(R.id.tv_vl_points);
            tv_vl_gold = (TextView) convertView.findViewById(R.id.tv_vl_gold);
            ll_violation_item = (LinearLayout) convertView.findViewById(R.id.ll_violation_item);
            iv_vl_remove = (ImageView) convertView.findViewById(R.id.iv_vl_remove);
            iv_vl_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment_5.violation_list.remove(position);
                    violation= Fragment_5.violation_list;
                    Log.d("violation.size>",">>>>>>.....违章详情====="+violation.size());
                   // list=violation;
                    myViolationAdapter.notifyDataSetChanged();
                    myViolationDesAdapter.notifyDataSetChanged();
                }
            });
            ll_violation_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myViolationDesAdapter.list=violation_des_list.get(position);
                    myViolationDesAdapter.notifyDataSetChanged();
                }
            });
            ///----------------------------
            String  item = (String) getItem(position);
            try {
                JSONObject jo = new JSONObject(item);
                tv_vl_name.setText(jo.getString("hphm"));
                //未处理违章
                String lists = jo.getString("lists");
                JSONArray ja = new JSONArray(lists);
                tv_vl_undisposed.setText("未处理违章 "+ja.length()+"  次");
                int points=0;//扣分
                int gold =0;//罚款
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo1 = ja.getJSONObject(i);
                    points+=Integer.parseInt(jo1.getString("fen"));
                    gold+= Integer.parseInt(jo1.getString("money"));
                }
                tv_vl_points.setText("扣"+points+"分");
                tv_vl_gold.setText("罚款："+gold+"元");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ///-----------------------------
            return convertView;

        }
    }


    private class MyViolationDesAdapter extends MyBaseAdapter {


        private TextView tv_card_time;
        private TextView tv_card_road;
        private TextView tv_card_des;
        private TextView tv_card_points;
        private TextView tv_violation_des_gold;

        public MyViolationDesAdapter(ArrayList list) {
            super(list);
        }
        @Override
        public View setView(int position, View convertView, ViewGroup parent) {
            Log.d("tag","view刷新了=============================================");
            convertView = View.inflate(getApplicationContext(), R.layout.violation_des_item_layout, null);
            tv_card_time = (TextView) convertView.findViewById(R.id.tv_card_time);
            tv_card_road = (TextView) convertView.findViewById(R.id.tv_card_road);
            tv_card_des = (TextView) convertView.findViewById(R.id.tv_card_des);
            tv_card_points = (TextView) convertView.findViewById(R.id.tv_card_points);
            tv_violation_des_gold = (TextView) convertView.findViewById(R.id.tv_violation_des_gold);

            //------------------------------
//            Object item = getItem(position);
            JSONObject jo = (JSONObject) getItem(position);
            try {
                tv_card_time.setText(jo.getString("date"));
                tv_card_road.setText(jo.getString("area"));
                tv_card_des.setText(jo.getString("act"));
                tv_card_points.setText("扣分:"+jo.getString("fen"));
                tv_violation_des_gold.setText("罚款:"+jo.getString("money"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //---------------------
            return convertView;
        }
    }


}

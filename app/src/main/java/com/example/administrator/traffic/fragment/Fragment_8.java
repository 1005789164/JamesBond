package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.http.HttpThread;
import com.example.administrator.traffic.util.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_8 extends Fragment implements View.OnClickListener {
    ArrayList<JSONObject> list = new ArrayList<>();
    private RadioButton rb_people_1,rb_people_2,rb_people_3;
    private View view_people_1,view_people_2,view_people_3;
    private LinearLayout ll_people_1,ll_people_2,ll_people_3,tabhost;
    private ListView lv_people_2;
    private EditText et_threshold;
    private TextView tv_threshold,tv_people_name,tv_people_sex,tv_people_phoneNumber,tv_people_idNumber,tv_people_date,tv_people_carNumber;
    private Button bt_people_set;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_8, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        rb_people_1 = (RadioButton) view.findViewById(R.id.rb_people_1);
        rb_people_1.setOnClickListener(this);
        view_people_1 = (View) view.findViewById(R.id.view_people_1);
        view_people_1.setOnClickListener(this);
        rb_people_2 = (RadioButton) view.findViewById(R.id.rb_people_2);
        rb_people_2.setOnClickListener(this);
        view_people_2 = (View) view.findViewById(R.id.view_people_2);
        view_people_2.setOnClickListener(this);
        rb_people_3 = (RadioButton) view.findViewById(R.id.rb_people_3);
        rb_people_3.setOnClickListener(this);
        view_people_3 = (View) view.findViewById(R.id.view_people_3);
        view_people_3.setOnClickListener(this);
        ll_people_1 = (LinearLayout) view.findViewById(R.id.ll_people_1);
        ll_people_1.setOnClickListener(this);
        lv_people_2 = (ListView) view.findViewById(R.id.lv_people_2);
        ll_people_2 = (LinearLayout) view.findViewById(R.id.ll_people_2);
        ll_people_2.setOnClickListener(this);
        ll_people_3 = (LinearLayout) view.findViewById(R.id.ll_people_3);
        ll_people_3.setOnClickListener(this);
        tabhost = (LinearLayout) view.findViewById(android.R.id.tabhost);
        tabhost.setOnClickListener(this);
        et_threshold= (EditText) view.findViewById(R.id.et_threshold);
        tv_threshold= (TextView) view.findViewById(R.id.tv_threshold);
        bt_people_set= (Button) view.findViewById(R.id.bt_people_set);
        bt_people_set.setOnClickListener(this);
        tv_people_sex= (TextView) view.findViewById(R.id.tv_people_sex);
        tv_people_carNumber= (TextView) view.findViewById(R.id.tv_people_carNumber);
        tv_people_date= (TextView) view.findViewById(R.id.tv_people_date);
        tv_people_idNumber= (TextView) view.findViewById(R.id.tv_people_idNumber);
        tv_people_name= (TextView) view.findViewById(R.id.tv_people_name);
        tv_people_phoneNumber= (TextView) view.findViewById(R.id.tv_people_phoneNumber);
    }

    private void initData() {
        MyAdapter adapter = new MyAdapter(list);
        lv_people_2.setAdapter(adapter);
        String Url="GetPersonInfo.do";

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_people_1:
                view_people_1.setVisibility(View.VISIBLE);
                view_people_2.setVisibility(View.GONE);
                view_people_3.setVisibility(View.GONE);
                ll_people_1.setVisibility(View.VISIBLE);
                ll_people_2.setVisibility(View.GONE);
                ll_people_3.setVisibility(View.GONE);
                break;
            case R.id.rb_people_2:
                view_people_1.setVisibility(View.GONE);
                view_people_2.setVisibility(View.VISIBLE);
                view_people_3.setVisibility(View.GONE);
                ll_people_1.setVisibility(View.GONE);
                ll_people_2.setVisibility(View.VISIBLE);
                ll_people_3.setVisibility(View.GONE);
                break;
            case R.id.rb_people_3:
                view_people_1.setVisibility(View.GONE);
                view_people_2.setVisibility(View.GONE);
                view_people_3.setVisibility(View.VISIBLE);
                ll_people_1.setVisibility(View.GONE);
                ll_people_2.setVisibility(View.GONE);
                ll_people_3.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_people_set:
                setThreshold();
                break;
        }
    }
    private void setThreshold(){
        String str=et_threshold.getText().toString();
        if(TextUtils.isEmpty(str)){
            Toast.makeText(getActivity(),"阈值不能为空，请设置值！",Toast.LENGTH_SHORT).show();
        }else {
            SpUtil.putString(getActivity(),"threshold",str);
            tv_threshold.setText("当前1-4号小车账户余额警告阈值为"+str+"元");
        }

    }

    class MyAdapter extends MyBaseAdapter {

        private TextView tv_recharge_item_date;
        private TextView tv_recharge_item_week;
        private TextView tv_recharge_item_people;
        private TextView tv_recharge_item_carnumber;
        private TextView tv_recharge_item_recharge;
        private TextView tv_recharge_item_money;
        private TextView tv_recharge_item_date2;
        private LinearLayout ll_recharge;

        public MyAdapter(ArrayList list) {
            super(list);
        }


        @Override
        public View setView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_recharge, null);
            JSONObject item = (JSONObject) getItem(position);
            tv_recharge_item_date = (TextView) view.findViewById(R.id.tv_recharge_item_date);
            tv_recharge_item_week = (TextView) view.findViewById(R.id.tv_recharge_item_week);
            tv_recharge_item_people = (TextView) view.findViewById(R.id.tv_recharge_item_date);
            tv_recharge_item_carnumber = (TextView) view.findViewById(R.id.tv_recharge_item_carnumber);
            tv_recharge_item_recharge = (TextView) view.findViewById(R.id.tv_recharge_item_recharge);
            tv_recharge_item_money = (TextView) view.findViewById(R.id.tv_recharge_item_money);
            tv_recharge_item_date2 = (TextView) view.findViewById(R.id.tv_recharge_item_date2);
            ll_recharge = (LinearLayout) view.findViewById(R.id.ll_recharge);
            try {
                tv_recharge_item_date.setText(item.getString("date"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}

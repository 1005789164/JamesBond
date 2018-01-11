package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyBaseAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_8 extends Fragment {
    ListView ll_people_2;
    ArrayList<JSONObject> list = new ArrayList<>();

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
        ll_people_2 = (ListView) view.findViewById(R.id.lv_people_2);

    }

    private void initData() {
        MyAdapter adapter = new MyAdapter(list);

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
            tv_recharge_item_date= (TextView) view.findViewById(R.id.tv_recharge_item_date);
            tv_recharge_item_week= (TextView) view.findViewById(R.id.tv_recharge_item_week);
            tv_recharge_item_people= (TextView) view.findViewById(R.id.tv_recharge_item_date);
            tv_recharge_item_carnumber= (TextView) view.findViewById(R.id.tv_recharge_item_carnumber);
            tv_recharge_item_recharge= (TextView) view.findViewById(R.id.tv_recharge_item_recharge);
            tv_recharge_item_money= (TextView) view.findViewById(R.id.tv_recharge_item_money);
            tv_recharge_item_date2= (TextView) view.findViewById(R.id.tv_recharge_item_date2);
            ll_recharge= (LinearLayout) view.findViewById(R.id.ll_recharge);
            try {
                tv_recharge_item_date.setText(item.getString("date"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }
}

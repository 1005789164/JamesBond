package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_1 extends Fragment {

    private View inflate;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private TextView tv_test1;
    private ListView lv_car;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void Net() {

        HttpThread httpThread = new HttpThread(HttpHelper.Get_Sense, "", handler);

        httpThread.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_1, null);
        initView(inflate);

        Net();
        return inflate;
    }

    private void initView(View inflate) {
        lv_car = (ListView) inflate.findViewById(R.id.lv_car);

    }

    private class MyAdapter extends MyBaseAdapter {


        private TextView tv_car_number;
        private ImageView iv_car_icon;
        private TextView tv_car_plate;
        private TextView tv_car_name;
        private CheckBox cb_car_1;
        private Button bt_car_gold;

        public MyAdapter(ArrayList list) {
            super(list);
        }

        @Override
        public View setView(int position, View convertView, ViewGroup parent) {
            View.inflate(getActivity(), R.layout.car_list_item, null);
            return null;
        }

        public  class ViewHolder {
            public View rootView;
            public TextView tv_car_number;
            public ImageView iv_car_icon;
            public TextView tv_car_plate;
            public TextView tv_car_name;
            public CheckBox cb_car_1;
            public Button bt_car_gold;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_car_number = (TextView) rootView.findViewById(R.id.tv_car_number);
                this.iv_car_icon = (ImageView) rootView.findViewById(R.id.iv_car_icon);
                this.tv_car_plate = (TextView) rootView.findViewById(R.id.tv_car_plate);
                this.tv_car_name = (TextView) rootView.findViewById(R.id.tv_car_name);
                this.cb_car_1 = (CheckBox) rootView.findViewById(R.id.cb_car_1);
                this.bt_car_gold = (Button) rootView.findViewById(R.id.bt_car_gold);
            }

        }

    }
}

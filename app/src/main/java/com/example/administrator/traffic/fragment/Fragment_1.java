package com.example.administrator.traffic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.traffic.MainActivity;
import com.example.administrator.traffic.R;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_1 extends Fragment {

    private View inflate;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==200){
                try {
                    JSONObject jo = new JSONObject((String) msg.obj);
                    tv_test1.setText(jo.getString("pm2.5").toString()+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private TextView tv_test1;



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
        tv_test1 = (TextView) inflate.findViewById(R.id.tv_test1);
        Net();
        return inflate;
    }
}

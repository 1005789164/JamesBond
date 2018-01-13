package com.example.administrator.traffic.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/1/9.
 */

public class Fragment_4 extends Fragment {
    public static final int fragment_4_handler_1=401;
    private TextView tvyear;
    private TextView tvweek;
    private TextView tvtemper;
    private TextView tvhumidity;
    private TextView tvpm;
    private ImageView imgrefresh;
    private Timer timer;
    private ImageView imgroad;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        imgrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpThread(HttpHelper.Get_Sense, "{}", handler,fragment_4_handler_1).start();
            }
        });
    }

    private void initView(View view) {
        tvyear = (TextView) view.findViewById(R.id.tvyear);
        tvweek = (TextView) view.findViewById(R.id.tvweek);
        tvtemper = (TextView) view.findViewById(R.id.tvtemper);
        tvhumidity = (TextView) view.findViewById(R.id.tvhumidity);
        tvpm = (TextView) view.findViewById(R.id.tvpm);
        imgrefresh = (ImageView) view.findViewById(R.id.imgrefresh);
        imgroad= (ImageView) view.findViewById(R.id.imgroad);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == fragment_4_handler_1) {
                try {
                    JSONObject jsonObject = new JSONObject((String) msg.obj);
                    Log.e("xxx", (String) msg.obj);
                    tvtemper.setText("温度：" + jsonObject.getString("temperature") + " ºC");
                    tvhumidity.setText("相对湿度：" + jsonObject.getString("humidity") + "%");
                    tvpm.setText("PM2.5：" + jsonObject.getString("pm2.5") + "μg/m3");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat format = new SimpleDateFormat("YYYY-M-D");
                tvyear.setText(format.format(System.currentTimeMillis()));
                format = new SimpleDateFormat("EEEE");
                tvweek.setText(format.format(new Date()));
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        (timer = new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                new HttpThread(HttpHelper.Get_Sense, "{}", handler,fragment_4_handler_1).start();
            }
        }, 300, 3000);
        AnimationDrawable animationDrawable= (AnimationDrawable) imgroad.getBackground();
        animationDrawable.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }
}

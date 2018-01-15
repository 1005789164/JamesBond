package com.example.administrator.traffic.MyApp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.traffic.util.UIUtls;

/**
 * Created by Administrator on 2018/1/8.
 */

public class MyApp extends Application {
    private static MyApp myApp;

    //---------------------------------------------------------------------
    private static String userRole;
    public Handler mHandler;

    public Handler getmHandler() {
        return mHandler;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    //---------------------------------------------------------------------
    public static MyApp getInstance() {
        if (myApp == null) {
            myApp = new MyApp();
        }
        return myApp;
    }

    public static Context context;

    public Context getContext() {
        return context;
    }


    public String Ip;
    public String Url;


    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public boolean checkIp(String ip) {
        String str = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        if (ip.matches(str)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                Toast.makeText(UIUtls.getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
//            }
//        };
        mHandler = new Handler();

    }
}

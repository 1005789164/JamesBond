package com.example.administrator.traffic.util;

import android.content.Context;

import com.example.administrator.traffic.MyApp.MyApp;

/**
 * Created by Administrator on 2018/1/13.
 */

public class UIUtls {
    public static Context getContext(){
        return MyApp.getInstance().getContext();
    }
}

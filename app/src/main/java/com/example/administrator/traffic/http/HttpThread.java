package com.example.administrator.traffic.http;

import android.os.Handler;
import android.os.Message;

import com.example.administrator.traffic.fragment.Fragment_1;
import com.example.administrator.traffic.fragment.Fragment_2;
import com.example.administrator.traffic.fragment.Fragment_3;
import com.example.administrator.traffic.fragment.Fragment_4;
import com.example.administrator.traffic.fragment.Fragment_5;

import com.example.administrator.traffic.LoginActivity;
import com.example.administrator.traffic.fragment.Fragment_6;
import com.example.administrator.traffic.fragment.Fragment_8;

/**
 * Created by Administrator on 2018/1/8.
 */

public class HttpThread extends Thread {
    private  int tag;
    private  String Url;
    private  String StrJson;
    private  Handler handler;

    public HttpThread(String Url, String StrJson, Handler handler,int tag){
        this.Url = Url;
        this.StrJson = StrJson;
        this.handler = handler;
        this.tag =tag;
    }

    @Override
    public void run() {
        super.run();
        Message obtain = Message.obtain();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int post = HttpHelper.post(Url, StrJson);

        if(post==200){
            switch (tag){
                case Fragment_1.fragment_1_handler_1:
                    obtain.what = Fragment_1.fragment_1_handler_1;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_1.fragment_1_handler_2:
                    obtain.what = Fragment_1.fragment_1_handler_2;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_2.fragment_2_handler_1:
                    obtain.what = Fragment_2.fragment_2_handler_1;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_3.fragment_3_handler_1:
                    obtain.what = Fragment_3.fragment_3_handler_1;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_3.fragment_3_handler_2:
                    obtain.what = Fragment_3.fragment_3_handler_2;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_4.fragment_4_handler_1:
                    obtain.what = Fragment_4.fragment_4_handler_1;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_5.fragment_5_handler_1:
                    obtain.what = Fragment_5.fragment_5_handler_1;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_6.fragment_6_handler_1:
                    obtain.what = Fragment_6.fragment_6_handler_1;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case LoginActivity.LOGINACTIVITY_CODE:
                    obtain.what=LoginActivity.LOGINACTIVITY_CODE;
                    obtain.obj=HttpHelper.getWebContext();
                    break;
                case Fragment_8.fragment_8_handler:
                    obtain.what=Fragment_8.fragment_8_handler;
                    obtain.obj=HttpHelper.getWebContext();
            }

        }
        handler.sendMessage(obtain);
    }
}

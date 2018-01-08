package com.example.administrator.traffic.http;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2018/1/8.
 */

public class HttpThread extends Thread {
    private  String Url;
    private  String StrJson;
    private  Handler handler;

    public HttpThread(String Url, String StrJson, Handler handler){
        this.Url = Url;
        this.StrJson = StrJson;
        this.handler = handler;
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
        obtain.what =post;
        if(post==200){
            obtain.obj=HttpHelper.getWebContext();
        }
        handler.sendMessage(obtain);
    }
}

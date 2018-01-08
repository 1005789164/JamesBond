package com.example.administrator.traffic.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.traffic.MyApp.MyApp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/1/8.
 */

public class HttpIcon  {

    private static Bitmap bitmap;

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        HttpIcon.bitmap = bitmap;
    }

    public static void post(final String Url, final Handler handler){
        Runnable runnable = new Runnable() {
            private int code;
            @Override
            public void run() {
                Message obtain = Message.obtain();
                try {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.connect();
            code = conn.getResponseCode();
            obtain.what= code;
            if(code ==200){
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                obtain.obj=bitmap;
            }
        } catch (Exception e) {
           // MyApp.handler.sendEmptyMessage(404);
            e.printStackTrace();
        }
        handler.sendMessage(obtain);
            }
        };
        new Thread(runnable).start();
    }

}

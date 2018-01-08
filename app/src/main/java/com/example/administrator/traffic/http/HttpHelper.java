package com.example.administrator.traffic.http;

import android.os.Handler;
import android.util.Log;

import com.example.administrator.traffic.MyApp.MyApp;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/1/8.
 */

public class HttpHelper {
    public static String  URL = "http://192.168.1.103:8080/transportservice/action/";
    public static String webContext;
    public final static String Get_Bus ="GetBusStationInfo.do";
    public  final static String Get_Sense = "GetAllSense.do";
    public static String Get_Car_Balance="GetCarAccountBalance.do";
    public static String Set_Car_top_up="SetCarAccountRecharge.do";

    public static String getWebContext() {
        return webContext;
    }

    public static void setWebContext(String webContext) {
        HttpHelper.webContext = webContext;
    }

    private  static int code;

    public static int post(String Url, String strjson){
        try {
            URL url = new URL(URL+Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","text/html");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.connect();
            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.write(strjson);
            pw.close();
            code = conn.getResponseCode();
            if(code ==200){
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
             //   new BufferedInputStream(isr);
                BufferedReader br = new BufferedReader(isr);
                String data="" ;
                String tem ="";
                while ((tem=br.readLine())!=null){
                    data+=tem;
                }
                JSONObject jo = new JSONObject(data);
               setWebContext(jo.getString("serverinfo"));
                Log.e("http","获取数据"+getWebContext());
            }
        } catch (Exception e) {
           MyApp.getInstance().handler.sendEmptyMessage(404);
            e.printStackTrace();
        }
        return code;
    }

}

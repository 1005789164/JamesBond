package com.example.administrator.traffic.http;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.traffic.MyApp.MyApp;
import com.example.administrator.traffic.util.SpUtil;
import com.example.administrator.traffic.util.UIUtls;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018/1/8.
 */

public class HttpHelper {

    public static String webContext;
    public final static String Get_Bus ="GetBusStationInfo.do";
    public  final static String Get_Sense = "GetAllSense.do";
    public static String Get_Car_Balance="GetCarAccountBalance.do";
    public static String Set_Car_top_up="SetCarAccountRecharge.do";
    public static String Get_Light="GetTrafficLightConfigAction.do";
    public static String Set_Light_Time="SetTrafficLightNowStatus.do";
    public static String Get_Violation="traffic_violations.do";
    private static String URL;

    public static String getWebContext() {
        return webContext;
    }

    public static void setWebContext(String webContext) {
        HttpHelper.webContext = webContext;
    }

    private  static int code;

    public synchronized static int post(String Url, String strjson){
        try {
            String IP = SpUtil.getString(UIUtls.getContext(),"ip","");

            if(!TextUtils.isEmpty(IP)){
                URL = "http://"+IP+":8080/transportservice/action/";
            }
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
//            Looper.prepare();
            Log.d("tag","-------------");

//            Looper.loop();
            e.printStackTrace();
        }
        return code;
    }

}

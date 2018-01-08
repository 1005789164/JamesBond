package com.example.administrator.traffic.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/8.
 */

public class SpUtil {
   private static SharedPreferences sp;
    public static  String getString(Context context,String key,String defValue){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);
    }

    public static  void putString(Context context,String key,String Value){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
         sp.edit().putString(key,Value).commit();
    }

    public static  Boolean getBoolean(Context context,String key,boolean defValue){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }

    public static  void putBoolean(Context context,String key,boolean Value){
        if(sp==null){
            sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        }
         sp.edit().putBoolean(key,Value).commit();
    }






}

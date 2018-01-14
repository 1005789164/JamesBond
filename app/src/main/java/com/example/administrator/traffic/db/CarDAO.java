package com.example.administrator.traffic.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.administrator.traffic.bean.CarBean;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/6.
 */

public class CarDAO {
    com.example.administrator.traffic.db.dbHelper dbHelper;
    static SQLiteDatabase db;

    int version = 1;
    static String tableName = "carInfo";
    static String columnNames = "CarId text,CarName text,CarImage text,Balance integer";
    static String fieldList = "CarId,CarName,CarImage,Balance";

    public CarDAO(Context context) {
        dbHelper = new dbHelper(context, tableName, columnNames, version);
        db = dbHelper.getWritableDatabase();
    }

    //添加(方法一）
    public static boolean insert(JSONObject jsonObject) {
        try {
            String sql = "insert into " + tableName + "(" + fieldList + ") values(?,?,?,?)";
            db.execSQL(sql, new Object[]{});  //传入的值(需补充参数）
            return true;
        } catch (Exception e)
        {
            return false;
        } finally {
            db.close();
        }
    }

    //添加(方法二）
    public static boolean insert(CarBean carBean) {
        try {
            db.insert(tableName, null, setValue(carBean));
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    //删除（方法一）
    public static boolean delete(int id) {
        try {
            String sql = "delete from " + tableName + " where _id=?";
            db.execSQL(sql, new Object[]{id});  //传入的值
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    //删除（方法二） 参数2可以省略
    public static boolean delete(int id, String tableName) {
        try {
            db.delete(CarDAO.tableName, "_id=?", new String[]{String.valueOf(id)});
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    //修改（方法一）
    public static boolean update(JSONObject jsonObject) {
        try {
            String sql = "update " + tableName + " set CarId=?,CarName=?,CarImage=?,Balance=? where _id=?";
            db.execSQL(sql, new Object[]{});  //传入的值(需补充参数）
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    //修改（方法二）
    public static boolean update(CarBean carBean, int id) {
        try {
            db.update(tableName, setValue(carBean), "_id=?", new String[]{String.valueOf(id)});
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
    }

    //查询单一记录(按主键）
    public static CarBean getCarBeanById(int id) {
        Cursor cursor = db.rawQuery("select * from " + tableName + " where _id=?",
                new String[]{String.valueOf(id)});
        if (cursor.moveToNext()) {
            CarBean info = new CarBean();
            info.setCarId(Integer.parseInt(cursor.getString(0)));
            info.setHphm(cursor.getString(1));
            info.setBanlance(cursor.getInt(2));
            return info;
        }
        cursor.close();
        return null;
    }

    //查询全部记录(方法一）
    public ArrayList<CarBean> getAllCarBean() {
        ArrayList<CarBean> list = new ArrayList<CarBean>();
        Cursor cursor = db.rawQuery("select * from " + tableName, null);
		 if (cursor != null && cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
           // cursor.moveToNext();
            CarBean info = new CarBean();
            info.setCarId(Integer.parseInt(cursor.getString(0)));
            info.setHphm(cursor.getString(1));
            info.setBanlance(cursor.getInt(2));
            list.add(info);
        }
		 }
        cursor.close();
        db.close();
        return list;
    }

    //查询指定记录（按条件,排序）(方法二）
    public ArrayList<CarBean> getCarBean(String selectionName, String selectionArgs, String OrderName, int Order) {

        ArrayList<CarBean> list = new ArrayList<CarBean>();
        //table:表名; columns：查询的列名,如果null代表查询所有列； selection:查询条件; selectionArgs：条件占位符的参数值,
        //groupBy:按什么字段分组, having:分组的条件, orderBy:按什么字段排序

        OrderName = (Order == 0 ? OrderName : OrderName + " desc"); // order 非0时为降序
        Cursor cursor = db.query(tableName, null, "" + selectionName + "= ?", new String[]{selectionArgs}, null, null, OrderName);
        if (cursor != null && cursor.getCount() > 0) {    //判断cursor中是否存在数据
            while (cursor.moveToNext()) {
                cursor.moveToNext();
                CarBean info = new CarBean();
                info.setCarId(Integer.parseInt(cursor.getString(0)));
                info.setHphm(cursor.getString(1));
                info.setBanlance(cursor.getInt(2));
                list.add(info);
            }
        }
        cursor.close();
        db.close();
        return list;
    }


    //设置值内容(添加和更新使用）
    private static ContentValues setValue(CarBean carBean) {
        ContentValues values = new ContentValues();
        values.put("CardId", carBean.getCarId());
        values.put("CarName", carBean.getHphm());
        values.put("Balance", carBean.getBanlance());
        return values;
    }

}

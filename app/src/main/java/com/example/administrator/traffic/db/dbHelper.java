package com.example.administrator.traffic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.ParcelUuid;

/**
 * Created by Administrator on 2018/1/6.
 */

 public class dbHelper extends SQLiteOpenHelper{

    static  final String DB_NAME="hnyd.db"; //数据库名
    String tableName;         //表名
    String columnNames;      //列名

    public dbHelper(Context context, String tableName, String columnNames, int version) {
        super(context, DB_NAME, null, version);
        this.tableName=tableName;     //表名
        this.columnNames=columnNames; //列名
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //columnNames: name text, sex text
        String sql="create table "+ tableName + "(_id integer primary key autoincrement,"+ columnNames +")";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //如何表结构有更改，删除原表，再创建。
        String sql = "DROP TABLE IF EXISTS " + tableName;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }
}

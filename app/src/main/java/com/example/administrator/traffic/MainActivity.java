package com.example.administrator.traffic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.fragment.Fragment_1;
import com.example.administrator.traffic.fragment.Fragment_2;
import com.example.administrator.traffic.fragment.Fragment_3;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private SlidingMenu slidingMenu;
    private ListView lv_menu;
    String[] arrTitle = new String[]{"车辆账户", "公交查询", "路灯管理", "路况查询", "车辆违章", "生活助手", "数据分析", "个人中心", "创意设计"};
    String[] arrLetter = new String[]{"C", "B", "L", "R", "P", "A", "D", "S", "O"};

    private FragmentTransaction transaction;
    private FrameLayout fl_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
        initMenu();
    }

    private void initView() {
        fl_main = (FrameLayout) findViewById(R.id.fl_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main,new Fragment_1(),"0");
        transaction.commit();
    }


    private void initMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setBehindOffset(600);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setAboveOffset(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setMenu(R.layout.menu_layout);
   //11111
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        lv_menu.setAdapter(new MyAdapter<String>(new ArrayList<String>(Arrays.asList(arrTitle))));

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                 FragmentTransaction transaction1 = supportFragmentManager.beginTransaction();
                switch (position) {
                    case 0:
                        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //系统自带淡入淡出效果
                        transaction1.replace(R.id.fl_main,new Fragment_1(),"car");
                        break;
                    case 1:
                        transaction1.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);//系统自带左向右滑入效果
                        transaction1.replace(R.id.fl_main,new Fragment_2(),"bus");
                        break;
                    case 2:
                        transaction1.replace(R.id.fl_main,new Fragment_3(),"light");
                        break;
                }
                transaction1.commit();
            }
        });
    }

    class MyAdapter<T> extends MyBaseAdapter {
        public MyAdapter(ArrayList<T> list) {
            super(list);
        }

        public View setView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getApplicationContext(), R.layout.menu_item, null);
            TextView tv_menuLetter = (TextView) convertView.findViewById(R.id.tv_menuLetter);
            TextView tv_menuTitle = (TextView) convertView.findViewById(R.id.tv_menuTitle);
            tv_menuLetter.setText(arrLetter[position]);
            tv_menuTitle.setText(arrTitle[position]);
            return convertView;
        }
    }


}

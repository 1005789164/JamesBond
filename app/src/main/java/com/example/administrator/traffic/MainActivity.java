package com.example.administrator.traffic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.traffic.MyApp.MyApp;
import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.fragment.Fragment_1;
import com.example.administrator.traffic.fragment.Fragment_2;
import com.example.administrator.traffic.fragment.Fragment_3;
import com.example.administrator.traffic.fragment.Fragment_5;
import com.example.administrator.traffic.fragment.Fragment_4;
import com.example.administrator.traffic.fragment.Fragment_7;
import com.example.administrator.traffic.fragment.Fragment_8;
import com.example.administrator.traffic.fragment.Fragment_9;
import com.example.administrator.traffic.fragment.Fragment_6;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public SlidingMenu slidingMenu;
    private ListView lv_menu;
    String[] arrTitle = new String[]{"车辆账户", "公交查询", "路灯管理", "路况查询", "车辆违章", "生活助手", "数据分析", "个人中心", "创意设计"};
    String[] arrLetter = new String[]{"C", "B", "L", "R", "P", "A", "D", "S", "O"};

    private FragmentTransaction transaction;
    private FrameLayout fl_main;
    public Toolbar toolbar;
    public TextView tv_tb_title;
    public ImageView iv_tb_icon;
    private MenuInflater menuInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initMenu();
        initToolbar();
        initView();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_tb_title = (TextView) findViewById(R.id.tv_tb_title);
        iv_tb_icon = (ImageView) findViewById(R.id.iv_tb_icon);
        iv_tb_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.car_menu, menu);
        return true;
    }

    private void initView() {
        fl_main = (FrameLayout) findViewById(R.id.fl_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main, new Fragment_1(), "0");
        transaction.commit();
    }


    private void initMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setBehindOffset(600);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setAboveOffset(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        View inflate = View.inflate(getApplicationContext(), R.layout.menu_layout, null);
        Button bt_logout = (Button) inflate.findViewById(R.id.bt_logout);
        slidingMenu.setMenu(inflate);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        lv_menu = (ListView) findViewById(R.id.lv_menu);
        lv_menu.setAdapter(new MyAdapter<String>(new ArrayList<String>(Arrays.asList(arrTitle))));

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction1 = supportFragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                switch (position) {
                    case 0:
                        tv_tb_title.setText("账号管理");
                        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //系统自带淡入淡出效果
                        transaction1.replace(R.id.fl_main, new Fragment_1(), "car");
                        break;
                    case 1:
                        tv_tb_title.setText("公交查询");
                        transaction1.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);//系统自带左向右滑入效果
                        transaction1.replace(R.id.fl_main, new Fragment_2(), "bus");
                        break;
                    case 2:
                        tv_tb_title.setText("红绿灯管理");
                        transaction1.replace(R.id.fl_main, new Fragment_3(), "light");
                        break;
                    case 3:
                        tv_tb_title.setText("路况查询");
                        transaction1.replace(R.id.fl_main, new Fragment_4(), "road");
                        break;
                    case 4:
                        tv_tb_title.setText("车辆违章");
                        transaction1.replace(R.id.fl_main, new Fragment_5(), "Violation");
                        break;
                    case 5:
                        tv_tb_title.setText("生活助手");
                        transaction1.replace(R.id.fl_main, new Fragment_6(), "life");
                        break;
                    case 6:
                        tv_tb_title.setText("数据分析");
                        transaction1.replace(R.id.fl_main, new Fragment_7(), "data");
                        break;
                    case 7:
                        tv_tb_title.setText("个人中心");
                        transaction1.replace(R.id.fl_main, new Fragment_8(), "people");
                        break;
                    case 8:
                        tv_tb_title.setText("创意设计");
                        transaction1.replace(R.id.fl_main, new Fragment_9(), "origina");
                        break;
                }
                transaction1.commit();
                slidingMenu.toggle();
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
            IsRole(position, convertView);

            return convertView;
        }
    }

    private void IsRole(int position, View convertView) {
        String userRole = MyApp.getInstance().getUserRole();
        Log.d("tag", "用户的权限>>>>>>>>>>>>>>>" + userRole);
        if (!TextUtils.isEmpty(userRole) && userRole.equals("R01")) {
            if (position == 0 || position == 2) {
                convertView.setVisibility(View.GONE);
                ListView.LayoutParams layoutParams = new ListView.LayoutParams(0, 1);
                convertView.setLayoutParams(layoutParams);
            } else {
                convertView.setVisibility(View.VISIBLE);
            }
        }
    }


}

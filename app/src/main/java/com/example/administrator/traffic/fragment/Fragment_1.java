package com.example.administrator.traffic.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.traffic.MainActivity;
import com.example.administrator.traffic.R;
import com.example.administrator.traffic.adapter.MyBaseAdapter;
import com.example.administrator.traffic.bean.BusBean;
import com.example.administrator.traffic.bean.CarBean;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/1/7.
 */
@SuppressLint("ValidFragment")
public class Fragment_1 extends Fragment {
    private  MainActivity mainActivity;
    private String[] name = new String[]{
            "张三丰", "张无忌", "孙悟空", "关羽", "武松"
    };

    private String[] car_number = new String[]{
            "辽A1350", "辽A1350", "辽A1350", "辽A1350", "辽A1350"
    };


    private View inflate;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                try {
                    JSONArray ja = new JSONArray((String) msg.obj);
                    CarBean carBean = new CarBean();
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = (JSONObject) ja.get(i);

                        balance.add(jo);
                    }
                    if(balance.size()!=0){
                        if(myAdapter==null) {
                            myAdapter = new MyAdapter(balance);
                            lv_car.setAdapter(myAdapter);
                        }else {
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                if(msg.obj.toString().contains("ok")){
                    for (int i = 1; i < 6; i++) {
                        Net(i);
                    }
                }else {
                    Toast.makeText(getActivity(),"充值失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    private TextView tv_test1;
    private ListView lv_car;
    private ArrayList<String> car_id;
    private ArrayList<String> plate_id;
    private TextView tv_car_top_up_plate;
    private ArrayList<JSONObject> balance;
    private MyAdapter myAdapter;

    public Fragment_1(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity.tv_tb_title.setText("账号管理");
        mainActivity.toolbar.inflateMenu(R.menu.car_menu);
        Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.toString()){
                    case "批量充值":
                        if(plate_id.size()==0){
                            Toast.makeText(mainActivity,"请勾选小车",Toast.LENGTH_SHORT).show();
                        }else {
                         showDialog();
                            plate_id.clear();
                            car_id.clear();
                        }
                        break;
                    case "充值记录":
                        break;
                }
                return false;
            }
        };
        mainActivity.toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }



    private void Net(int i) {
        HttpThread httpThread = new HttpThread(HttpHelper.Get_Car_Balance,i+"", handler);
        httpThread.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_1, null);
        initData();
        initView(inflate);

        return inflate;
    }

    private void initData() {
        for (int i = 1; i <5 ; i++) {
            Net(i);
        }
    }

    private void initView(View inflate) {
        car_id = new ArrayList<>();
        plate_id = new ArrayList<>();
        balance = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            balance.add(i, String.valueOf(10+new Random().nextInt(300)));
//        }

        lv_car = (ListView) inflate.findViewById(R.id.lv_car);
        tv_car_top_up_plate = (TextView) inflate.findViewById(R.id.tv_car_top_up_plate);


    }

    private class MyAdapter extends MyBaseAdapter {


        private TextView tv_car_number;
        private ImageView iv_car_icon;
        private TextView tv_car_plate;
        private TextView tv_car_name;
        private CheckBox cb_car_1;
        private Button bt_car_gold;

        public MyAdapter(ArrayList list) {
            super(list);
        }

        @Override
        public View setView(final int position, View convertView, ViewGroup parent) {
            final int i = position + 1;
            convertView = View.inflate(getActivity(), R.layout.car_list_item, null);
            final ViewHolder viewHolder = new ViewHolder(convertView);
            viewHolder.tv_car_name.setText(name[position] + "");
            viewHolder.tv_car_number.setText(i + "");
            viewHolder.tv_car_plate.setText(car_number[position] + i + "");
            //  viewHolder.iv_car_icon.setImageBitmap();
            JSONObject jo = balance.get(i);
            try {
                viewHolder.tv_car_balance.setText(jo.getString("Balance")+"");
            } catch (JSONException e) {

            }
            viewHolder.cb_car_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String s = viewHolder.tv_car_plate.getText().toString();
                    if (isChecked) {
                        if (!TextUtils.isEmpty(s)) {
                            car_id.add(i+"");
                            plate_id.add(s);
                        }
                    } else {
                        if(plate_id.size()!=0){
                            for (int j = 0; j < plate_id.size(); j++) {
                                if(plate_id.get(j).equals(s)){
                                    car_id.remove(j);
                                    plate_id.remove(j);
                                }
                            }
                        }
                    }
                }
            });
            viewHolder.bt_car_gold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(plate_id.size()==0){
                        Toast.makeText(mainActivity,"请勾选小车",Toast.LENGTH_SHORT).show();
                    }else {
                        showDialog();
                        plate_id.clear();
                        car_id.clear();

                    }
                }
            });

            return convertView;
        }

        public class ViewHolder {
            private  TextView tv_car_balance;
            public View rootView;
            public TextView tv_car_number;
            public ImageView iv_car_icon;
            public TextView tv_car_plate;
            public TextView tv_car_name;
            public CheckBox cb_car_1;
            public Button bt_car_gold;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_car_number = (TextView) rootView.findViewById(R.id.tv_car_number);
                this.iv_car_icon = (ImageView) rootView.findViewById(R.id.iv_car_icon);
                this.tv_car_plate = (TextView) rootView.findViewById(R.id.tv_car_plate);
                this.tv_car_name = (TextView) rootView.findViewById(R.id.tv_car_name);
                this.cb_car_1 = (CheckBox) rootView.findViewById(R.id.cb_car_1);
                this.bt_car_gold = (Button) rootView.findViewById(R.id.bt_car_gold);
                this.tv_car_balance = (TextView) rootView.findViewById(R.id.tv_car_balance);
            }

        }

    }

    private void showDialog() {
        View inflate = View.inflate(getActivity(), R.layout.car_dialog_layout, null);
        final EditText et_car_top_gold = (EditText) inflate.findViewById(R.id.et_car_top_gold);
        TextView tv_car_top_up_plate = (TextView) inflate.findViewById(R.id.tv_car_top_up_plate);
        tv_car_top_up_plate.setText("小车车牌："+plate_id.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String trim = et_car_top_gold.getText().toString().trim();
                int gold = Integer.parseInt(trim);
                if(!TextUtils.isEmpty(trim)&&(gold<=999)&&(gold>=1)){
                    Net2(car_id,gold);
                    dialog.dismiss();

                }else {
                    Toast.makeText(getActivity(),"请输入正确金额",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        //builder.setTitle("小车充值");
        AlertDialog dialog = builder.create();
        dialog.setView(inflate);
        dialog.show();
    }

    private void Net2(ArrayList<String> car_id, int gold) {
        //小车充值

        new HttpThread(HttpHelper.Set_Car_top_up,"{'CarId':"+car_id+",'Money':"+gold+"}",handler2);


    }
}

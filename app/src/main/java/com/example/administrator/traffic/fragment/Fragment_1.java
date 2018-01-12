package com.example.administrator.traffic.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.LinearLayout;
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
import com.example.administrator.traffic.util.SpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2018/1/7.
 */

public class Fragment_1 extends Fragment {
    public final static int fragment_1_handler_1=101;
    public final static int fragment_1_handler_2=102;
    private  MainActivity mainActivity;
    ArrayList<HashMap<String,Boolean>> checkStatus=new ArrayList();
    private View inflate;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==fragment_1_handler_1) {
                balance.clear();
                    LoadBean(msg);
                    if(balance.size()!=0){
                        if(myAdapter==null) {
                            myAdapter = new MyAdapter(balance);
                            lv_car.setAdapter(myAdapter);
                        }else {
                            myAdapter.notifyDataSetChanged();
                        }
                    }
            }
        }
    };

    private Handler handler3 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                bitmaps.add((Bitmap) msg.obj);
            }
        }
    };
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<CarBean> top_up_list;
    private int gold;
    private boolean isSendTopUp=true;

    private void LoadBean(Message msg) {
        try {

            JSONObject jo = new JSONObject(String.valueOf(msg.obj));
            JSONArray ja =new JSONArray(jo.getString("batch"));
            int i =0;
            while (i<4){
                CarBean carBean = new CarBean();
                JSONObject jo1= ja.getJSONObject(i);
                carBean.setBanlance(Integer.parseInt(jo1.getString("Banlance")));
                if(i==2){
                    carBean.setCarId(Integer.parseInt(jo1.getString("CarId"))+1);
                }else {
                    carBean.setCarId(Integer.parseInt(jo1.getString("CarId")));
                }
                carBean.setCarlogo(jo1.getString("carlogo"));
                carBean.setHphm(jo1.getString("hphm"));
                carBean.setName(jo1.getString("name"));
                balance.add(carBean);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==fragment_1_handler_2){
                if(msg.obj.toString().contains("ok")){
                    isSendTopUp=false;//发送一次充值记录
                    //充值成功
                    ArrayList<String> str_list = new ArrayList<>();//充值记录
                    String str ="";
                    Log.d("tag","充值id有多少<<>>>>"+top_up_list.size());
                    for (int i = 0; i < top_up_list.size(); i++) {
                        CarBean carBeen = top_up_list.get(i);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//充值时间
                        String time = simpleDateFormat.format(new Date());
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");//充值时间
                        String date = simpleDateFormat1.format(new Date());//充值日期
                        Log.d("tag","时间>>>>>>"+time+".....data====="+date);

                        String UserName = SpUtil.getString(getActivity(), "user_name", "user1");//充值人
                        String hphm = carBeen.getHphm();//充值车牌号
                        //需要取得充值金额  有充值小车号
                        int banlance = carBeen.getBanlance();//充值前余额
                        int money =banlance+gold;
                        str="{\"date\":"+"\""+date+"\""+",\"UserName\":"+"\""+UserName+"\""+",\"hphm\":"+"\""+hphm+"\""+",\"gold\":"+"\""+gold+"\""+",\"money\":"+"\""+money+"\""+",\"time\":"+"\""+time+"\""+"}";
                        Log.d("tag","充值记录================"+str);
                        str_list.add(str);
                        Log.d("tag","充值记录================"+str_list.toString());
                    }
                        //假设存入balance的索引值
                    SpUtil.putString(getActivity(),"car_top_up",str_list.toString());
                    Log.e("tag","balanced 的值==="+str_list.toString());
                    String car_top_up = SpUtil.getString(getActivity(), "car_top_up", "");
                    Log.e("tag","car_top_up 的值==="+car_top_up.toString());
                        Net();
                    isTopUp=false;  //充值成功清除id

                }else {
                    isSendTopUp=false;//发送一次充值记录
                    top_up_list.clear();
                    Toast.makeText(getActivity(),"充值失败",Toast.LENGTH_SHORT).show();
                }
                myAdapter.notifyDataSetChanged();
            }
        }
    };

    private TextView tv_test1;
    private ListView lv_car;
    private ArrayList<String> car_id;
    private ArrayList<String> plate_id;
    private TextView tv_car_top_up_plate;
    private ArrayList<CarBean> balance;
    private MyAdapter myAdapter;
    private boolean isTopUp=true;//设置是否清除id数组值


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolBar();

    }

    public void initToolBar() {
        MainActivity activity = (MainActivity) getActivity();
        activity.toolbar.inflateMenu(R.menu.car_menu);

        Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.toString()){
                    case "批量充值":
                        if(plate_id.size()==0){
                            Toast.makeText(mainActivity,"请勾选小车",Toast.LENGTH_SHORT).show();
                        }else {
                            //根据小车id的值去获取carbean的值
                            Log.d("tag","小车id的值______"+car_id.toString());
                            Log.d("tag","小车balance的值______"+balance.toString());
                            String tem = "";
                            showDialog();
//                            if(isTopUp){
//                                car_id.clear();
//                                plate_id.clear();
//                            }
                        }
                        break;
                    case "充值记录":
                        //跳转到个人中心
//                        FragmentManager fm = getFragmentManager();
//                        FragmentTransaction transaction = fm.beginTransaction();
//                        transaction.replace(R.id.fl_main,new Fr)
                        break;
                }
                return false;
            }
        };
        activity.toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }



    private void Net() {
       // HttpThread httpThread = new HttpThread(HttpHelper.Get_Car_Balance,"{'CarId':"+i+",'UserName':user1}", handler);
        HttpThread httpThread = new HttpThread("GetCarAccountBalanceAll.do","", handler,fragment_1_handler_1);
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
            Net();
    }

    private void initView(View inflate) {
        top_up_list = new ArrayList<>();
        car_id = new ArrayList<>();
        plate_id = new ArrayList<>();
        balance = new ArrayList<>();
        bitmaps = new ArrayList<>();//用于保存图片
        lv_car = (ListView) inflate.findViewById(R.id.lv_car);
        tv_car_top_up_plate = (TextView) inflate.findViewById(R.id.tv_car_top_up_plate);
        lv_car.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("car_list====",v.toString());
            }
        });
    }

    private class MyAdapter extends MyBaseAdapter {


        private TextView tv_car_number;
        private ImageView iv_car_icon;
        private TextView tv_car_plate;
        private TextView tv_car_name;
        private CheckBox cb_car_1;
        private Button bt_car_gold;
        private  int[] ints;


        public MyAdapter(ArrayList list) {
            super(list);
            ints = new int[]{
                    R.drawable.car_bm, R.drawable.car_bc, R.drawable.car_mzd, R.drawable.car_dz
            };
        }

        @Override
        public View setView( int position, View convertView, ViewGroup parent) {

              final CarBean carBean = balance.get(position);
            convertView = View.inflate(getActivity(), R.layout.car_list_item, null);
            final ViewHolder viewHolder = new ViewHolder(convertView);
            viewHolder.tv_car_name.setText(carBean.getName()+ "");
            final int carId = carBean.getCarId();
            viewHolder.tv_car_number.setText( carId+ "");
            viewHolder.tv_car_plate.setText(carBean.getHphm() + "");
        //    loadIcon(carBean.getCarlogo());
            //viewHolder.iv_car_icon.setImageBitmap(bitmaps.get(position));
            viewHolder.iv_car_icon.setImageResource(ints[position]);
            viewHolder.tv_car_balance.setText(carBean.getBanlance()+"");
          //  Log.d("car","余额2====="+carBean.getBanlance());
            if(carBean.getBanlance()< Integer.parseInt(SpUtil.getString(getActivity(),"warm","50"))){
                viewHolder.ll_car_root.setBackgroundColor(Color.YELLOW);
            }else {
                viewHolder.ll_car_root.setBackgroundColor(Color.GRAY);
            }
            viewHolder.cb_car_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String s = viewHolder.tv_car_plate.getText().toString();
                    HashMap<String, Boolean> hashMap = new HashMap<>();
                    hashMap.put(String.valueOf(buttonView.getId()),isChecked);
                    checkStatus.add(hashMap);
                    if (isChecked) {
                        if (!TextUtils.isEmpty(s)) {

                                car_id.add(carId+"");
                                plate_id.add(s);
                                Log.d("car_id","小车id的值====="+plate_id.toString());

                        }
                    } else {
                        if(plate_id.size()!=0){
                            for (int j = 0; j < plate_id.size(); j++) {
                                if(plate_id.get(j).equals(s)){
                                    car_id.remove(j);
                                    plate_id.remove(j);
                                    Log.d("car_id","小车id的值====="+car_id.toString());

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
                        if(plate_id.size()>1){
                            Toast.makeText(getActivity(),"请选择批量设置",Toast.LENGTH_SHORT).show();
                        }else {
                            showDialog();
//                            if(isTopUp){
//                                car_id.clear();
//                                plate_id.clear();
//                            }
                        }
                    }
                }
            });

            return convertView;
        }

        public class ViewHolder {
            private LinearLayout ll_car_root;
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
                this.ll_car_root = (LinearLayout)rootView.findViewById(R.id.ll_car_root);
            }

        }

    }


    private void showDialog() {
        Log.d("car_id","小车id的值=====消息框"+car_id.toString());
        View inflate = View.inflate(getContext(), R.layout.car_dialog_layout, null);
        final EditText et_car_top_gold = (EditText) inflate.findViewById(R.id.et_car_top_gold);
        TextView tv_car_top_up_plate = (TextView) inflate.findViewById(R.id.tv_car_top_up_plate);
        tv_car_top_up_plate.setText("小车车牌："+plate_id.toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String trim = et_car_top_gold.getText().toString().trim();
                gold = Integer.parseInt(trim);
                if(!TextUtils.isEmpty(trim)&&(gold <=999)&&(gold >=1)){
                    Log.d("car_id","小车id的值=====按下确定"+car_id.toString());
                           int i=0;
                          while (i<car_id.size()){
                              Net2(car_id.get(i), gold);
                              i++;
                          }
                    dialog.dismiss();
//                    plate_id.clear();
//                    car_id.clear();
                }else {
                    Toast.makeText(getActivity(),"请输入正确金额",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                plate_id.clear();
                car_id.clear();
            }
        });
        //builder.setTitle("小车充值");
        AlertDialog dialog = builder.create();
        dialog.setView(inflate);
        dialog.show();
    }

    private void Net2(String car_id, int gold) {
        //小车充值
        if(this.car_id!=null&&isSendTopUp){
            Log.d("tag","充值id有多少<<car_id>>>"+this.car_id.size());
            for (int i = 0; i < this.car_id.size(); i++) {
                CarBean carBean1 = balance.get(Integer.parseInt(this.car_id.get(i))-1);
                top_up_list.add(carBean1);
            }
            this.car_id.clear();
            plate_id.clear();
            Log.e("tag","top_up_list 的值==="+top_up_list.toString());
        }
        HttpThread httpThread = new HttpThread(HttpHelper.Set_Car_top_up, "{'CarId':" + car_id + ",'Money':" + gold + "}", handler2,fragment_1_handler_2);
        httpThread.start();

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Menu menu = mainActivity.toolbar.getMenu();
        menu.clear();
    }
}

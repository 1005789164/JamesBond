package com.example.administrator.traffic.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.traffic.MainActivity;
import com.example.administrator.traffic.R;
import com.example.administrator.traffic.util.SpUtil;


public class Fragment_9 extends Fragment {
    MainActivity mainActivity;
    Button bt_dj, bt_sos;
    String number_sos, number_drive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从SpUtil里读取数据并赋值给sos和drive
        number_sos = SpUtil.getString(getActivity(), "number_sos", "13915674589");
        number_drive = SpUtil.getString(getActivity(), "number_drive", "13800000000");


        //加载menu中的菜单
        mainActivity.toolbar.inflateMenu(R.menu.sos_menu);

        ((TextView) mainActivity.toolbar.findViewById(R.id.tv_tb_title)).setText("创意设计");
        mainActivity.toolbar.findViewById(R.id.menu_BulkRecharge).setVisibility(View.GONE);
        mainActivity.toolbar.findViewById(R.id.menu_RechargeRecord).setVisibility(View.GONE);

        //设置toolbar主菜单的监听
        mainActivity.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final View view;
                final AlertDialog dialog;
                switch (item.getItemId()) {
                    case R.id.menu_numberSet:
                        //创建一个dialog菜单对象
                        dialog = new AlertDialog.Builder(getActivity()).create();
                        //将R.layout.dialog_numberset布局加载到view变量里
                        view = View.inflate(getActivity(), R.layout.dialog_numberset, null);
                        //设置dialog菜单的标题名
                        dialog.setTitle("号码设置");
                        //将view设置到dialog菜单里
                        dialog.setView(view);
                        //显示dialog
                        dialog.show();

                        ((EditText) view.findViewById(R.id.et_sosnumber)).setText(number_sos);
                        ((EditText) view.findViewById(R.id.et_drivenumber)).setText(number_drive);


                        view.findViewById(R.id.bt_numDialog_ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String number_sos = ((EditText) view.findViewById(R.id.et_sosnumber)).getText().toString();
                                String number_drive = ((EditText) view.findViewById(R.id.et_drivenumber)).getText().toString();
                                if (number_drive.isEmpty() || number_sos.isEmpty()) {
                                    Toast.makeText(getActivity(), "电话号码不能为空。", Toast.LENGTH_SHORT).show();
                                } else {
                                    SpUtil.putString(getActivity(), "number_sos", number_sos);
                                    SpUtil.putString(getActivity(), "number_drive", number_drive);
                                    Toast.makeText(getActivity(), "电话号码设置成功。", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                        view.findViewById(R.id.bt_numDialog_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                }
                return false;
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_9, null);
        return view;
    }

    public Fragment_9(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bt_dj = (Button) view.findViewById(R.id.bt_dj);
        bt_sos = (Button) view.findViewById(R.id.bt_sos);


        bt_dj.setOnClickListener(new MyClick());
        bt_sos.setOnClickListener(new MyClick());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Menu menu = mainActivity.toolbar.getMenu();
        menu.clear();
    }


    class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_dj:
                    String gps = getGps();
                    String text_dj = "我在" + gps + "请速来代驾";
                    sendCall(number_drive);
                    sendSms(number_drive, text_dj);
                    break;
                case R.id.bt_sos:
                    String gps1 = getGps();
                    String text_sos = "我在" + gps1 + "请速来代驾";
                    sendCall(number_sos);
                    sendSms(number_sos, text_sos);
                    break;
            }
        }
    }

    private void sendCall(String number) {
        int call = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
        if (call == PackageManager.PERMISSION_GRANTED) {
            Uri uri = Uri.parse("tel:" + number);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private void sendSms(String number, String text) {
        int sms = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);
        if (sms == PackageManager.PERMISSION_GRANTED) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, text, null, null);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 2);
        }
    }

    private String getGps() {
        LocationManager lm;
        Location location;
        lm = (LocationManager) mainActivity.getSystemService(mainActivity.getApplicationContext().LOCATION_SERVICE);
        Double gpsLatitude = 0.0, gpsLongitude = 0.0;
        int gps = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (gps == PackageManager.PERMISSION_GRANTED) {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                gpsLatitude = location.getLatitude();
                gpsLongitude = location.getLongitude();
            } else {
                gpsLatitude = 28.259 + Math.random() + 0.01;
                gpsLongitude = 113.075 + Math.random() + 0.01;


            }
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
        }
        String Data = gpsLatitude.toString() + "," + gpsLongitude.toString();
        return Data;
    }

}


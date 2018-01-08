package com.example.administrator.traffic.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.traffic.R;

import java.util.ArrayList;
import java.util.Objects;

public class Fragment_9 extends Fragment {
    private Object str;
    Double gpsLatitude, gpsLongitude;
    LocationManager lm;
    Location location;
    private Button bt_dj, bt_sos;
    private static final int CALL_REQUEST_CODE = 1, SMS_REQUEST_CODE = 2, REQUEST_CODE = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_9, null);
        return view;
    }

    public Fragment_9(Object str) {
        this.str = str;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bt_dj = (Button) view.findViewById(R.id.bt_dj);
        bt_sos = (Button) view.findViewById(R.id.bt_sos);
        lm = (LocationManager) str;
        getGpsPermissions();
        sos();
        yjdj();
    }

    public void getGpsPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int pass = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (pass == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            } else {
                getGps();
            }
        } else {
            getGps();
        }
    }

    public void getGps() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            gpsLatitude = location.getLatitude();
            gpsLongitude = location.getLongitude();
        } else {
            gpsLatitude = 120.111;
            gpsLongitude = 90.112;
        }

    }

    public void sos() {
        bt_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:10010");
                getCallPer(uri);
                String number = "10010";
                String text = "我在“" + gpsLatitude + ":" + gpsLongitude + "”，请速来定损，谢谢！";
                //getSmsPermissions(number, text);
            }
        });
    }

    public void yjdj() {
        bt_dj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:10010");
                getCallPer(uri);
                String number = "10010";
                String text = "我在“" + gpsLongitude + ":" + gpsLongitude + "”，请赶到此位置代驾";
                getSmsPermissions(number, text);
            }
        });
    }

    public void getCall(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        startActivity(intent);
    }

    public void getCallPer(Uri uri) {
        if (Build.VERSION.SDK_INT >= 23) {
            int pass = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
            if (pass == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);
            } else {
                getCall(uri);
            }
        } else {
            getCall(uri);
        }
    }

    public void getSmsPermissions(String number, String text) {

        if (Build.VERSION.SDK_INT >= 23) {
            int pass = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);
            if (pass == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_REQUEST_CODE);
            } else {
                getSms(number, text);
            }
        } else {
            getSms(number, text);
        }
    }

    public void getSms(String number, String text) {
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        ArrayList<String> list = smsManager.divideMessage(text);
        for (String text1 : list) {
            smsManager.sendTextMessage(number, null, text1, null, null);
        }
    }
}


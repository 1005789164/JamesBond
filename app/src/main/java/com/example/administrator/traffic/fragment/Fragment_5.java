package com.example.administrator.traffic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.traffic.R;
import com.example.administrator.traffic.ViolationActivity;
import com.example.administrator.traffic.http.HttpHelper;
import com.example.administrator.traffic.http.HttpThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/11.
 */

public class Fragment_5 extends Fragment implements View.OnClickListener {
    private EditText et_violation_query;
    private Button bt_violation_query;
    public final static  int fragment_5_handler_1=501;
    public static ArrayList<String> violation_list = new ArrayList<>();
    private Handler  handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==fragment_5_handler_1){
                Intent intent = new Intent(getActivity(), ViolationActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                intent.putExtra("violation",msg.obj.toString());
                if(!TextUtils.isEmpty(msg.obj.toString())){
                    try {
                        JSONObject jsonObject = new JSONObject(msg.obj.toString());
                        String result = jsonObject.getString("result");
                        violation_list.add(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    violation_list.add("");
                }
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("violation",violation_list);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_5, null);
//        Log.e("TAG","Fragment4 ====================================================OnCreate");
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        et_violation_query = (EditText) inflate.findViewById(R.id.et_violation_query);
        bt_violation_query = (Button) inflate.findViewById(R.id.bt_violation_query);
        bt_violation_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_violation_query:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String query = et_violation_query.getText().toString().trim();
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getContext(), "query不能为空", Toast.LENGTH_SHORT).show();
        }else {
            Net1(query);
        }
    }

    private void Net1(String query) {
        HttpThread httpThread = new HttpThread(HttpHelper.Get_Violation, "{hphm:湘" + query + "}", handler1, fragment_5_handler_1);
        httpThread.start();
    }



    public interface GetViolationDes{

    }


}

package com.example.administrator.traffic;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.traffic.MyApp.MyApp;
import com.example.administrator.traffic.http.HttpThread;
import com.example.administrator.traffic.util.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText et_user_name, et_user_pwd;
    Button bt_register, bt_login;
    CheckBox cb_auto_login, cb_save_pwd;
    View view;
    static final int REQUST_CODE=31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.set);
        //将net_menu菜单加载到toolbar中
        ((Toolbar)findViewById(R.id.toolbar)).inflateMenu(R.menu.net_menu);
        //把set1这张图片设置到toolbar左上角的图标里
        ((ImageView)findViewById(R.id.iv_tb_icon)).setImageResource(R.drawable.set1);
        //将toolbar的标题设为“用户登录”
        ((TextView)findViewById(R.id.tv_tb_title)).setText("用户登录");
        //初始化控件
        initView();
        getUserInfo();
        //设置toolbar的菜单监听
        ((Toolbar)findViewById(R.id.toolbar)).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //new一个dialog对象并创建dialog菜单
                final AlertDialog dialog=new AlertDialog.Builder(LoginActivity.this).create();
                //设置dialog标题
                dialog.setTitle("IP设置");
                //将dialog_setip布局加载给view变量
                view=View.inflate(LoginActivity.this,R.layout.dialog_setip,null);
                //将view设置进dialog
                dialog.setView(view);
                //显示dialog菜单
                dialog.show();
                //当sp里有数据时读取并显示在dialog的ExitView里
                getIpInfo();
                //对dialog菜单里的确定按钮进行监听
                view.findViewById(R.id.bt_ip_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ip1=((EditText)view.findViewById(R.id.et_ip1)).getText().toString();
                        String ip2=((EditText)view.findViewById(R.id.et_ip2)).getText().toString();
                        String ip3=((EditText)view.findViewById(R.id.et_ip3)).getText().toString();
                        String ip4=((EditText)view.findViewById(R.id.et_ip4)).getText().toString();
                        String str_ip=ip1+"."+ip2+"."+ip3+"."+ip4;

                        if(TextUtils.isEmpty(ip1)||TextUtils.isEmpty(ip2)||TextUtils.isEmpty(ip3)||TextUtils.isEmpty(ip4)){
                            Toast.makeText(LoginActivity.this,"IP地址不能为空！",Toast.LENGTH_SHORT).show();
                        }else {
                            //检查ip合法性
                           boolean b= MyApp.getInstance().checkIp(str_ip);
                            if(b){
                                //储存到sp
                                SpUtil.putString(LoginActivity.this,"ip",str_ip);
                                //关闭dialog
                                dialog.dismiss();
                            }else {
                                Toast.makeText(LoginActivity.this,"IP地址无效！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                //取消按钮监听
                view.findViewById(R.id.bt_ip_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });

    }

    public void initView() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_pwd = (EditText) findViewById(R.id.et_user_pwd);
        cb_auto_login = (CheckBox) findViewById(R.id.cb_auto_login);
        cb_save_pwd = (CheckBox) findViewById(R.id.cb_save_pwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_login.setOnClickListener(new MyClick());
        bt_register.setOnClickListener(new MyClick());


    }

    class MyClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_login:
                    isLogin();
                    break;
                case R.id.bt_register:
                    break;
            }

        }
    }

    private void getIpInfo(){
        String ip=SpUtil.getString(LoginActivity.this,"ip","");
        if(!TextUtils.isEmpty(ip)){
            String [] ips=ip.split("\\.");
            ((EditText)view.findViewById(R.id.et_ip1)).setText(ips[0]);
            ((EditText)view.findViewById(R.id.et_ip2)).setText(ips[1]);
            ((EditText)view.findViewById(R.id.et_ip3)).setText(ips[2]);
            ((EditText)view.findViewById(R.id.et_ip4)).setText(ips[3]);
        }
    }
    private void getUserInfo() {
        String user_name = SpUtil.getString(getApplicationContext(), "user_name", "");
        String user_pwd = SpUtil.getString(getApplicationContext(), "user_pwd", "");
        et_user_name.setText(user_name);
        et_user_pwd.setText(user_pwd);
        if (!TextUtils.isEmpty(user_name)&&!TextUtils.isEmpty(user_pwd)) {
            cb_save_pwd.setChecked(true);

        }
    }

    private void CheckOption() {
        if (cb_auto_login.isChecked()) {
            SpUtil.putBoolean(getApplicationContext(), "auto", true);
        }
        if (cb_save_pwd.isChecked()) {
            SpUtil.putString(getApplicationContext(), "user_name", et_user_name.getText().toString());
            SpUtil.putString(getApplicationContext(), "user_pwd", et_user_pwd.getText().toString());
        }else {
            SpUtil.putString(getApplicationContext(), "user_name", et_user_name.getText().toString());
            SpUtil.putString(getApplicationContext(), "user_pwd", "");
        }
    }

    private void isLogin() {
        //1.获取用户输入
        //2.有效性判断
        //3.调用服务器的接口
        //4.记住用户权限
        JSONObject object = new JSONObject();
            try {
            String user_name = et_user_name.getText().toString();
            String user_pwd = et_user_pwd.getText().toString();
            if (TextUtils.isEmpty(user_name)|| TextUtils.isEmpty(user_pwd)) {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            object.put("username", user_name);
            object.put("password", user_pwd);
            String url = "user_login.do";
            HttpThread thread = new HttpThread(url, object.toString(), mhandler,REQUST_CODE);
            thread.start();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                try {
                    JSONObject jsonObject = new JSONObject(msg.obj.toString());
                    String str = jsonObject.getString("result");
                    if (str.equals("s")) {
                        MyApp.getInstance().setUserRole(jsonObject.getString("UserRole"));
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       // SpUtil.putString(getApplicationContext(), "user_name", et_user_name.getText().toString());
                        CheckOption();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

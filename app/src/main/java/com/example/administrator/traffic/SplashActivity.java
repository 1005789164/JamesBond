package com.example.administrator.traffic;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.solver.widgets.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.example.administrator.traffic.util.SpUtil;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout splash;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean isFirst=SpUtil.getBoolean(SplashActivity.this,"first",true);

        if(isFirst){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   isAutoLogin();
                }
            }, 2000);
        }else {
            isAutoLogin();
        }


//        splash= (LinearLayout) findViewById(R.id.splash);
//        AlphaAnimation animator=new AlphaAnimation(0.0f,1.0f);
//        animator.setDuration(2000);
//        animator.start();
//        splash.setAnimation(animator);
//
//        animator.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
//                startActivity(intent);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

    }
        private void isAutoLogin(){
            boolean isAuto=SpUtil.getBoolean(getApplicationContext(),"auto",false);
            if(isAuto){
                intent = new Intent(SplashActivity.this, MainActivity.class);
            }else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(intent);
        }

}

package com.example.administrator.traffic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.traffic.http.HttpIcon;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/1/12.
 */

public class ViolationPhotoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView gl_iv_1;
    private ImageView gl_iv_2;
    private ImageView gl_iv_3;
    private ImageView gl_iv_4;
    private GridLayout gl_layout;
    private Button bt_finish;
    private RelativeLayout fl_photo_root;
    private ArrayList<Bitmap> bitmaps;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==200){
                for (int i = 0; i < 4; i++) {
                    bitmaps.add(httpIcon.getBitmap());
                }
                if(bitmaps.size()!=0){
                    gl_iv_1.setImageBitmap(bitmaps.get(0));
                    gl_iv_2.setImageBitmap(bitmaps.get(1));
                    gl_iv_3.setImageBitmap(bitmaps.get(2));
                    gl_iv_4.setImageBitmap(bitmaps.get(3));
                }
            }
        }
    };
    private HttpIcon httpIcon;
    private boolean once=true;
    private MyView myView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pohoto_layout);
        initView();
        initData();
    }

    private void initData() {
        bitmaps=new ArrayList<>();
        Intent intent = getIntent();
        String photo = intent.getStringExtra("photo");
//        HttpIcon.post(photo,handler);
        httpIcon = new HttpIcon();
        httpIcon.post(photo,handler);
    }

    private void initView() {
        gl_iv_1 = (ImageView) findViewById(R.id.gl_iv_1);
        gl_iv_2 = (ImageView) findViewById(R.id.gl_iv_2);
        gl_iv_3 = (ImageView) findViewById(R.id.gl_iv_3);
        gl_iv_4 = (ImageView) findViewById(R.id.gl_iv_4);
        gl_layout = (GridLayout) findViewById(R.id.gl_layout);
        bt_finish = (Button) findViewById(R.id.bt_finish);
        fl_photo_root = (RelativeLayout) findViewById(R.id.fl_photo_root);
        bt_finish.setOnClickListener(this);
        myView = new MyView(getApplicationContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height=600;
        layoutParams.width=600;
        myView.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_finish:
                finish();
                break;
        }
    }


    public void Magnify(View v){

        switch (v.getId()){
            case R.id.gl_iv_1:
                myView.setImageBitmap(bitmaps.get(0));
                break;
            case R.id.gl_iv_2:
                myView.setImageBitmap(bitmaps.get(1));
                break;
            case R.id.gl_iv_3:
                myView.setImageBitmap(bitmaps.get(2));
                break;
            case R.id.gl_iv_4:
                myView.setImageBitmap(bitmaps.get(3));
                break;
        }
        fl_photo_root.addView(myView);
        gl_layout.setVisibility(View.GONE);
    }

    @SuppressLint("AppCompatCustomView")
    private class MyView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
        private Matrix matrix = new Matrix();
        private  float[] MatrixFloat = new float[9];
        private   float Scale_Max=4.0f;//最大缩放因子
        private   float Scale_Min=1.0f;//最小缩放因子

        private ScaleGestureDetector scaleGestureDetector;

        public MyView(Context context) {
            this(context,null);

        }

        public MyView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            super.setScaleType(ScaleType.MATRIX);
            Log.d("tag","111111111122");
            scaleGestureDetector = new ScaleGestureDetector(context, this);
            this.setOnTouchListener(this);
        }


        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Drawable drawable = getDrawable();
            if(drawable==null)return true;
            float scaleFactor = detector.getScaleFactor();
            float scale = getScale();
            if(scale<Scale_Max&&scaleFactor>1.0f||
                    scale>Scale_Min&&scaleFactor<1.0f){
                matrix.postScale(scaleFactor,scaleFactor,getWidth()/2,getHeight()/2);
                setImageMatrix(matrix);
            }
            Log.d("tag","111111");
            return true;
        }

        private float getScale() {
            matrix.getValues(MatrixFloat);
            return MatrixFloat[Matrix.MSCALE_X];
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return scaleGestureDetector.onTouchEvent(event);
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        @SuppressWarnings("deprecation")
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

        @Override
        public void onGlobalLayout() {
            if(once){
                Drawable drawable = getDrawable();
                if(drawable==null)return;
                float scale = 1.0f;
                Scale_Min=scale;
                matrix.postScale(scale,scale,getWidth()/2,getHeight()/2);
                setImageMatrix(matrix);
                once=false;
            }
        }
    }
}

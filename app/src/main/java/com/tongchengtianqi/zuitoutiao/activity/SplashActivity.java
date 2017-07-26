package com.tongchengtianqi.zuitoutiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongchengtianqi.zuitoutiao.MainActivity;
import com.tongchengtianqi.zuitoutiao.R;
import com.tongchengtianqi.zuitoutiao.base.BaseActivity;

//闪屏登陆页面
public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mSplashBackgroundImg;
    private TextView mSplashRemainingTime;
    private ImageView mSplashSkip;
    private Handler handler;
    private MyCountDownTimer mTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        getWindowManager().getDefaultDisplay().getHeight();
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        handler = new Handler();
        mTimer = new MyCountDownTimer(6000, 1000);
        mTimer.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jundToMainActivity();
            }
        }, 6000);
    }

    @Override
    protected void initView() {
        mSplashBackgroundImg = (ImageView) findViewById(R.id.splash_background_img);
        mSplashRemainingTime = (TextView) findViewById(R.id.splash_remaining_time);
        mSplashSkip = (ImageView) findViewById(R.id.splash_skip);
        /*获取状态栏高度*/
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID  
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值  
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
    }

    @Override
    protected void initListener() {
        mSplashSkip.setOnClickListener(this);
        mSplashBackgroundImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.splash_skip:
                //跳转到主页的方法，并关闭自身页面
                jundToMainActivity();
                break;
            case R.id.splash_background_img:
                //跳转到详情页面，并关闭自身页面
                jundToMainActivity();
                break;
            default:
                break;
        }
    }

    //跳转到主页的方法，并关闭自身页面
    public void jundToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //activity销毁时清除所有消息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    class MyCountDownTimer extends CountDownTimer {

        //参1表示以毫秒为单位 倒计时的总数  参2表示间隔多少毫秒调用一次onTick方法
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mSplashRemainingTime.setText(l / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            mSplashRemainingTime.setText("正在跳转");
        }
    }
}

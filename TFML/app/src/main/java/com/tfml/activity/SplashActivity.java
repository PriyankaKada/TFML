package com.tfml.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.tfml.R;

public class SplashActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showBasicSplash();
    }
     public void showBasicSplash()
     {
       new Thread()
       {
           @Override
           public void run() {
               super.run();
               try {
                   Thread.sleep(1500);
                   handler.sendEmptyMessage(0);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }.start();

     }
    Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this,BannerActivity.class));
            finish();
        }
    };

}

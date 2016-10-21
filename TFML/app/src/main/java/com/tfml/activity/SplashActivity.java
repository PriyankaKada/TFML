package com.tfml.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.auth.Constant;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.logResponseModel.LogInputModel;
import com.tfml.model.logResponseModel.LogResponseModel;
import com.tfml.util.PreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {
    TmflApi tmflApi;
    LogInputModel logInputModel;
    LogResponseModel logResponseModel;
    private String TAG = "SplashLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tmflApi = ApiService.getInstance().call();
        logInputModel = new LogInputModel();
        logResponseModel = new LogResponseModel();
        showBasicSplash();
    }

    public void showBasicSplash() {
        new Thread() {
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (PreferenceHelper.getBoolean("SaveLogin")) {
                Log.e(TAG, "savelogin" + "" + PreferenceHelper.getString(PreferenceHelper.USER_ID));
                if (CommonUtils.isNetworkAvailable(SplashActivity.this)) {
                    Log.e(TAG, PreferenceHelper.getString(PreferenceHelper.API_TOKEN) + " " + PreferenceHelper.getString(PreferenceHelper.USER_ID));
                    logInputModel.setApi_token(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
                    logInputModel.setUser_id(PreferenceHelper.getString(PreferenceHelper.USER_ID));
                    CallLogService(logInputModel);
                } else {
                    Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }

            } else {
                startActivity(new Intent(SplashActivity.this, BannerActivity.class));
                finish();
            }
        }
    };

    public void CallLogService(LogInputModel logInputModel) {
        tmflApi.getLogResponse(logInputModel).enqueue(new Callback<LogResponseModel>() {
            @Override
            public void onResponse(Call<LogResponseModel> call, Response<LogResponseModel> response) {
                Log.e("isLogin", new Gson().toJson(response.body()));

                if (response.body().getStatus().toString().contains("Success")) {
                    PreferenceHelper.insertBoolean(PreferenceHelper.ISLOGIN, true);
                    startActivity(new Intent(SplashActivity.this, ContractActivity.class));
                    finish();
                } else {
                    PreferenceHelper.insertBoolean(PreferenceHelper.ISLOGIN, false);
                    startActivity(new Intent(SplashActivity.this, BannerActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LogResponseModel> call, Throwable t) {
              //  Log.e("ERROR", t.getMessage());
               // Toast.makeText(SplashActivity.this,"Network Error...",Toast.LENGTH_SHORT).show();
            }
        });

    }

}

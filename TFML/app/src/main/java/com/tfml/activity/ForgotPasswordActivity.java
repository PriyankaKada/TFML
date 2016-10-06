package com.tfml.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.forgotPasswordResponseModel.ForgotInputModel;
import com.tfml.model.forgotPasswordResponseModel.ForgotResponse;
import com.tfml.util.SetFonts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText txtUserId;
    private Button btnSubmit;
    private ImageView imgBack;
    String strUserID;
    TmflApi tmflApi;
    ForgotInputModel forgotInputModel;
    ForgotResponse forgotResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        tmflApi= ApiService.getInstance().call();
        forgotInputModel=new ForgotInputModel();
        forgotResponse=new ForgotResponse();
        init();
    }
    public void init()
    {
        txtUserId=(EditText)findViewById(R.id.txt_user_id);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        imgBack=(ImageView)findViewById(R.id.img_forgot_pass_back);
        SetFonts.setFonts(this,imgBack,2);
        btnSubmit.setOnClickListener(this);
        imgBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit:
                strUserID=txtUserId.getText().toString();
            if(CommonUtils.isNetworkAvailable(this))
            {

                if(TextUtils.isEmpty(strUserID))
                {
                    Toast.makeText(ForgotPasswordActivity.this,"Please Enter User ID", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    forgotInputModel.setUserID(strUserID);
                    callForgotService(forgotInputModel);
                }
            }
                else
            {
                Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
            }

                break;
            case R.id.img_forgot_pass_back:
                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                break;
        }
    }

    public void callForgotService(ForgotInputModel forgotInputModel)
    {
        Log.e("REq ",new Gson().toJson(forgotInputModel));
        tmflApi.getForgotResponse(forgotInputModel).enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {

                Log.e("response ",new Gson().toJson(response.body()));
                if(response.body()!=null)
                {
                    if(response.body().getStatus().toString().contains("Success"))
                    {
                        Toast.makeText(getBaseContext(), "You will get password soon", Toast.LENGTH_SHORT).show();
                        txtUserId.setText("");
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Invalid UserId", Toast.LENGTH_SHORT).show();
                    }
                }else {

                    Log.e("null","");
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
            Log.e("Failure",t.getMessage());
            }
        });
    }

}

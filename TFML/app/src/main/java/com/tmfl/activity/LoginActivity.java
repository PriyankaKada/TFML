package com.tmfl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.R;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.model.loginResponseModel.LoginRequestModel;
import com.tmfl.model.loginResponseModel.LoginResponseModel;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity implements View.OnClickListener {
    LoginRequestModel loginRequestModel;
    LoginResponseModel loginResponseModel;
    TmflApi tmflApi;
    String strApiToken, strUsrId, strEmail;
    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private String strUserID, strPassword;
    private TextView txtForgotPassword, txtLoginTitle;
    private CheckBox chkRememberMe;
    private ImageView loginBack;
    private String strPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        txtLoginTitle = (TextView) findViewById(R.id.txt_login_header);
        txtUserName = (EditText) findViewById(R.id.edt_user_name);
        txtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtForgotPassword = (TextView) findViewById(R.id.txt_forgot_password);
        chkRememberMe = (CheckBox) findViewById(R.id.chkRemember_password);
        loginBack = (ImageView) findViewById(R.id.img_login_back);
        SetFonts.setFonts(this, txtLoginTitle, 2);
        tmflApi = ApiService.getInstance().call();
        btnLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        loginBack.setOnClickListener(this);
        SetFonts.setFonts(this, btnLogin, 2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Validation();
                break;
            case R.id.txt_forgot_password:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
                break;
            case R.id.img_login_back:
                startActivity(new Intent(LoginActivity.this, BannerActivity.class));
                finish();
                break;
        }
    }

    public void Validation() {
        strUserID = txtUserName.getText().toString();
        strPassword = txtPassword.getText().toString();

        if (TextUtils.isEmpty(strUserID)) {
            Toast.makeText(getBaseContext(), "Please Enter User Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(strPassword)) {
            Toast.makeText(getBaseContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else if (!TextUtils.isEmpty(strUserID) && !TextUtils.isEmpty(strPassword)) {
            if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                loginRequestModel = new LoginRequestModel();
                loginResponseModel = new LoginResponseModel();
                loginRequestModel.setUser_id(strUserID);
                loginRequestModel.setPassword(strPassword);

                CommonUtils.showProgressDialog(LoginActivity.this, "Getting Your Information");
                CallLoginService(loginRequestModel);

            } else {
                Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Please Enter User Name and Password", Toast.LENGTH_SHORT).show();
        }
    }

    public void CallLoginService(LoginRequestModel loginRequestModel) {
        tmflApi.getLoginResponse(loginRequestModel).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {

                CommonUtils.closeProgressDialog();
                if (response.body() != null && response.body().getStatus().toString().contains("success")) {
                    PreferenceHelper.insertBoolean("SaveLogin", true);
                    strApiToken = response.body().getData().getApiToken();
                    strUsrId = response.body().getData().getUserId();
                    strEmail = response.body().getData().getEmail() == null ? "NA" : response.body().getData().getEmail().toString();
                    strPhone = response.body().getData().getMobile();
                    PreferenceHelper.insertBoolean("SaveLogin", true);
                    Log.e("API TOKEN", strApiToken);
                  /*  if (chkRememberMe.isChecked()) {
				        PreferenceHelper.insertBoolean("SaveLogin", true);
                    }
                    else
                    {
                        PreferenceHelper.insertBoolean("SaveLogin", false);
                    }*/

                    PreferenceHelper.insertString(PreferenceHelper.USER_FIRT_NAME, response.body().getData().getFname());
                    PreferenceHelper.insertString(PreferenceHelper.USER_ID, strUsrId);
                    PreferenceHelper.insertString(PreferenceHelper.API_TOKEN, strApiToken);
                    PreferenceHelper.insertString(PreferenceHelper.EMAIL, strEmail);
                    PreferenceHelper.insertString(PreferenceHelper.MOBILE, strPhone);
                    startActivity(new Intent(LoginActivity.this, ContractActivity.class));
                    finish();
                    Log.e("ResponseData", response.body().getData().getApiToken() + " " + response.body().getData().getUserId());
                    //  PreferenceHelper.insertBoolean(PreferenceHelper.ISLOGIN,true);
                    // PreferenceHelper.insertBoolean(PreferenceHelper.FLAG_LOGGED_OUT, false);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid UserName And Password", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.d("error", t.getMessage() + "");
                CommonUtils.closeProgressDialog();
            }
        });

    }

}

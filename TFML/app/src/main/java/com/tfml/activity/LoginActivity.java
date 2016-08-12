package com.tfml.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.common.CommonUtils;

import okhttp3.internal.Util;

public class LoginActivity extends Activity implements View.OnClickListener
{
    private EditText txtUserName,txtPassword;
    private   Button btnLogin;
    private   String strUserName,strPassword;
    private TextView txtForgotPassword;
    private CheckBox chkRememberMe;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    public void init()
    {
        txtUserName=(EditText)findViewById(R.id.edt_user_name);
        txtPassword=(EditText)findViewById(R.id.edt_password);
        btnLogin=(Button)findViewById(R.id.btn_login);
        txtForgotPassword=(TextView)findViewById(R.id.txt_forgot_password);
        chkRememberMe=(CheckBox)findViewById(R.id.chkRemember_password);
        btnLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                Validation();
                break;
            case R.id.txt_forgot_password:
                break;
        }
    }

    public void Validation()
    {
        strUserName=txtUserName.getText().toString();
        strPassword=txtPassword.getText().toString();
        if(TextUtils.isEmpty(strUserName))
        {
            Toast.makeText(getBaseContext(),"Please Enter User Name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(strPassword))
        {
            Toast.makeText(getBaseContext(),"Please Enter Password", Toast.LENGTH_SHORT).show();
        } else
        if(!TextUtils.isEmpty(strUserName)&&!TextUtils.isEmpty(strPassword))
        {
           if(CommonUtils.isNetworkAvailable(LoginActivity.this))
            {
              startActivity(new Intent(LoginActivity.this,ContractActivity.class));
            }
            else
            {
                Toast.makeText(getBaseContext(),"Please Check Network Connection", Toast.LENGTH_SHORT).show();
                //CommonUtils.showAlert1(LoginActivity.this,"","Please Check Network Connection",false);
            }
        }
        else
        {
            Toast.makeText(getBaseContext(),"Please Enter User Name and Password", Toast.LENGTH_SHORT).show();
           // CommonUtils.showAlert1(getBaseContext(),"","Please Enter User Name and Password.",false);
        }
    }

}

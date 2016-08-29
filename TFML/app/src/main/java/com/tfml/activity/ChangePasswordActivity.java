package com.tfml.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tfml.R;

public class ChangePasswordActivity extends BaseActivity {
    private EditText txtOldpass,txtNewPass,txtConfirmPass;
    private Button btnChangePass;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }
    public void init()
    {
        txtOldpass=(EditText)findViewById(R.id.txt_old_password);
        txtNewPass=(EditText)findViewById(R.id.txt_new_password);
        txtConfirmPass=(EditText)findViewById(R.id.txt_confirm_password);
        imgBack=(ImageView)findViewById(R.id.img_change_pass_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this,ContractActivity.class));
            }
        });
    }
}

package com.tfml.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.util.SetFonts;

import java.util.Set;

public class ChangePasswordActivity extends BaseActivity {
    private EditText txtOldpass,txtNewPass,txtConfirmPass;
    private Button btnChangePass;
    private TextView txtPasswordTitle;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }
    public void init()
    {
        txtPasswordTitle=(TextView)findViewById(R.id.txt_toolbar_title);
        txtOldpass=(EditText)findViewById(R.id.txt_old_password);
        txtNewPass=(EditText)findViewById(R.id.txt_new_password);
        txtConfirmPass=(EditText)findViewById(R.id.txt_confirm_password);
        imgBack=(ImageView)findViewById(R.id.img_change_pass_back);
        btnChangePass=(Button)findViewById(R.id.btn_change_password);
        SetFonts.setFonts(this,txtPasswordTitle,2);
        SetFonts.setFonts(this,btnChangePass,2);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this,ContractActivity.class));
            }
        });
    }
}

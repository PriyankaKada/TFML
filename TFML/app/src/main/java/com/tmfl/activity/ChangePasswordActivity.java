package com.tmfl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.R;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.model.changePasswordModel.ChangePasswordInputModel;
import com.tmfl.model.changePasswordModel.ChangePasswordResponse;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends DrawerBaseActivity implements View.OnClickListener {
	TmflApi                  tmflApi;
	ChangePasswordInputModel changePasswordInputModel;
	ChangePasswordResponse   changePasswordResponse;
	private EditText txtOldpass, txtNewPass, txtConfirmPass;
	private Button    btnChangePass;
	private TextView  txtPasswordTitle;
	private ImageView imgBack, img_drawer_download;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		View    view    = getLayoutInflater().inflate( R.layout.activity_change_password, frameLayout );
		Toolbar toolbar = ( Toolbar ) view.findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		getSupportActionBar().setTitle( "" );
		tmflApi = ApiService.getInstance().call();
		init();
	}

	public void init() {
		txtPasswordTitle = ( TextView ) findViewById( R.id.txt_toolbar_title );
		txtOldpass = ( EditText ) findViewById( R.id.txt_old_password );
		txtNewPass = ( EditText ) findViewById( R.id.txt_new_password );
		img_drawer_download = ( ImageView ) findViewById( R.id.img_drawer_download );
		txtConfirmPass = ( EditText ) findViewById( R.id.txt_confirm_password );
		imgBack = ( ImageView ) findViewById( R.id.img_change_pass_back );
		btnChangePass = ( Button ) findViewById( R.id.btn_change_password );
		SetFonts.setFonts( this, txtPasswordTitle, 2 );
		SetFonts.setFonts( this, btnChangePass, 2 );
		imgBack.setOnClickListener( this );
		btnChangePass.setOnClickListener( this );
		img_drawer_download.setOnClickListener( this );

	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.img_change_pass_back:
				startActivity( new Intent( ChangePasswordActivity.this, ContractActivity.class ) );
				finish();
				break;
			case R.id.btn_change_password:
				if ( CommonUtils.isNetworkAvailable( ChangePasswordActivity.this ) ) {
					if ( TextUtils.isEmpty( txtOldpass.getText().toString() ) ) {
						Toast.makeText( getBaseContext(), "Please Enter Old Password", Toast.LENGTH_SHORT ).show();
					}
					else if ( TextUtils.isEmpty( txtNewPass.getText().toString() ) ) {
						Toast.makeText( getBaseContext(), "Please Enter New Password", Toast.LENGTH_SHORT ).show();
					}

					else if ( TextUtils.isEmpty( txtConfirmPass.getText().toString() ) ) {
						Toast.makeText( getBaseContext(), "Please Enter Confirm Password", Toast.LENGTH_SHORT ).show();
					}
					else if ( txtNewPass.getText().toString().equals( txtConfirmPass.getText().toString() ) ) {
						changePasswordInputModel = new ChangePasswordInputModel();
						changePasswordResponse = new ChangePasswordResponse();
						changePasswordInputModel.setUser_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
						changePasswordInputModel.setNew_password( txtNewPass.getText().toString() );
						changePasswordInputModel.setOld_password( txtOldpass.getText().toString() );
						changePasswordInputModel.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
						CommonUtils.showProgressDialog( ChangePasswordActivity.this, "Getting Your Information" );
						callChangePassword( changePasswordInputModel );
					}
					else {
						Toast.makeText( getBaseContext(), "New Password and Confirm Password should be Same", Toast.LENGTH_SHORT ).show();
					}

				}
				else {
					Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}
				break;
			case R.id.img_drawer_download:
				openDrawer();
				break;
		}
	}

	public void callChangePassword( ChangePasswordInputModel changePasswordInputModel ) {
		tmflApi.getChangePassResponse( changePasswordInputModel ).enqueue( new Callback< ChangePasswordResponse >() {
			@Override
			public void onResponse( Call< ChangePasswordResponse > call, Response< ChangePasswordResponse > response ) {
				CommonUtils.closeProgressDialog();
				if ( response.body() != null && response.body().getStatus().contains( "Success" ) ) {
					Toast.makeText( getBaseContext(), "Password changed Successfully", Toast.LENGTH_SHORT ).show();
					clearData();
				}
				else {
					Intent intent = new Intent( ChangePasswordActivity.this, BannerActivity.class );
					startActivity( intent );
					finish();
				}
			}

			@Override
			public void onFailure( Call< ChangePasswordResponse > call, Throwable t ) {
				CommonUtils.closeProgressDialog();
			}
		} );
	}

	public void clearData() {
		txtOldpass.setText( "" );
		txtNewPass.setText( "" );
		txtConfirmPass.setText( "" );
	}
}

package com.tmfl.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.R;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SocialUtil;
import com.tmfl.model.branchResponseModel.InputBranchState;
import com.tmfl.model.stateResponseModel.BranchStateResponseModel;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;
import java.util.List;

public class LocateUsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
	ImageView imgBack;
	TextView  txtTitle;
	Spinner   spnState, spnBranch;
	String stateCode, branchCode;
	InputBranchState inputBranchState;
	WebView          webview;
	private List< String > stateList, branchList;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.branch_locator_dialog );
		init();
		stateList = new ArrayList< String >();
		stateList.add( "Select State" );
		spnState.setAdapter( new ArrayAdapter< String >( this, android.R.layout.simple_spinner_dropdown_item, stateList ) );
		branchList = new ArrayList< String >();
		branchList.add( "Select Branch" );
		spnBranch.setAdapter( new ArrayAdapter< String >( this, android.R.layout.simple_spinner_dropdown_item, branchList ) );

	}

	public void init() {
		txtTitle = ( TextView ) findViewById( R.id.txt_toolbar_title );
		imgBack = ( ImageView ) findViewById( R.id.img_map_back );
		spnState = ( Spinner ) findViewById( R.id.sp_select_state );
		spnState.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( this, "Please Wait....." );
		SocialUtil.getBranchStateListData( this, spnState, "Select state" );
		spnBranch = ( Spinner ) findViewById( R.id.sp_select_branch_data );
		spnBranch.setOnItemSelectedListener( this );
		webview = ( WebView ) findViewById( R.id.webView1 );
		SetFonts.setFonts( this, imgBack, 2 );
		imgBack.setOnClickListener( this );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.img_map_back:
				onBackPressed();
				break;
		}
	}

	@Override
	public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
		switch ( parent.getId() ) {
			case R.id.sp_select_state:
				if ( position != 0 ) {
					stateCode = ( ( BranchStateResponseModel ) parent.getItemAtPosition( position ) ).getTerrTerritoryid();
					Log.e( "STATECODE", stateCode );
					inputBranchState = new InputBranchState();
					inputBranchState.setStateId( stateCode );
					CommonUtils.showProgressDialog( this, "Getting Your Information" );
					SocialUtil.getStateBranchList( this, spnBranch, inputBranchState, "Select Branch" );
				}

				break;
			case R.id.sp_select_branch_data:
				if ( position != 0 ) {
					webview.setWebViewClient( new WebViewClient() );
					webview.getSettings().setJavaScriptEnabled( true );
					if ( CommonUtils.isNetworkAvailable( LocateUsActivity.this ) ) {
						webview.loadUrl( "https://www.google.co.in/maps/search/Tata+Motors+in+india/@19.1384285,72.9847334,13z/data=!3m1!4b1" );
					}
					else {
						Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
					}

				}

				break;
		}
	}

	@Override
	public void onNothingSelected( AdapterView< ? > parent ) {

	}


}

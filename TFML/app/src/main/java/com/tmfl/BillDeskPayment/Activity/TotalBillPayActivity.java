package com.tmfl.BillDeskPayment.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.BillDeskPayment.Adapter.CustomAdapter;
import com.tmfl.BillDeskPayment.GetBillDeskMsg;
import com.tmfl.BillDeskPayment.Models.Contract;
import com.tmfl.BillDeskPayment.Models.Example;
import com.tmfl.BillDeskPayment.Models.JSONData;
import com.tmfl.R;
import com.tmfl.activity.BannerActivity;
import com.tmfl.activity.DrawerBaseActivity;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalBillPayActivity extends DrawerBaseActivity implements View.OnClickListener {

	public String amount = "";
	ListView       listView;
	ProgressDialog dialog;
	TextView       txt_title_contract;
	double totalAmount = 0.0;
	String queryString = "";
	private List< Contract > listOfContract;
	private Dialog           payBillDialog;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		//setContentView( R.layout.list_layout );
		View    view    = getLayoutInflater().inflate( R.layout.list_layout, frameLayout );
		Toolbar toolbar = ( Toolbar ) view.findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		getSupportActionBar().setTitle( "" );
		listView = ( ListView ) findViewById( R.id.lstView );
		txt_title_contract = ( TextView ) findViewById( R.id.txt_title_contract );
		ImageView img_contract = ( ImageView ) findViewById( R.id.img_contract );
		txt_title_contract.setText( "Due Details" );
		img_contract.setOnClickListener( this );

		ImageView imgDrawerPayment = ( ImageView ) findViewById( R.id.img_drawer_payament );
		imgDrawerPayment.setOnClickListener( this );
		dialog = new ProgressDialog( TotalBillPayActivity.this );

		findViewById( R.id.btnPayNow ).setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				callPayNow();
				showPayNowDialog();
			}
		} );
		getData();

	}

	private void callPayNow() {

		queryString = "";
		if ( listOfContract != null ) {
			for ( Contract contract : listOfContract ) {
				if ( contract.getIsSelected() ) {
					if ( !TextUtils.isEmpty( queryString ) ) {
						queryString = queryString + ",";
					}
					queryString = queryString + contract.getUsrConNo() + "@" + contract.getNewTotalCurrentDue();
					if ( TextUtils.isDigitsOnly( contract.getTotalCurrentDue() ) ) {
						totalAmount = totalAmount + Double.parseDouble( contract.getNewTotalCurrentDue() );
					}
				}
			}

			Log.e( "total amount ", " total amount " + totalAmount );
			//   ((TextView) findViewById(R.id.totalamounttextview)).setText(String.valueOf(totalAmount + ""));
		}

		if ( TextUtils.isEmpty( queryString ) ) {
			Toast.makeText( TotalBillPayActivity.this
					, "Select atleast 1 contract "
					, Toast.LENGTH_LONG ).show();
		}
		else {
		}
//		callPayNow();

		Log.e( "query string ", " query amount " + queryString );
	}


	private void showPayNowDialog() {

		payBillDialog = new Dialog( this );
		payBillDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
		payBillDialog.setContentView( R.layout.dialog_paynow );
		payBillDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
		payBillDialog.setCancelable( true );
		payBillDialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;

		final EditText edtmobileno         = ( EditText ) payBillDialog.findViewById( R.id.edt_mobile_no );
		TextView       totalamounttextview = ( TextView ) payBillDialog.findViewById( R.id.total_amount );
		TextView       btnloanstatus       = ( TextView ) payBillDialog.findViewById( R.id.btn_loan_status );
		TextView       btnLoanSkip         = ( TextView ) payBillDialog.findViewById( R.id.btn_loan_skip );
		edtmobileno.setText( PreferenceHelper.getString( PreferenceHelper.MOBILE ) );

		totalamounttextview.setText( Double.toString( totalAmount ) );

		btnloanstatus.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {

				String monumber = edtmobileno.getText().toString();
//				String otpnumber = edtotpno.getText().toString();
				if ( TextUtils.isEmpty( monumber ) ) {
					Toast.makeText( TotalBillPayActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT ).show();
				}
				else {
					new GetBillDeskMsg( TotalBillPayActivity.this, queryString, monumber );
				}


			}
		} );

		btnLoanSkip.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				payBillDialog.dismiss();
			}
		} );

		payBillDialog.show();

//		String stringdouble = Double.toString( totalAmount );
//		totalamounttextview.setText( stringdouble );
//
//		final Dialog totalAmountDialog = new Dialog( this, android.R.style.Theme_Holo_Dialog_NoActionBar );
//
//		totalAmountDialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
//		totalAmountDialog.setContentView( R.layout.dialog_paynow );
//		WindowManager.LayoutParams params = totalAmountDialog.getWindow().getAttributes();
//		params.y = 120;
//		params.x = 120;
//		params.gravity = Gravity.CENTER | Gravity.CENTER;
//		totalAmountDialog.getWindow().setAttributes( params );
//		totalAmountDialog.setCancelable( true );
//		totalAmountDialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
//		final EditText edtmobileno         = ( EditText ) totalAmountDialog.findViewById( R.id.edt_moEEEbile_no );
//		final TextView totalamounttextview = ( TextView ) totalAmountDialog.findViewById( R.id.total_amount );
//		final Button   btnloanstatus       = ( Button ) totalAmountDialog.findViewById( R.id.btn_loan_status );
//		edtmobileno.setText( PreferenceHelper.getString( PreferenceHelper.MOBILE ) );
//
//		String stringdouble = Double.toString( totalAmount );
//		totalamounttextview.setText( stringdouble );
//
//		SetFonts.setFonts( this, btnloanstatus, 2 );
//		btnloanstatus.setOnClickListener( new View.OnClickListener() {
//			@Override
//			public void onClick( View v ) {
//
//				String monumber = edtmobileno.getText().toString();
////				String otpnumber = edtotpno.getText().toString();
//				if ( TextUtils.isEmpty( monumber ) ) {
//					Toast.makeText( TotalBillPayActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT ).show();
//				}
//				else {
//					new GetBillDeskMsg( TotalBillPayActivity.this, queryString, monumber );
//
//				}
//
//
//			}
//		} );
//		totalAmountDialog.show();
//		totalAmountDialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
//			@Override
//			public void onCancel( DialogInterface dialog ) {
////				linloanstatus.setBackgroundColor( Color.parseColor( "#004c92" ) );
////				selectedView.setVisibility( View.INVISIBLE );
//			}
//		} );
//
	}

	public void getData() {

		TmflApi service = ApiService.getInstance().call();

		dialog.setCanceledOnTouchOutside( false );
		dialog.setCancelable( false );
		dialog.setMessage( "Loading" );
		dialog.show();

		JSONData jsonData = new JSONData();
		jsonData.setUser_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
		jsonData.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );

		service.getListData( jsonData ).enqueue( new Callback< Example >() {
			@Override
			public void onResponse( Call< Example > call, Response< Example > response ) {

				List< Contract > list = new ArrayList< Contract >();
				list.addAll( response.body().getData().getActive().getContracts() );
				Log.e( "List Size", String.valueOf( response.body().getData().getActive().getContracts().size() ) );
				for ( int i = 0; i < list.size(); i++ ) {
					list.get( i ).setSelected( false );
				}
				CustomAdapter adapter = new CustomAdapter( TotalBillPayActivity.this, listOfContract = list );
				listView.setAdapter( adapter );
				dialog.dismiss();
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure( Call< Example > call, Throwable t ) {
				dialog.dismiss();
				Log.e( "Error Log", "Error:" + t.getMessage() );
			}
		} );
	}

	public void updateTotalAmount() {

		totalAmount = 0;

		if ( listOfContract != null ) {
			for ( Contract contract : listOfContract ) {
				if ( contract.getIsSelected() ) {

					//queryString = queryString + contract.getUsrConNo() + "@" + contract.getNewTotalCurrentDue();
					if ( !TextUtils.isEmpty( contract.getTotalCurrentDue() ) ) {
						try {
							totalAmount = totalAmount + Double.parseDouble( contract.getNewTotalCurrentDue() );
						}
						catch (
								NumberFormatException e
								) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		Log.e( "total amount ", " total amount " + totalAmount );
		( ( TextView ) findViewById( R.id.total_amount ) ).setText( String.valueOf( totalAmount + "" ) );
	}

	@Override
	public void onClick( View view ) {
		switch ( view.getId() ) {
			case R.id.img_emi_back:
				finish();
				break;

			case R.id.img_drawer_payament:
				openDrawer();
				break;

			case R.id.img_contract:
				if ( PreferenceHelper.getBoolean( "SaveLogin" ) ) {
					finish();
				}
				else {
					startActivity( new Intent( TotalBillPayActivity.this, BannerActivity.class ) );
					finish();
				}

				break;
		}
	}
}

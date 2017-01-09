package com.tmfl.BillDeskPayment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.billdesk.sdk.PaymentOptions;
import com.google.gson.Gson;
import com.tmfl.BillDeskPayment.Activity.TotalBillPayActivity;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.model.billDeskMsgResponseModel.BillDeskMsgInputModel;
import com.tmfl.model.billDeskMsgResponseModel.BillDeskMsgResponseModel;
import com.tmfl.util.PreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webworks on 4/1/17.
 */

public class GetBillDeskMsg {

	BillDeskMsgInputModel billDeskMsgInputModel = new BillDeskMsgInputModel();
	Context        context;
	String         contract;
	ProgressDialog progressDialog;
	TmflApi        tmflApi;
	String         list, mobile;


	public GetBillDeskMsg( Context mContext, String contract, String mobile ) {
		this.context = mContext;
		this.list = contract;
		this.mobile = mobile;
		CommonUtils.showProgressDialog( context, "Getting Your Information" );
		getBillDeskMsg();

	}


	public void getBillDeskMsg()


	{
		tmflApi = ApiService.getInstance().call();

		billDeskMsgInputModel.setContracts( list );
		billDeskMsgInputModel.setCustomer_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
		billDeskMsgInputModel.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
		billDeskMsgInputModel.setMobile_no( PreferenceHelper.getString( PreferenceHelper.MOBILE ) );

		Log.d( "request", billDeskMsgInputModel.toString() );

		tmflApi.getBillDeskMsgResponse( billDeskMsgInputModel ).enqueue( new Callback< BillDeskMsgResponseModel >() {
			@Override
			public void onResponse( Call< BillDeskMsgResponseModel > call, Response< BillDeskMsgResponseModel > response ) {
				CommonUtils.closeProgressDialog();
				if ( response.body() != null ) {

					String email;

					email = PreferenceHelper.getString( PreferenceHelper.EMAIL );
					if ( TextUtils.isEmpty( email ) ) {
						email = "NA";
					}

//					mobile = PreferenceHelper.getString( PreferenceHelper.MOBILE );
					if ( TextUtils.isEmpty( mobile ) ) {
						mobile = "NA";
					}

					SampleCallBack callbackObj = new SampleCallBack();
					Intent intent = new Intent( context,
					                            PaymentOptions.class );
//					Log.d( "MSG", response.body().getMsg() );
					Gson gson = new Gson();
					gson.toString();
					intent.putExtra( "msg", response.body().getMsg() ); // pg_msg
//				intent.putExtra("token", strToken);
					intent.putExtra( "user-email", email );
					intent.putExtra( "user-mobile", mobile );
					intent.putExtra( "callback", callbackObj );
					context.startActivity( intent );
					( ( TotalBillPayActivity ) context ).finish();
				}
			}

			@Override
			public void onFailure( Call< BillDeskMsgResponseModel > call, Throwable t ) {
				CommonUtils.closeProgressDialog();

				Log.d( "error", t.getMessage() );

			}
		} );

	}

}

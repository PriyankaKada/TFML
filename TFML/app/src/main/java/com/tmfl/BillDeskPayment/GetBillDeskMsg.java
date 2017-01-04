package com.tmfl.BillDeskPayment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.billdesk.sdk.PaymentOptions;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.model.billDeskMsgResponseModel.BillDeskMsgInputModel;
import com.tmfl.model.billDeskMsgResponseModel.BillDeskMsgResponseModel;
import com.tmfl.model.referFriendResponseModel.ReferFriendInputModel;
import com.tmfl.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tmfl.util.PreferenceHelper;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webworks on 4/1/17.
 */

public class GetBillDeskMsg {

    BillDeskMsgInputModel billDeskMsgInputModel = new BillDeskMsgInputModel();
    Context context;
    String contract;
    ProgressDialog progressDialog;
    TmflApi tmflApi;
    String list;


    public GetBillDeskMsg(Context mContext, String contract) {
        this.context = mContext;
        this.list = contract;
        CommonUtils.showProgressDialog(context, "Getting Your Information");
        getBillDeskMsg();

    }


    public void getBillDeskMsg()


    {
        tmflApi = ApiService.getInstance().call();

        billDeskMsgInputModel.setContracts(list);
        billDeskMsgInputModel.setCustomer_id(PreferenceHelper.getString(PreferenceHelper.USER_ID));
        billDeskMsgInputModel.setApi_token(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
        billDeskMsgInputModel.setMobile_no("9892827269");

        tmflApi.getBillDeskMsgResponse(billDeskMsgInputModel).enqueue(new Callback<BillDeskMsgResponseModel>() {
            @Override
            public void onResponse(Call<BillDeskMsgResponseModel> call, Response<BillDeskMsgResponseModel> response) {
                CommonUtils.closeProgressDialog();
                if (response.body() != null) {

                    SampleCallBack callbackObj = new SampleCallBack();
                    Intent intent = new Intent(context,
                            PaymentOptions.class);
                    Log.d("MSG", response.body().getMsg());
                    intent.putExtra("msg", response.body().getMsg()); // pg_msg
//				intent.putExtra("token", strToken);
                    intent.putExtra("user-email", "NA");
                    intent.putExtra("user-mobile", "9892827269");
                    intent.putExtra("callback", callbackObj);
                    context.startActivity(intent);
//                      {
//                        Log.e( "getFriendResponse", response.body().getMsg() );
////                        showAlert( getActivity(), "Refer Friends", "Thanks for this approach", true );
//
//                    }

                }


            }

            @Override
            public void onFailure(Call<BillDeskMsgResponseModel> call, Throwable t) {
                CommonUtils.closeProgressDialog();

                Log.d("error", t.getMessage());

            }


        });

    }

}

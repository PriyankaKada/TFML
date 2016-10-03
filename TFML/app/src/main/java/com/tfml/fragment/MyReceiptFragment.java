package com.tfml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.adapter.ReceiptListAdapter;
import com.tfml.common.CommonUtils;
import com.tfml.common.SoapApiService;
import com.tfml.model.soapModel.request.ReqBody;
import com.tfml.model.soapModel.request.ReqData;
import com.tfml.model.soapModel.request.RequestEnvelpe;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tfml.common.SocialUtil.tmflApi;


public class MyReceiptFragment extends Fragment implements View.OnClickListener {
    Button btnBack;
    View view;
    ListView lstReceipt;
    Date date;
    String modifiedDate;
    ResponseEnvelope.Body responseEnvelope;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_receipt, container, false);
        init();
      /*  ResponseEnvelope.Body responseEnvelope = (ResponseEnvelope.Body) PreferenceHelper.getObject(PreferenceHelper.SOAPSTATMENTOFACCOUNTRESPONSE, ResponseEnvelope.Body.class);
        if (responseEnvelope != null) {

            for (int i = 0; i < responseEnvelope.getZCISResponse().getI_REC().size(); i++) {
                Log.e("ResponseModel",""+responseEnvelope.getZCISResponse().getI_REC().size());
                lstReceipt.setAdapter(new ReceiptListAdapter(getActivity(),responseEnvelope));

            }

        }*/
        return view;
    }

    public void init() {
        lstReceipt = (ListView) view.findViewById(R.id.lst_my_receipt);
        btnBack = (Button) view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        SetFonts.setFonts(getActivity(), btnBack, 2);
        date = new Date();
        modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        Log.e("ModifiedDate",modifiedDate);
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            CommonUtils.showProgressDialog(getActivity(), "Please Wait Data Loaded....");
            callSoapRequest();
        } else {
            Toast.makeText(getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail, new EmiDetailFragment()).commit();
                break;
        }
    }


    public void callSoapRequest() {
        RequestEnvelpe requestEnvelpe = new RequestEnvelpe();
        final ReqBody reqBody = new ReqBody();
        ReqData reqData = new ReqData();
        reqData.setContactId("0000005000197989");
        reqData.setREQDATE(modifiedDate);
        reqBody.setReqData(reqData);
        requestEnvelpe.setReqBody(reqBody);
        tmflApi = SoapApiService.getInstance().call();
        tmflApi.callStmtAcRequest(requestEnvelpe).enqueue(new Callback<ResponseEnvelope>() {
            @Override
            public void onResponse(Call<ResponseEnvelope> call, Response<ResponseEnvelope> response) {
                CommonUtils.closeProgressDialog();
                if (response.body() != null) {

                    responseEnvelope = response.body().getBody();
                    if(responseEnvelope!=null)
                    {
                        for(int i=0;i<responseEnvelope.getZCISResponse().getI_REC().size();i++)
                        {
                            lstReceipt.setAdapter(new ReceiptListAdapter(getActivity(),responseEnvelope));
                        }

                    }

                  //  responseEnvelope = response.body().getBody();
                //    PreferenceHelper.insertObject(PreferenceHelper.SOAPSTATMENTOFACCOUNTRESPONSE, responseEnvelope);
                //    Log.e("Response ", responseEnvelope.getZCISResponse().getIT_CARDEX1().getItem().getREQDATE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_CARDEX2().getItem().getZZDATE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_DUES1().getItem().getCONTRACTNO());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_DUES2().getItem().getODC_CHRG_DUE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_DUES3().getItem().getTOT_EXP_DUE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_ODC().size() + "");
                    Log.e("Res", response.body().getBody().getZCISResponse().getI_REC().get(0).getCONTRACTNO() + "---" + response.body().getBody().getZCISResponse().getI_REC().size());
                    Log.e("REQDATE", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getREQDATE());
                    Log.e("Aggrement No", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCONTRACTNO());
                    Log.e("Aggrement Date", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCONTSTARTDT());
                    Log.e("Tenure", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getTENURE());
                    Log.e("TenureUNIT", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getTENURE_UNIT());
                    Log.e("EngineNO", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getENGINE_NO());
                    Log.e("Chasis No", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCHASIS_NO());
                    Log.e("RC NO", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getREG_NO());

                }
            }

            @Override
            public void onFailure(Call<ResponseEnvelope> call, Throwable t) {
                Log.e("Response ", "" + t.getLocalizedMessage());
                CommonUtils.closeProgressDialog();
            }
        });

    }
}

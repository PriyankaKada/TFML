package com.tfml.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.activity.LoginActivity;
import com.tfml.adapter.EmiExpandableListView;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.ContractResponseModel.ContractModel;
import com.tfml.model.emiListReponseModel.Datum;
import com.tfml.model.emiListReponseModel.EmiListInputModel;
import com.tfml.model.emiListReponseModel.EmiListResponseModel;
import com.tfml.model.logResponseModel.LogInputModel;
import com.tfml.model.logResponseModel.LogResponseModel;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmiDetailFragment extends Fragment implements View.OnClickListener {

    Button btnReciept;
    View view;
    ExpandableListView expandableListViewEmi;
    TmflApi tmflApi,tmflLogin;
    EmiListInputModel emiListInputModel;
    String datavalue="";
    LogInputModel logInputModel;
    LogResponseModel logResponseModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_emi_detail, container, false);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        datavalue = (String) bundle.getString("datamodelvalue");
        logInputModel=new LogInputModel();
        logResponseModel=new LogResponseModel();
        init();
        return  view;
    }

    public void init()
    {
        tmflLogin=ApiService.getInstance().call();
        tmflApi= ApiService.getInstance().call();
        callCheckLogin();
        emiListInputModel=new EmiListInputModel();
        emiListInputModel.setApiToken(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
        emiListInputModel.setContractNo(PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));

       /* if (!datavalue.isEmpty()){
            emiListInputModel.setContractNo(datavalue);
        }*/
        emiListInputModel.setContractNo(PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));
        Log.e("Req ",new Gson().toJson(emiListInputModel));
        expandableListViewEmi=(ExpandableListView)view.findViewById(R.id.expandableListViewEmi);
        btnReciept=(Button)view.findViewById(R.id.btnReciept);
        btnReciept.setOnClickListener(this);
        SetFonts.setFonts(getActivity(),btnReciept,2);
        Log.e("Req EMI",new Gson().toJson(emiListInputModel)+"");
        callCheckLogin();

    }
    public void callCheckLogin()
    {
        if(CommonUtils.isNetworkAvailable(getActivity()))
        {

            logInputModel.setApi_token(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
            logInputModel.setUser_id(PreferenceHelper.getString(PreferenceHelper.USER_ID));
            callLogService(logInputModel );
        }
        else
        {
            Toast.makeText(getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void callLogService(LogInputModel logInputModel)
    {
        tmflLogin.getLogResponse(logInputModel).enqueue(new Callback<LogResponseModel>() {
            @Override
            public void onResponse(Call<LogResponseModel> call, Response<LogResponseModel> response) {
                Log.e("isLogin",new Gson().toJson(response.body()));

                if(response.body().getStatus().toString().contains("Success"))
                {      if(getActivity()!=null)
                        CommonUtils.showProgressDialog(getActivity(), "Getting Your Information");
                        callSoapRequest();
                }
                else
                {
                    Toast.makeText(getActivity(),"User Logged in from another Device",Toast.LENGTH_LONG).show();
                        Intent loginIntent=new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(loginIntent);

                }


            }

            @Override
            public void onFailure(Call<LogResponseModel> call, Throwable t) {
//                Log.e("ERROR",t.getMessage());
            }
        });


    }

    public void callSoapRequest()
    {
        tmflApi.getEmiListResponse(emiListInputModel).enqueue(new Callback<EmiListResponseModel>() {
            @Override
            public void onResponse(Call<EmiListResponseModel> call, Response<EmiListResponseModel> response) {
                CommonUtils.closeProgressDialog();
                Log.e("Response EMI",new Gson().toJson(response.body())+"");
                if(response.body()!=null ){
                    if( response.body().getStatus().toString().contains("Success"))
                    {
                        Log.e("Response EMI",response.body().getData().size()+"");
                        ArrayList<Datum> parent=new ArrayList<Datum>();
                        ArrayList<Datum> child=new ArrayList<Datum>();

                        for(int i=0;i<response.body().getData().size();i++){
                            parent.add(response.body().getData().get(i));
                            child.add(response.body().getData().get(i));
                        }

                        /*Collections.reverse(parent);
                        Collections.reverse(child);*/

                        expandableListViewEmi.setAdapter(new EmiExpandableListView(getActivity(),parent,child));

                    }else if( response.body().getStatus().toString().contains("Failed"))
                    {
                        CommonUtils.closeProgressDialog();
                        Toast.makeText(getActivity(),"Server Under Maintenance,Please try after Sometime ",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        CommonUtils.closeProgressDialog();
                        Intent loginIntent=new Intent(getActivity(), LoginActivity.class);
                        getActivity().startActivity(loginIntent);
                    }

                }


            }

            @Override
            public void onFailure(Call<EmiListResponseModel> call, Throwable t) {
             //Log.e("SERVERError",""+t.getMessage());
                CommonUtils.closeProgressDialog();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReciept:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail, new MyReceiptFragment()).commit();
                break;
        }
    }
}

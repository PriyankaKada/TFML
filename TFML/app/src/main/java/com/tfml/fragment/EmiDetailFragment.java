package com.tfml.fragment;


import android.content.Context;
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
import com.tfml.adapter.EmiExpandableListView;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.model.emiListReponseModel.Datum;
import com.tfml.model.emiListReponseModel.EmiListInputModel;
import com.tfml.model.emiListReponseModel.EmiListResponseModel;
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
    TmflApi tmflApi;

    EmiListInputModel emiListInputModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_emi_detail, container, false);
        init();
        return  view;
    }

     public void init()
     {
         tmflApi= ApiService.getInstance().call();
         emiListInputModel=new EmiListInputModel();
         emiListInputModel.setApiToken(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
         emiListInputModel.setContractNo(PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));
         Log.e("Req ",new Gson().toJson(emiListInputModel));
         expandableListViewEmi=(ExpandableListView)view.findViewById(R.id.expandableListViewEmi);
         btnReciept=(Button)view.findViewById(R.id.btnReciept);
         btnReciept.setOnClickListener(this);
         SetFonts.setFonts(getActivity(),btnReciept,2);

         tmflApi.getEmiListResponse(emiListInputModel).enqueue(new Callback<EmiListResponseModel>() {
             @Override
             public void onResponse(Call<EmiListResponseModel> call, Response<EmiListResponseModel> response) {
                 if(response.body()!=null){
                     Log.e("Response EMI",response.body().getData().size()+"");
                     ArrayList<Datum> parent=new ArrayList<Datum>();
                     ArrayList<Datum> child=new ArrayList<Datum>();

                     for(int i=0;i<response.body().getData().size();i++){
                         parent.add(response.body().getData().get(i));
                         child.add(response.body().getData().get(i));
                     }

                     Collections.reverse(parent);
                     Collections.reverse(child);

                     expandableListViewEmi.setAdapter(new EmiExpandableListView(getActivity(),parent,child));


                 }
                 else
                 {
                     Toast.makeText(getActivity(),"Server Under Maintenance,Please try after Sometime",Toast.LENGTH_LONG).show();
                 }
             }

             @Override
             public void onFailure(Call<EmiListResponseModel> call, Throwable t) {

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

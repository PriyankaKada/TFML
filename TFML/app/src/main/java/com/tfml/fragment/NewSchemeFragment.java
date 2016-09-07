package com.tfml.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.adapter.SchemesListAdapter;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.schemesResponseModel.Datum;
import com.tfml.model.schemesResponseModel.SchemesResponse;
import com.tfml.util.SetFonts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewSchemeFragment extends Fragment
{


   private ListView lstnewschemes;
    TmflApi tmflApi;
    TextView txtNewSchemesHeader;
    SchemesListAdapter schemesListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_scheme, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstnewschemes=(ListView)view.findViewById(R.id.lst_new_schemes);
        txtNewSchemesHeader=(TextView)view.findViewById(R.id.new_schemes_header);
        SetFonts.setFonts(getActivity(),txtNewSchemesHeader,2);
        tmflApi = ApiService.getInstance().call();
        if(CommonUtils.isNetworkAvailable(getActivity()))
        {
            CommonUtils.showProgressDialog(getActivity(),"Loading wait....");

            callSchemesResponseModel();
        }
        else
        {
            CommonUtils.showAlert1(getActivity(),"Network Error","Please Check Network Connection",false);
        }

    }

        public void callSchemesResponseModel()
        {

            tmflApi.getSchemesResponse().enqueue(new Callback<SchemesResponse>() {
                @Override
                public void onResponse(Call<SchemesResponse> call, Response<SchemesResponse> response) {
                    CommonUtils.closeProgressDialog();
                    Log.e("SchemesResponse",new Gson().toJson(response.body()));
                    List<Datum> body = response.body().getData();
                    lstnewschemes.setAdapter(new SchemesListAdapter(getActivity(), body));
                }


                @Override
                public void onFailure(Call<SchemesResponse> call, Throwable t) {
                    Log.e("Resp","Error");
                    CommonUtils.closeProgressDialog();
                }
            });

        }
     }


package com.tfml.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.adapter.DownloadAdapter;
import com.tfml.adapter.SchemesListAdapter;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;
import com.tfml.model.downloadResponseModel.DownloadResponse;
import com.tfml.model.schemesResponseModel.Datum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadDataActivity extends BaseActivity
{
    ListView lstDownloadList;
     TmflApi tmflApi;
    ImageView img_download_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data);
        lstDownloadList=(ListView)findViewById(R.id.lst_download_data);
        img_download_back=(ImageView)findViewById(R.id.img_login_back);
        img_download_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DownloadDataActivity.this, ContractActivity.class));
            }
        });
        init();

    }
    public void init()
    {

        if(CommonUtils.isNetworkAvailable(this))
        {
            CommonUtils.showProgressDialog(this,"Getting Your Information");

            downloadData();
        }
        else
        {
                 CommonUtils.showAlert1(this,"Network Error","Please Check Network Connection",false);
        }


    }


    public  void downloadData()
    {
        tmflApi = ApiService.getInstance().call();

        tmflApi.getDownloadResponse().enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                CommonUtils.closeProgressDialog();
                Log.e("DownloadResponse",new Gson().toJson(response.body()));
                List<com.tfml.model.downloadResponseModel.Datum> body= response.body().getData();
                lstDownloadList.setAdapter(new DownloadAdapter(DownloadDataActivity.this, body));

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                CommonUtils.closeProgressDialog();
                Log.e("Resp","Error");
            }
        });
    }

}

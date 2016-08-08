package com.tfml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.tfml.auth.TfmlApi;
import com.tfml.common.ApiService;
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.downloadResponseModel.DownloadResponse;
import com.tfml.model.referFriendResponseModel.ReferFriendInputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tfml.model.schemesResponseModel.SchemesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    TfmlApi tfmlApi;
    InputModel inputModel;
    ReferFriendInputModel friendInputModel;
    String strMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check Banner Webservice

        tfmlApi=ApiService.getInstance().call();
      //  callBannerListResponse();
       // callDownloadResponseModel();
      //  callSchemesResponseModel();
        //inputModel=new InputModel();
       // initApplyLoan();
       // callApplyLoanResponseModel(inputModel);
        friendInputModel=new ReferFriendInputModel();
        initFriendResponse();
        callFriendResponseModel(friendInputModel);


    }

   public void initApplyLoan()
   {
       inputModel.setFirstName("Satyawan");
       inputModel.setLastName("Hajare");
       inputModel.setMobileNumber("9860909729");
       inputModel.setLandlineNumber("0232133434");
       inputModel.setProductId("245");
       inputModel.setBranchState("Maharashtra");
       inputModel.setBranchCity("Mumbai");
       inputModel.setBranch("Mumbai");
       inputModel.setEmailAddress("satyahajare@gmail.com");
       inputModel.setState("Maharashtra");
       inputModel.setCity("Mumbai");
       inputModel.setPincode("413390");
       inputModel.setLeadType("pqr");
       inputModel.setOrganisationName("webwerks");
       inputModel.setVehicalType("2_Wheeler");


   }
    public void initFriendResponse()
    {
        friendInputModel.setFirstName("Satyawan");
        friendInputModel.setLastName("Hajare");
        friendInputModel.setMobileNumber("9860909729");
        friendInputModel.setLandlineNumber("0232133434");
        friendInputModel.setProductId("245");
        friendInputModel.setBranchState("Maharashtra");
        friendInputModel.setBranchCity("Mumbai");
        friendInputModel.setBranch("Mumbai");
        friendInputModel.setEmailAddress("satyahajare@gmail.com");
        friendInputModel.setState("Maharashtra");
        friendInputModel.setCity("Mumbai");
        friendInputModel.setPincode("413390");
        friendInputModel.setLeadType("pqr");
        friendInputModel.setOrganisationName("webwerks");
        friendInputModel.setVehicalType("2_Wheeler");
        friendInputModel.setReferedBy("2");

    }

    public void callBannerListResponse()
    {
        tfmlApi.getBannerResponse().enqueue(new Callback<BannerlistResponse>() {
            @Override
            public void onResponse(Call<BannerlistResponse> call, Response<BannerlistResponse> response) {
                Log.e("CallbannerListResponse",new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<BannerlistResponse> call, Throwable t) {
                Log.e("Resp","Error");

            }
        });
    }

    public void callDownloadResponseModel()
    {

        tfmlApi.getDownloadResponse().enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                Log.e("DownloadResponse",new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                Log.e("Resp","Error");
            }
        });
    }

    public void callSchemesResponseModel()
    {

        tfmlApi.getSchemesResponse().enqueue(new Callback<SchemesResponse>() {
            @Override
            public void onResponse(Call<SchemesResponse> call, Response<SchemesResponse> response) {
                Log.e("SchemesResponse",new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<SchemesResponse> call, Throwable t) {
                Log.e("Resp","Error");
            }
        });

    }

     public void callApplyLoanResponseModel(InputModel inputModel)
     {
         tfmlApi.getApplyResponse(inputModel).enqueue(new Callback<ApplyLoanResponse>() {
             @Override
             public void onResponse(Call<ApplyLoanResponse> call, Response<ApplyLoanResponse> response) {
                 if ( response != null && response.body().getStatus().contains("success"))
                 {
                     Log.e("ApplyLoanResponseModel",response.body().getStatus());
                 }
                 else
                 {
                     if ( response != null && response.body().getStatus().contains("error"))
                    strMsg=new Gson().toJson(response.body().getErrors());
                     Log.e("ErrApplyLoanRespoModel",strMsg);
                 }


             }

             @Override
             public void onFailure(Call<ApplyLoanResponse> call, Throwable t) {
                 Log.e("Resp","Error");
             }
         });
     }


  public void callFriendResponseModel(ReferFriendInputModel referFriendInputModel)
  {
      tfmlApi.getFriendResponse(referFriendInputModel).enqueue(new Callback<ReferFriendResponseModel>() {
          @Override
          public void onResponse(Call<ReferFriendResponseModel> call, Response<ReferFriendResponseModel> response) {
              if ( response != null && response.body().getStatus().contains("success"))
              {
                  Log.e("ApplyLoanResponseModel",response.body().getStatus());
              }
              else
              {
                  if ( response != null && response.body().getStatus().contains("error"))
                      strMsg=new Gson().toJson(response.body().getErrors());
                  Log.e("ErrApplyLoanRespoModel",strMsg);
              }
          }

          @Override
          public void onFailure(Call<ReferFriendResponseModel> call, Throwable t) {
              Log.e("Resp","Error");
          }
      });
  }

}

package com.tfml.auth;


import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tfml.model.QuickcallResponseModel.QuickCallInputModel;
import com.tfml.model.QuickcallResponseModel.QuickCallResponse;
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.branchResponseModel.BranchResponseModel;
import com.tfml.model.branchResponseModel.InputBranchModel;
import com.tfml.model.cityResponseModel.BranchCityResponseModel;
import com.tfml.model.cityResponseModel.CityResponseModel;
import com.tfml.model.cityResponseModel.InputCityModel;
import com.tfml.model.downloadResponseModel.DownloadResponse;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.productResponseModel.ProductListResponseModel;
import com.tfml.model.referFriendResponseModel.ReferFriendInputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tfml.model.schemesResponseModel.SchemesResponse;
import com.tfml.model.socialResponseModel.ContactListResponseModel;
import com.tfml.model.stateResponseModel.BranchStateResponseModel;
import com.tfml.model.stateResponseModel.StateResponseModel;
import com.tfml.model.uploadRcResponseModel.RcUploadDataInputModel;
import com.tfml.model.uploadRcResponseModel.RcUploadResponseModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by webwerks on 29/7/16.
 */

public interface TmflApi {

    @POST(Constant.BANNER)
    Call<BannerlistResponse> getBannerResponse();

    @POST(Constant.DOWNLOADS)
    Call<DownloadResponse> getDownloadResponse();

    @POST(Constant.SCHEMES)
    Call<SchemesResponse> getSchemesResponse();

    @POST(Constant.APPLYLOAN)
    Call<ApplyLoanResponse> getApplyLoanResponse(@Body InputModel inputModel);

    @POST(Constant.REFERFRIEND)
    Call<ReferFriendResponseModel> getFriendResponse(@Body ReferFriendInputModel referFriendInputModel);

    @POST(Constant.REQUESTQUICKCALL)
    Call<QuickCallResponse> getQuickCallResponse(@Body QuickCallInputModel quickCallInputModel);

    @POST(Constant.VERIFYOTP)
    Call<LoanStatusResponse>getOtpResponse(@Body LoanStatusInputModel loanStatusInputModel);

    @GET(Constant.CONTACTDETAILS)
    Call<ContactListResponseModel>getContactList();

    @GET(Constant.PRODUCTS)
    Call<List<ProductListResponseModel>>getProductList();

    @GET(Constant.STATES)
    Call<List<BranchStateResponseModel>>getBranchStateList();

    @POST(Constant.GETCITIES)
    Call<List<BranchCityResponseModel>>getBranchCityList(@Body InputCityModel inputCityModel);

    @POST(Constant.GETBRANCHES)
    Call<List<BranchResponseModel>>getBranchList(@Body InputBranchModel inputBranchModel);

    @GET(Constant.STATELIST)
    Call<List<StateResponseModel>>getStateListData();

    @POST(Constant.CITYLIST)
    Call<List<CityResponseModel>>getCityListData(@Body InputCityModel inputCityModel);

   @POST(Constant.UPDATERC)
    Call<RcUploadResponseModel>getRcUploadData(@Body RcUploadDataInputModel rcUploadDataInputModel);

    @GET
    @Streaming
    Call<ResponseBody> getFile(@Url String url);
}

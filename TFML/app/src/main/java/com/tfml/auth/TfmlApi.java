package com.tfml.auth;


import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tfml.model.QuickcallResponseModel.QuickCallInputModel;
import com.tfml.model.QuickcallResponseModel.QuickCallResponse;
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.downloadResponseModel.DownloadResponse;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.referFriendResponseModel.ReferFriendInputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tfml.model.schemesResponseModel.SchemesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.tfml.auth.Constant.APPLYLOAN;

/**
 * Created by webwerks on 29/7/16.
 */

public interface TfmlApi {

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

}

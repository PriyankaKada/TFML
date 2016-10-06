package com.tfml.auth;


import com.tfml.model.ContractResponseModel.ContractModel;
import com.tfml.model.ContractResponseModel.ContractsInputModel;
import com.tfml.model.ContractResponseModel.ContractsResponseModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tfml.model.QuickcallResponseModel.QuickCallInputModel;
import com.tfml.model.QuickcallResponseModel.QuickCallResponse;
import com.tfml.model.accountStmtPdfResponseModel.AccountStatementInputModel;
import com.tfml.model.accountStmtPdfResponseModel.AccountStmtResponse;
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.branchResponseModel.BranchResponseModel;
import com.tfml.model.branchResponseModel.InputBranchModel;
import com.tfml.model.branchResponseModel.InputBranchState;
import com.tfml.model.cityResponseModel.BranchCityResponseModel;
import com.tfml.model.cityResponseModel.CityResponseModel;
import com.tfml.model.cityResponseModel.InputCityModel;
import com.tfml.model.downloadResponseModel.DownloadResponse;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.forgotPasswordResponseModel.ForgotInputModel;
import com.tfml.model.forgotPasswordResponseModel.ForgotResponse;
import com.tfml.model.logResponseModel.LogInputModel;
import com.tfml.model.logResponseModel.LogResponseModel;
import com.tfml.model.loginResponseModel.LoginRequestModel;
import com.tfml.model.loginResponseModel.LoginResponseModel;
import com.tfml.model.preClosurePdfResponseModel.PreClosureInputModel;
import com.tfml.model.preClosurePdfResponseModel.PreClosureStmtPdfResponse;
import com.tfml.model.productResponseModel.ProductListResponseModel;
import com.tfml.model.referFriendResponseModel.ReferFriendInputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tfml.model.schemesResponseModel.SchemesResponse;
import com.tfml.model.soapModel.preClosureRequest.ReqData;
import com.tfml.model.soapModel.preClosureRequest.RequestEnvelope;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.model.socialResponseModel.ContactListResponseModel;
import com.tfml.model.stateResponseModel.BranchStateResponseModel;
import com.tfml.model.stateResponseModel.StateResponseModel;
import com.tfml.model.soapModel.request.RequestEnvelpe;
import com.tfml.model.uploadRcResponseModel.RcUploadDataInputModel;
import com.tfml.model.uploadRcResponseModel.RcUploadResponseModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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

    @POST(Constant.STATEBRNCHES)
    Call<List<BranchResponseModel>>getStateBranches(@Body InputBranchState inputBranchState) ;

    @Headers({
            "SOAPAction : http://sap.com/xi/WebService/soap1.1/StmtOfAcc_Out",
            "Authorization : Basic cHJkX3BpX3dlYjpsb3NAMTIzNA=="
    })
    @POST("MessageServlet?senderParty=&senderService=Service_TMFUniverse&receiverParty=&receiverService=&interface=StmtOfAcc_Out&interfaceNamespace=http://tmf.com:TMFUniverse")
    Call<ResponseEnvelope> callStmtAcRequest(@Body RequestEnvelpe requestEnvelope);

    @Headers({
            "SOAPAction : http://sap.com/xi/WebService/soap1.1/TerminalDues_Out",
            "Authorization : Basic cHJkX3BpX3dlYjpsb3NAMTIzNA=="
    })
    @POST("MessageServlet?senderParty=&senderService=Service_TMFUniverse&receiverParty=&receiverService=&interface=TerminalDues_Out&interfaceNamespace=http://tmf.com:TMFUniverse")
    Call<com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope> callClosureTableRequest(@Body RequestEnvelope requestEnvelope);

    @POST(Constant.MYCONTRACT)
    Call<ContractsResponseModel>getContractListData(@Body ContractsInputModel contractsInputModel);
    @POST(Constant.LOGIN)
    Call<LoginResponseModel>getLoginResponse(@Body LoginRequestModel loginRequestModel);
    @POST(Constant.ACCOUNT_STATEMENT_DOWNLOAD)
    Call<AccountStmtResponse>getAccStmtDownload(@Body AccountStatementInputModel accountStatementInputModel);
     @POST(Constant.PRECLOSURE_STATEMENT_DOWNLOAD)
    Call<PreClosureStmtPdfResponse>getPreClosureDownload(@Body PreClosureInputModel preClosureInputModel);
    @POST(Constant.FORGOT_PASSWORD)
    Call<ForgotResponse>getForgotResponse(@Body ForgotInputModel forgotInputModel);
    @POST(Constant.LOGGEDIN)
    Call<LogResponseModel>getLogResponse(@Body LogInputModel logInputModel);
 /*   @POST(Constant.RECEIPTPDF)
    Call<>*/
}

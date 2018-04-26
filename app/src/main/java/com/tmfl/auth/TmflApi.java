package com.tmfl.auth;


import com.tmfl.BillDeskPayment.Models.Example;
import com.tmfl.BillDeskPayment.Models.JSONData;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseRequestEnvelope;
import com.tmfl.complaintnetwork.createcase.response.CreateCaseResponseEnvelope;
import com.tmfl.complaintnetwork.findcase.request.FindCaseRequestEnvelope;
import com.tmfl.complaintnetwork.findcase.response.FindCaseResponseEnvelope;
import com.tmfl.complaintnetwork.uploaddoc.request.UploadDocRequestEnvelope;
import com.tmfl.complaintnetwork.uploaddoc.response.UploadDocResponseEnvelope;
import com.tmfl.model.ContractResponseModel.ContractsInputModel;
import com.tmfl.model.ContractResponseModel.ContractsResponseModel;
import com.tmfl.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tmfl.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tmfl.model.QuickcallResponseModel.QuickCallInputModel;
import com.tmfl.model.QuickcallResponseModel.QuickCallResponse;
import com.tmfl.model.accountStmtPdfResponseModel.AccountStatementInputModel;
import com.tmfl.model.accountStmtPdfResponseModel.AccountStmtResponse;
import com.tmfl.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tmfl.model.applyLoanResponseModel.InputModel;
import com.tmfl.model.bannerResponseModel.BannerlistResponse;
import com.tmfl.model.billDeskMsgResponseModel.BillDeskMsgInputModel;
import com.tmfl.model.billDeskMsgResponseModel.BillDeskMsgResponseModel;
import com.tmfl.model.branchResponseModel.BranchResponseModel;
import com.tmfl.model.branchResponseModel.InputBranchModel;
import com.tmfl.model.branchResponseModel.InputBranchState;
import com.tmfl.model.changePasswordModel.ChangePasswordInputModel;
import com.tmfl.model.changePasswordModel.ChangePasswordResponse;
import com.tmfl.model.cityResponseModel.BranchCityResponseModel;
import com.tmfl.model.cityResponseModel.CityResponseModel;
import com.tmfl.model.cityResponseModel.InputCityModel;
import com.tmfl.model.downloadResponseModel.DownloadResponse;
import com.tmfl.model.emiListReponseModel.EmiListInputModel;
import com.tmfl.model.emiListReponseModel.EmiListResponseModel;
import com.tmfl.model.forgotPasswordResponseModel.ForgotInputModel;
import com.tmfl.model.forgotPasswordResponseModel.ForgotResponse;
import com.tmfl.model.logResponseModel.LogInputModel;
import com.tmfl.model.logResponseModel.LogResponseModel;
import com.tmfl.model.loginResponseModel.LoginRequestModel;
import com.tmfl.model.loginResponseModel.LoginResponseModel;
import com.tmfl.model.logoutResponseModel.LogoutInputModel;
import com.tmfl.model.logoutResponseModel.LogoutResponseModel;
import com.tmfl.model.myReciptPdfResponseModel.MyReceiptInputModel;
import com.tmfl.model.myReciptPdfResponseModel.MyReceiptResponseModel;
import com.tmfl.model.preClosurePdfResponseModel.PreClosureInputModel;
import com.tmfl.model.preClosurePdfResponseModel.PreClosureStmtPdfResponse;
import com.tmfl.model.productResponseModel.ProductListResponseModel;
import com.tmfl.model.referFriendResponseModel.ReferFriend;
import com.tmfl.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tmfl.model.schemesResponseModel.SchemesResponse;
import com.tmfl.model.soapModel.preClosureRequest.RequestEnvelope;
import com.tmfl.model.soapModel.request.RequestEnvelpe;
import com.tmfl.model.soapModel.response.ResponseEnvelope;
import com.tmfl.model.socialResponseModel.ContactListResponseModel;
import com.tmfl.model.stateResponseModel.BranchStateResponseModel;
import com.tmfl.model.stateResponseModel.StateResponseModel;
import com.tmfl.model.uploadRcResponseModel.RcUploadResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
    Call<ReferFriendResponseModel> getFriendResponse(@Body ReferFriend referFriendInputModel);

    @POST(Constant.REQUESTQUICKCALL)
    Call<QuickCallResponse> getQuickCallResponse(@Body QuickCallInputModel quickCallInputModel);

    @POST(Constant.VERIFYOTP)
    Call<LoanStatusResponse> getOtpResponse(@Body LoanStatusInputModel loanStatusInputModel);

    @GET(Constant.CONTACTDETAILS)
    Call<ContactListResponseModel> getContactList();

    @GET(Constant.PRODUCTS)
    Call<List<ProductListResponseModel>> getProductList();

    @GET(Constant.STATES)
    Call<List<BranchStateResponseModel>> getBranchStateList();

    @POST(Constant.GETCITIES)
    Call<List<BranchCityResponseModel>> getBranchCityList(@Body InputCityModel inputCityModel);

    @POST(Constant.GETBRANCHES)
    Call<List<BranchResponseModel>> getBranchList(@Body InputBranchModel inputBranchModel);

    @GET(Constant.STATELIST)
    Call<List<StateResponseModel>> getStateListData();

    @POST(Constant.CITYLIST)
    Call<List<CityResponseModel>> getCityListData(@Body InputCityModel inputCityModel);

    @Multipart
    @POST(Constant.UPDATERC)
    Call<RcUploadResponseModel> getRcUploadData(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part image);

    @GET
    @Streaming
    Call<ResponseBody> getFile(@Url String url);

    @POST(Constant.STATEBRNCHES)
    Call<List<BranchResponseModel>> getStateBranches(@Body InputBranchState inputBranchState);

    @Headers({
            "SOAPAction:http://sap.com/xi/WebService/soap1.1/StmtOfAcc_Out",
            "Authorization:Basic cHJkX3BpX3dlYjpwcmRwaXdlYkAx"
    })
    @POST("MessageServlet?senderParty=&senderService=Service_TMFUniverse&receiverParty=&receiverService=&interface=StmtOfAcc_Out&interfaceNamespace=http://tmf.com:TMFUniverse")
    Call<ResponseEnvelope> callStmtAcRequest(@Body RequestEnvelpe requestEnvelope) throws Exception;

    @Headers({
            "SOAPAction:http://sap.com/xi/WebService/soap1.1/TerminalDues_Out",
            "Authorization:Basic cHJkX3BpX3dlYjpwcmRwaXdlYkAx"
    })
    @POST("MessageServlet?senderParty=&senderService=Service_TMFUniverse&receiverParty=&receiverService=&interface=TerminalDues_Out&interfaceNamespace=http://tmf.com:TMFUniverse")
    Call<com.tmfl.model.soapModel.preClousreResponse.ResponseEnvelope> callClosureTableRequest(@Body RequestEnvelope requestEnvelope) throws Exception;

    @Headers({
            "SOAPAction:http://tempuri.org/FindCase",
            "Content-Type:text/xml; charset=utf-8"
    })
    @POST("CustOne_Case_Webservice?op=FindCase")
    Call<FindCaseResponseEnvelope> findCaseRequest(@Body FindCaseRequestEnvelope requestEnvelope) throws Exception;

    @Headers({
            "SOAPAction:http://tempuri.org/CreateCase_CustOne",
            "Content-Type:text/xml; charset=utf-8"
    })
    @POST("CustOne_Case_Webservice?op=CreateCase_CustOne")
    Call<CreateCaseResponseEnvelope> createCaseRequest(@Body CreateCaseRequestEnvelope requestEnvelope) throws Exception;

    @Headers({
            "SOAPAction:http://tempuri.org/UploadDoc",
            "Content-Type:text/xml; charset=utf-8"
    })
    @POST("CustOne_Case_Webservice?op=UploadDoc")
    Call<UploadDocResponseEnvelope> uploadDocRequest(@Body UploadDocRequestEnvelope requestEnvelope) throws Exception;

    @POST(Constant.MYCONTRACT)
    Call<ContractsResponseModel> getContractListData(@Body ContractsInputModel contractsInputModel);

    @POST(Constant.LOGIN)
    Call<LoginResponseModel> getLoginResponse(@Body LoginRequestModel loginRequestModel);

    @POST(Constant.ACCOUNT_STATEMENT_DOWNLOAD)
    Call<AccountStmtResponse> getAccStmtDownload(@Body AccountStatementInputModel accountStatementInputModel);

    @POST(Constant.PRECLOSURE_STATEMENT_DOWNLOAD)
    Call<PreClosureStmtPdfResponse> getPreClosureDownload(@Body PreClosureInputModel preClosureInputModel);

    @POST(Constant.FORGOT_PASSWORD)
    Call<ForgotResponse> getForgotResponse(@Body ForgotInputModel forgotInputModel);

    @POST(Constant.LOGGEDIN)
    Call<LogResponseModel> getLogResponse(@Body LogInputModel logInputModel);

    @POST(Constant.EMILIST)
    Call<EmiListResponseModel> getEmiListResponse(@Body EmiListInputModel emiListInputModel);

    @POST(Constant.CHANGPASSWORD)
    Call<ChangePasswordResponse> getChangePassResponse(@Body ChangePasswordInputModel changePasswordInputModel);

    @POST(Constant.RECEIPTPDF)
    Call<MyReceiptResponseModel> getMyReceiptResponse(@Body MyReceiptInputModel myReceiptInputModel);

    @POST(Constant.LOGOUT)
    Call<LogoutResponseModel> getLogoutResponse(@Body LogoutInputModel logoutInputModel);

    @POST(Constant.BILL_DESK_MESSAGE)
    Call<BillDeskMsgResponseModel> getBillDeskMsgResponse(@Body BillDeskMsgInputModel billDeskMsgInputModel);

    @POST("customer/myContracts")
    Call<Example> getListData(@Body JSONData jsonData);

}
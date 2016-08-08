package com.tfml.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.EGLDisplay;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.adapter.BannerAdapter;
import com.tfml.auth.TfmlApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.fragment.BannerFragment;
import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tfml.model.QuickcallResponseModel.QuickCallInputModel;
import com.tfml.model.QuickcallResponseModel.QuickCallResponse;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.bannerResponseModel.Datum;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tfml.R.id.imageView1;

public class BannerActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener
{
    Toolbar mToolbar;
    private BannerFragment bannerFragment;
    private ImageView imgQuickCall,imgSocial;
    private TextView txtTitle;
    public ViewPager recentViewpager;
    CirclePageIndicator circlePageIndicator;
    TfmlApi tfmlApi;
    BannerAdapter bannerAdapter;
    private ImageView[] dots;
    private int dotsCount;
    LinearLayout linSchemes,linApplyLoan,linReferFriend,linLoanStaus,linLogin;
    TextView txtSchemes ,txtApplyLoan,txtReferFriend,txtLoanStatus,txtLogin;
     ImageView imgSchemes,imgApplyLoan,imgReferFriend,imgLoanStatus,imgLogin;
    String errormsg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        init();
    }
     public void init()
     {
         mToolbar=(Toolbar)findViewById(R.id.toolbar);
         imgQuickCall=(ImageView)findViewById(R.id.imgQuickCall);
         imgSocial=(ImageView)findViewById(R.id.imgSocial);
         txtTitle=(TextView)findViewById(R.id.txtTitle);
         txtSchemes=(TextView)findViewById(R.id.txtSchemes);
         txtApplyLoan=(TextView)findViewById(R.id.txtApplyLoan);
         txtReferFriend=(TextView)findViewById(R.id.txtReferFriend);
         txtLoanStatus=(TextView)findViewById(R.id.txtLoanStatus);
         txtLogin=(TextView)findViewById(R.id.txtLogin);
         imgSchemes=(ImageView)findViewById(R.id.imageSchemes) ;
         imgApplyLoan=(ImageView)findViewById(R.id.imgApplyLoan);
         imgReferFriend=(ImageView)findViewById(R.id.imgReferFriend);
         imgLoanStatus=(ImageView)findViewById(R.id.imgLoanStatus);
         imgLogin=(ImageView)findViewById(R.id.imgLogin);
         recentViewpager = (ViewPager)findViewById(R.id.recentViewpager);
         circlePageIndicator = (CirclePageIndicator)findViewById(R.id.titles);
         linSchemes=(LinearLayout)findViewById(R.id.linSchemes);
         linApplyLoan=(LinearLayout)findViewById(R.id.linApplyLoan);
         linReferFriend=(LinearLayout)findViewById(R.id.linReferFriend);
         linLoanStaus=(LinearLayout)findViewById(R.id.linLoanStaus);
         linLogin=(LinearLayout)findViewById(R.id.linLogin);
         circlePageIndicator.setRadius(10.0f);
         txtTitle.setText("Welcome to TFML");
         loadBannerData();
         imgQuickCall.setOnClickListener(this);
         imgSocial.setOnClickListener(this);
         linSchemes.setOnClickListener(this);
         linApplyLoan.setOnClickListener(this);
         linReferFriend.setOnClickListener(this);
         linLoanStaus.setOnClickListener(this);
         linLogin.setOnClickListener(this);


     }
     public void loadBannerData()
     {
         tfmlApi = ApiService.getInstance().call();
         if(CommonUtils.isNetworkAvailable(BannerActivity.this))
         {
             CommonUtils.showProgressDialog(BannerActivity.this,"Please Wait Load.....");
             callBannerList();
         }
         else
         {
             Toast.makeText(getBaseContext(),"Please Check Network Connection",Toast.LENGTH_SHORT).show();
         }

     }


    public void callBannerList()
    {
        tfmlApi.getBannerResponse().enqueue(new Callback<BannerlistResponse>() {
            @Override
            public void onResponse(Call<BannerlistResponse> call, Response<BannerlistResponse> response) {
                BannerlistResponse bannerlistResponse = response.body();
                CommonUtils.closeProgressDialog();
                if(response.body().getStatus()!=null && response.body().getStatus().equals("success"))
                {

                    Log.e("BannerlistResponse",new Gson().toJson(response.body().getStatus()));
                    Log.e("CallbannerListResponse",""+bannerlistResponse.getBanners().getData().get(0).getImage());
                    bannerAdapter=new BannerAdapter(BannerActivity.this, (ArrayList<Datum>) bannerlistResponse.getBanners().getData());
                    recentViewpager.setAdapter(bannerAdapter);
                    setUiPageViewController();
                    recentViewpager.setCurrentItem(0);
                    circlePageIndicator.setViewPager(recentViewpager);

                }
                else
                {
                    Log.e("ErrorResponse",response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(Call<BannerlistResponse> call, Throwable t) {
                Log.e("Resp", "Error");
                CommonUtils.closeProgressDialog();
            }
        });

    }

    private void setUiPageViewController() {

        dotsCount = bannerAdapter.getCount();
        Log.e("Logcount",""+dotsCount);
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(BannerActivity.this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            recentViewpager.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgQuickCall:
                quickCallDialog();
                break;

            case R.id.imgSocial:
               // startActivity(new Intent(this, ForgetPasswordActivity.class));
                socialDialog();
                break;
            case R.id.linSchemes:
            //    linSchemesClick();
                startActivity(new Intent(this, SchemesActivity.class));
                break;
            case R.id.linApplyLoan:
                startActivity(new Intent(this, SchemesActivity.class));
                break;
            case R.id.linReferFriend:
                startActivity(new Intent(this, SchemesActivity.class));
                break;
            case R.id.linLoanStaus:
                loanStatusDialog();
                break;
            case R.id.linLogin:
                break;

        }
    }
    public void linSchemesClick()
    {
        txtSchemes.setTextColor(Color.parseColor("#4171FE"));
        txtApplyLoan.setTextColor(Color.parseColor("#000000"));
        txtReferFriend.setTextColor(Color.parseColor("#000000"));
        txtLoanStatus.setTextColor(Color.parseColor("#000000"));
        txtLogin.setTextColor(Color.parseColor("#000000"));

    }
    public void quickCallDialog()
    {
        final Dialog dialog = new Dialog(BannerActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_quick_call);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.y = 5; params.x = 5;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        dialog.setCancelable(false);
        final EditText edtQuickCall = (EditText) dialog.findViewById(R.id.edt_quick_call);
       final Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btnSubmit=(Button)dialog.findViewById(R.id.btn_submit);
        btnCancel.setVisibility(View.VISIBLE);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String strQuickCall=edtQuickCall.getText().toString();
               if(strQuickCall.length()!=0)
               {
                   String strmobileno=edtQuickCall.getText().toString();
                   Log.e("Number",strmobileno);
                   QuickCallInputModel quickCallInputModel=new QuickCallInputModel();
                   quickCallInputModel.setMobileNumber(strmobileno);
                   callResponseModel(quickCallInputModel);
                //   Toast.makeText(BannerActivity.this,edtQuickCall.getText().toString(),Toast.LENGTH_SHORT).show();
                   dialog.dismiss();
                   btnCancel.setVisibility(View.GONE);
               }
                else
               {
                   Toast.makeText(BannerActivity.this,"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
               }
            }
        });
        dialog.show();
    }
     public void callResponseModel(QuickCallInputModel quickCallInputModel)
     {
         Log.e("callResponseModel",""+quickCallInputModel.getMobileNumber());
         tfmlApi.getQuickCallResponse(quickCallInputModel).enqueue(new Callback<QuickCallResponse>() {
             @Override
             public void onResponse(Call<QuickCallResponse> call, Response<QuickCallResponse> response) {
                 Log.e("getQuickCallResponse",new Gson().toJson(response.body()));

                 if ( response != null && response.body().getStatus().contains("success"))
                 {
                     loanStatusDialog();


                     Log.e("getQuickCallResponse",response.body().getStatus());

                 }
                 else
                 {
                     if ( response != null && response.body().getStatus().contains("error"))
                        errormsg= response.body().getError().getMobileNo().get(0);
                     Log.e("getcallErrorResponse",errormsg);
                 }
             }

             @Override
             public void onFailure(Call<QuickCallResponse> call, Throwable t) {

             }
         });

     }

     public void socialDialog()
     {
         imgSocial.setVisibility(View.INVISIBLE);
         final Dialog socialdialog = new Dialog(BannerActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
         socialdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
         socialdialog.setContentView(R.layout.dialog_social);
        WindowManager.LayoutParams params = socialdialog.getWindow().getAttributes();
         params.y = 5; params.x = 5;
         params.gravity = Gravity.TOP | Gravity.RIGHT;
        socialdialog.getWindow().setAttributes(params);
         socialdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
         socialdialog.setCancelable(true);
         final ImageView imgmessage = (ImageView) socialdialog.findViewById(R.id.imgmsg);
         final ImageView imgmap=(ImageView)socialdialog.findViewById(R.id.imgmap);
         final ImageView imgwhatsapp=(ImageView)socialdialog.findViewById(R.id.imgwhatsapp);
         final ImageView imgphonecall=(ImageView)socialdialog.findViewById(R.id.imgcall);
         final ImageView imgcancel=(ImageView)socialdialog.findViewById(R.id.imgcancel);
         imgcancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 socialdialog.dismiss();
                 imgSocial.setVisibility(View.VISIBLE);
             }
         });
         socialdialog.show();

     }
    public void loanStatusDialog()
    {
     final Dialog loanstatusdialog=new Dialog(BannerActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        loanstatusdialog.setContentView(R.layout.dialog_laon_status);
        WindowManager.LayoutParams params = loanstatusdialog.getWindow().getAttributes();
        params.y = 15; params.x = 15;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        loanstatusdialog.getWindow().setAttributes(params);
        loanstatusdialog.setCancelable(true);
       // getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
      //  WindowManager.LayoutParams params = loanstatusdialog.getWindow().getAttributes();
      //  params.y = 5; params.x = 5;
      //  params.gravity = Gravity.BOTTOM | Gravity.END;
        //loanstatusdialog.getWindow().setAttributes(params);
        loanstatusdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        final EditText edtmobileno=(EditText)loanstatusdialog.findViewById(R.id.edt_mobile_no);
        final EditText edtotpno=(EditText)loanstatusdialog.findViewById(R.id.edt_otp_no);
        final Button  btnloanstatus=(Button)loanstatusdialog.findViewById(R.id.btn_loan_status);

        btnloanstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String monumber=edtmobileno.getText().toString();
               String otpnumber=edtotpno.getText().toString();
                if(TextUtils.isEmpty(monumber))
                {
                    Toast.makeText(getBaseContext(),"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(otpnumber))
                {
                    Toast.makeText(getBaseContext(),"Please Enter OTP Number",Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(monumber) && !TextUtils.isEmpty(otpnumber))
                {
                    LoanStatusInputModel loanStatusInputModel=new LoanStatusInputModel();
                    loanStatusInputModel.setOtpNumber(otpnumber);
                    loanStatusInputModel.setMobileNumber(monumber);
                    CallLoanStatusModel(loanStatusInputModel);

                }

            }
        });
        loanstatusdialog.show();
    }

    public void CallLoanStatusModel(LoanStatusInputModel loanStatusInputModel)
    {
        tfmlApi.getOtpResponse(loanStatusInputModel).enqueue(new Callback<LoanStatusResponse>() {
            @Override
            public void onResponse(Call<LoanStatusResponse> call, Response<LoanStatusResponse> response) {
                if(response.body().getStatus().contains("success"))
                {
                    Log.e("CallLoanStatusModel",response.body().getStatus());
                    Toast.makeText(getBaseContext(),"Get Application Loan Process ",Toast.LENGTH_SHORT).show();
                }
                if(response.body().getStatus().contains("error"))
                {
                    Log.e("CallLoanStatusModel",response.body().getError());
                    Toast.makeText(getBaseContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"Get Application Loan Process ",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoanStatusResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
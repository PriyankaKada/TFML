package com.tfml.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.michael.easydialog.EasyDialog;
import com.tfml.R;
import com.tfml.adapter.BannerAdapter;
import com.tfml.auth.Constant;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;
import com.tfml.fragment.BannerFragment;
import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tfml.model.QuickcallResponseModel.QuickCallInputModel;
import com.tfml.model.QuickcallResponseModel.QuickCallResponse;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.bannerResponseModel.Datum;
import com.tfml.util.Logger;
import com.tfml.util.SetFonts;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.tfml.R.id.imageView1;


public class BannerActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    Toolbar mToolbar;
    private BannerFragment bannerFragment;
    private ImageView imgQuickCall, imgSocial;
    private TextView txtTitle;
    public ViewPager recentViewpager;
    CirclePageIndicator circlePageIndicator;
    TmflApi tmflApi;
    BannerAdapter bannerAdapter;
    private ImageView[] dots;
    private int dotsCount;
    LinearLayout linSchemes, linApplyLoan, linReferFriend, linLoanStaus, linLogin;
    private TextView txtSchemes, txtApplyLoan, txtReferFriend, txtLoanStatus, txtLogin;g
    private ImageView imgSchemes, imgApplyLoan, imgReferFriend, imgLoanStatus, imgLogin;
    String errormsg;
    View selectedView;
    String strQuickCall, strOtpNo, strMobileNo;
    //String email,whatsAppNo,phoneNo;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    TmflApi tmflApiOtpSubmit;
    ImageView imgRefreshOtp;
    EasyDialog dialog;
    EditText edtQuickCall, edtOtpNo;
    QuickCallInputModel quickCallInputModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        init();
    }

    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        imgQuickCall = (ImageView) findViewById(R.id.imgQuickCall);
        imgSocial = (ImageView) findViewById(R.id.imgSocial);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtSchemes = (TextView) findViewById(R.id.txtSchemes);
        txtApplyLoan = (TextView) findViewById(R.id.txtApplyLoan);
        txtReferFriend = (TextView) findViewById(R.id.txtReferFriend);
        txtLoanStatus = (TextView) findViewById(R.id.txtLoanStatus);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        imgSchemes = (ImageView) findViewById(R.id.imageSchemes);
        imgApplyLoan = (ImageView) findViewById(R.id.imgApplyLoan);
        imgReferFriend = (ImageView) findViewById(R.id.imgReferFriend);
        imgLoanStatus = (ImageView) findViewById(R.id.imgLoanStatus);
        imgLogin = (ImageView) findViewById(R.id.imgLogin);
        recentViewpager = (ViewPager) findViewById(R.id.recentViewpager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.titles);
        linSchemes = (LinearLayout) findViewById(R.id.linSchemes);
        linApplyLoan = (LinearLayout) findViewById(R.id.linApplyLoan);
        linReferFriend = (LinearLayout) findViewById(R.id.linReferFriend);
        linLoanStaus = (LinearLayout) findViewById(R.id.linLoanStaus);
        linLogin = (LinearLayout) findViewById(R.id.linLogin);
        selectedView = (View) findViewById(R.id.viewId);
        circlePageIndicator.setRadius(8.0f);
        txtTitle.setText("Welcome to TMFL");
        SetFonts.setFonts(this, txtTitle, 2);
        SetFonts.setFonts(this, txtSchemes, 2);
        SetFonts.setFonts(this, txtApplyLoan, 2);
        SetFonts.setFonts(this, txtReferFriend, 2);
        SetFonts.setFonts(this, txtLoanStatus, 2);
        SetFonts.setFonts(this, txtLogin, 2);
        loadBannerData();
        imgQuickCall.setOnClickListener(this);
        imgSocial.setOnClickListener(this);
        imgSocial.setVisibility(View.VISIBLE);
        linSchemes.setOnClickListener(this);
        linApplyLoan.setOnClickListener(this);
        linReferFriend.setOnClickListener(this);
        linLoanStaus.setOnClickListener(this);
        linLogin.setOnClickListener(this);


    }

    public void loadBannerData() {
        tmflApi = ApiService.getInstance().call();
        if (CommonUtils.isNetworkAvailable(BannerActivity.this)) {
            CommonUtils.showProgressDialog(BannerActivity.this, "Getting Your Information");
            callBannerList();
        } else {
            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }

    }


    public void callBannerList() {
        tmflApi.getBannerResponse().enqueue(new Callback<BannerlistResponse>() {
            @Override
            public void onResponse(Call<BannerlistResponse> call, Response<BannerlistResponse> response) {
                BannerlistResponse bannerlistResponse = response.body();
                CommonUtils.closeProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus() != null && response.body().getStatus().equals("success")) {
                        // Logger.e(BannerActivity.class,new Gson().toJson(response.body().getStatus()));
                        Log.e("BannerlistResponse", new Gson().toJson(response.body().getStatus()));
                        // Log.e("CallbannerListResponse", "" + bannerlistResponse.getBanners().getData().get(0).getImage());
                        bannerAdapter = new BannerAdapter(BannerActivity.this, (ArrayList<Datum>) bannerlistResponse.getBanners().getData());
                        recentViewpager.setAdapter(bannerAdapter);
                        setUiPageViewController();
                        recentViewpager.setCurrentItem(0);
                        circlePageIndicator.setViewPager(recentViewpager);


                    } else {
                        Log.e("ErrorResponse", response.errorBody().toString());
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Server Error........", Toast.LENGTH_LONG).show();
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
        Log.e("Logcount", "" + dotsCount);
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
                socialDialog();
                break;
            case R.id.linSchemes:
                Intent intentSchema = new Intent(this, SchemesActivity.class);
                intentSchema.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intentSchema.putExtra("TAB_SELECTED", Constant.ISSCHEMASTABSELECT);
                startActivity(intentSchema);
                //
                break;
            case R.id.linApplyLoan:
                Intent intentApplyLoan = new Intent(this, SchemesActivity.class);
                intentApplyLoan.putExtra("TAB_SELECTED", Constant.ISAPPLYLOANSELECT);
                startActivity(intentApplyLoan);
                break;
            case R.id.linReferFriend:
                Intent intentReferFriend = new Intent(this, SchemesActivity.class);
                intentReferFriend.putExtra("TAB_SELECTED", Constant.ISREFERFREINDSELECT);
                startActivity(intentReferFriend);
                break;
            case R.id.linLoanStaus:
                linLoanStausClick();
                SocialUtil.loanStatusDialog(BannerActivity.this, linLoanStaus, selectedView);
                break;
            case R.id.linLogin:
                startActivity(new Intent(BannerActivity.this, LoginActivity.class));
                break;

        }
    }

    public void linLoanStausClick() {
        int width = linLoanStaus.getWidth();
        Log.e("Widthoflin", "" + width);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linLoanStaus.setBackgroundColor(Color.parseColor("#003668"));
            selectedView.setVisibility(View.VISIBLE);
        }
    }


    public void quickCallDialog() {

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_quick_calling, null);
        dialog = new EasyDialog(this)
                .setLayout(view)
                .setBackgroundColor(Color.parseColor("#FFFFFF"))
                .setLocationByAttachedView(imgQuickCall)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 500, 800, 0)
                .setAnimationAlphaShow(500, 0, 0.5f, 1)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1, 0)
                .setTouchOutsideDismiss(true)
                .setMatchParent(true)
                .setMarginLeftAndRight(25, 25)
                .setOutsideColor(ContextCompat.getColor(this, R.color.background_color_black))
                .show();


        edtQuickCall = (EditText) view.findViewById(R.id.edt_mobile_no);
        edtOtpNo = (EditText) view.findViewById(R.id.edt_otp_no);
        imgRefreshOtp = (ImageView) view.findViewById(R.id.img_Refresh_token);
        TextView txtSubmit = (TextView) view.findViewById(R.id.txt_submit);

        edtQuickCall.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 10 || s.length() == 12) {
                    quickCallInputModel = new QuickCallInputModel();
                    quickCallInputModel.setMobileNumber(edtQuickCall.getText().toString());
                    callResponseModel(quickCallInputModel);
                    imgRefreshOtp.postDelayed(new Runnable() {
                        public void run() {
                            imgRefreshOtp.setVisibility(View.VISIBLE);
                        }
                    }, 10000);
                }
            }
        });


        txtSubmit.setOnClickListener(new View.OnClickListener()

                                     {

                                         @Override
                                         public void onClick(View v) {

                                             if (TextUtils.isEmpty(edtQuickCall.getText().toString())) {
                                                 Toast.makeText(BannerActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                                             }

                                             if (TextUtils.isEmpty(edtOtpNo.getText().toString())) {
                                                 Toast.makeText(BannerActivity.this, "Please Enter OTP Number", Toast.LENGTH_SHORT).show();
                                             }
                                             if (!TextUtils.isEmpty(edtQuickCall.getText().toString()) && !TextUtils.isEmpty(edtOtpNo.getText().toString())) {
                                                 LoanStatusInputModel loanStatusInputModel = new LoanStatusInputModel();
                                                 loanStatusInputModel.setOtpNumber(edtOtpNo.getText().toString());
                                                 loanStatusInputModel.setMobileNumber(edtQuickCall.getText().toString());
                                                 if (edtQuickCall.getText().toString().length() == 10 || edtQuickCall.getText().toString().length() == 12) {
                                                     CallLoanStatusModel(loanStatusInputModel);
                                                 }
                                                 else
                                                 {
                                                     Toast.makeText(BannerActivity.this, "Mobile Number must between 10 or 15 digit", Toast.LENGTH_SHORT).show();
                                                 }

                                             }

                                         }
                                     }

        );


    }

    public void callResponseModel(QuickCallInputModel quickCallInputModel) {
        Log.e("callResponseModel", "" + quickCallInputModel.getMobileNumber());
        tmflApi.getQuickCallResponse(quickCallInputModel).enqueue(new Callback<QuickCallResponse>() {
            @Override
            public void onResponse(Call<QuickCallResponse> call, Response<QuickCallResponse> response) {
                Log.e("getQuickCallResponse", new Gson().toJson(response.body()));

                if (response != null && response.body().getStatus().contains("success")) {

                    // SocialUtil.loanStatusDialog(BannerActivity.this, linLoanStaus, selectedView);
                    // strOtpNo = response.body().getData().getOtp().toString();

                } else {
                    if (response != null && response.body().getStatus().contains("error"))
                        errormsg = response.body().getError().getMobileNo().get(0);
                    // Toast.makeText(getBaseContext(),errormsg,Toast.LENGTH_SHORT).show();
                    Log.e("getcallErrorResponse", errormsg);
                }
            }

            @Override
            public void onFailure(Call<QuickCallResponse> call, Throwable t) {

            }
        });

    }

    public void CallLoanStatusModel(LoanStatusInputModel loanStatusInputModel) {
        tmflApiOtpSubmit = ApiService.getInstance().call();
        tmflApiOtpSubmit.getOtpResponse(loanStatusInputModel).enqueue(new Callback<LoanStatusResponse>() {
            @Override
            public void onResponse(Call<LoanStatusResponse> call, Response<LoanStatusResponse> response) {
                if (response.body().getStatus().contains("success")) {
                    //  Log.e("CallLoanStatusModel", response.body().getStatus());
                    Toast.makeText(BannerActivity.this, "Thanks for Quick calling ", Toast.LENGTH_SHORT).show();
                    edtQuickCall.setText("");
                    edtOtpNo.setText("");
                    dialog.dismiss();

                }
                if (response.body().getStatus().contains("error")) {
                    //  Log.e("CallLoanStatusModel", response.body().getError());
                    Toast.makeText(BannerActivity.this, response.body().getError(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoanStatusResponse> call, Throwable t) {

            }
        });
    }

    public void socialDialog() {
        imgSocial.setVisibility(View.INVISIBLE);
        final Dialog socialdialog = new Dialog(BannerActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar);
        socialdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        socialdialog.setContentView(R.layout.dialog_social);
        WindowManager.LayoutParams params = socialdialog.getWindow().getAttributes();
        params.y = 5;
        params.x = 5;
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        socialdialog.getWindow().setAttributes(params);
        socialdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        socialdialog.setCancelable(true);
        final ImageView imgMessage = (ImageView) socialdialog.findViewById(R.id.imgmsg);
        final ImageView imgMap = (ImageView) socialdialog.findViewById(R.id.imgmap);
        final ImageView imgWhatsApp = (ImageView) socialdialog.findViewById(R.id.imgwhatsapp);
        final ImageView imgPhoneCall = (ImageView) socialdialog.findViewById(R.id.imgcall);
        final ImageView imgcancel = (ImageView) socialdialog.findViewById(R.id.imgcancel);
        if (CommonUtils.isNetworkAvailable(BannerActivity.this)) {
            SocialUtil.getContactList();
        } else {
            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }

        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialdialog.dismiss();
                imgSocial.setVisibility(View.VISIBLE);
            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtil.sendMail(BannerActivity.this, SocialUtil.email);
            }
        });
        imgPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtil.dialPhoneCall(BannerActivity.this, SocialUtil.phoneNo);
            }
        });
        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtil.sendWhatsAppMsg(BannerActivity.this, SocialUtil.whatsAppNo);
            }
        });
        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BannerActivity.this, LocateUsActivity.class));

            }
        });

        socialdialog.show();
        socialdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                imgSocial.setVisibility(View.VISIBLE);
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

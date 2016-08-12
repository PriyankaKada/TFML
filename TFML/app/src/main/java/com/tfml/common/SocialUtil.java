package com.tfml.common;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.activity.BannerActivity;
import com.tfml.auth.TfmlApi;
import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by webwerks on 12/8/16.
 */

public class SocialUtil {

   public static  TfmlApi tfmlApi;


    public static void dialPhoneCall(Context context)
    {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:18002090188"));
        context.startActivity(callIntent);

    }

    public static void sendMail(Context context)
    {
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
           context. startActivity(Intent.createChooser(emailIntent, "Send mail..."));
           //context.finish();
            Log.i("Finish sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendWhatsAppMsg(Context context)
    {

        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp",context);
        if (isWhatsappInstalled) {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Welcome to TFML";
            waIntent.setPackage("com.whatsapp");
            if (waIntent != null) {
                waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                context.startActivity(Intent.createChooser(waIntent, "Share with"));
            } else {
                Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(context, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
           context. startActivity(goToMarket);

        }

    }

    private static boolean whatsappInstalledOrNot(String uri,Context context) {
        PackageManager pm =context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

  public static void  loanStatusDialog(final Context context)
  {
      final Dialog loanstatusdialog=new Dialog(context,android.R.style.Theme_Holo_Dialog_NoActionBar);
      loanstatusdialog.setContentView(R.layout.dialog_laon_status);
      WindowManager.LayoutParams params = loanstatusdialog.getWindow().getAttributes();
      params.y = 15; params.x = 15;
      params.gravity = Gravity.BOTTOM | Gravity.CENTER;
      loanstatusdialog.getWindow().setAttributes(params);
      loanstatusdialog.setCancelable(true);
      loanstatusdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
      final EditText edtmobileno=(EditText)loanstatusdialog.findViewById(R.id.edt_mobile_no);
      final EditText edtotpno=(EditText)loanstatusdialog.findViewById(R.id.edt_otp_no);
      final Button btnloanstatus=(Button)loanstatusdialog.findViewById(R.id.btn_loan_status);

      btnloanstatus.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              String monumber=edtmobileno.getText().toString();
              String otpnumber=edtotpno.getText().toString();
              if(TextUtils.isEmpty(monumber))
              {
                  Toast.makeText(context,"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
              }
              if(TextUtils.isEmpty(otpnumber))
              {
                  Toast.makeText(context,"Please Enter OTP Number",Toast.LENGTH_SHORT).show();
              }
              if(!TextUtils.isEmpty(monumber) && !TextUtils.isEmpty(otpnumber))
              {
                  LoanStatusInputModel loanStatusInputModel=new LoanStatusInputModel();
                  loanStatusInputModel.setOtpNumber(otpnumber);
                  loanStatusInputModel.setMobileNumber(monumber);
                  CallLoanStatusModel(loanStatusInputModel,context);

              }

          }
      });
      loanstatusdialog.show();
  }

    public static void CallLoanStatusModel(LoanStatusInputModel loanStatusInputModel, final Context context)
    {
        tfmlApi = ApiService.getInstance().call();

        tfmlApi.getOtpResponse(loanStatusInputModel).enqueue(new Callback<LoanStatusResponse>() {
            @Override
            public void onResponse(Call<LoanStatusResponse> call, Response<LoanStatusResponse> response) {
                if(response.body().getStatus().contains("success"))
                {
                    Log.e("CallLoanStatusModel",response.body().getStatus());
                    Toast.makeText(context,"Get Application Loan Process ",Toast.LENGTH_SHORT).show();
                }
                if(response.body().getStatus().contains("error"))
                {
                    Log.e("CallLoanStatusModel",response.body().getError());
                    Toast.makeText(context,response.body().getError(),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Get Application Loan Process ",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoanStatusResponse> call, Throwable t) {

            }
        });
    }
}

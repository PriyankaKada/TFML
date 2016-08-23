package com.tfml.common;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.activity.BannerActivity;
import com.tfml.auth.TfmlApi;
import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static com.tfml.R.id.linLoanStaus;


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

    public static void sendMail(Context context) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/html");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);

        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains("email") || info.activityInfo.name.toLowerCase().contains("email")) {
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, "Welcome to TMFL");
                    intent.setPackage(info.activityInfo.packageName);
                   context. startActivity(Intent.createChooser(intent, "Sending mail Through TMFL"));
                }
            }
        }
    }

    public static void sendWhatsAppMsg(Context context)
    {

        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp",context);
        if (isWhatsappInstalled) {
            Uri uri = Uri.parse("smsto:" + "9820399105");
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hai Good Morning");
           /* sendIntent.setType("text/plain");*/
            sendIntent.setPackage("com.whatsapp");
            context.startActivity(sendIntent);

        } else {
            Toast.makeText(context, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en");
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

  public static void  loanStatusDialog(final Context context,final LinearLayout linloanstatus)
  {
      final Dialog loanstatusdialog=new Dialog(context,android.R.style.Theme_Holo_Dialog_NoActionBar);

      loanstatusdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
      loanstatusdialog.setContentView(R.layout.dialog_laon_status);
      WindowManager.LayoutParams params = loanstatusdialog.getWindow().getAttributes();
      params.y = 60; params.x = 50;
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
      loanstatusdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
          @Override
          public void onCancel(DialogInterface dialog) {
              linloanstatus.setBackgroundColor(Color.parseColor("#004c92"));
          }
      });
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

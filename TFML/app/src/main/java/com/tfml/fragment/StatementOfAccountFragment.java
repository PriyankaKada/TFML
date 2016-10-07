package com.tfml.fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.activity.BannerActivity;
import com.tfml.activity.ContractActivity;
import com.tfml.activity.SplashActivity;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SoapApiService;
import com.tfml.model.ContractResponseModel.ContractModel;
import com.tfml.model.accountStmtPdfResponseModel.AccountStatementInputModel;
import com.tfml.model.accountStmtPdfResponseModel.AccountStmtResponse;
import com.tfml.model.logResponseModel.LogInputModel;
import com.tfml.model.logResponseModel.LogResponseModel;
import com.tfml.model.soapModel.request.ReqBody;
import com.tfml.model.soapModel.request.ReqData;
import com.tfml.model.soapModel.request.RequestEnvelpe;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.DatePickerDialog;
import com.tfml.util.DatePickerFragment;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tfml.common.SocialUtil.tmflApi;


public class StatementOfAccountFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateChangeListener {
    private TextView txtAccDate, btnSubmit;
    private Button btnBasicDetail;
    private Button btnFinanceDetail;
    private ImageView btnDownload;
    private View view;
    private Spinner spnContractNo;
    private DatePickerFragment date;
    private FrameLayout frmAccDetail;
    BasicDetailFragment frmBasicAccDetail;
    FinanceDetailFragment frmFinanceDetail;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LinearLayout linAccDetail;
    String contractNo;
    private List<String> contractLst;
    ResponseEnvelope.Body responseEnvelope;
    ArrayList<ContractModel> modelArrayList;
    private int itemindex = 0;
    TextView txt_repaymentmode, txt_emiamount, txt_dueamount, txt_duedate, txt_rc_no;
    private String datavalue = "";
    private String rcNo = "";
    private String dueDate = "";
    private String repaymentMode = "";
    private String currentEmi = "";
    String strPathUrl;
    TmflApi tmflSoapApi, tmflDownload,tmflLogin;
    AccountStatementInputModel accountStatementInputModel;
    AccountStmtResponse accountStmtResponse;
    ProgressDialog progressdialog;
    String servicestring = Context.DOWNLOAD_SERVICE;
    DownloadManager downloadmanager;
    LogInputModel logInputModel;
    LogResponseModel logResponseModel;
    private String overdue="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_statment_of_account, container, false);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        modelArrayList =
                (ArrayList<ContractModel>) bundle.getSerializable("datamodel");
        datavalue = (String) bundle.getString("datamodelvalue");
        rcNo = (String) bundle.getString("RCNO");
        dueDate = (String) bundle.getString("DUEDATE");
        repaymentMode = (String) bundle.getString("OVERDUEAMT");
        currentEmi = (String) bundle.getString("CURRENTEMI");
        overdue=(String)bundle.get("OVERDUEAMT");
        tmflLogin=ApiService.getInstance().call();
        tmflDownload = ApiService.getInstance().call();
        logInputModel=new LogInputModel();
        logResponseModel=new LogResponseModel();
        accountStatementInputModel = new AccountStatementInputModel();
        accountStmtResponse = new AccountStmtResponse();
        init();
        return view;
    }

    public void init() {
        spnContractNo = (Spinner) view.findViewById(R.id.spnContractNo);
        txtAccDate = (TextView) view.findViewById(R.id.txt_acc_select_date);
        btnSubmit = (TextView) view.findViewById(R.id.btn_submit);
        btnDownload = (ImageView) view.findViewById(R.id.img_download);
        btnBasicDetail = (Button) view.findViewById(R.id.btn_basic_detail);
        btnFinanceDetail = (Button) view.findViewById(R.id.btn_finance_detail);
        frmAccDetail = (FrameLayout) view.findViewById(R.id.frm_acc_detail);
        linAccDetail = (LinearLayout) view.findViewById(R.id.lin_acc_detail);
        txt_rc_no = (TextView) view.findViewById(R.id.txt_rc_no);
        txt_repaymentmode = (TextView) view.findViewById(R.id.txt_repaymentmode);
        txt_emiamount = (TextView) view.findViewById(R.id.txt_emiamount);
        txt_dueamount = (TextView) view.findViewById(R.id.txt_dueamount);
        txt_duedate = (TextView) view.findViewById(R.id.txt_duedate);
        setColorButtonBasic();
        if (rcNo != null)
            txt_rc_no.setText(rcNo);
        if (repaymentMode != null)
            txt_repaymentmode.setText(repaymentMode);
        if (dueDate != null)
            txt_duedate.setText(dueDate);
        if (currentEmi != null)
            txt_emiamount.setText(currentEmi);
        if(overdue!=null)
        {
            txt_dueamount.setText(overdue);
        }
        SetFonts.setFonts(getActivity(), btnSubmit, 2);
        date = new DatePickerFragment();
        contractLst = new ArrayList<String>();
        if (modelArrayList.size() > 0) {
            contractLst.add("Select Contract No");
            for (int i = 0; i < modelArrayList.size(); i++) {
                ContractModel model = modelArrayList.get(i);
                if (model != null)
                    contractLst.add(model.getUsrConNo());
            }
        }
        spnContractNo.setSelection(1);
        spnContractNo = (Spinner) view.findViewById(R.id.spnContractNo);
        contractLst = new ArrayList<String>();
        if (modelArrayList.size() > 0) {
            contractLst.add(datavalue);
            for (int i = 0; i < modelArrayList.size(); i++) {
                ContractModel model = modelArrayList.get(i);
                if (model != null)
                    contractLst.add(model.getUsrConNo());
//                 System.out.println("::::: "+model.getDueDate() +" :::: "+model.getDueAmount());
            }
        }

        spnContractNo.setSelection(1);
//         spnContractNo.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,contractLst));


        ArrayAdapter<String> madapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, contractLst) {

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        madapter.setDropDownViewResource(R.layout.spinner_item);
        spnContractNo.setAdapter(madapter);
        madapter.notifyDataSetChanged();
        spnContractNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                contractNo = spnContractNo.getSelectedItem().toString();
                itemindex = position;

                ContractModel model = modelArrayList.get(itemindex);
                setData(model);
                callCheckLogin();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit.setOnClickListener(this);
        txtAccDate.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnBasicDetail.setOnClickListener(this);
        btnFinanceDetail.setOnClickListener(this);


    }

    private void setData(ContractModel model) {
        txt_rc_no.setText(model.getRcNumber()==null?"":model.getRcNumber().toString());
        txt_duedate.setText(model.getDueDate()==null?"":model.getDueDate().toString());
        // txt_dueamount.setText(model.getDueAmount()==null?"":"Rs. "+model.getDueAmount().toString());
        txt_emiamount.setText(model.getDueAmount()==null?"":"Rs. "+model.getDueAmount().toString());
        txt_repaymentmode.setText(model.getPdcFlag()==null?"":model.getPdcFlag().toString());
        txt_dueamount.setText(model.getTotalCurrentDue()==null?"":"Rs."+model.getTotalCurrentDue().toString());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_acc_select_date:
                selectDate();
                break;
            case R.id.btn_submit:
                if(!TextUtils.isEmpty(txtAccDate.getText().toString()))
                {
                    callCheckLogin();
                }
                else {
                Toast.makeText(getActivity(), "Please Select Date", Toast.LENGTH_SHORT).show();
            }

                break;
            case R.id.img_download:
                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    callDownloadService();
                    getDownloadData(accountStatementInputModel);
                } else {
                    Toast.makeText(getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_basic_detail:
                if (!TextUtils.isEmpty(txtAccDate.getText().toString()))
                {
                    callBasicDetail();
                }

                break;
            case R.id.btn_finance_detail:
                setColorButtonFinance();
                if(!TextUtils.isEmpty(txtAccDate.getText().toString()))
                {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    frmFinanceDetail = new FinanceDetailFragment();
                    if(responseEnvelope!=null)
                    {
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("ResponseModel", responseEnvelope);
                        frmFinanceDetail.setArguments(bundle1);
                    }
                    fragmentTransaction.add(R.id.frm_acc_detail, frmFinanceDetail);
                    fragmentTransaction.commit();
                }

                break;

        }
    }

    public void callSoapRequest() {
        RequestEnvelpe requestEnvelpe = new RequestEnvelpe();
        final ReqBody reqBody = new ReqBody();
        ReqData reqData = new ReqData();
        reqData.setContactId(contractNo);
        reqData.setREQDATE(txtAccDate.getText().toString());
        reqBody.setReqData(reqData);
        requestEnvelpe.setReqBody(reqBody);
        tmflSoapApi = SoapApiService.getInstance().call();
        tmflSoapApi.callStmtAcRequest(requestEnvelpe).enqueue(new Callback<ResponseEnvelope>() {
            @Override
            public void onResponse(Call<ResponseEnvelope> call, Response<ResponseEnvelope> response) {
                CommonUtils.closeProgressDialog();
                if (response.body() != null) {
                    responseEnvelope = response.body().getBody();
                    PreferenceHelper.insertObject(PreferenceHelper.SOAPSTATMENTOFACCOUNTRESPONSE, responseEnvelope);
                    Log.e("Response ", responseEnvelope.getZCISResponse().getIT_CARDEX1().getItem().getREQDATE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_CARDEX2().getItem().getZZDATE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_DUES1().getItem().getCONTRACTNO());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_DUES2().getItem().getODC_CHRG_DUE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_DUES3().getItem().getTOT_EXP_DUE());
                    Log.e("Response ", response.body().getBody().getZCISResponse().getIT_ODC().size() + "");
                    Log.e("Res", response.body().getBody().getZCISResponse().getI_REC().get(0).getCONTRACTNO() + "---" + response.body().getBody().getZCISResponse().getI_REC().size());
                    Log.e("REQDATE", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getREQDATE());
                    Log.e("Aggrement No", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCONTRACTNO());
                    Log.e("Aggrement Date", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCONTSTARTDT());
                    Log.e("Tenure", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getTENURE());
                    Log.e("TenureUNIT", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getTENURE_UNIT());
                    Log.e("EngineNO", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getENGINE_NO());
                    Log.e("Chasis No", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCHASIS_NO());
                    Log.e("RC NO", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getREG_NO());
                    callBasicDetail();
                }
            }

            @Override
            public void onFailure(Call<ResponseEnvelope> call, Throwable t) {
                Log.e("Response ", "" + t.getLocalizedMessage());
                CommonUtils.closeProgressDialog();
            }
        });

    }

    public void callBasicDetail() {
        setColorButtonBasic();
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frmBasicAccDetail = new BasicDetailFragment();
        if(responseEnvelope!=null)
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable("ResponseModel", responseEnvelope);
            frmBasicAccDetail.setArguments(bundle);
        }

        fragmentTransaction.add(R.id.frm_acc_detail, frmBasicAccDetail);
        fragmentTransaction.commit();


    }


    @SuppressLint("NewApi")
    public void setColorButtonBasic() {
        /*ON SELECTED*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            btnBasicDetail.setBackground(getActivity().getDrawable(R.drawable.tab_btnleft_selector));
        }
        btnBasicDetail.setTextColor(ContextCompat.getColor(getActivity(), R.color.tab_bg));

        /*NOT SELECTED*/
        btnFinanceDetail.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_bg));
        btnFinanceDetail.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setColorButtonFinance() {
         /*ON SELECTED*/
        btnFinanceDetail.setBackground(getActivity().getDrawable(R.drawable.tab_btnright_selector));
        btnFinanceDetail.setTextColor(ContextCompat.getColor(getActivity(), R.color.tab_bg));
        /*NOT SELECTED*/
        btnBasicDetail.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_bg));
        btnBasicDetail.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }


    public void callDownloadService() {

        if (PreferenceHelper.getString(PreferenceHelper.API_TOKEN )!= null) {
            accountStatementInputModel.setApiToken(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
        }
        if (contractNo != null) {
            accountStatementInputModel.setContractNo(contractNo);
        }
        if (txtAccDate.getText().toString() != null) {
            accountStatementInputModel.setRequestDate(txtAccDate.getText().toString());
        }

    }

    public void getDownloadData(AccountStatementInputModel accountStatementInputModel) {

        tmflDownload.getAccStmtDownload(accountStatementInputModel).enqueue(new Callback<AccountStmtResponse>() {
            @Override
            public void onResponse(Call<AccountStmtResponse> call, Response<AccountStmtResponse> response) {

                if (response.body().getFilepath() != null) {
                    Log.e("File Path", "" + response.body().getFilepath());
                    strPathUrl = response.body().getFilepath().toString();
                    Uri uri = Uri.parse(strPathUrl);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "StatementOFAccount" + SystemClock.currentThreadTimeMillis());
                    DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
                else
                {
                   Log.e("RESONSE ERR","REPONSE ERROR"+response.message().toString());
                }


            }

            @Override
            public void onFailure(Call<AccountStmtResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDateChange(String date, String picker) {
        if (picker.equalsIgnoreCase(" ")) {
            txtAccDate.setText(txtAccDate.getText().toString() + " " + date);
        }
    }

    public void selectDate() {
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    /**
     * The Ondate.
     */
    android.app.DatePickerDialog.OnDateSetListener ondate = new android.app.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
            txtAccDate.setText(year + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) : ("0" + (monthOfYear + 1))) + "-" + (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth));
            //dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;

        }
    };

    public void callCheckLogin()
    {
        if(CommonUtils.isNetworkAvailable(getActivity()))
        {

            logInputModel.setApi_token(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
            logInputModel.setUser_id(PreferenceHelper.getString(PreferenceHelper.USER_ID));
            callLogService(logInputModel );
        }
        else
        {
            Toast.makeText(getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void callLogService(LogInputModel logInputModel)
    {
        tmflLogin.getLogResponse(logInputModel).enqueue(new Callback<LogResponseModel>() {
            @Override
            public void onResponse(Call<LogResponseModel> call, Response<LogResponseModel> response) {
                Log.e("isLogin",new Gson().toJson(response.body()));

                if(response.body().getStatus().toString().contains("Success"))
                {
                    if (CommonUtils.isNetworkAvailable(getActivity())) {

                            CommonUtils.showProgressDialog(getActivity(), "Getting Your Information");
                            callSoapRequest();
                            linAccDetail.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                   Toast.makeText(getActivity(),"Unauthorized User access",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LogResponseModel> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
            }
        });


    }


}

package com.tfml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.adapter.MyExpandableListAdapter;
import com.tfml.adapter.ReceiptListAdapter;
import com.tfml.auth.Constant;
import com.tfml.common.CommonUtils;
import com.tfml.common.SoapApiService;
import com.tfml.model.soapModel.request.ReqBody;
import com.tfml.model.soapModel.request.ReqData;
import com.tfml.model.soapModel.request.RequestEnvelpe;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tfml.common.SocialUtil.tmflApi;


public class MyReceiptFragment extends Fragment implements View.OnClickListener {
    Button btnBack;
    View view;
    ListView lstReceipt;
    ExpandableListView expandableListView;
    Date date;
    String modifiedDate;
    ResponseEnvelope.Body responseEnvelope;
    HashMap<String, ArrayList<ResponseEnvelope.Item>> hashMap = new HashMap<String, ArrayList<ResponseEnvelope.Item>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_receipt, container, false);
        init();
        return view;
    }

    public void init() {
        lstReceipt = (ListView) view.findViewById(R.id.lst_my_receipt);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        btnBack = (Button) view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        SetFonts.setFonts(getActivity(), btnBack, 2);
        date = new Date();
        modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        Log.e("ModifiedDate", modifiedDate);
        if (CommonUtils.isNetworkAvailable(getActivity())) {
            CommonUtils.showProgressDialog(getActivity(), "Getting Your Information");
            callSoapRequest();
        } else {
            Toast.makeText(getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail, new EmiDetailFragment()).commit();
                break;
        }
    }


    public void callSoapRequest() {
        RequestEnvelpe requestEnvelpe = new RequestEnvelpe();
        final ReqBody reqBody = new ReqBody();
        ReqData reqData = new ReqData();
        if (PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO) != null) {
            Log.e("SelectedContractNo", PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));
            reqData.setContactId(PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));
        } else {
            Log.e("SelectedContractNoDef", "0000005000197989");
            reqData.setContactId(PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));
        }
        reqData.setREQDATE(modifiedDate);
        reqBody.setReqData(reqData);
        requestEnvelpe.setReqBody(reqBody);
        tmflApi = SoapApiService.getInstance().call();
        tmflApi.callStmtAcRequest(requestEnvelpe).enqueue(new Callback<ResponseEnvelope>() {
            @Override
            public void onResponse(Call<ResponseEnvelope> call, Response<ResponseEnvelope> response) {
                CommonUtils.closeProgressDialog();
                if (response.body() != null) {

                    responseEnvelope = response.body().getBody();

                    if (responseEnvelope != null) {
                        HashMap<String, ArrayList<ResponseEnvelope.Item>> hashMap = new HashMap<String, ArrayList<ResponseEnvelope.Item>>();

                        List<String> itemsCategory = new ArrayList<String>();
                        for (ResponseEnvelope.Item item : response.body().getBody().getZCISResponse().getI_REC()) {
                            itemsCategory.add(item.getBELNR());
                        }
                        HashSet<String> categories = new HashSet<>();
                        categories.addAll(itemsCategory);

                        for (String s : categories) {
                            ArrayList<ResponseEnvelope.Item> items = new ArrayList<ResponseEnvelope.Item>();
                            for (ResponseEnvelope.Item item : response.body().getBody().getZCISResponse().getI_REC()) {
                                if (s.equals(item.getBELNR())) {
                                    items.add(item);
                                }
                            }
                            hashMap.put(s, items);
                        }
                        ArrayList<String> groupar = new ArrayList<>();
                        ArrayList<Double> amountar = new ArrayList<Double>();

                        Log.e("HashMap ", new Gson().toJson(hashMap) + "");
                        Iterator it = hashMap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry) it.next();
                            String key = (String) pair.getKey();
                            ArrayList<ResponseEnvelope.Item> ar = (ArrayList<ResponseEnvelope.Item>) pair.getValue();
                            Log.e("Size ", "" + ar.size());
                            Double amount = 0.00;
                            for (int i = 0; i < ar.size(); i++) {
                                Log.e("item ", "" + ar.get(i).getDMBTR());
                                amount = amount + Double.parseDouble(ar.get(i).getDMBTR());
                                if (i == 0) {
                                    groupar.add(ar.get(i).getZFBDT() + " / " + ar.get(i).getBELNR());
                                }
                            }
                            amountar.add(amount);

                        }
                        expandableListView.setAdapter(new MyExpandableListAdapter(getActivity(), hashMap, groupar, amountar));

                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Server Under Maintenance,Please try after Sometime",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEnvelope> call, Throwable t) {
                Log.e("Response ", "" + t.getLocalizedMessage());
                CommonUtils.closeProgressDialog();
            }
        });

    }
}

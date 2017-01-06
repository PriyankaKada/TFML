package com.tmfl.BillDeskPayment.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.tmfl.BillDeskPayment.Adapter.CustomAdapter;
import com.tmfl.BillDeskPayment.GetBillDeskMsg;
import com.tmfl.BillDeskPayment.Models.Contract;
import com.tmfl.BillDeskPayment.Models.Example;
import com.tmfl.BillDeskPayment.Models.JSONData;
import com.tmfl.R;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.util.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TotalBillPayActivity extends AppCompatActivity {

    ListView listView;
    Contract contractModel;
    ProgressDialog dialog;
    TextView totalamounttextview, txtMobileNo;
    static String total;
    private List<Contract> listOfContract;
    double totalAmount = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        listView = (ListView) findViewById(R.id.lstView);
        totalamounttextview = (TextView) findViewById(R.id.totalamounttextview);
        txtMobileNo = (TextView) findViewById(R.id.txtMobileNo);

        txtMobileNo.setText(PreferenceHelper.getString(PreferenceHelper.MOBILE));


        findViewById(R.id.btnPayNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String queryString = "";
                if (listOfContract != null) {
                    for (Contract contract : listOfContract) {
                        if (contract.getIsSelected()) {
                            if (!TextUtils.isEmpty(queryString)) {
                                queryString = queryString + ",";
                            }
                            queryString = queryString + contract.getUsrConNo() + "@" + contract.getTotalCurrentDue();
                            if (TextUtils.isDigitsOnly(contract.getTotalCurrentDue()))
                                totalAmount = totalAmount + Double.parseDouble(contract.getTotalCurrentDue());
                        }
                    }

                    Log.e("total amount ", " total amount " + totalAmount);
                    //   ((TextView) findViewById(R.id.totalamounttextview)).setText(String.valueOf(totalAmount + ""));
                }

                if (TextUtils.isEmpty(queryString)) {
                    Toast.makeText(TotalBillPayActivity.this
                            , "Select atleast 1 contract "
                            , Toast.LENGTH_LONG).show();
                } else {
                    new GetBillDeskMsg(TotalBillPayActivity.this, queryString);
                }

                Log.e("query string ", " query amount " + queryString);
            }
        });
        getData();

    }

    public void getData() {

        TmflApi service = ApiService.getInstance().call();

        dialog = new ProgressDialog(TotalBillPayActivity.this);
        dialog.setMessage("Loading");
        dialog.show();

        JSONData jsonData = new JSONData();
        jsonData.setUser_id(PreferenceHelper.getString(PreferenceHelper.USER_ID));
        jsonData.setApi_token(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));

        service.getListData(jsonData).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                List<Contract> list = new ArrayList<Contract>();
                list.addAll(response.body().getData().getActive().getContracts());
                Log.e("List Size", String.valueOf(response.body().getData().getActive().getContracts().size()));
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(false);
                }
                CustomAdapter adapter = new CustomAdapter(TotalBillPayActivity.this, listOfContract = list);
                listView.setAdapter(adapter);
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.e("Error Log", "Error:" + t.getMessage());
            }
        });
    }

    public void updateTotalAmount() {
        totalAmount = 0.0;
        if (listOfContract != null) {
            for (Contract contract : listOfContract) {
                if (contract.getIsSelected()) {
                    if (TextUtils.isDigitsOnly(contract.getTotalCurrentDue()) && !(TextUtils.isEmpty(contract.getTotalCurrentDue())))
                        totalAmount = totalAmount + Double.parseDouble(contract.getTotalCurrentDue());
                }
            }

        }
        Log.e("total amount ", " total amount " + totalAmount);
        ((TextView) findViewById(R.id.totalamounttextview)).setText(String.valueOf(totalAmount + ""));
    }

}

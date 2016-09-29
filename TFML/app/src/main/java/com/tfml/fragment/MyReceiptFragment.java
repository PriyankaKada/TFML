package com.tfml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.adapter.ReceiptListAdapter;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;


public class MyReceiptFragment extends Fragment implements View.OnClickListener {
    Button btnBack;
    View view;
    ListView lstReceipt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_receipt, container, false);
        init();
        ResponseEnvelope.Body responseEnvelope = (ResponseEnvelope.Body) PreferenceHelper.getObject(PreferenceHelper.SOAPSTATMENTOFACCOUNTRESPONSE, ResponseEnvelope.Body.class);
        if (responseEnvelope != null) {

            for (int i = 0; i < responseEnvelope.getZCISResponse().getI_REC().size(); i++) {
                Log.e("ResponseModel",""+responseEnvelope.getZCISResponse().getI_REC().size());
                lstReceipt.setAdapter(new ReceiptListAdapter(getActivity(),responseEnvelope));

            }

        }
        return view;
    }

    public void init() {
        lstReceipt = (ListView) view.findViewById(R.id.lst_my_receipt);
        btnBack = (Button) view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        SetFonts.setFonts(getActivity(), btnBack, 2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail, new EmiDetailFragment()).commit();
                break;
        }
    }
}

package com.tfml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.util.SetFonts;


public class MyReceiptFragment extends Fragment implements View.OnClickListener{
    Button btnBack;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_receipt, container, false);
        init();
        return view;
    }
    public void init()
    {
        btnBack=(Button)view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
        SetFonts.setFonts(getActivity(),btnBack,2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail,new EmiDetailFragment()).commit();
                break;
        }
    }
}

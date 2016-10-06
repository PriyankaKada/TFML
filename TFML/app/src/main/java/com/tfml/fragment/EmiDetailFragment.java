package com.tfml.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.tfml.R;
import com.tfml.util.SetFonts;

public class EmiDetailFragment extends Fragment implements View.OnClickListener {

    Button btnReciept;
    View view;
    ExpandableListView expandableListViewEmi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_emi_detail, container, false);
        init();
        return  view;
    }

     public void init()
     {
         expandableListViewEmi=(ExpandableListView)view.findViewById(R.id.expandableListViewEmi);
         btnReciept=(Button)view.findViewById(R.id.btnReciept);
         btnReciept.setOnClickListener(this);
         SetFonts.setFonts(getActivity(),btnReciept,2);
     }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnReciept:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail, new MyReceiptFragment()).commit();
                break;
        }
    }
}

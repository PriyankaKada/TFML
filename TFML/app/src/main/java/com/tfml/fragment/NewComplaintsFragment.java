package com.tfml.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tfml.R;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class NewComplaintsFragment extends Fragment {

    TextView txtComplainCaseId,txtFromDate,txtToDate;
    Button btnGo;
    LinearLayout linearLayout;
    ListView list;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView;
        rootView=inflater.inflate(R.layout.new_complaint,container,false);
        txtComplainCaseId=(TextView) rootView.findViewById(R.id.txtComplainCaseId);
        txtFromDate=(TextView) rootView.findViewById(R.id.txtFromDate);
        txtToDate=(TextView) rootView.findViewById(R.id.txtToDate);
        btnGo=(Button) rootView.findViewById(R.id.btnGo);
        linearLayout=(LinearLayout) rootView.findViewById(R.id.linearLayout);
        list=(ListView) rootView.findViewById(R.id.lstComplaints);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.VISIBLE);
                list.setVisibility(View.VISIBLE);
            }
        });
    }
}

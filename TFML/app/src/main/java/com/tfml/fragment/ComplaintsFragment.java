package com.tfml.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfml.R;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class ComplaintsFragment extends Fragment {

    TextView txtNewComplaint,txtTrackStatus;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView;
        rootView= inflater.inflate(R.layout.complaints_layout,container,false);
        txtNewComplaint=(TextView) rootView.findViewById(R.id.txtNewComplain);
        txtTrackStatus=(TextView) rootView.findViewById(R.id.txttrackStatus);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       final NewComplaintsFragment newComplaintsFragment= new NewComplaintsFragment();
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.containerFrameLayout,newComplaintsFragment);
        ft.commit();

        txtTrackStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTrackStatus.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.tab_bg));
                txtTrackStatus.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                txtNewComplaint.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
                txtNewComplaint.setTextColor(ContextCompat.getColor(getContext(),R.color.tab_bg));
                TrackStatusFragment trackStatusFragment= new TrackStatusFragment();
                FragmentManager fm= getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.add(R.id.containerFrameLayout,trackStatusFragment);
                ft.commit();

            }
        });

        txtNewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTrackStatus.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
                txtTrackStatus.setTextColor(ContextCompat.getColor(getContext(),R.color.tab_bg));
                txtNewComplaint.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.tab_bg));
                txtNewComplaint.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
                NewComplaintsFragment trackStatusFragment= new NewComplaintsFragment();
                FragmentManager fm= getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.add(R.id.containerFrameLayout,trackStatusFragment);
                ft.commit();
            }
        });
    }
}

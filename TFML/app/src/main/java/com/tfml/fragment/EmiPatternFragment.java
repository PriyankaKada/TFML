package com.tfml.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tfml.R;

public class EmiPatternFragment extends Fragment
{

    FrameLayout frmEmiPattern;
    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EmiDetailFragment emiDetailFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_emi_pattern, container, false);
        init();
        return view;
    }
     public void init()
     {
         frmEmiPattern=(FrameLayout)view.findViewById(R.id.frm_emi_detail);
          loadEmiDetail();

     }

    public void loadEmiDetail()
    {
        fragmentManager =getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        emiDetailFragment=new EmiDetailFragment();
        fragmentTransaction.add(R.id.frm_emi_detail,emiDetailFragment);
        fragmentTransaction.commit();

    }
}

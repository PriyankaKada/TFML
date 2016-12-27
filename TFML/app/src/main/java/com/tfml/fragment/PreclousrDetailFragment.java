package com.tfml.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.common.CommonUtils;


public class PreclousrDetailFragment extends Fragment {
	ListView lstPreclousre;
	View     view;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_preclousr_detail, container, false );
		init();
		return view;
	}

	public void init() {
		lstPreclousre = ( ListView ) view.findViewById( R.id.lst_pre_closure );


		if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
			CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
			// callSoapRequest();
		}
		else {
			Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
	}


}

package com.tfml.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.adapter.SchemesListAdapter;
import com.tfml.model.schemesResponseModel.Datum;
import com.tfml.model.schemesResponseModel.SchemesResponse;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.util.ArrayList;


public class NewSchemeFragment extends Fragment {


	TextView           txtNewSchemesHeader;
	ArrayList< Datum > arDatumList;
	SchemesResponse    response;
	private ListView lstnewschemes;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		response = ( SchemesResponse ) PreferenceHelper.getObject( "Scheme response", SchemesResponse.class );
		arDatumList = new ArrayList<>();
		arDatumList = response.getData();
		System.out.println( "----------------------->" + arDatumList );
		return inflater.inflate( R.layout.fragment_new_scheme, container, false );

	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
		lstnewschemes = ( ListView ) view.findViewById( R.id.lst_new_schemes );
		txtNewSchemesHeader = ( TextView ) view.findViewById( R.id.new_schemes_header );
		SetFonts.setFonts( getActivity(), txtNewSchemesHeader, 2 );

		if ( arDatumList != null && arDatumList.size() > 0 ) {
			lstnewschemes.setAdapter( new SchemesListAdapter( getActivity(), arDatumList ) );
		}

	}


}


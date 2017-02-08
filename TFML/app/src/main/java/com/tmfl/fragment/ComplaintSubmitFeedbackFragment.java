package com.tmfl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmfl.R;

/**
 * Created by webwerks on 14/12/16.
 */
public class ComplaintSubmitFeedbackFragment extends Fragment {

	private TextView txtCallScript, txtReferenceNo;

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.fragment_complaint_thanku, container, false );

		initView( view );

		String id = getActivity().getIntent().getStringExtra( "caseId" );
		txtReferenceNo.setText( "Your reference number is " + id );

		return view;
	}

	private void initView( View view ) {
		txtCallScript = ( TextView ) view.findViewById( R.id.txtCallScript );
		txtReferenceNo = ( TextView ) view.findViewById( R.id.txtReferenceNo );

		String textCallScript = "<font color=#f00>CallScript: </font>" + "<font color=#6D6D6D> " + getString( R.string.call_script ) + "</font>";
		txtCallScript.setText( Html.fromHtml( textCallScript ) );
	}
}

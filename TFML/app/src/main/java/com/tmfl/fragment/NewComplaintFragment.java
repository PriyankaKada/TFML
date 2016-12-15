package com.tmfl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tmfl.R;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class NewComplaintFragment extends Fragment implements View.OnClickListener {

	private Button btnSubmit;

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.fragment_new_complaint, container, false );

		initView( view );


		return view;
	}

	private void initView( View view ) {
		btnSubmit = ( Button ) view.findViewById( R.id.btnSubmit );

		btnSubmit.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {
		switch ( view.getId() ) {
			case R.id.btnSubmit:

				getFragmentManager()
						.beginTransaction()
						.addToBackStack( getClass().getName() )
						.replace( R.id.frame_complaint_container, new ComplaintThankuFragment() )
						.commit();

				break;
		}
	}
}

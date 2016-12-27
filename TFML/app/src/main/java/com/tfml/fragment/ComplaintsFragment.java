package com.tfml.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

	TextView txtNewComplaint, txtTrackStatus;
	private TrackStatusFragment  trackStatusFragment;
	private NewComplaintFragment newComplaintFragment;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View view;
		view = inflater.inflate( R.layout.fragment_complaint, container, false );
		txtNewComplaint = ( TextView ) view.findViewById( R.id.txtNewComplain );
		txtTrackStatus = ( TextView ) view.findViewById( R.id.txttrackStatus );
		return view;
	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
		trackStatusFragment = new TrackStatusFragment();
		newComplaintFragment = new NewComplaintFragment();

		getFragmentManager().beginTransaction().add( R.id.frame_complaint_container, newComplaintFragment ).commit();
		setNewComplaintLayout();

		txtTrackStatus.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				setTrackStatusLayout();
				getFragmentManager().beginTransaction().replace( R.id.frame_complaint_container, trackStatusFragment ).commit();
			}
		} );

		txtNewComplaint.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				setNewComplaintLayout();
				getFragmentManager().beginTransaction().replace( R.id.frame_complaint_container, newComplaintFragment ).commit();
			}
		} );
	}

	public void setNewComplaintLayout() {
		txtTrackStatus.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.tab_bg ) );
		txtTrackStatus.setTextColor( ContextCompat.getColor( getContext(), R.color.white ) );
		txtNewComplaint.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.white ) );
		txtNewComplaint.setTextColor( ContextCompat.getColor( getContext(), R.color.tab_bg ) );
	}

	public void setTrackStatusLayout() {
		txtTrackStatus.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.white ) );
		txtTrackStatus.setTextColor( ContextCompat.getColor( getContext(), R.color.tab_bg ) );
		txtNewComplaint.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.tab_bg ) );
		txtNewComplaint.setTextColor( ContextCompat.getColor( getContext(), R.color.white ) );
	}
}

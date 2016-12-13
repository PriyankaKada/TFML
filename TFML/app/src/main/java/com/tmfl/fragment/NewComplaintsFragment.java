package com.tmfl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.util.DatePickerDialog;
import com.tmfl.util.DatePickerFragment;

import java.util.Calendar;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class NewComplaintsFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateChangeListener {

	TextView txtComplainCaseId, txtFromDate, txtToDate;
	Button       btnGo;
	LinearLayout linearLayout;
	ListView     list;
	android.app.DatePickerDialog.OnDateSetListener fromDate = new android.app.DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtFromDate.setText( year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;

		}
	};
	android.app.DatePickerDialog.OnDateSetListener toDate   = new android.app.DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtToDate.setText( year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;

		}
	};
	private DatePickerFragment date;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View rootView;
		rootView = inflater.inflate( R.layout.fragment_new_complaint, container, false );
		date = new DatePickerFragment();
		txtComplainCaseId = ( TextView ) rootView.findViewById( R.id.txtComplainCaseId );
		txtFromDate = ( TextView ) rootView.findViewById( R.id.txtFromDate );
		txtToDate = ( TextView ) rootView.findViewById( R.id.txtToDate );
		btnGo = ( Button ) rootView.findViewById( R.id.btnGo );
		linearLayout = ( LinearLayout ) rootView.findViewById( R.id.linearLayout );
		list = ( ListView ) rootView.findViewById( R.id.lstComplaints );

		txtFromDate.setOnClickListener( this );
		txtToDate.setOnClickListener( this );
		return rootView;
	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
		btnGo.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				linearLayout.setVisibility( View.VISIBLE );
				list.setVisibility( View.VISIBLE );
			}
		} );
	}

	public void selectDate( String from ) {
		Calendar calender = Calendar.getInstance();
		Bundle   args     = new Bundle();
		args.putInt( "year", calender.get( Calendar.YEAR ) );
		args.putInt( "month", calender.get( Calendar.MONTH ) );
		args.putInt( "day", calender.get( Calendar.DAY_OF_MONTH ) );
		date.setArguments( args );
		/**
		 * Set Call back to capture selected date
		 */

		if ( from.equalsIgnoreCase( "fromDate" ) ) {
			date.setCallBack( fromDate, from );
		}
		else {
			date.setCallBack( toDate, from );
		}
		date.show( getFragmentManager(), "Date Picker" );
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {
			case R.id.txtFromDate:

				selectDate( "fromDate" );

				break;

			case R.id.txtToDate:

				selectDate( "toDate" );

				break;
		}
	}

	@Override
	public void onDateChange( String date, String picker ) {
		if ( picker.equalsIgnoreCase( " " ) ) {
			txtFromDate.setText( txtFromDate.getText().toString() + " " + date );
			txtToDate.setText( txtToDate.getText().toString() + " " + date );
		}
	}
}

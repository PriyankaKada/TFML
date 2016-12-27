package com.tfml.util;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Date;

public class DatePickerFragment extends DialogFragment {
	OnDateSetListener ondateSet;
	DatePickerDialog  datePickerDialog;
	String            from;
	private int year, month, day;

	public DatePickerFragment() {
	}

	public void setCallBack( OnDateSetListener ondate, String from ) {
		ondateSet = ondate;
		this.from = from;
	}

	@Override
	public void setArguments( Bundle args ) {
		super.setArguments( args );
		year = args.getInt( "year" );
		month = args.getInt( "month" );
		day = args.getInt( "day" );
	}

	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState ) {
		datePickerDialog = new DatePickerDialog( getActivity(), ondateSet, year, month, day );
		if ( from.equalsIgnoreCase( "statementOfAccount" ) || from.equalsIgnoreCase( "fromDate" ) || from.equalsIgnoreCase( "toDate" ) ) {
			datePickerDialog.getDatePicker().setMaxDate( new Date().getTime() );
		}

//		if ( from.equalsIgnoreCase( "fromDate" ) ) {
//			datePickerDialog.getDatePicker().setMaxDate( new Date().getTime() );
//		}
//
//		if ( from.equalsIgnoreCase( "toDate" ) ) {
//			datePickerDialog.getDatePicker().setMinDate( new Date().getTime() );
//		}

		return datePickerDialog;
	}
}
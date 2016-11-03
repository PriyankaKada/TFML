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
	private int year, month, day;

	public DatePickerFragment() {
	}

	public void setCallBack( OnDateSetListener ondate ) {
		ondateSet = ondate;
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
		datePickerDialog.getDatePicker().setMaxDate( new Date().getTime() );
		return datePickerDialog;
	}
}
package com.tmfl.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.tmfl.R;


/**
 * Created by webwerks on 26/5/15.
 */
public class DatePickerDialog extends AlertDialog implements android.app.DatePickerDialog.OnDateSetListener, View.OnClickListener {

	OnDateChangeListener listener;
	String               date, picker;
	DatePicker datePicker;

	public DatePickerDialog( Context context, OnDateChangeListener listener, String picker ) {
		super( context );
		this.listener = listener;
		this.picker = picker;
		View view = LayoutInflater.from( context ).inflate( R.layout.dialog_datepicker, null );
		datePicker = ( DatePicker ) view.findViewById( R.id.date_picker );
		view.findViewById( R.id.btnDone ).setOnClickListener( this );
		setView( view );
		show();
	}

	@Override
	public void onClick( View v ) {
		dismiss();
		StringBuilder builder = new StringBuilder();
		builder.append( datePicker.getYear() + "-" );
		builder.append( ( datePicker.getMonth() + 1 ) + "-" );//month is 0 based
		builder.append( datePicker.getDayOfMonth() );
		date = builder.toString();
		listener.onDateChange( date, picker );
	}

	@Override
	public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
		date = String.valueOf( year ) + "-" + ( ( String.valueOf( monthOfYear + 1 ) ) ) + "-" + String.valueOf( dayOfMonth );
	}

	public interface OnDateChangeListener {
		void onDateChange( String date, String picker );
	}
}

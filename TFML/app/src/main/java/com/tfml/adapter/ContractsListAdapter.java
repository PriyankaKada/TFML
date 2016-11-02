package com.tfml.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.activity.EmiActivity;
import com.tfml.model.ContractResponseModel.ContractModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by webwerks on 1/10/16.
 */


public class ContractsListAdapter extends ArrayAdapter< ContractModel > {

	Context  mContext;
	TextView txt_contract_no, txt_rc_no, txt_product_name, txt_current_emi_amount,
			txt_last_payment_date, txt_next_due_date, txt_overdue_amount, txt_repayment_mode;

	TextView txtContractNoTerm, txt_rc_no_term, txt_terminated_name, txt_terminated_date;
	TextView btn_pay_emi, btn_more_detail;
	ArrayList< ContractModel > arrayList;
	SimpleDateFormat           dateFormat, format2;
	Date varDate;

	public ContractsListAdapter( Context context, ArrayList< ContractModel > objects ) {
		super( context, 0, objects );
		mContext = context;
		this.arrayList = objects;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		final ContractModel model = getItem( position );
		if ( model == null ) {
			convertView = LayoutInflater.from( mContext ).inflate( R.layout.header_terminated_contract, parent, false );
		}
		else if ( model.getContractStatus().equalsIgnoreCase( "L" ) ) {
			convertView = LayoutInflater.from( mContext ).inflate( R.layout.row_active_contract, parent, false );
			txt_product_name = ( TextView ) convertView.findViewById( R.id.txt_product_name );
			txt_contract_no = ( TextView ) convertView.findViewById( R.id.txt_contract_no );
			txt_rc_no = ( TextView ) convertView.findViewById( R.id.txt_rc_no );
			txt_current_emi_amount = ( TextView ) convertView.findViewById( R.id.txt_current_emi_amount );
			txt_next_due_date = ( TextView ) convertView.findViewById( R.id.txt_next_due_date );
			txt_overdue_amount = ( TextView ) convertView.findViewById( R.id.txt_overdue_amount );
			txt_repayment_mode = ( TextView ) convertView.findViewById( R.id.txt_repayment_mode );
			txt_last_payment_date = ( TextView ) convertView.findViewById( R.id.txt_last_payment_date );
		  /*  btn_pay_emi=(Button) convertView.findViewById(R.id.btn_pay_emi);
		    btn_more_detail=(Button)convertView.findViewById(R.id.btn_more_detail);*/
			btn_pay_emi = ( TextView ) convertView.findViewById( R.id.btn_pay_emi );
			btn_more_detail = ( TextView ) convertView.findViewById( R.id.btn_more_detail );
			txt_product_name.setText( model.getProduct() == null ? "" : model.getProduct().toString() );
			txt_contract_no.setText( model.getUsrConNo() == null ? "" : model.getUsrConNo() );
			txt_rc_no.setText( model.getRcNumber() == null ? "" : model.getRcNumber() );
			//getTotalCurrentDue instead of getOdAmt()
			txt_overdue_amount.setText( model.getOdAmt() == null ? "" : "Rs." + model.getOdAmt() );
			txt_repayment_mode.setText( model.getPdcFlag() == null ? "" : model.getPdcFlag() );
			txt_next_due_date.setText( model.getDueDate() == null ? "" : model.getDueDate().toString() );
			txt_last_payment_date.setText( model.getLastReceiptDate() == null ? "" : model.getLastReceiptDate().toString() );
			txt_current_emi_amount.setText( model.getDueAmount().toString() == null ? "" : "Rs." + model.getDueAmount().toString() );
			Log.e( "DueAmount", model.getDueAmount().toString() );
			Log.e( "TDueAmount", txt_current_emi_amount.getText().toString() );

			btn_more_detail.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick( View v ) {
					System.out.println( "arrayList---------->" + arrayList.size() );
					String senddatavalue     = txt_contract_no.getText().toString();
					String sendRcno          = txt_rc_no.getText().toString();
					String sendOverdueAmt    = txt_overdue_amount.getText().toString();
					String sendRepaymentMode = txt_repayment_mode.getText().toString();
					String sendNextDueDate   = txt_next_due_date.getText().toString();
					String sendCurrentEmiAmt = txt_current_emi_amount.getText().toString();
					String sendLastPay       = txt_last_payment_date.getText().toString();
					Intent intent            = new Intent( mContext, EmiActivity.class );
					Bundle bundle            = new Bundle();
					bundle.putSerializable( "datamodel", arrayList );
					bundle.putString( "datamodelvalue", senddatavalue );
					bundle.putString( "RCNO", sendRcno );
					bundle.putString( "OVERDUEAMT", sendOverdueAmt );
					bundle.putString( "REPAYMENT", sendRepaymentMode );
					bundle.putString( "DUEDATE", sendNextDueDate );
					bundle.putString( "CURRENTEMI", sendCurrentEmiAmt );
					bundle.putString( "LASTPAYMODE", sendLastPay );
					intent.putExtras( bundle );
					mContext.startActivity( intent );
				}
			} );

		}
		else if ( model.getContractStatus().equalsIgnoreCase( "T" ) ) {
			convertView = LayoutInflater.from( mContext ).inflate( R.layout.row_terminated_contract, parent, false );
			txtContractNoTerm = ( TextView ) convertView.findViewById( R.id.txtContractNoTerm );
			txt_rc_no_term = ( TextView ) convertView.findViewById( R.id.txt_rc_no_term );
			txt_terminated_name = ( TextView ) convertView.findViewById( R.id.txt_terminated_name );
			txt_terminated_date = ( TextView ) convertView.findViewById( R.id.txt_terminated_date );
			if ( model.getContractStatusDate() != null ) {
				txt_terminated_name.setText( model.getProduct() == null ? "" : model.getProduct().toString() );
				txtContractNoTerm.setText( model.getUsrConNo() == null ? "" : model.getUsrConNo().toString() );
				txt_rc_no_term.setText( model.getRcNumber() == null ? "" : model.getRcNumber().toString() );

				dateFormat = new SimpleDateFormat( "dd-MMM-yyyy" );
				try {
					if ( model.getContractStatusDate() != null ) {
						varDate = dateFormat.parse( model.getContractStatusDate().toString() );
						dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
					}
					txt_terminated_date.setText( model.getContractStatusDate() == null ? "" : "Terminated On " + dateFormat.format( varDate ) );

				}
				catch ( Exception e ) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}

		return convertView;
	}


}

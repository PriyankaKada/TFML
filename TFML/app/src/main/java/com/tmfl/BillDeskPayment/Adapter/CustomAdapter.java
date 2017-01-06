package com.tmfl.BillDeskPayment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmfl.BillDeskPayment.Models.Contract;
import com.tmfl.R;


import java.util.List;

/**
 * Created by webwerks on 2/1/17.
 */
public class CustomAdapter extends ArrayAdapter<Contract> {


	List< Contract > listItems;
	Context          mContext;
	ViewHolder       holder;


	public CustomAdapter( Context context, List< Contract > contract ) {
		super( context, 0, contract );
		mContext = context;
		listItems = contract;
	}


	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent ) {
		final Contract contract = getItem( position );
		LayoutInflater inflater = ( LayoutInflater ) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		if ( convertView == null ) {

			convertView = inflater.inflate( R.layout.list_item_layout, parent, false );
			holder = new ViewHolder();
			holder.txtContractNoValue=(TextView ) convertView.findViewById( R.id.txtContractNoValue );
			holder.txtRcNo = ( TextView ) convertView.findViewById( R.id.txtRcNoValue );
			holder.txtMonthlyEMI = ( TextView ) convertView.findViewById( R.id.txtMonthlyEmiValue );
			holder.txtOverdue = ( TextView ) convertView.findViewById( R.id.txtOverdueValue );
			holder.txtOverdueChanged = ( TextView ) convertView.findViewById( R.id.txtOverdueChargedValue );
			holder.txtExpenses = ( TextView ) convertView.findViewById( R.id.txtExpensesValue );
			holder.txtTotalDue = ( TextView ) convertView.findViewById( R.id.txtTotalDueValue );
			holder.txtEnterAmount = ( EditText ) convertView.findViewById( R.id.txtAmountValue );
			holder.imgTick=(ImageView ) convertView.findViewById( R.id.imgTick );
			convertView.setTag( holder );
		}
		else {
			holder = ( ViewHolder ) convertView.getTag();
		}

		Log.e("contract",contract.getRcNumber());
		holder.txtContractNoValue.setText( contract.getUsrConNo() );
		holder.txtRcNo.setText( contract.getRcNumber() );
		holder.txtMonthlyEMI.setText( contract.getDueAmount() );
		holder.txtOverdue.setText( contract.getOdcCollectioAmount() );
		holder.txtOverdueChanged.setText( contract.getOdAmt() );
		holder.txtExpenses.setText( contract.getTotalExpenses() );
		holder.txtTotalDue.setText( contract.getDueAmount() );
		if(contract.getIsSelected()) {
			holder.imgTick.setImageResource(R.drawable.ic_check_circle_green );
		}else {
			holder.imgTick.setImageResource( R.drawable.ic_check_circle_amber);
		}


		holder.imgTick.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				if(contract.getIsSelected()) {
					contract.setSelected( false );
					holder.imgTick.setImageResource( R.drawable.ic_check_circle_amber);
				}else {
					contract.setSelected( true );
					holder.imgTick.setImageResource(R.drawable.ic_check_circle_green );
					Log.e( "IMage POsition", contract.getUsrConNo() );
				}
				notifyDataSetChanged();

			}
		} );
		return convertView;
	}

	class ViewHolder {
		TextView txtContractNoValue,txtRcNo, txtMonthlyEMI, txtOverdue, txtOverdueChanged, txtExpenses, txtTotalDue;
		EditText txtEnterAmount;
		ImageView imgTick;

	}
}

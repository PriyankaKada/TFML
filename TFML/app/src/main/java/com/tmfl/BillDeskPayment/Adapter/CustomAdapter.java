package com.tmfl.BillDeskPayment.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmfl.BillDeskPayment.Activity.TotalBillPayActivity;
import com.tmfl.BillDeskPayment.Models.Contract;
import com.tmfl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 2/1/17.
 */

public class CustomAdapter extends ArrayAdapter< Contract > {

	static int            amount      = 0;
	static List< String > TotalAmount = new ArrayList<>();
	List< Contract > listItems;
	Context          mContext;
	double totalAmount = 0.0;


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

		Contract       dummyContract = contract;
		LayoutInflater inflater      = ( LayoutInflater ) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		final ViewHolder holder;
		if ( convertView == null ) {
			convertView = inflater.inflate( R.layout.list_item_layout, parent, false );
			holder = new ViewHolder();
			holder.txtContractNoValue = ( TextView ) convertView.findViewById( R.id.txtContractNoValue );
			holder.txtRcNo = ( TextView ) convertView.findViewById( R.id.txtRcNoValue );
			holder.txtMonthlyEMI = ( TextView ) convertView.findViewById( R.id.txtMonthlyEmiValue );
			holder.txtOverdue = ( TextView ) convertView.findViewById( R.id.txtOverdueValue );
			holder.txtOverdueChanged = ( TextView ) convertView.findViewById( R.id.txtOverdueChargedValue );
			holder.txtExpenses = ( TextView ) convertView.findViewById( R.id.txtExpensesValue );
			holder.txtTotalDue = ( TextView ) convertView.findViewById( R.id.txtTotalDueValue );
			holder.txtEnterAmount = ( EditText ) convertView.findViewById( R.id.txtAmountValue );
			holder.mWatcher = new TextListener();
			holder.txtEnterAmount.addTextChangedListener( holder.mWatcher );
			holder.imgTick = ( ImageView ) convertView.findViewById( R.id.imgTick );

			convertView.setTag( holder );
		}
		else {
			holder = ( ViewHolder ) convertView.getTag();
		}

		Log.e( "contract", contract.getRcNumber() );
		if ( contract.getUsrConCompCode().equalsIgnoreCase( "5000" ) ) {
			holder.txtContractNoValue.setText( contract.getUsrConNo() );
			holder.txtRcNo.setText( contract.getRcNumber() );
			holder.txtOverdue.setText( contract.getOdAmt() );
			holder.txtOverdueChanged.setText( contract.getDueAmount() );
			holder.txtExpenses.setText( contract.getTotalExpenses() );
			holder.txtTotalDue.setText( contract.getTotalCurrentDue() );

			holder.mWatcher.active = false;
//		holder.txtEnterAmount.setInputType( InputType.TYPE_NUMBER_FLAG_DECIMAL );
			holder.txtEnterAmount.setText( contract.getNewTotalCurrentDue() == null || contract.getNewTotalCurrentDue().equalsIgnoreCase( "" ) ? contract.getTotalCurrentDue() : contract.getNewTotalCurrentDue() );
			holder.mWatcher.pos = position;

			holder.mWatcher.active = true;
			holder.imgTick.setImageResource( contract.getIsSelected() ? R.drawable.ic_check_circle_green : R.drawable.ic_check_circle_amber );
			holder.txtEnterAmount.setEnabled( !contract.getIsSelected() );

			if ( contract.getIsSelected() ) {
				holder.txtEnterAmount.setEnabled( true );
			}
			else {
				holder.txtEnterAmount.setEnabled( false );
			}
			holder.imgTick.setTag( holder.txtEnterAmount );
			holder.imgTick.setOnClickListener( new View.OnClickListener() {
				@Override
				public void onClick( View view ) {

					EditText editText = ( EditText ) view.getTag();
					double value = Double.parseDouble( editText.getText().toString().trim().equalsIgnoreCase( "" )
							                                   ? "0.0" : editText.getText().toString().trim() );
					listItems.get( position ).setSelected( !listItems.get( position ).getIsSelected() );
					holder.imgTick.setImageResource( listItems.get( position ).getIsSelected() ? R.drawable.ic_check_circle_green : R.drawable.ic_check_circle_amber );
					editText.setEnabled( listItems.get( position ).getIsSelected() );

					( ( TotalBillPayActivity ) mContext ).updateTotalAmount();

				}
			} );
		}

		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	class ViewHolder {
		public TextListener mWatcher;
		TextView txtContractNoValue, txtRcNo, txtMonthlyEMI, txtOverdue, txtOverdueChanged, txtExpenses, txtTotalDue;
		EditText  txtEnterAmount;
		ImageView imgTick;

		public ViewHolder() {

		}
	}

	class TextListener implements TextWatcher {

		int     pos;
		boolean active;

		public int getPos() {
			return pos;
		}

		public void setPos( int pos ) {
			this.pos = pos;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive( boolean active ) {
			this.active = active;
		}

		@Override
		public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

		}

		@Override
		public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 ) {

		}

		@Override
		public void afterTextChanged( Editable editable ) {
			if ( active ) {
//				getItem( pos ).setTotalCurrentDue( editable.toString() );

				//( ( TotalBillPayActivity ) mContext ).amount = editable.toString();
				getItem( pos ).setNewTotalCurrentDue( editable.toString() );

				if ( TextUtils.isEmpty( editable.toString() ) ) {
					getItem( pos ).setNewTotalCurrentDue( "0.0" );
				}
				//( ( TotalBillPayActivity ) mContext ).updateTotalAmount( 0, pos );
			}


			( ( TotalBillPayActivity ) mContext ).updateTotalAmount();
		}
	}
}

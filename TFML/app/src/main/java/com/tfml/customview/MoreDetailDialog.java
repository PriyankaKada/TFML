package com.tfml.customview;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.activity.EmiActivity;
import com.tfml.auth.Constant;
import com.tfml.model.ContractResponseModel.ActiveContractsModel;
import com.tfml.model.ContractResponseModel.ContractModel;
import com.tfml.util.PreferenceHelper;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by webwerks on 16/12/16.
 */
public class MoreDetailDialog extends Dialog implements View.OnClickListener {

	Bundle bundle;
	private Context  mContext;
	private TextView txtReceipt, txtEmiPattern, txtStatementOfAccount, txtRcUpdate, txtPreClosure;
	private ActiveContractsModel       activeContractsModel;
	private ArrayList< ContractModel > contractModels;
	private ContractModel              contractModel;

	public MoreDetailDialog( Context context ) {
		super( context );
		mContext = context;
		init();
	}

	public static String trimLeadingZeros( String source ) {
		/*for ( int i = 0; i < source.length(); ++i ) {
			char c = source.charAt( i );
			if ( c != '0' && !Character.isSpaceChar( c ) ) {
				return source.substring( i );
			}
		}*/
		return source;
	}

	private void init() {
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
		setContentView( R.layout.dialog_more_details );
		getWindow().setLayout( WRAP_CONTENT, WRAP_CONTENT );

		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.gravity = Gravity.CENTER;
		getWindow().setAttributes( params );
		getWindow().getAttributes().windowAnimations = R.style.animationdialog;
		setCancelable( true );

		txtReceipt = ( TextView ) findViewById( R.id.txtReceipt );
		txtEmiPattern = ( TextView ) findViewById( R.id.txtEmiPattern );
		txtStatementOfAccount = ( TextView ) findViewById( R.id.txtStatementOfAccount );
		txtRcUpdate = ( TextView ) findViewById( R.id.txtRcUpdate );
		txtPreClosure = ( TextView ) findViewById( R.id.txtPreClosure );

		txtReceipt.setOnClickListener( this );
		txtEmiPattern.setOnClickListener( this );
		txtStatementOfAccount.setOnClickListener( this );
		txtRcUpdate.setOnClickListener( this );
		txtPreClosure.setOnClickListener( this );

		contractModels = new ArrayList<>();

		activeContractsModel = ( ActiveContractsModel ) PreferenceHelper.getObject( Constant.ONGOING_LOAN, ActiveContractsModel.class );
		contractModels.addAll( activeContractsModel.getContracts() );
		contractModel = ( ContractModel ) PreferenceHelper.getObject( Constant.CONTRACT_DETAIL, ContractModel.class );

		bundle = new Bundle();

		bundle.putSerializable( "datamodel", contractModels );
		bundle.putString( "datamodelvalue", contractModel.getUsrConNo() );
		PreferenceHelper.insertString( PreferenceHelper.CONTRACT_NO, trimLeadingZeros( contractModel.getUsrConNo() ) );
		bundle.putString( "RCNO", contractModel.getRcNumber() );
		bundle.putString( "OVERDUEAMT", contractModel.getOdAmt() );
		bundle.putString( "REPAYMENT", contractModel.getPdcFlag() );
		bundle.putString( "DUEDATE", String.valueOf( contractModel.getDueDate() ) );
		bundle.putString( "CURRENTEMI", String.valueOf( contractModel.getDueAmount() ) );
		bundle.putString( "LASTPAYMODE", contractModel.getLastReceiptDate() );

		show();
	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {
			case R.id.txtReceipt:

				PreferenceHelper.insertString( Constant.SHOW_PAGE, Constant.RECEIPT );

				mContext.startActivity( new Intent( mContext, EmiActivity.class )
						                        .putExtras( bundle ) );

				dismiss();

				break;

			case R.id.txtEmiPattern:

				PreferenceHelper.insertString( Constant.SHOW_PAGE, Constant.EMI_PATTERN );

				mContext.startActivity( new Intent( mContext, EmiActivity.class )
						                        .putExtras( bundle ) );

				dismiss();

				break;

			case R.id.txtStatementOfAccount:

				PreferenceHelper.insertString( Constant.SHOW_PAGE, Constant.STATEMENT_OF_ACCOUNT );

				mContext.startActivity( new Intent( mContext, EmiActivity.class )
						                        .putExtras( bundle ) );

				dismiss();

				break;

			case R.id.txtRcUpdate:

				PreferenceHelper.insertString( Constant.SHOW_PAGE, Constant.RC_UPDATE );

				mContext.startActivity( new Intent( mContext, EmiActivity.class )
						                        .putExtras( bundle ) );

				dismiss();

				break;

			case R.id.txtPreClosure:

				PreferenceHelper.insertString( Constant.SHOW_PAGE, Constant.PRECLOSURE );

				mContext.startActivity( new Intent( mContext, EmiActivity.class )
						                        .putExtras( bundle ) );

				dismiss();

				break;
		}
	}
}

package com.tmfl.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.model.ContractResponseModel.ContractModel;
import com.tmfl.util.PreferenceHelper;

import java.util.ArrayList;

public class EmiPatternFragment extends Fragment {

	FrameLayout                frmEmiPattern;
	View                       view;
	FragmentManager            fragmentManager;
	FragmentTransaction        fragmentTransaction;
	EmiDetailFragment          emiDetailFragment;
	Spinner                    spnContractNo;
	ArrayList< ContractModel > modelArrayList;
	TextView                   txt_repaymentmode, txt_emiamount, txt_dueamount, txt_duedate, txt_rc_no;
	int itemindex = 0;
	private ArrayList< String > nonzerocontractLst;
	private ArrayList< String > contractlist;
	private String datavalue     = "";
	private String rcNo          = "";
	private String dueDate       = "";
	private String repaymentMode = "";
	private String currentEmi    = "";
	private String overdue       = "";

	public static String trimLeadingZeros( String source ) {
		/*for ( int i = 0; i < source.length(); ++i ) {
			char c = source.charAt( i );
			if ( c != '0' && !Character.isSpaceChar( c ) ) {
				return source.substring( i );
			}
		}*/
		return source;
//		source.replaceFirst("^0+(?!$)", "");
//	String s=	source;

	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_emi_pattern, container, false );
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		modelArrayList =
				( ArrayList< ContractModel > ) bundle.getSerializable( "datamodel" );
//		datavalue = ( String ) bundle.getString( "datamodelvalue" );
		datavalue = PreferenceHelper.getString( PreferenceHelper.CONTRACT_NO );
		PreferenceHelper.insertString( PreferenceHelper.CONTRACT_NO, datavalue );
		rcNo = ( String ) bundle.getString( "RCNO" );
		dueDate = ( String ) bundle.getString( "DUEDATE" );
		repaymentMode = ( String ) bundle.getString( "REPAYMENT" );
		currentEmi = ( String ) bundle.getString( "CURRENTEMI" );
		overdue = ( String ) bundle.get( "OVERDUEAMT" );
//		((AppCompatActivity )getActivity()).getSupportActionBar().setTitle(R.string.Emipattern);


		init();
		return view;

	}

	public void init() {
		spnContractNo = ( Spinner ) view.findViewById( R.id.spnContractNo );

		contractlist = new ArrayList< String >();
		nonzerocontractLst = new ArrayList< String >();

		if ( modelArrayList.size() > 0 ) {
			for ( int i = 0; i < modelArrayList.size(); i++ ) {
//			contractLst.add(modelArrayList.get( i ).getUsrConNo());
//				contractlist.add( modelArrayList.get( i ).getUsrConNo());
				nonzerocontractLst.add( trimLeadingZeros( modelArrayList.get( i ).getUsrConNo() ) );
//				contractLst.add(Integer.valueOf(modelArrayList.get( i ).getUsrConNo()).toString());

			}

			ArrayAdapter< String > madapter = new ArrayAdapter< String >( getActivity(), R.layout.spinner_row, nonzerocontractLst ) {

				@Override
				public boolean isEnabled( int position ) {
					return true;
				}

				@Override
				public View getDropDownView( int position, View convertView,
				                             ViewGroup parent ) {
					View     view = super.getDropDownView( position, convertView, parent );
					TextView tv   = ( TextView ) view;
					tv.setTextColor( Color.BLACK );
					return view;
				}
			};
			madapter.setDropDownViewResource( R.layout.spinner_item );
			spnContractNo.setAdapter( madapter );
			madapter.notifyDataSetChanged();

			Log.d( "datavalue", datavalue + contractlist.size() );
			for ( int i = 0; i < contractlist.size(); i++ ) {
				if ( contractlist.get( i ).equalsIgnoreCase( datavalue ) ) {
					spnContractNo.setSelection( i );
					Log.d( "contractList", contractlist.get( i ) );
				}
			}
		}

//		spnContractNo.setSelection( 1 );
//         spnContractNo.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,contractLst));


		txt_rc_no = ( TextView ) view.findViewById( R.id.txt_rc_no );
		frmEmiPattern = ( FrameLayout ) view.findViewById( R.id.frm_emi_detail );
		txt_repaymentmode = ( TextView ) view.findViewById( R.id.txt_repaymentmode );
		txt_emiamount = ( TextView ) view.findViewById( R.id.txt_emiamount );
		txt_dueamount = ( TextView ) view.findViewById( R.id.txt_dueamount );
		txt_duedate = ( TextView ) view.findViewById( R.id.txt_duedate );
		if ( rcNo != null ) {
			txt_rc_no.setText( rcNo );
		}
		if ( repaymentMode != null ) {
			txt_repaymentmode.setText( repaymentMode );
		}
		if ( dueDate != null ) {
			txt_duedate.setText( dueDate );
		}
		if ( currentEmi != null ) {
			txt_emiamount.setText( "Rs." + currentEmi );
		}
		if ( overdue != null ) {
			txt_dueamount.setText( "Rs." + overdue );
		}

		spnContractNo.post( new Runnable() {
			@Override
			public void run() {
				spnContractNo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
						itemindex = position;
						ContractModel model = modelArrayList.get( itemindex );
						setData( model );
						Log.e( "ID", " " + modelArrayList.get( position ).getUsrConNo() );
						PreferenceHelper.insertString( PreferenceHelper.CONTRACT_NO, modelArrayList.get( position ).getUsrConNo() );
						getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.frm_emi_detail, new MyReceiptFragment() ).commit();
					}

					@Override
					public void onNothingSelected( AdapterView< ? > parent ) {

					}
				} );
			}
		} );

		loadEmiDetail();
	}

	private void setData( ContractModel model ) {
		if ( model != null ) {
			txt_rc_no.setText( model.getRcNumber() == null ? "" : model.getRcNumber().toString() );
			txt_duedate.setText( model.getDueDate() == null ? "" : model.getDueDate().toString() );
			txt_emiamount.setText( model.getDueAmount() == null ? "Rs. 0" : "Rs. " + model.getDueAmount().toString() );
			txt_repaymentmode.setText( model.getPdcFlag() == null ? "" : model.getPdcFlag().toString() );
			txt_dueamount.setText( model.getTotalCurrentDue() == null ? "Rs. 0" : "Rs." + model.getTotalCurrentDue().toString() );
		}
	}

	public void loadEmiDetail() {
		fragmentManager = getActivity().getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		emiDetailFragment = new EmiDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString( "datamodelvalue", datavalue );
		fragmentTransaction.add( R.id.frm_emi_detail, emiDetailFragment );
		fragmentTransaction.commit();
	}
}

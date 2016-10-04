package com.tfml.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.SetFonts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BasicDetailFragment extends Fragment {
	String aggrementNo, aggrementDate, maturityDate, tenure, tenure_unit, chasisNo, engineNo, strSoaDateTo, strSoaDateFrom;
	String strFromDate, strToDate, strAggrementDate, strMaturityDate;
	private View     view;
	private TextView txtStmtofAc, txtContractNo, txtContractDate, txtMaturityDate, txtTenure, txtChasisNo, txtEngineNo, txtEmailId;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_basic_detail, container, false );

		Bundle bundle = getArguments();
		if ( bundle != null ) {
			ResponseEnvelope.Body body = ( ResponseEnvelope.Body ) bundle.getSerializable( "ResponseModel" );

			strSoaDateTo = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_TO();
			strSoaDateFrom = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_FROM();
			aggrementNo = body.getZCISResponse().getIT_CARDEX1().getItem().getCONTRACTNO().toString();
			aggrementDate = body.getZCISResponse().getIT_CARDEX1().getItem().getCONTSTARTDT();
			maturityDate = body.getZCISResponse().getIT_CARDEX1().getItem().getCONTENDDT();
			tenure = body.getZCISResponse().getIT_CARDEX1().getItem().getTENURE().trim();
			tenure_unit = body.getZCISResponse().getIT_CARDEX1().getItem().getTENURE_UNIT();
			chasisNo = body.getZCISResponse().getIT_CARDEX1().getItem().getCHASIS_NO();
			engineNo = body.getZCISResponse().getIT_CARDEX1().getItem().getENGINE_NO();
			DateFormat inputFormat  = new SimpleDateFormat( "yyyy-MM-dd" );
			DateFormat outputFormat = new SimpleDateFormat( "dd-MMM-yyyy" );

			try {
				Date date  = inputFormat.parse( strSoaDateTo );
				Date date1 = inputFormat.parse( strSoaDateFrom );
				Date date2 = inputFormat.parse( aggrementDate );
				Date date3 = inputFormat.parse( maturityDate );
				strToDate = outputFormat.format( date );
				strFromDate = outputFormat.format( date1 );
				strAggrementDate = outputFormat.format( date2 );
				strMaturityDate = outputFormat.format( date3 );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			init();
		}


		return view;
	}

	public void init() {
		txtStmtofAc = ( TextView ) view.findViewById( R.id.txt_statement_of_account_detail );
		txtContractNo = ( TextView ) view.findViewById( R.id.txt_agreement_no );
		txtContractDate = ( TextView ) view.findViewById( R.id.txt_agreement_date );
		txtMaturityDate = ( TextView ) view.findViewById( R.id.txt_maturity_date );
		txtTenure = ( TextView ) view.findViewById( R.id.txt_tenure_period );
		txtChasisNo = ( TextView ) view.findViewById( R.id.txt_chasis_no );
		txtEngineNo = ( TextView ) view.findViewById( R.id.txt_engine_no );
		txtEmailId = ( TextView ) view.findViewById( R.id.txt_email_id );

		SetFonts.setFonts( getActivity(), txtStmtofAc, 2 );
		SetFonts.setFonts( getActivity(), txtContractNo, 2 );
		SetFonts.setFonts( getActivity(), txtContractDate, 2 );
		SetFonts.setFonts( getActivity(), txtMaturityDate, 2 );
		SetFonts.setFonts( getActivity(), txtChasisNo, 2 );
		SetFonts.setFonts( getActivity(), txtEngineNo, 2 );
		SetFonts.setFonts( getActivity(), txtEmailId, 2 );
		txtStmtofAc.setText( "For the Period " + strFromDate + " To " + strToDate );
		txtContractNo.setText( aggrementNo );
		txtContractDate.setText( strAggrementDate );
		txtMaturityDate.setText( strMaturityDate );
		txtTenure.setText( tenure + " " + tenure_unit );
		txtChasisNo.setText( chasisNo );
		txtEngineNo.setText( engineNo );


	}

}

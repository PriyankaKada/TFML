package com.tmfl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.model.soapModel.response.ResponseEnvelope;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BasicDetailFragment extends Fragment {
	String aggrementNo, aggrementDate, maturityDate, tenure, tenure_unit, chasisNo, engineNo, strSoaDateTo, strSoaDateFrom, strClientName, strEmail;
	String strFromDate, strToDate, strAggrementDate, strMaturityDate;
	private View     view;
	private TextView txtStmtofAc, txtContractNo, txtContractDate, txtMaturityDate, txtTenure, txtChasisNo, txtEngineNo, txtEmailId, txtClientName, txtRCNO, txtStatement_of_account,lbl_statement_of_accountLabel;
	private String rcNo, currentDate;
	private String dateString;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_basic_detail, container, false );

		Bundle bundle = getArguments();
		if ( bundle != null ) {
			dateString=bundle.getString("date");
			ResponseEnvelope.Body body = ( ResponseEnvelope.Body ) bundle.getSerializable( "ResponseModel" );
			//  strFinAmt=body.getZCISResponse().getIT_CARDEX1().getItem().getFIN_AMT()==null?"":body.getZCISResponse().getIT_CARDEX1().getItem().getFIN_AMT();
			strClientName = body.getZCISResponse().getIT_CARDEX1().getItem().getCLIENT_NAME() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getCLIENT_NAME().toString();
			strSoaDateTo = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_TO() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_TO();
			strSoaDateFrom = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_FROM() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_FROM();
			aggrementNo = body.getZCISResponse().getIT_CARDEX1().getItem().getCONTRACTNO().toString() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getCONTRACTNO().toString();
			aggrementDate = body.getZCISResponse().getIT_CARDEX1().getItem().getCONTSTARTDT() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getCONTSTARTDT();
			maturityDate = body.getZCISResponse().getIT_CARDEX1().getItem().getCONTENDDT() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getMATURITY_DATE();
			tenure = body.getZCISResponse().getIT_CARDEX1().getItem().getTENURE().trim() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getTENURE().trim();
			tenure_unit = body.getZCISResponse().getIT_CARDEX1().getItem().getTENURE_UNIT() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getTENURE_UNIT();
			chasisNo = body.getZCISResponse().getIT_CARDEX1().getItem().getCHASIS_NO() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getCHASIS_NO();
			engineNo = body.getZCISResponse().getIT_CARDEX1().getItem().getENGINE_NO() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getENGINE_NO();
			strEmail = PreferenceHelper.getString( PreferenceHelper.EMAIL );
			rcNo = body.getZCISResponse().getIT_CARDEX1().getItem().getREG_NO();
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
				currentDate = outputFormat.format( System.currentTimeMillis() );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			init();
		}
		return view;
	}

	public void init() {

		txtStatement_of_account = ( TextView ) view.findViewById( R.id.txtStatement_of_account );
		txtRCNO = ( TextView ) view.findViewById( R.id.txtRCNO );
		txtClientName = ( TextView ) view.findViewById( R.id.txt_client_name );
		txtStmtofAc = ( TextView ) view.findViewById( R.id.txt_statement_of_account_detail );
		txtContractNo = ( TextView ) view.findViewById( R.id.txt_agreement_no );
		txtContractDate = ( TextView ) view.findViewById( R.id.txt_agreement_date );
		txtMaturityDate = ( TextView ) view.findViewById( R.id.txt_maturity_date );
		txtTenure = ( TextView ) view.findViewById( R.id.txt_tenure_period );
		txtChasisNo = ( TextView ) view.findViewById( R.id.txt_chasis_no );
		txtEngineNo = ( TextView ) view.findViewById( R.id.txt_engine_no );
		txtEmailId = ( TextView ) view.findViewById( R.id.txt_email_id );
		SetFonts.setFonts( getActivity(), txtClientName, 2 );
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
		txtClientName.setText( strClientName );
		txtEmailId.setText( strEmail );
		txtRCNO.setText( rcNo );
//	txtStatement_of_account.setText( currentDate );

		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");

		Date date = null;
		try {
			date = inputFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String outputDateStr = outputFormat.format(date);


		lbl_statement_of_accountLabel=(TextView)view.findViewById(R.id.lbl_statement_of_accountLabel);
		lbl_statement_of_accountLabel.setText(" ");
		lbl_statement_of_accountLabel.setText("Statement of Account as on- "+outputDateStr);

	}

}

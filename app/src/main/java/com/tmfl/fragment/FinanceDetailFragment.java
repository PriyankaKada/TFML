package com.tmfl.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.model.soapModel.response.ResponseEnvelope;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FinanceDetailFragment extends Fragment {
	String strFromDate, strToDate;
	private View     view;
	private TextView txtAmtFinanced, txtFinanceCharge, txtInsurancePrvision, txtOptionMoney, txtTotalReceivable, txtAmtCreditedLbl, txtAmtCredited, txtCreditedEconomicYearLbl, txtAmtCreditEconomicYear, txtyTotalCreditLbl, txtTotalCredit, txtBalPayable, txtPrincipalPeriodLbl, txtPrincipalPeriod, txtfinanceChargesLbl, txtFinanceChargeEconomicYear;
	private String strFinAmt, strFinCharge, strInsPro, strOptionMoney, strDueTillDate, strSoaPrvAmt, strSoaPrdAmt, strSoaDateTo, strSoaDateFrom, strCred, strOverdue, strSoaPrincipal, strSoaFinCharge;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_finance_detail, container, false );
		Bundle bundle = getArguments();
		if ( bundle != null ) {
			ResponseEnvelope.Body body = ( ResponseEnvelope.Body ) bundle.getSerializable( "ResponseModel" );
			strFinAmt = body.getZCISResponse().getIT_CARDEX1().getItem().getFIN_AMT() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getFIN_AMT();
			strFinCharge = body.getZCISResponse().getIT_CARDEX1().getItem().getFIN_CHRG() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getFIN_CHRG();
			strInsPro = body.getZCISResponse().getIT_CARDEX1().getItem().getINS_PROV() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getINS_PROV();
			strOptionMoney = body.getZCISResponse().getIT_CARDEX1().getItem().getOPTION_MONEY() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getOPTION_MONEY();
			strDueTillDate = body.getZCISResponse().getIT_CARDEX1().getItem().getDUE_TILL_DATE() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getDUE_TILL_DATE();
			strSoaPrvAmt = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_PRV_AMT() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_PRV_AMT();
			strSoaPrdAmt = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_PRD_AMT() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_PRD_AMT();
			strSoaDateTo = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_TO() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_TO();
			strSoaDateFrom = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_FROM() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_DATE_FROM();
			strCred = body.getZCISResponse().getIT_ODC().get( ( body.getZCISResponse().getIT_ODC().size() - 1 ) ).getC_REC() == null ? "" : body.getZCISResponse().getIT_ODC().get( ( body.getZCISResponse().getIT_ODC().size() - 1 ) ).getC_REC();
			strOverdue = body.getZCISResponse().getIT_CARDEX1().getItem().getOVERDUE() == null ? "" : body.getZCISResponse().getIT_CARDEX1().getItem().getOVERDUE();
			strSoaPrincipal = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_PRINCIPAL() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_PRINCIPAL();
			strSoaFinCharge = body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_FIN_CHRG() == null ? "" : body.getZCISResponse().getIT_CARDEX2().getItem().getSOA_FIN_CHRG();

			DateFormat inputFormat  = new SimpleDateFormat( "yyyy-MM-dd" );
			DateFormat outputFormat = new SimpleDateFormat( "dd-MMM-yyyy" );

			try {
				Date date  = inputFormat.parse( strSoaDateTo );
				Date date1 = inputFormat.parse( strSoaDateFrom );
				strToDate = outputFormat.format( date );
				strFromDate = outputFormat.format( date1 );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}

			init();
		}

		return view;
	}

	public void init() {
		txtAmtFinanced = ( TextView ) view.findViewById( R.id.txt_amount_financed );
		txtFinanceCharge = ( TextView ) view.findViewById( R.id.txt_finance_charges );
		txtInsurancePrvision = ( TextView ) view.findViewById( R.id.txt_insurance_provision );
		txtOptionMoney = ( TextView ) view.findViewById( R.id.txt_option_money );
		txtTotalReceivable = ( TextView ) view.findViewById( R.id.txt_total_receivable );
		txtAmtCreditedLbl = ( TextView ) view.findViewById( R.id.lbl_amt_credited );
		txtAmtCredited = ( TextView ) view.findViewById( R.id.txt_amt_credited );
		txtCreditedEconomicYearLbl = ( TextView ) view.findViewById( R.id.lbl_amt_credited_economic_year );
		txtAmtCreditEconomicYear = ( TextView ) view.findViewById( R.id.txt_amt_credited_economic_year );
		txtyTotalCreditLbl = ( TextView ) view.findViewById( R.id.lbl_total_credit_uptop_31mar );
		txtTotalCredit = ( TextView ) view.findViewById( R.id.txt_total_credit_uptop_31mar );
		txtBalPayable = ( TextView ) view.findViewById( R.id.txt_balance_payable_on_contract );
		txtPrincipalPeriodLbl = ( TextView ) view.findViewById( R.id.lbl_principal_period );
		txtPrincipalPeriod = ( TextView ) view.findViewById( R.id.txt_principal_period );
		txtfinanceChargesLbl = ( TextView ) view.findViewById( R.id.lbl_finance_charges_economy_year );
		txtFinanceChargeEconomicYear = ( TextView ) view.findViewById( R.id.txt_finance_charges_economy_year );
		txtAmtFinanced.setText( strFinAmt );
		txtFinanceCharge.setText( strFinCharge );
		txtInsurancePrvision.setText( strInsPro );
		txtOptionMoney.setText( strOptionMoney );


		String totalReceivable = new DecimalFormat( "00.00" ).format( Double.parseDouble( strFinAmt ) + Double.parseDouble( strFinCharge )
				                                                              + Double.parseDouble( strInsPro ) + Double.parseDouble( strOptionMoney ) );
		Log.d( "amounts", strFinAmt + " " + strFinCharge + " " + strInsPro + " " + strOptionMoney + totalReceivable );
		txtTotalReceivable.setText( totalReceivable );
		txtAmtCreditedLbl.setText( "Amount Credited Upto " + strToDate );
		txtAmtCredited.setText( strSoaPrvAmt );
		txtCreditedEconomicYearLbl.setText( "Amount Credited from " + strFromDate + " To " + strToDate );
		txtAmtCreditEconomicYear.setText( strSoaPrdAmt );
		txtyTotalCreditLbl.setText( "Total Credit Upto " + strToDate );
		String txtTotalCredit_str=new DecimalFormat( "00.00" ).format( Double.parseDouble( strSoaPrvAmt ) + Double.parseDouble( strSoaPrdAmt ));

		txtTotalCredit.setText( txtTotalCredit_str);

		String balPayable = new DecimalFormat( "00.00" ).format( Double.parseDouble( totalReceivable ) - Double.parseDouble( txtTotalCredit_str ) );
		txtBalPayable.setText( balPayable );
		txtPrincipalPeriodLbl.setText( "Principal for the Period " + strFromDate + " To " + strToDate );
		txtPrincipalPeriod.setText( strSoaPrincipal );
		txtfinanceChargesLbl.setText( "Finance Charges For the Period " + strFromDate + " To " + strToDate );
		txtFinanceChargeEconomicYear.setText( strSoaFinCharge );
	}

}

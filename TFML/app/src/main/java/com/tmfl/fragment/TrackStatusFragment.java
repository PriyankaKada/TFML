package com.tmfl.fragment;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tmfl.R;
import com.tmfl.adapter.ComplaintsToTrackListAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ComplaintSoapApiService;
import com.tmfl.complaintnetwork.findcase.request.FindCaseBody;
import com.tmfl.complaintnetwork.findcase.request.FindCaseData;
import com.tmfl.complaintnetwork.findcase.request.FindCaseRequestEnvelope;
import com.tmfl.complaintnetwork.findcase.response.FindCaseResponseEnvelope;
import com.tmfl.complaintnetwork.findcase.response.FindCaseResult;
import com.tmfl.model.ContractResponseModel.ActiveContractsModel;
import com.tmfl.util.DatePickerDialog;
import com.tmfl.util.DatePickerFragment;
import com.tmfl.util.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class TrackStatusFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateChangeListener {

	TextView txtComplainCaseId, txtFromDate, txtToDate;
	Spinner      spnContractNo;
	LinearLayout llComplaintListHeader;
	ListView     list;

	OnDateSetListener fromDate = new OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtFromDate.setText( year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;
		}
	};
	OnDateSetListener toDate   = new OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtToDate.setText( year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;
		}
	};
	private Button               btnGo;
	private TextView             txtUploadFile;
	private DatePickerFragment   date;
	private ActiveContractsModel activeContractsModel;
	private ArrayList< String >  contractsModelList;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		View rootView;
		rootView = inflater.inflate( R.layout.fragment_track_status, container, false );
		date = new DatePickerFragment();
		txtComplainCaseId = ( TextView ) rootView.findViewById( R.id.txtComplainCaseId );
		txtFromDate = ( TextView ) rootView.findViewById( R.id.txtFromDate );
		txtToDate = ( TextView ) rootView.findViewById( R.id.txtToDate );
		btnGo = ( Button ) rootView.findViewById( R.id.btnGo );
		llComplaintListHeader = ( LinearLayout ) rootView.findViewById( R.id.llComplaintListHeader );
		list = ( ListView ) rootView.findViewById( R.id.lstComplaints );
		spnContractNo = ( Spinner ) rootView.findViewById( R.id.spnContractNo );
		txtUploadFile = ( TextView ) rootView.findViewById( R.id.txtUploadFile );
		txtUploadFile.setOnClickListener( this );

		activeContractsModel = ( ActiveContractsModel ) PreferenceHelper.getObject( Constant.ONGOING_LOAN, ActiveContractsModel.class );
		contractsModelList = new ArrayList<>();

		for ( int i = 0; i < activeContractsModel.getContracts().size(); i++ ) {
			contractsModelList.add( activeContractsModel.getContracts().get( i ).getUsrConNo() );
			Log.d( "contract no", contractsModelList.get( i ) + " " + activeContractsModel.getContracts().size() );
		}
		spnContractNo.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.spinner_row, contractsModelList ) );

		txtFromDate.setOnClickListener( this );
		txtToDate.setOnClickListener( this );
		return rootView;
	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
		btnGo.setOnClickListener( this );
	}

	public void selectDate( String from ) {

		Calendar calender = Calendar.getInstance();
		Bundle   args     = new Bundle();
		args.putInt( "year", calender.get( Calendar.YEAR ) );
		args.putInt( "month", calender.get( Calendar.MONTH ) );
		args.putInt( "day", calender.get( Calendar.DAY_OF_MONTH ) );
		date.setArguments( args );

		if ( from.equalsIgnoreCase( "fromDate" ) ) {
			date.setCallBack( fromDate, from );
		}
		else {
			date.setCallBack( toDate, from );
		}
		date.show( getFragmentManager(), "Date Picker" );
	}

	@Override
	public void onClick( View view ) {
		switch ( view.getId() ) {
			case R.id.txtFromDate:
				selectDate( "fromDate" );
				break;

			case R.id.txtToDate:
				selectDate( "toDate" );
				break;

			case R.id.btnGo:
				findCase();
				break;

			case R.id.txtUploadFile:

				uploadDoc();

				break;
		}
	}

	private void uploadDoc() {


	}

	private void findCase() {

		FindCaseRequestEnvelope requestEnvelope = new FindCaseRequestEnvelope();
		FindCaseBody            findCaseReqBody = new FindCaseBody();
		FindCaseData            findCaseReqData = new FindCaseData();

		if ( txtComplainCaseId.getText().toString().length() != 0 ) {
			findCaseReqData.setCaseId( txtComplainCaseId.getText().toString().trim() );
			findCaseReqData.setContractNo( "" );
			findCaseReqData.setStartDate( "" );
			findCaseReqData.setEndDate( "" );
		}
		else {
			findCaseReqData.setCaseId( "" );
			findCaseReqData.setContractNo( spnContractNo.getSelectedItem().toString() );
			findCaseReqData.setStartDate( txtFromDate.getText().toString() );
			findCaseReqData.setEndDate( txtToDate.getText().toString() );
		}

		findCaseReqBody.setReqData( findCaseReqData );
		requestEnvelope.setCaseBody( findCaseReqBody );
		TmflApi apiService = ComplaintSoapApiService.getInstance().call();

		apiService.findCaseRequest( requestEnvelope ).enqueue( new Callback< FindCaseResponseEnvelope >() {
			@Override
			public void onResponse( Call< FindCaseResponseEnvelope > call, Response< FindCaseResponseEnvelope > response ) {
				Log.d( "success", response.body().getFindCaseBody().getFindCaseResponse().getFindCaseResult() );

				PreferenceHelper.insertObject( Constant.FIND_CASE_RESPONSE, response.body().getFindCaseBody() );

				setCaseDetails( response.body().getFindCaseBody().getFindCaseResponse().getFindCaseResult() );

			}

			@Override
			public void onFailure( Call< FindCaseResponseEnvelope > call, Throwable t ) {
				Log.d( "error", t.getMessage() );
			}
		} );

	}

	private void setCaseDetails( String findCaseResult ) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = XML.toJSONObject( "<case><CaseId>1804428</CaseId><Casestage>Assigned</Casestage><CreatedDate>2/6/2017 12:51:08 PM</CreatedDate><Description>Test Case Description</Description></case><case><CaseId>1804428</CaseId><Casestage>Assigned</Casestage><CreatedDate>2/6/2017 12:51:08 PM</CreatedDate><Description>Test Case Description</Description></case><Result>1</Result><Message>Cases successfully retrieved.</Message>" );
			Gson           gson       = new Gson();
			FindCaseResult caseResult = gson.fromJson( jsonObject.toString(), FindCaseResult.class );


			Log.d( "String", caseResult.getMessage() + " " + caseResult.toString() );
			llComplaintListHeader.setVisibility( View.VISIBLE );
			list.setAdapter( new ComplaintsToTrackListAdapter( getActivity(), 0, caseResult.getCase() ) );
		}
		catch ( JSONException e ) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDateChange( String date, String picker ) {
		if ( picker.equalsIgnoreCase( " " ) ) {
			txtFromDate.setText( txtFromDate.getText().toString() + " " + date );
			txtToDate.setText( txtToDate.getText().toString() + " " + date );
		}
	}
}

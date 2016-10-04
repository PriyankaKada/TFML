package com.tfml.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.adapter.PreClosureAdapter;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SoapApiService;
import com.tfml.model.preClosurePdfResponseModel.PreClosureInputModel;
import com.tfml.model.preClosurePdfResponseModel.PreClosureStmtPdfResponse;
import com.tfml.model.soapModel.preClosureRequest.RequestEnvelope;
import com.tfml.util.DatePickerDialog;
import com.tfml.util.DatePickerFragment;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PreClosureFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateChangeListener {

	PreClosureAdapter preClosureAdapter;
	TextView          txtGenDate, txtBal;
	String strContractNo, strAccdate, strDate;
	LinearLayout linTable, llHeader;
	TmflApi tmflSoapApi, tmflApi;
	PreClosureInputModel                                              preClosureInputModel;
	PreClosureStmtPdfResponse                                         preClosureStmtPdfResponse;
	com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope.Body responseEnvelope;
	String servicestring = Context.DOWNLOAD_SERVICE;
	DownloadManager downloadmanager;
	String          strPathUrl;
	private TextView txtAccDate;
	/**
	 * The Ondate.
	 */
	android.app.DatePickerDialog.OnDateSetListener ondate = new android.app.DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth ) {
			txtAccDate.setText( year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;

		}
	};
	private TextView           btnSubmit;
	private ImageView          btnDownload;
	private View               view;
	private DatePickerFragment date;
	private ListView           lstPreClosure;
	private List< String >     contractLst;
	private Spinner            spnContractNo;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_pre_closure, container, false );
		tmflApi = ApiService.getInstance().call();
		init();
		return view;

	}

	public void init() {
		spnContractNo = ( Spinner ) view.findViewById( R.id.spnContractNo );
		txtAccDate = ( TextView ) view.findViewById( R.id.txt_acc_select_date );
		btnSubmit = ( TextView ) view.findViewById( R.id.btn_submit );
		btnDownload = ( ImageView ) view.findViewById( R.id.img_download );
		lstPreClosure = ( ListView ) view.findViewById( R.id.lst_pre_closure );
		llHeader = ( LinearLayout ) view.findViewById( R.id.lstHeader );
		linTable = ( LinearLayout ) view.findViewById( R.id.linTabledesc );
		txtGenDate = ( TextView ) view.findViewById( R.id.txtGeneratedOn );
		txtBal = ( TextView ) view.findViewById( R.id.txtFooter );
		SetFonts.setFonts( getActivity(), btnSubmit, 2 );
		contractLst = new ArrayList< String >();
		contractLst.add( "Select Contract No" );
		contractLst.add( "0000005000197989" );
		spnContractNo.setSelection( 1 );
		spnContractNo.setAdapter( new ArrayAdapter< String >( getActivity(), android.R.layout.simple_spinner_dropdown_item, contractLst ) );
		spnContractNo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
				strContractNo = spnContractNo.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected( AdapterView< ? > parent ) {

			}
		} );

		date = new DatePickerFragment();
		btnSubmit.setOnClickListener( this );
		txtAccDate.setOnClickListener( this );
		btnDownload.setOnClickListener( this );
		preClosureInputModel = new PreClosureInputModel();
		preClosureStmtPdfResponse = new PreClosureStmtPdfResponse();

	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.txt_acc_select_date:
				selectDate();
				break;
			case R.id.btn_submit:
				CommonUtils.showProgressDialog( getActivity(), "Loading........" );
				String currentDateTimeString = DateFormat.getDateTimeInstance().format( new Date() );
				strAccdate = txtAccDate.getText().toString();
				DateFormat inputFormat = new SimpleDateFormat( "yyyy-MM-dd" );
				DateFormat outputFormat = new SimpleDateFormat( "dd MMM yyyy" );

				try {
					Date date = inputFormat.parse( strAccdate );
					strDate = outputFormat.format( date );
				}
				catch ( ParseException e ) {
					e.printStackTrace();
				}
				txtGenDate.setText( "Generated On" + txtAccDate.getText().toString() + "|" + currentDateTimeString );
				txtBal.setText( "Total Balance" + "+amount show above is on" + "subject providing clearance proof of all prior receipts." + "* The Total balance" + "will change if not paid on " + strDate );
				callSoapDataRequest();
				if ( !TextUtils.isEmpty( txtAccDate.toString() ) ) {

				}
				else {
					Toast.makeText( getActivity(), "Please Select Date", Toast.LENGTH_SHORT ).show();
				}
				break;
			case R.id.img_download:
				if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
					callDownloadService();
					getDownloadData( preClosureInputModel );
				}
				else {
					Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}
				break;

		}
	}

	@Override
	public void onDateChange( String date, String picker ) {
		if ( picker.equalsIgnoreCase( " " ) ) {
			txtAccDate.setText( txtAccDate.getText().toString() + " " + date );
		}
	}

	public void selectDate() {
		Calendar calender = Calendar.getInstance();
		Bundle   args     = new Bundle();
		args.putInt( "year", calender.get( Calendar.YEAR ) );
		args.putInt( "month", calender.get( Calendar.MONTH ) );
		args.putInt( "day", calender.get( Calendar.DAY_OF_MONTH ) );
		date.setArguments( args );
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack( ondate );
		date.show( getFragmentManager(), "Date Picker" );
	}

	public void callSoapDataRequest() {
		RequestEnvelope                                    requestEnvelope = new RequestEnvelope();
		com.tfml.model.soapModel.preClosureRequest.ReqBody reqBody         = new com.tfml.model.soapModel.preClosureRequest.ReqBody();
		com.tfml.model.soapModel.preClosureRequest.ReqData reqData         = new com.tfml.model.soapModel.preClosureRequest.ReqData();
		reqData.setContactId( strContractNo );
		reqData.setAdustSd( "R" );
		reqData.setReqDate( txtAccDate.getText().toString() );
		reqBody.setReqData( reqData );
		requestEnvelope.setReqBody( reqBody );
		tmflSoapApi = SoapApiService.getInstance().call();
		tmflSoapApi.callClosureTableRequest( requestEnvelope ).enqueue( new Callback< com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope >() {
			@Override
			public void onResponse( Call< com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope > call, Response< com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope > response ) {
				// Log.e("ResponseModel",response.body().getBody().getZ_TERMINALDUESResponse().getI_DTL().get(0).getCONTRACTNO());

				if ( response.body() != null ) {

					responseEnvelope = response.body().getBody();
					if ( responseEnvelope != null ) {
						for ( int i = 0; i < responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().size(); i++ ) {
							lstPreClosure.setAdapter( new PreClosureAdapter( getActivity(), responseEnvelope ) );
						}

					}
					linTable.setVisibility( View.VISIBLE );
					llHeader.setVisibility( View.VISIBLE );
				}
				CommonUtils.closeProgressDialog();
			}

			@Override
			public void onFailure( Call< com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope > call, Throwable t ) {
				Log.e( "ERROR", t.getMessage() );
				CommonUtils.closeProgressDialog();
			}
		} );
	}

	public void callDownloadService() {

		if ( PreferenceHelper.API_TOKEN != null ) {
			preClosureInputModel.setApiToken( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
		}
		if ( strContractNo != null ) {
			preClosureInputModel.setContractNo( strContractNo );
		}
		if ( txtAccDate.getText().toString() != null ) {
			preClosureInputModel.setRequestDate( txtAccDate.getText().toString() );
		}


	}


	public void getDownloadData( PreClosureInputModel preClosureInputModel ) {
		tmflApi.getPreClosureDownload( preClosureInputModel ).enqueue( new Callback< PreClosureStmtPdfResponse >() {
			@Override
			public void onResponse( Call< PreClosureStmtPdfResponse > call, Response< PreClosureStmtPdfResponse > response ) {
				Log.e( "File Path", response.body().getFilepath() );

				strPathUrl = response.body().getFilepath().toString();


//				downloadmanager = ( DownloadManager ) getActivity().getSystemService( servicestring );
				//startDownload(strPathUrl);
				Uri                     uri     = Uri.parse( strPathUrl );
				DownloadManager.Request request = new DownloadManager.Request( uri );
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
				request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "Pre-closure" + SystemClock.currentThreadTimeMillis() );
				DownloadManager manager = ( DownloadManager ) getActivity().getSystemService( Context.DOWNLOAD_SERVICE );
				manager.enqueue( request );
			}

			@Override
			public void onFailure( Call< PreClosureStmtPdfResponse > call, Throwable t ) {

			}
		} );
	}


}

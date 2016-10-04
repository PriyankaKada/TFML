package com.tfml.fragment;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SoapApiService;
import com.tfml.model.accountStmtPdfResponseModel.AccountStatementInputModel;
import com.tfml.model.accountStmtPdfResponseModel.AccountStmtResponse;
import com.tfml.model.soapModel.request.ReqBody;
import com.tfml.model.soapModel.request.ReqData;
import com.tfml.model.soapModel.request.RequestEnvelpe;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.DatePickerDialog;
import com.tfml.util.DatePickerFragment;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatementOfAccountFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateChangeListener {
	public static final int Progress_Dialog_Progress = 0;
	BasicDetailFragment   frmBasicAccDetail;
	FinanceDetailFragment frmFinanceDetail;
	FragmentManager       fragmentManager;
	FragmentTransaction   fragmentTransaction;
	LinearLayout          linAccDetail;
	String                contractNo;
	ResponseEnvelope.Body responseEnvelope;
	String                strPathUrl;
	TmflApi               tmflSoapApi, tmfl;
	AccountStatementInputModel accountStatementInputModel;
	AccountStmtResponse        accountStmtResponse;
	ProgressDialog             progressdialog;
	String servicestring = Context.DOWNLOAD_SERVICE;
	DownloadManager downloadmanager;
	private TextView txtAccDate, btnSubmit;
	/**
	 * The Ondate.
	 */
	android.app.DatePickerDialog.OnDateSetListener ondate = new android.app.DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear,
		                       int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtAccDate.setText( year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;

		}
	};
	private Button btnBasicDetail, btnFinanceDetail;
	private ImageView          btnDownload;
	private View               view;
	private Spinner            spnContractNo;
	private DatePickerFragment date;
	private FrameLayout        frmAccDetail;
	private List< String >     contractLst;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_statment_of_account, container, false );
		tmfl = ApiService.getInstance().call();
		init();
		return view;
	}

	public void init() {
		spnContractNo = ( Spinner ) view.findViewById( R.id.spnContractNo );
		txtAccDate = ( TextView ) view.findViewById( R.id.txt_acc_select_date );
		btnSubmit = ( TextView ) view.findViewById( R.id.btn_submit );
		btnDownload = ( ImageView ) view.findViewById( R.id.img_download );
		btnBasicDetail = ( Button ) view.findViewById( R.id.btn_basic_detail );
		btnFinanceDetail = ( Button ) view.findViewById( R.id.btn_finance_detail );
		frmAccDetail = ( FrameLayout ) view.findViewById( R.id.frm_acc_detail );
		linAccDetail = ( LinearLayout ) view.findViewById( R.id.lin_acc_detail );
		SetFonts.setFonts( getActivity(), btnSubmit, 2 );
		date = new DatePickerFragment();
		contractLst = new ArrayList< String >();
		contractLst.add( "Select Contract No" );
		contractLst.add( "0000005000197989" );
		spnContractNo.setSelection( 1 );
		spnContractNo.setAdapter( new ArrayAdapter< String >( getActivity(), android.R.layout.simple_spinner_dropdown_item, contractLst ) );
		spnContractNo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
				contractNo = spnContractNo.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected( AdapterView< ? > parent ) {

			}
		} );

		btnSubmit.setOnClickListener( this );
		txtAccDate.setOnClickListener( this );
		btnDownload.setOnClickListener( this );
		btnBasicDetail.setOnClickListener( this );
		btnFinanceDetail.setOnClickListener( this );
		accountStatementInputModel = new AccountStatementInputModel();
		accountStmtResponse = new AccountStmtResponse();


	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.txt_acc_select_date:
				selectDate();
				break;
			case R.id.btn_submit:
				if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
					CommonUtils.showProgressDialog( getActivity(), "Please Wait Data Loaded...." );
					callSoapRequest();
				}
				else {
					Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}

				if ( !TextUtils.isEmpty( txtAccDate.toString() ) ) {
					linAccDetail.setVisibility( View.VISIBLE );
				}
				else {
					Toast.makeText( getActivity(), "Please Select Date", Toast.LENGTH_SHORT ).show();
				}

				break;
			case R.id.img_download:
				if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
					callDownloadService();
					getDownloadData( accountStatementInputModel );
				}
				else {
					Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}
				break;
			case R.id.btn_basic_detail:
				callBasicDetail();
				break;
			case R.id.btn_finance_detail:
				setColorButtonFinance();
				fragmentManager = getActivity().getSupportFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();
				frmFinanceDetail = new FinanceDetailFragment();
				Bundle bundle1 = new Bundle();
				bundle1.putSerializable( "ResponseModel", responseEnvelope );
				frmFinanceDetail.setArguments( bundle1 );
				fragmentTransaction.add( R.id.frm_acc_detail, frmFinanceDetail );
				fragmentTransaction.commit();
				break;

		}
	}

	public void callSoapRequest() {
		RequestEnvelpe requestEnvelpe = new RequestEnvelpe();
		final ReqBody  reqBody        = new ReqBody();
		ReqData        reqData        = new ReqData();
		reqData.setContactId( contractNo );
		reqData.setREQDATE( txtAccDate.getText().toString() );
		reqBody.setReqData( reqData );
		requestEnvelpe.setReqBody( reqBody );
		tmflSoapApi = SoapApiService.getInstance().call();
		tmflSoapApi.callStmtAcRequest( requestEnvelpe ).enqueue( new Callback< ResponseEnvelope >() {
			@Override
			public void onResponse( Call< ResponseEnvelope > call, Response< ResponseEnvelope > response ) {
				CommonUtils.closeProgressDialog();
				if ( response.body() != null ) {
					responseEnvelope = response.body().getBody();
					PreferenceHelper.insertObject( PreferenceHelper.SOAPSTATMENTOFACCOUNTRESPONSE, responseEnvelope );
					Log.e( "Response ", responseEnvelope.getZCISResponse().getIT_CARDEX1().getItem().getREQDATE() );
					Log.e( "Response ", response.body().getBody().getZCISResponse().getIT_CARDEX2().getItem().getZZDATE() );
					Log.e( "Response ", response.body().getBody().getZCISResponse().getIT_DUES1().getItem().getCONTRACTNO() );
					Log.e( "Response ", response.body().getBody().getZCISResponse().getIT_DUES2().getItem().getODC_CHRG_DUE() );
					Log.e( "Response ", response.body().getBody().getZCISResponse().getIT_DUES3().getItem().getTOT_EXP_DUE() );
					Log.e( "Response ", response.body().getBody().getZCISResponse().getIT_ODC().size() + "" );
					Log.e( "Res", response.body().getBody().getZCISResponse().getI_REC().get( 0 ).getCONTRACTNO() + "---" + response.body().getBody().getZCISResponse().getI_REC().size() );
					Log.e( "REQDATE", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getREQDATE() );
					Log.e( "Aggrement No", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCONTRACTNO() );
					Log.e( "Aggrement Date", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCONTSTARTDT() );
					Log.e( "Tenure", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getTENURE() );
					Log.e( "TenureUNIT", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getTENURE_UNIT() );
					Log.e( "EngineNO", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getENGINE_NO() );
					Log.e( "Chasis No", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getCHASIS_NO() );
					Log.e( "RC NO", response.body().getBody().getZCISResponse().getIT_CARDEX1().getItem().getREG_NO() );
					callBasicDetail();
				}
			}

			@Override
			public void onFailure( Call< ResponseEnvelope > call, Throwable t ) {
				Log.e( "Response ", "" + t.getLocalizedMessage() );
				CommonUtils.closeProgressDialog();
			}
		} );

	}

	public void callDownloadService() {

		if ( PreferenceHelper.API_TOKEN != null ) {
			accountStatementInputModel.setApiToken( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
		}
		if ( contractNo != null ) {
			accountStatementInputModel.setContractNo( contractNo );
		}
		if ( txtAccDate.getText().toString() != null ) {
			accountStatementInputModel.setRequestDate( txtAccDate.getText().toString() );
		}
	}

	public void getDownloadData( AccountStatementInputModel accountStatementInputModel ) {

		tmfl.getAccStmtDownload( accountStatementInputModel ).enqueue( new Callback< AccountStmtResponse >() {
			@Override
			public void onResponse( Call< AccountStmtResponse > call, Response< AccountStmtResponse > response ) {

				Log.e( "File Path", response.body().getFilepath() );

				strPathUrl = response.body().getFilepath().toString();

//              downloadmanager = (DownloadManager)getActivity(). getSystemService(servicestring);
//            //  startDownload(strPathUrl);
//              Uri uri = Uri.parse(strPathUrl);
//              DownloadManager.Request request = new DownloadManager.Request(uri);
//              Long reference = downloadmanager.enqueue(request);

				Uri                     uri     = Uri.parse( strPathUrl );
				DownloadManager.Request request = new DownloadManager.Request( uri );
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
				request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "StatementOFAccount" + SystemClock.currentThreadTimeMillis() );
				DownloadManager manager = ( DownloadManager ) getActivity().getSystemService( Context.DOWNLOAD_SERVICE );
				manager.enqueue( request );
			}

			@Override
			public void onFailure( Call< AccountStmtResponse > call, Throwable t ) {

			}
		} );
	}

	public void startDownload( String strPathUrl ) {
		new DownloadFileAsync().execute( strPathUrl );
	}

	public void callBasicDetail() {
		setColorButtonBasic();
		fragmentManager = getActivity().getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		frmBasicAccDetail = new BasicDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable( "ResponseModel", responseEnvelope );
		frmBasicAccDetail.setArguments( bundle );
		fragmentTransaction.add( R.id.frm_acc_detail, frmBasicAccDetail );
		fragmentTransaction.commit();

	}

	public void setColorButtonBasic() {
		btnBasicDetail.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.white ) );
		btnBasicDetail.setTextColor( ContextCompat.getColor( getActivity(), R.color.tab_bg ) );
		btnFinanceDetail.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.tab_bg ) );
		btnFinanceDetail.setTextColor( ContextCompat.getColor( getActivity(), R.color.white ) );
	}

	public void setColorButtonFinance() {
		btnFinanceDetail.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.white ) );
		btnFinanceDetail.setTextColor( ContextCompat.getColor( getActivity(), R.color.tab_bg ) );
		btnBasicDetail.setBackgroundColor( ContextCompat.getColor( getActivity(), R.color.tab_bg ) );
		btnBasicDetail.setTextColor( ContextCompat.getColor( getActivity(), R.color.white ) );
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

	class DownloadFileAsync extends AsyncTask< String, String, String > {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			CommonUtils.showProgressDialog( getActivity(), "Download PDF Please Wait......." );
		}

		@Override
		protected String doInBackground( String... aurl ) {
			int count;

			try {
				URL           url      = new URL( aurl[0] );
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d( "ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile );

				InputStream  input  = new BufferedInputStream( url.openStream() );
				OutputStream output = new FileOutputStream( "/sdcard/tmfl.pdf" );

				byte data[] = new byte[1024];

				long total = 0;

				while ( ( count = input.read( data ) ) != -1 ) {
					total += count;
					publishProgress( "" + ( int ) ( ( total * 100 ) / lenghtOfFile ) );
					output.write( data, 0, count );
				}

				output.flush();
				output.close();
				input.close();
			}
			catch ( Exception e ) {
			}
			return null;

		}

		protected void onProgressUpdate( String... progress ) {
			Log.d( "ANDRO_ASYNC", progress[0] );
		}

		@Override
		protected void onPostExecute( String unused ) {
			CommonUtils.closeProgressDialog();
		}
	}

}

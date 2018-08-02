package com.tmfl.fragment;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.google.gson.Gson;
import com.tmfl.R;
import com.tmfl.activity.LoginActivity;
import com.tmfl.adapter.PreClosureAdapter;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SoapApiService;
import com.tmfl.model.ContractResponseModel.ContractModel;
import com.tmfl.model.logResponseModel.LogInputModel;
import com.tmfl.model.logResponseModel.LogResponseModel;
import com.tmfl.model.preClosurePdfResponseModel.PreClosureInputModel;
import com.tmfl.model.preClosurePdfResponseModel.PreClosureStmtPdfResponse;
import com.tmfl.model.soapModel.preClosureRequest.ReqBody;
import com.tmfl.model.soapModel.preClosureRequest.ReqData;
import com.tmfl.model.soapModel.preClosureRequest.RequestEnvelope;
import com.tmfl.model.soapModel.preClousreResponse.ResponseEnvelope;
import com.tmfl.model.soapModel.preClousreResponse.ResponseEnvelope.Body;
import com.tmfl.util.DatePickerDialog;
import com.tmfl.util.DatePickerFragment;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

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

	LinearLayout      ll_footerclouser;
	PreClosureAdapter preClosureAdapter;
	TextView          txtGenDate, txtBal;
	LinearLayout linTable, llHeader;
	ArrayList< ContractModel > modelArrayList;
	String                     strContractNo, strAccdate, strDate;
	TextView txt_repaymentmode, txt_emiamount, txt_dueamount, txt_duedate, txt_rc_no;
	TmflApi tmflSoapApi, tmflApi, tmflLogin;
	PreClosureInputModel      preClosureInputModel;
	PreClosureStmtPdfResponse preClosureStmtPdfResponse;
	Body                      responseEnvelope;
	String servicestring = Context.DOWNLOAD_SERVICE;
	DownloadManager  downloadmanager;
	String           strPathUrl;
	LogInputModel    logInputModel;
	LogResponseModel logResponseModel;
	private TextView           btnSubmit;
	private ImageView          btnDownload;
	private View               view;
	private DatePickerFragment date;
	private ListView           lstPreClosure;
	private List< String >     contractLst;
	private List< String >     nonzerocontractLst;
	private Spinner            spnContractNo;
	private int    itemindex     = 0;
	private String datavalue     = "";
	private String rcNo          = "";
	private String dueDate       = "";
	private String repaymentMode = "";
	private String currentEmi    = "";
	private String overdue       = "";
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
	private boolean submitClicked = false;


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
		view = inflater.inflate( R.layout.fragment_pre_closure, container, false );
		tmflApi = ApiService.getInstance().call();
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		modelArrayList =
				( ArrayList< ContractModel > ) bundle.getSerializable( "datamodel" );
		datavalue = ( String ) bundle.getString( "datamodelvalue" );
		rcNo = ( String ) bundle.getString( "RCNO" );
		dueDate = ( String ) bundle.getString( "DUEDATE" );
		repaymentMode = ( String ) bundle.getString( "REPAYMENT" );
		currentEmi = ( String ) bundle.getString( "CURRENTEMI" );
		overdue = ( String ) bundle.get( "OVERDUEAMT" );
		tmflLogin = ApiService.getInstance().call();
//		((AppCompatActivity )getActivity()).getSupportActionBar().setTitle( R.string.preclosure_statement);

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
		txt_rc_no = ( TextView ) view.findViewById( R.id.txt_rc_no );
		txt_repaymentmode = ( TextView ) view.findViewById( R.id.txt_repaymentmode );
		txt_emiamount = ( TextView ) view.findViewById( R.id.txt_emiamount );
		txt_dueamount = ( TextView ) view.findViewById( R.id.txt_dueamount );
		txt_duedate = ( TextView ) view.findViewById( R.id.txt_duedate );
		ll_footerclouser = ( LinearLayout ) view.findViewById( R.id.ll_footerclouser );
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
		SetFonts.setFonts( getActivity(), btnSubmit, 2 );
		spnContractNo = ( Spinner ) view.findViewById( R.id.spnContractNo );
		contractLst = new ArrayList< String >();
		nonzerocontractLst = new ArrayList< String >();
		if ( modelArrayList.size() > 0 ) {

			for ( int i = 0; i < modelArrayList.size(); i++ ) {
				nonzerocontractLst.add( trimLeadingZeros( modelArrayList.get( i ).getUsrConNo() ) );
				contractLst.add( modelArrayList.get( i ).getUsrConNo() );
				ContractModel model = modelArrayList.get( i );
			   /* if (model != null)
			        contractLst.add(model.getUsrConNo());*/
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

			for ( int i = 0; i < contractLst.size(); i++ ) {
				if ( contractLst.get( i ).equalsIgnoreCase( datavalue ) ) {
					spnContractNo.setSelection( i );
					Log.d( "contractList", contractLst.get( i ) );
				}
			}
		}

//		spnContractNo.setSelection( 1 );


		spnContractNo.post( new Runnable() {
			@Override
			public void run() {
				spnContractNo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
						strContractNo = modelArrayList.get( position ).getUsrConNo();
						itemindex = position;
						if ( itemindex >= 0 ) {
							ContractModel model = modelArrayList.get( itemindex );
							setData( model );
							callCheckLogin();
						}
					}

					@Override
					public void onNothingSelected( AdapterView< ? > parent ) {

					}
				} );
			}
		} );


		date = new DatePickerFragment();
		btnSubmit.setOnClickListener( this );
		txtAccDate.setOnClickListener( this );
		btnDownload.setOnClickListener( this );
		preClosureInputModel = new PreClosureInputModel();
		preClosureStmtPdfResponse = new PreClosureStmtPdfResponse();
	}

	private void setData( ContractModel model ) {
		txt_rc_no.setText( model.getRcNumber() == null ? "" : model.getRcNumber().toString() );
		txt_duedate.setText( model.getDueDate() == null ? "" : model.getDueDate().toString() );
		// txt_dueamount.setText(model.getDueAmount()==null?"":"Rs. "+model.getDueAmount().toString());
		txt_emiamount.setText( model.getDueAmount() == null ? "Rs. 0" : "Rs. " + model.getDueAmount().toString() );
		txt_repaymentmode.setText( model.getPdcFlag() == null ? "" : model.getPdcFlag().toString() );
		txt_dueamount.setText( model.getTotalCurrentDue() == null ? "Rs. 0" : "Rs." + model.getTotalCurrentDue().toString() );

	}

	public void callCheckLogin() {
		logInputModel = new LogInputModel();
		logResponseModel = new LogResponseModel();
		if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {

			logInputModel.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
			logInputModel.setUser_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
			callLogService( logInputModel );
		}
		else {
			Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.txt_acc_select_date:
				selectDate();
				break;
			case R.id.btn_submit:
				submitClicked = true;
				callCheckLogin();
				break;
			case R.id.img_download:
				if ( submitClicked == true ) {
					try {
						if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
							callDownloadService();
							CommonUtils.showProgressDialog( getActivity(), "File downloading..." );
							getDownloadData( preClosureInputModel );
						}
						else {
							Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
						}
					}
					catch ( Exception e ) {
						e.printStackTrace();
					}
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

		date.setCallBack( ondate, "preclosure" );
		date.show( getFragmentManager(), "Date Picker" );
	}

	public void callSoapDataRequest() {
		final RequestEnvelope requestEnvelope = new RequestEnvelope();
		ReqBody               reqBody         = new ReqBody();
		ReqData               reqData         = new ReqData();
		strContractNo = spnContractNo.getSelectedItem().toString();

		int    zeroCount = 16 - strContractNo.length();
		String zero      = "";

		for ( int i = 0; i < zeroCount; i++ ) {
			zero = zero + "0";
		}

		Log.d( "zeros", zero );
		strContractNo = zero + strContractNo;
		reqData.setContactId( strContractNo );
		reqData.setAdustSd( "R" );
		reqData.setReqDate( txtAccDate.getText().toString() );
		reqBody.setReqData( reqData );
		requestEnvelope.setReqBody( reqBody );
		tmflSoapApi = SoapApiService.getInstance().call();
		try {
			tmflSoapApi.callClosureTableRequest( requestEnvelope ).enqueue( new Callback< ResponseEnvelope >() {
                @Override
                public void onResponse( Call< ResponseEnvelope > call, Response< ResponseEnvelope > response ) {
                    // Log.e("ResponseModel",response.body().getBody().getZ_TERMINALDUESResponse().getI_DTL().get(0).getCONTRACTNO());
                    CommonUtils.closeProgressDialog();
                    if ( response.body() != null ) {
                        ll_footerclouser.setVisibility( View.VISIBLE );
                        responseEnvelope = response.body().getBody();

                        Body                                    dummy                  = new Body();
                        ResponseEnvelope.Z_TERMINALDUESResponse z_terminalduesResponse = new ResponseEnvelope.Z_TERMINALDUESResponse();
                        List< ResponseEnvelope.Item >           items                  = new ArrayList< ResponseEnvelope.Item >();

                        List< String > stringList = new ArrayList< String >();
                        if ( responseEnvelope != null ) {
                            for ( int i = 0; i < responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().size(); i++ ) {

                                Log.e( "element  ", responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get( i ).getDESCP() );
                                items.add( responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get( i ) );
                                if ( responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get( i ).getDESCP().equals( "TOTAL" ) ) {
                                    Log.e( "inside elememt  ", responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get( i ).getDESCP() );
                                    break;
                                }
                                else {
                                    stringList.add( responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get( i ).getDESCP() );
                                }
                            }

                            z_terminalduesResponse.setI_DTL( items );
                            dummy.setZ_TERMINALDUESResponse( z_terminalduesResponse );
                            for ( String s : stringList ) {
                                Log.e( "String", "" + s );
                            }
                            lstPreClosure.setAdapter( new PreClosureAdapter( getActivity(), dummy ) );
                        }

                        linTable.setVisibility( View.VISIBLE );
                        llHeader.setVisibility( View.VISIBLE );
                    }
                }

                @Override
                public void onFailure( Call< ResponseEnvelope > call, Throwable t ) {
                    //  Log.e("ERROR", t.getMessage());
                    CommonUtils.closeProgressDialog();
                }
            } );
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtils.closeProgressDialog();
			Toast.makeText(getContext(), "Try After Some time", Toast.LENGTH_SHORT).show();
		}
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

	public void SoapServiceResult() {
		if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
			if ( !TextUtils.isEmpty( txtAccDate.getText().toString() ) ) {
				// CommonUtils.showProgressDialog(getActivity(), "Getting Your Information");
				String currentDateTimeString = DateFormat.getDateTimeInstance().format( new Date() );
				String newDate               = null;
				strAccdate = txtAccDate.getText().toString();
				DateFormat       inputFormat  = new SimpleDateFormat( "yyyy-MM-dd" );
				DateFormat       outputFormat = new SimpleDateFormat( "dd-MM-yyyy" );
				SimpleDateFormat sdf          = new SimpleDateFormat( "hh:mm a" );
				String           strTime      = sdf.format( new Date() );
				try {
					Date date = inputFormat.parse( strAccdate );
					strDate = outputFormat.format( date );
					newDate = outputFormat.format( System.currentTimeMillis() );
				}
				catch ( ParseException e ) {
					e.printStackTrace();
				}

				txtGenDate.setText( "Generated On " + newDate + "|" + strTime );
				txtBal.setText( getActivity().getResources().getString( R.string.txt_total_bal ) + " " + strDate );
				callSoapDataRequest();
			}
			else {
				Toast.makeText( getActivity(), "Please Select Date", Toast.LENGTH_SHORT ).show();
				CommonUtils.closeProgressDialog();
			}
		}
		else {
			Toast.makeText( getActivity(), "Please Check Network Connectionmatch_parent", Toast.LENGTH_LONG ).show();
		}
	}

	public void getDownloadData( PreClosureInputModel preClosureInputModel ) {
		tmflApi.getPreClosureDownload( preClosureInputModel ).enqueue( new Callback< PreClosureStmtPdfResponse >() {
			@Override
			public void onResponse( Call< PreClosureStmtPdfResponse > call, Response< PreClosureStmtPdfResponse > response ) {
				//Log.e( "File Path", response.body().getFilepath() );
				CommonUtils.closeProgressDialog();
				if ( response.body().getFilepath() != null && response.body().getStatus().contains( "Success" ) ) {
					strPathUrl = response.body().getFilepath().toString();
					Uri                     uri     = Uri.parse( strPathUrl );
					DownloadManager.Request request = new DownloadManager.Request( uri );
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
					request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "Pre-closure" + SystemClock.currentThreadTimeMillis() );
					DownloadManager manager = ( DownloadManager ) getActivity().getSystemService( Context.DOWNLOAD_SERVICE );
					manager.enqueue( request );
				}
				else {
					if ( response.body().getStatus().contains( "failed" ) ) {
						Intent loginIntent = new Intent( getActivity(), LoginActivity.class );
						startActivity( loginIntent );
						getActivity().finish();
					}
				}
			}

			@Override
			public void onFailure( Call< PreClosureStmtPdfResponse > call, Throwable t ) {
				CommonUtils.closeProgressDialog();
			}
		} );
	}

	public void callLogService( LogInputModel logInputModel ) {
		tmflLogin.getLogResponse( logInputModel ).enqueue( new Callback< LogResponseModel >() {
			@Override
			public void onResponse( Call< LogResponseModel > call, Response< LogResponseModel > response ) {
				Log.e( "isLogin", new Gson().toJson( response.body() ) );

				if ( response.body().getStatus().toString().contains( "Success" ) ) {
					if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {

						CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
						SoapServiceResult();

					}
					else {
						Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
					}

				}

				else {
					Toast.makeText( getActivity(), "User Logged in from another Device", Toast.LENGTH_LONG ).show();
					Intent loginIntent = new Intent( getActivity(), LoginActivity.class );
					getActivity().startActivity( loginIntent );
				}
			}

			@Override
			public void onFailure( Call< LogResponseModel > call, Throwable t ) {
				Log.e( "ERROR", t.getMessage() );
			}
		} );
	}
}

package com.tmfl.fragment;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.tmfl.R;
import com.tmfl.adapter.ComplaintsToTrackListAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ComplaintSoapApiService;
import com.tmfl.complaintnetwork.XMLPullParser;
import com.tmfl.complaintnetwork.createcase.response.ParsedResponse;
import com.tmfl.complaintnetwork.findcase.request.FindCaseBody;
import com.tmfl.complaintnetwork.findcase.request.FindCaseData;
import com.tmfl.complaintnetwork.findcase.request.FindCaseRequestEnvelope;
import com.tmfl.complaintnetwork.findcase.response.FindCaseResponseEnvelope;
import com.tmfl.complaintnetwork.findcase.response.FindCaseResult;
import com.tmfl.complaintnetwork.uploaddoc.UploadFileInterface;
import com.tmfl.complaintnetwork.uploaddoc.request.FileKeyValuePair;
import com.tmfl.complaintnetwork.uploaddoc.request.UploadDocReqBody;
import com.tmfl.complaintnetwork.uploaddoc.request.UploadDocReqData;
import com.tmfl.complaintnetwork.uploaddoc.request.UploadDocRequestEnvelope;
import com.tmfl.complaintnetwork.uploaddoc.response.UploadDocResponseEnvelope;
import com.tmfl.fragment.NewComplaintFragment.RealPathUtil;
import com.tmfl.model.ContractResponseModel.ActiveContractsModel;
import com.tmfl.util.DatePickerDialog;
import com.tmfl.util.DatePickerFragment;
import com.tmfl.util.PreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class TrackStatusFragment extends Fragment implements UploadFileInterface, View.OnClickListener, DatePickerDialog.OnDateChangeListener {

	TextView txtComplainCaseId, txtFromDate, txtToDate;
	Spinner      spnContractNo;
	LinearLayout llComplaintListHeader;
	ListView     list;
	Dialog       fileDialog;
	TextView     txtFileName1, txtFileName2, txtFileName3, txtCancel;
	ImageView imgFile1, imgFile2, imgFile3, imgUploadFile;
	FileKeyValuePair fileKeyValuePair1, fileKeyValuePair2, fileKeyValuePair3;
	File   file;
	String path, startDate, endDate;
	byte[] fileByte;
	String base64File;
	Uri    uri;

	OnDateSetListener fromDate = new OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtFromDate.setText( ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + year );
			startDate = year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;
		}
	};

	OnDateSetListener toDate = new OnDateSetListener() {
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
			// txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "-" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "-" + year);
			txtToDate.setText( ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth ) + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + year );
			endDate = year + "-" + ( ( monthOfYear + 1 ) > 9 ? ( monthOfYear + 1 ) : ( "0" + ( monthOfYear + 1 ) ) ) + "-" + ( dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth );
			//dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;
		}
	};

	private TextView txtCaseId, txtReqComplaintDate, txtDesc, txtCaseStage, txtUploadFile;
	private Button             btnGo;
	private DatePickerFragment date;
	private ProgressDialog     progressDialog;
	private String             caseId;
	private LinearLayout       linearLayout;

	public static byte[] convertFileToByteArray( File f ) {
		byte[] byteArray = null;
		try {
			@SuppressWarnings( "resource" )
			InputStream inputStream = new FileInputStream( f );
			ByteArrayOutputStream bos       = new ByteArrayOutputStream();
			byte[]                b         = new byte[1024 * 8];
			int                   bytesRead = 0;

			while ( ( bytesRead = inputStream.read( b ) ) != -1 ) {
				bos.write( b, 0, bytesRead );
			}

			byteArray = bos.toByteArray();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
		return byteArray;
	}

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

		ActiveContractsModel activeContractsModel = ( ActiveContractsModel ) PreferenceHelper.getObject( Constant.ONGOING_LOAN, ActiveContractsModel.class );
		ArrayList< String >  contractsModelList   = new ArrayList<>();

		linearLayout = ( LinearLayout ) rootView.findViewById( R.id.llRow );

		fileKeyValuePair1 = new FileKeyValuePair();
		fileKeyValuePair2 = new FileKeyValuePair();
		fileKeyValuePair3 = new FileKeyValuePair();

		txtCaseId = ( TextView ) rootView.findViewById( R.id.txtCaseId );
		txtReqComplaintDate = ( TextView ) rootView.findViewById( R.id.txtReqComplaintDate );
		txtDesc = ( TextView ) rootView.findViewById( R.id.txtDesc );
		txtCaseStage = ( TextView ) rootView.findViewById( R.id.txtCaseStage );
		imgUploadFile = ( ImageView ) rootView.findViewById( R.id.imgUploadFile );


		imgUploadFile.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				uploadFile( txtCaseId.getText().toString() );
			}
		} );

		progressDialog = new ProgressDialog( getActivity() );
		progressDialog.setMessage( "Please Wait..." );
		progressDialog.setCancelable( false );
		progressDialog.setCanceledOnTouchOutside( false );

		contractsModelList.add( "Select Contract" );
		for ( int i = 0; i < activeContractsModel.getContracts().size(); i++ ) {
			contractsModelList.add( activeContractsModel.getContracts().get( i ).getUsrConNo() );
			Log.d( "contract no", contractsModelList.get( i ) + " " + activeContractsModel.getContracts().size() );
		}
		spnContractNo.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.spinner_complaint_row, contractsModelList ) );

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

		Intent intent       = new Intent();
		String manufactures = android.os.Build.MANUFACTURER;

		switch ( view.getId() ) {
			case R.id.txtFromDate:
				selectDate( "fromDate" );
				break;

			case R.id.txtToDate:
				selectDate( "toDate" );
				break;

			case R.id.btnGo:
				progressDialog.show();
				if ( validate() ) {
					findCase();
				}
				break;

			case R.id.imgUploadFile1:
				/*if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					try {
						intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
						intent.putExtra( "CONTENT_TYPE", "text/plain|image*//*|application*//*.pdf" );
						intent.addCategory( Intent.CATEGORY_DEFAULT );
					}
					catch ( Exception e ) {

						e.printStackTrace();

						intent = new Intent( Intent.ACTION_GET_CONTENT );
						intent.setType( "text/plain|image*//*|application*//*.pdf" );
						intent.addCategory( Intent.CATEGORY_OPENABLE );
					}
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image*//*|application*//*.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}
				startActivityForResult( intent, 1 );
*/

				Intent newIntent = new Intent( Intent.ACTION_GET_CONTENT );
				newIntent.setType( "text/plain|image|application.pdf" );
				newIntent.addCategory( Intent.CATEGORY_OPENABLE );
				this.startActivityForResult(
						Intent.createChooser( getFileChooserIntent(), "Select File" ),
						1 );
				break;

			case R.id.imgUploadFile2:
				/*if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
					intent.putExtra( "CONTENT_TYPE", "text/plain|image*//*|application*//*.pdf" );
					intent.addCategory( Intent.CATEGORY_DEFAULT );
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image*//*|application*//*.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}
				intent.setType( "text/plain|image*//*|application*//*.pdf" );
				startActivityForResult( intent, 2 );*/

				newIntent = new Intent( Intent.ACTION_GET_CONTENT );
				newIntent.setType( "text/plain|image|application.pdf" );
				newIntent.addCategory( Intent.CATEGORY_OPENABLE );
				this.startActivityForResult(
						Intent.createChooser( getFileChooserIntent(), "Select File" ),
						2 );
				break;

			case R.id.imgUploadFile3:
				/*if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
					intent.putExtra( "CONTENT_TYPE", "text/plain|image*//*|application*//*.pdf" );
					intent.addCategory( Intent.CATEGORY_DEFAULT );
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image*//*|application*//*.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}
				intent.setType( "text/plain|image*//*|application*//*.pdf" );
				startActivityForResult( intent, 3 );*/

				newIntent = new Intent( Intent.ACTION_GET_CONTENT );
				newIntent.setType( "text/plain|image|application.pdf" );
				newIntent.addCategory( Intent.CATEGORY_OPENABLE );
				this.startActivityForResult(
						Intent.createChooser( getFileChooserIntent(), "Select File" ),
						3 );
				break;

			case R.id.txtUpload:

				uploadDocs();

				break;

			case R.id.txtCancel:

				fileDialog.dismiss();

				break;
		}

	}

	private boolean validate() {

		if ( TextUtils.isEmpty( txtComplainCaseId.getText().toString().trim() ) ) {
			if ( spnContractNo.getSelectedItemPosition() == 0 && TextUtils.isEmpty( txtFromDate.getText().toString().trim() ) && TextUtils.isEmpty( txtToDate.getText().toString().trim() ) ) {
				txtComplainCaseId.setError( "Please enter Case Id!" );
				return false;
			}
			else if ( spnContractNo.getSelectedItemPosition() == 0 ) {
				Toast.makeText( getActivity(), "Please select Contract No!", Toast.LENGTH_SHORT ).show();
//				txtComplainCaseId.setError( null );
				return false;
			}
			else if ( TextUtils.isEmpty( txtFromDate.getText().toString() ) ) {
				txtFromDate.setError( "Please select From Date!" );
//				txtComplainCaseId.setError( null );
				return false;
			}
			else if ( TextUtils.isEmpty( txtToDate.getText().toString() ) ) {
				txtToDate.setError( "Please select End Date!" );
//				txtComplainCaseId.setError( null );
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}

	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );
		if ( resultCode == Activity.RESULT_OK ) {
			Uri    selectedImageUri;
			String selectedImagePath;
			switch ( requestCode ) {
				case 1:
					/*uri = data.getData();
					file = new File( getRealPathFromURI( uri ) );*/

					selectedImageUri = data.getData();
					selectedImagePath = uriToFilename( selectedImageUri );

					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName1.setText( file.getName() );
					break;

				case 2:

					selectedImageUri = data.getData();
					selectedImagePath = uriToFilename( selectedImageUri );

					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair2.setKey( file.getName() );
					fileKeyValuePair2.setValue( base64File );

					txtFileName2.setText( file.getName() );

					break;

				case 3:

					selectedImageUri = data.getData();
					selectedImagePath = uriToFilename( selectedImageUri );

					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair3.setKey( file.getName() );
					fileKeyValuePair3.setValue( base64File );

					txtFileName3.setText( file.getName() );

					break;
			}
		}
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
			findCaseReqData.setStartDate( startDate );
			findCaseReqData.setEndDate( endDate );
		}

		findCaseReqBody.setReqData( findCaseReqData );
		requestEnvelope.setCaseBody( findCaseReqBody );
		TmflApi apiService = ComplaintSoapApiService.getInstance().call();

		apiService.findCaseRequest( requestEnvelope ).enqueue( new Callback< FindCaseResponseEnvelope >() {
			@Override
			public void onResponse( Call< FindCaseResponseEnvelope > call, Response< FindCaseResponseEnvelope > response ) {
				progressDialog.dismiss();
				Log.d( "success", response.body().getFindCaseBody().getFindCaseResponse().getFindCaseResult() );

				PreferenceHelper.insertObject( Constant.FIND_CASE_RESPONSE, response.body().getFindCaseBody() );

				setCaseDetails( response.body().getFindCaseBody().getFindCaseResponse().getFindCaseResult() );

				txtComplainCaseId.setText( "" );
				txtFromDate.setText( "" );
				spnContractNo.setSelection( 0 );
				txtToDate.setText( "" );

			}

			@Override
			public void onFailure( Call< FindCaseResponseEnvelope > call, Throwable t ) {
				progressDialog.dismiss();
				Log.d( "error", t.getMessage() );
			}
		} );
	}

	private void uploadDocs() {

		UploadDocRequestEnvelope requestEnvelope = new UploadDocRequestEnvelope();
		UploadDocReqBody         reqBody         = new UploadDocReqBody();
		UploadDocReqData         reqData         = new UploadDocReqData();

		reqData.setCaseId( caseId );
		if ( fileKeyValuePair1.getKey() != null ) {
			reqData.setAttachFiles1( fileKeyValuePair1 );
		}
		else {
			reqData.setAttachFiles1( new FileKeyValuePair( "", "" ) );
		}
		if ( fileKeyValuePair2.getKey() != null ) {
			reqData.setAttachFiles2( fileKeyValuePair2 );
		}
		else {
			reqData.setAttachFiles2( new FileKeyValuePair( "", "" ) );
		}
		if ( fileKeyValuePair3.getKey() != null ) {
			reqData.setAttachFiles3( fileKeyValuePair3 );
		}
		else {
			reqData.setAttachFiles3( new FileKeyValuePair( "", "" ) );
		}

		reqBody.setUploadDocData( reqData );
		requestEnvelope.setUploadDocReqBody( reqBody );
		fileDialog.dismiss();

		TmflApi apiService = ComplaintSoapApiService.getInstance().call();


		progressDialog.show();
		apiService.uploadDocRequest( requestEnvelope ).enqueue( new Callback< UploadDocResponseEnvelope >() {
			@Override
			public void onResponse( Call< UploadDocResponseEnvelope > call, Response< UploadDocResponseEnvelope > response ) {

				progressDialog.dismiss();
				Log.d( "success", response.body().getResponseBody().getResponse().getResult() );

				XMLPullParser  xmlPullParser = new XMLPullParser( response.body().getResponseBody().getResponse().getResult() );
				ParsedResponse caseFile      = xmlPullParser.parse();

				if ( caseFile.getCaseFile().getResult().equalsIgnoreCase( "1" ) ) {
					Toast.makeText( getActivity(), caseFile.getCaseFile().getMessage(), Toast.LENGTH_SHORT ).show();
				}
				else {
					Toast.makeText( getActivity(), caseFile.getCaseFile().getMessage(), Toast.LENGTH_SHORT ).show();
				}

				txtComplainCaseId.setText( "" );
				txtFromDate.setText( "" );
				spnContractNo.setSelection( 0 );
				txtToDate.setText( "" );
			}

			@Override
			public void onFailure( Call< UploadDocResponseEnvelope > call, Throwable t ) {

				progressDialog.dismiss();
				Log.d( "error", t.getMessage() );

				txtComplainCaseId.setText( "" );
				txtFromDate.setText( "" );
				spnContractNo.setSelection( 0 );
				txtToDate.setText( "" );
			}
		} );
	}

	private void setCaseDetails( String findCaseResult ) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = XML.toJSONObject( findCaseResult );
			Gson           gson       = new Gson();
			FindCaseResult caseResult = gson.fromJson( jsonObject.toString(), FindCaseResult.class );


			Log.d( "String", caseResult.getMessage() + " " + caseResult.toString() );
			if ( !caseResult.getCaseCount().equalsIgnoreCase( "0" ) ) {
				llComplaintListHeader.setVisibility( View.VISIBLE );
				if ( caseResult.getCase() instanceof List ) {
					linearLayout.setVisibility( View.GONE );
					list.setAdapter( new ComplaintsToTrackListAdapter( getActivity(), 0, ( List< LinkedTreeMap > ) caseResult.getCase(), this ) );
				}
				else {
					LinkedTreeMap map = ( LinkedTreeMap ) caseResult.getCase();
					linearLayout.setVisibility( View.VISIBLE );
					txtCaseId.setText( map.get( "CaseId" ).toString().substring( 0, map.get( "CaseId" ).toString().indexOf( "." ) ) );
					txtCaseStage.setText( map.get( "Casestage" ).toString() );
					txtDesc.setText( map.get( "Description" ).toString() );
					txtReqComplaintDate.setText( map.get( "CreatedDate" ).toString() );
				}
			}
			else {
				Toast.makeText( getActivity(), caseResult.getMessage(), Toast.LENGTH_SHORT ).show();
			}

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

	@Override
	public void uploadFile( String mCase ) {

		fileDialog = new Dialog( getActivity() );

		fileDialog.setContentView( R.layout.dialog_upload_file );
		fileDialog.getWindow().setLayout( MATCH_PARENT, WRAP_CONTENT );
		fileDialog.setCancelable( false );

		fileDialog.show();

		caseId = mCase;
		txtFileName1 = ( TextView ) fileDialog.findViewById( R.id.txtFileName1 );
		txtFileName2 = ( TextView ) fileDialog.findViewById( R.id.txtFileName2 );
		txtFileName3 = ( TextView ) fileDialog.findViewById( R.id.txtFileName3 );
		txtUploadFile = ( TextView ) fileDialog.findViewById( R.id.txtUpload );
		txtCancel = ( TextView ) fileDialog.findViewById( R.id.txtCancel );

		imgFile1 = ( ImageView ) fileDialog.findViewById( R.id.imgUploadFile1 );
		imgFile2 = ( ImageView ) fileDialog.findViewById( R.id.imgUploadFile2 );
		imgFile3 = ( ImageView ) fileDialog.findViewById( R.id.imgUploadFile3 );

		imgFile1.setOnClickListener( this );
		imgFile2.setOnClickListener( this );
		imgFile3.setOnClickListener( this );
		txtCancel.setOnClickListener( this );
		txtUploadFile.setOnClickListener( this );
	}

	public String getRealPathFromURI( Uri contentUri ) {

		String   path   = null;
		String[] proj   = { MediaStore.Files.FileColumns.DATA };
		Cursor   cursor = getActivity().getContentResolver().query( contentUri, proj, null, null, null );
		if ( cursor.moveToFirst() ) {
			int column_index = cursor.getColumnIndexOrThrow( MediaStore.MediaColumns.DATA );
			path = cursor.getString( column_index );
		}
		cursor.close();
		return path;
	}

	private Intent getFileChooserIntent() {
		String[] mimeTypes = { "image/*", "application/pdf" };

		Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
		intent.addCategory( Intent.CATEGORY_OPENABLE );

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ) {
			intent.setType( mimeTypes.length == 1 ? mimeTypes[0] : "*/*" );
			if ( mimeTypes.length > 0 ) {
				intent.putExtra( Intent.EXTRA_MIME_TYPES, mimeTypes );
			}
		}
		else {
			String mimeTypesStr = "";

			for ( String mimeType : mimeTypes ) {
				mimeTypesStr += mimeType + "|";
			}

			intent.setType( mimeTypesStr.substring( 0, mimeTypesStr.length() - 1 ) );
		}

		return intent;
	}

	private String uriToFilename( Uri uri ) {
		String path = null;

		if ( Build.VERSION.SDK_INT < 11 ) {
			path = RealPathUtil.getRealPathFromURI_BelowAPI11( getActivity(), uri );
		}
		else if ( Build.VERSION.SDK_INT < 19 ) {
			path = RealPathUtil.getRealPathFromURI_API11to18( getActivity(), uri );
		}
		else {
			path = RealPathUtil.getRealPathFromURI_API19( getActivity(), uri );
		}

		return path;
	}
}

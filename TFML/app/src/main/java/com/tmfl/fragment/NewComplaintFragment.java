package com.tmfl.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ComplaintSoapApiService;
import com.tmfl.complaintnetwork.createcase.request.AttachFiles;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseReqBody;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseReqData;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseRequestEnvelope;
import com.tmfl.complaintnetwork.createcase.request.FileKeyValuePair;
import com.tmfl.complaintnetwork.createcase.response.CreateCaseResponseEnvelope;
import com.tmfl.complaintnetwork.createcase.response.ParsedResponse;
import com.tmfl.complaintnetwork.createcase.response.XMLPullParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class NewComplaintFragment extends Fragment implements View.OnClickListener {

	FileKeyValuePair fileKeyValuePair1, fileKeyValuePair2, fileKeyValuePair3;
	File   file;
	String path;
	byte[] fileByte;
	String base64File;
	Uri    uri;
	private Button    btnSubmit;
	private ImageView imgUpload1, imgUpload2, imgUpload3;
	private CreateCaseReqData caseReqData;
	private MultipartBody.Part part = null;
	private TextView txtContractNo, txtDescription;
	private TmflApi        tmflApi;
	private AttachFiles    attachFiles;
	private ProgressDialog progressDialog;

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

	@Nullable
	@Override
	public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.fragment_new_complaint, container, false );

		initView( view );
		tmflApi = ComplaintSoapApiService.getInstance().call();
		fileKeyValuePair1 = new FileKeyValuePair();
		fileKeyValuePair2 = new FileKeyValuePair();
		fileKeyValuePair3 = new FileKeyValuePair();

		return view;
	}

	private void initView( View view ) {

		progressDialog = new ProgressDialog( getActivity() );
		progressDialog.setMessage( "Please Wait..." );
		progressDialog.setCancelable( false );
		progressDialog.setCanceledOnTouchOutside( false );

		caseReqData = new CreateCaseReqData();
		attachFiles = new AttachFiles();

		imgUpload1 = ( ImageView ) view.findViewById( R.id.imgUpload1 );
		imgUpload2 = ( ImageView ) view.findViewById( R.id.imgUpload2 );
		imgUpload3 = ( ImageView ) view.findViewById( R.id.imgUpload3 );

		txtDescription = ( TextView ) view.findViewById( R.id.txtDescription );
		txtContractNo = ( TextView ) view.findViewById( R.id.txtContractNo );
		btnSubmit = ( Button ) view.findViewById( R.id.btnSubmit );
		btnSubmit.setOnClickListener( this );

		imgUpload1.setOnClickListener( this );
		imgUpload2.setOnClickListener( this );
		imgUpload3.setOnClickListener( this );
	}

	@Override
	public void onClick( View view ) {
		Intent intent = new Intent();
		switch ( view.getId() ) {

			case R.id.btnSubmit:

				createCase();

				break;

			case R.id.imgUpload1:

				intent = new Intent( Intent.ACTION_GET_CONTENT );
				intent.setType( "text/plain|image/*|application/pdf" );
				startActivityForResult( intent, 1 );

				break;

			case R.id.imgUpload2:

				intent = new Intent( Intent.ACTION_GET_CONTENT );
				intent.setType( "text/plain|image/*|application/pdf" );
				startActivityForResult( intent, 2 );

				break;

			case R.id.imgUpload3:

				intent = new Intent( Intent.ACTION_GET_CONTENT );
				intent.setType( "text/plain|image/*|application/pdf" );
				startActivityForResult( intent, 3 );

				break;
		}
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );
		if ( resultCode == Activity.RESULT_OK ) {

			switch ( requestCode ) {
				case 1:

					uri = data.getData();
//					if ( path != null ) {
					file = new File( getRealPathFromURI( uri ) );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );
					//					}

					break;

				case 2:

					uri = data.getData();
//					if ( path != null ) {
					file = new File( getRealPathFromURI( uri ) );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair2.setKey( file.getName() );
					fileKeyValuePair2.setValue( base64File );

					break;

				case 3:

					uri = data.getData();
//					if ( path != null ) {
					file = new File( getRealPathFromURI( uri ) );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair3.setKey( file.getName() );
					fileKeyValuePair3.setValue( base64File );

					break;
			}
		}
	}

	private void createCase() {

		progressDialog.show();
		CreateCaseRequestEnvelope requestEnvelope = new CreateCaseRequestEnvelope();
		CreateCaseReqBody         reqBody         = new CreateCaseReqBody();
		CreateCaseReqData         reqData         = new CreateCaseReqData();

		reqData.setContractNo( txtContractNo.getText().toString().trim() );
		reqData.setCaseDescription( txtDescription.getText().toString().trim() );
		reqData.setAttachFiles1( fileKeyValuePair1 );
		reqData.setAttachFiles2( fileKeyValuePair2 );
		reqData.setAttachFiles3( fileKeyValuePair3 );

		reqBody.setCreateCaseReqData( reqData );
		requestEnvelope.setCreateCaseReqBody( reqBody );

		tmflApi.createCaseRequest( requestEnvelope ).enqueue( new Callback< CreateCaseResponseEnvelope >() {
			@Override
			public void onResponse( Call< CreateCaseResponseEnvelope > call, Response< CreateCaseResponseEnvelope > response ) {
				progressDialog.dismiss();
				Log.d( "success", response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );

				XMLPullParser  xmlPullParser = new XMLPullParser( response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );
				ParsedResponse caseFile      = xmlPullParser.parse();

				Log.d( "caseid", caseFile.getCaseFile().getCaseId() );
				ComplaintSubmitFeedbackFragment fragment = new ComplaintSubmitFeedbackFragment();
				if ( caseFile.getCaseFile().getResult().equalsIgnoreCase( "1" ) ) {

					Bundle bundle = new Bundle();
					bundle.putString( "caseId", caseFile.getCaseFile().getCaseId() );

					getFragmentManager()
							.beginTransaction()
							.replace( R.id.frame_complaint_container, fragment )
							.addToBackStack( this.getClass().getName() )
							.commit();
					fragment.setArguments( bundle );
				}
			}

			@Override
			public void onFailure( Call< CreateCaseResponseEnvelope > call, Throwable t ) {
				progressDialog.dismiss();
				Log.d( "error", t.getMessage() );
			}
		} );

	}

	private HashMap< String, RequestBody > callMapMethod() {
		HashMap< String, RequestBody > params = new HashMap<>();
		// add another part within the multipart request
		RequestBody contractno = RequestBody.create( MediaType.parse( "text/plain" ), txtContractNo.getText().toString().trim() );
		params.put( "contrNo", contractno );
		RequestBody rcno = RequestBody.create( MediaType.parse( "text/plain" ), txtDescription.getText().toString().trim() );
		params.put( "caseDesc", rcno );

		return params;
	}

	String toBinary( byte[] bytes ) {
		StringBuilder sb = new StringBuilder( bytes.length * Byte.SIZE );
		for ( int i = 0; i < Byte.SIZE * bytes.length; i++ ) {
			sb.append( ( bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80 ) == 0 ? '0' : '1' );
		}
		return sb.toString();
	}

	public String getRealPathFromURI( Uri contentUri ) {

		String   path   = null;
		String[] proj   = { MediaStore.MediaColumns.DATA };
		Cursor   cursor = getActivity().getContentResolver().query( contentUri, proj, null, null, null );
		if ( cursor.moveToFirst() ) {
			int column_index = cursor.getColumnIndexOrThrow( MediaStore.MediaColumns.DATA );
			path = cursor.getString( column_index );
		}
		cursor.close();
		return path;
	}
}

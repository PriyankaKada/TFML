package com.tmfl.fragment;

import android.app.Activity;
import android.content.CursorLoader;
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

	private Button    btnSubmit;
	private ImageView imgUpload1, imgUpload2, imgUpload3;
	private CreateCaseReqData caseReqData;
	private MultipartBody.Part part = null;
	private TextView txtContractNo, txtDescription;
	private TmflApi     tmflApi;
	private AttachFiles attachFiles;

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


		return view;
	}

	private void initView( View view ) {

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
		switch ( view.getId() ) {
			case R.id.btnSubmit:

				createCase();

				/*getFragmentManager()
						.beginTransaction()
						.replace( R.id.frame_complaint_container, new ComplaintSubmitFeedbackFragment() )
						.commit();*/

				break;

			case R.id.imgUpload1:

				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
				intent.setType( "image/*" );
				startActivityForResult( intent, 0 );

				break;

			case R.id.imgUpload2:

				break;

			case R.id.imgUpload3:

				break;
		}
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		if ( resultCode == Activity.RESULT_OK ) {
			Uri      selectedImageUri = data.getData();
			String[] projection1      = { MediaStore.MediaColumns.DATA };
			CursorLoader cursorLoader = new CursorLoader( getActivity(), selectedImageUri, projection1, null, null,
			                                              null );
			Cursor cursor       = cursorLoader.loadInBackground();
			int    column_index = cursor.getColumnIndexOrThrow( MediaStore.MediaColumns.DATA );
			cursor.moveToFirst();
			String filePath = cursor.getString( column_index );


			if ( filePath != null ) {
				File file = new File( filePath );

				byte[] fileByte       = convertFileToByteArray( file );
				byte[] file_in_base64 = Base64.encode( fileByte, Base64.DEFAULT );

				String binary = toBinary( file_in_base64 );

				FileKeyValuePair fileKeyValuePair = new FileKeyValuePair();
				fileKeyValuePair.setKey( file.getName() );
				fileKeyValuePair.setValue( fileByte );

				attachFiles.setFileKeyValuePair( fileKeyValuePair );

				caseReqData.setContractNo( txtContractNo.getText().toString().trim() );
				caseReqData.setCaseDescription( txtDescription.getText().toString().trim() );
				caseReqData.setAttachFiles( attachFiles );

				RequestBody fileToUpload = RequestBody.create( MediaType.parse( "image/*" ), file );
				part = MultipartBody.Part.createFormData( "image", file.getName(), fileToUpload );

			}
		}
	}

	private void createCase() {

		CreateCaseRequestEnvelope requestEnvelope = new CreateCaseRequestEnvelope();
		CreateCaseReqBody         reqBody         = new CreateCaseReqBody();
		CreateCaseReqData         reqData         = new CreateCaseReqData();

		reqData.setContractNo( txtContractNo.getText().toString().trim() );
		reqData.setCaseDescription( txtDescription.getText().toString().trim() );
		reqData.setAttachFiles( attachFiles );

		reqBody.setCreateCaseReqData( reqData );
		requestEnvelope.setCreateCaseReqBody( reqBody );

		tmflApi.createCaseRequest( requestEnvelope ).enqueue( new Callback< CreateCaseResponseEnvelope >() {
			@Override
			public void onResponse( Call< CreateCaseResponseEnvelope > call, Response< CreateCaseResponseEnvelope > response ) {
				Log.d( "success", response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );
			}

			@Override
			public void onFailure( Call< CreateCaseResponseEnvelope > call, Throwable t ) {
				Log.d( "error", t.getMessage() );
			}
		} );

//		HashMap< String, RequestBody > params = callMapMethod();

		/*tmflApi.createCaseRequest( params, part ).enqueue( new Callback< CreateCaseResponseEnvelope >() {
			@Override
			public void onResponse( Call< CreateCaseResponseEnvelope > call, Response< CreateCaseResponseEnvelope > response ) {
				Log.d( "success", response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );
			}

			@Override
			public void onFailure( Call< CreateCaseResponseEnvelope > call, Throwable t ) {

				Log.d( "error", t.getMessage() );

			}
		} );*/

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
}

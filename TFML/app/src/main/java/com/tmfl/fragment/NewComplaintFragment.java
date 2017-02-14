package com.tmfl.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.R;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ComplaintSoapApiService;
import com.tmfl.complaintnetwork.XMLPullParser;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseReqBody;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseReqData;
import com.tmfl.complaintnetwork.createcase.request.CreateCaseRequestEnvelope;
import com.tmfl.complaintnetwork.createcase.request.FileKeyValuePair;
import com.tmfl.complaintnetwork.createcase.response.CreateCaseResponseEnvelope;
import com.tmfl.complaintnetwork.createcase.response.ParsedResponse;
import com.tmfl.model.ContractResponseModel.ActiveContractsModel;
import com.tmfl.util.PreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class NewComplaintFragment extends Fragment implements View.OnClickListener {

	FileKeyValuePair fileKeyValuePair1, fileKeyValuePair2, fileKeyValuePair3;
	File    file;
	byte[]  fileByte;
	String  base64File;
	Spinner spnContractNo;
	Uri     uri;
	private Button    btnSubmit;
	private ImageView imgUpload1, imgUpload2, imgUpload3;
	private TextView txtContractNo, txtDescription, txtFileName1, txtFileName2, txtFileName3;
	private TmflApi              tmflApi;
	private ActiveContractsModel activeContractsModel;
	private ArrayList< String >  contractsModelList;
	private ProgressDialog       progressDialog;

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
		spnContractNo = ( Spinner ) view.findViewById( R.id.spnContractNo );
		contractsModelList = new ArrayList<>();
		activeContractsModel = ( ActiveContractsModel ) PreferenceHelper.getObject( Constant.ONGOING_LOAN, ActiveContractsModel.class );

		contractsModelList.add( "Select Contract" );
		for ( int i = 0; i < activeContractsModel.getContracts().size(); i++ ) {
			contractsModelList.add( activeContractsModel.getContracts().get( i ).getUsrConNo() );
			Log.d( "contract no", contractsModelList.get( i ) + " " + activeContractsModel.getContracts().size() );
		}
		spnContractNo.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.spinner_row, contractsModelList ) );

		return view;
	}

	private void initView( View view ) {

		progressDialog = new ProgressDialog( getActivity() );
		progressDialog.setMessage( "Please Wait..." );
		progressDialog.setCancelable( false );
		progressDialog.setCanceledOnTouchOutside( false );

		imgUpload1 = ( ImageView ) view.findViewById( R.id.imgUpload1 );
		imgUpload2 = ( ImageView ) view.findViewById( R.id.imgUpload2 );
		imgUpload3 = ( ImageView ) view.findViewById( R.id.imgUpload3 );

		txtFileName1 = ( TextView ) view.findViewById( R.id.txtFileName1 );
		txtFileName2 = ( TextView ) view.findViewById( R.id.txtFileName2 );
		txtFileName3 = ( TextView ) view.findViewById( R.id.txtFileName3 );

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
		Intent intent;
		String manufactures = android.os.Build.MANUFACTURER;

		switch ( view.getId() ) {

			case R.id.btnSubmit:

				if ( validate() ) {
					createCase();
				}

				break;

			case R.id.imgUpload1:

				if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
					intent.putExtra( "CONTENT_TYPE", "text/plain|image/*|application/*.pdf" );
					intent.addCategory( Intent.CATEGORY_DEFAULT );
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image/*|application/*.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}
				startActivityForResult( intent, 1 );

				break;

			case R.id.imgUpload2:

				if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
					intent.putExtra( "CONTENT_TYPE", "text/plain|image/*|application/*.pdf" );
					intent.addCategory( Intent.CATEGORY_DEFAULT );
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image/*|application/*.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}
				startActivityForResult( intent, 2 );

				break;

			case R.id.imgUpload3:

				if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
					intent.putExtra( "CONTENT_TYPE", "text/plain|image/*|application/*.pdf" );
					intent.addCategory( Intent.CATEGORY_DEFAULT );
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image/*|application/*.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}
				startActivityForResult( intent, 3 );

				break;
		}
	}

	private boolean validate() {

		if ( spnContractNo.getSelectedItemPosition() == 0 ) {
			Toast.makeText( getActivity(), "Please select Contract No!", Toast.LENGTH_SHORT ).show();
			return false;
		}
		else if ( TextUtils.isEmpty( txtDescription.getText().toString() ) ) {
			txtDescription.setError( "Please fill Description!" );
			return false;
		}

		return true;
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );
		if ( resultCode == Activity.RESULT_OK ) {

			switch ( requestCode ) {
				case 1:

					uri = data.getData();
					file = new File( getFileNameByUri( getActivity(), uri ) );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName1.setText( file.getName() );

					break;

				case 2:

					uri = data.getData();
					file = new File( getFileNameByUri( getActivity(), uri ) );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair2.setKey( file.getName() );
					fileKeyValuePair2.setValue( base64File );

					txtFileName2.setText( file.getName() );
					break;

				case 3:

					uri = data.getData();
					file = new File( getFileNameByUri( getActivity(), uri ) );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair3.setKey( file.getName() );
					fileKeyValuePair3.setValue( base64File );

					txtFileName3.setText( file.getName() );

					break;
			}
		}
	}

	private void createCase() {

		progressDialog.show();
		CreateCaseRequestEnvelope requestEnvelope = new CreateCaseRequestEnvelope();
		CreateCaseReqBody         reqBody         = new CreateCaseReqBody();
		CreateCaseReqData         reqData         = new CreateCaseReqData();

		if ( spnContractNo.getSelectedItemPosition() != 0 ) {
			reqData.setContractNo( spnContractNo.getSelectedItem().toString() );
		}
		else {
			Toast.makeText( getActivity(), "Please select Contract No!", Toast.LENGTH_SHORT ).show();
		}

		if ( !TextUtils.isEmpty( txtDescription.getText().toString().trim() ) ) {
			reqData.setCaseDescription( txtDescription.getText().toString().trim() );
		}
		else {
			txtDescription.setError( "Please enter Description!" );
		}

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

		reqBody.setCreateCaseReqData( reqData );
		requestEnvelope.setCreateCaseReqBody( reqBody );

		tmflApi.createCaseRequest( requestEnvelope ).enqueue( new Callback< CreateCaseResponseEnvelope >() {
			@Override
			public void onResponse( Call< CreateCaseResponseEnvelope > call, Response< CreateCaseResponseEnvelope > response ) {
				progressDialog.dismiss();
				Log.d( "success", response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );

				XMLPullParser  xmlPullParser = new XMLPullParser( response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );
				ParsedResponse caseFile      = xmlPullParser.parse();

				ComplaintSubmitFeedbackFragment fragment = new ComplaintSubmitFeedbackFragment();
				if ( caseFile.getCaseFile().getResult().equalsIgnoreCase( "1" ) ) {

					Bundle bundle = new Bundle();
					bundle.putString( "caseId", caseFile.getCaseFile().getCaseId() );
					bundle.putString( "message", caseFile.getCaseFile().getMessage() );
					fragment.setArguments( bundle );

					getFragmentManager()
							.beginTransaction()
							.replace( R.id.frame_complaint_container, fragment )
							.addToBackStack( this.getClass().getName() )
							.commit();
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

	private String getFileNameByUri( Context context, Uri uri ) {
		String filepath = "";//default fileName
		//Uri filePathUri = uri;
		File file;
		if ( uri.getScheme().toString().compareTo( "content" ) == 0 ) {
			Cursor cursor       = context.getContentResolver().query( uri, new String[]{ android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION }, null, null, null );
			int    column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );

			cursor.moveToFirst();

			String mImagePath = cursor.getString( column_index );
			cursor.close();
			filepath = mImagePath;

		}
		else if ( uri.getScheme().compareTo( "file" ) == 0 ) {
			try {
				file = new File( new URI( uri.toString() ) );
				if ( file.exists() ) {
					filepath = file.getAbsolutePath();
				}

			}
			catch ( URISyntaxException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			filepath = uri.getPath();
		}
		return filepath;
	}
}

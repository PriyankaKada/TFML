package com.tmfl.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.BuildConfig;
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
import com.tmfl.util.ImageDecoding;
import com.tmfl.util.PreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class NewComplaintFragment extends Fragment implements View.OnClickListener {

	private static final int REQUEST_CAMERA = 100;
	private static final int SELECT_IMAGE   = 101;
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
	private Uri                  selectedImage;
	private Dialog               updateImageDialog;
	private Uri                  imageToUploadUri;

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
		spnContractNo.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.spinner_complaint_row, contractsModelList ) );

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
		String manufactures = android.os.Build.MANUFACTURER;

		switch ( view.getId() ) {

			case R.id.btnSubmit:


				progressDialog.show();
				if ( validate() ) {
					try {
						createCase();
					}
					catch ( OutOfMemoryError error ) {
						progressDialog.dismiss();
						Toast.makeText( getActivity(), "Something went wrong, try after some time!", Toast.LENGTH_SHORT ).show();
						error.printStackTrace();
					}
				}

				break;

			case R.id.imgUpload1:

				upLoadRCdoc( 1, 10 );

				break;

			case R.id.imgUpload2:

				upLoadRCdoc( 2, 20 );

				break;

			case R.id.imgUpload3:

				upLoadRCdoc( 3, 30 );

				break;

			case R.id.txtUploadPic:
				updateImageDialog.dismiss();
				Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
				intent.setType( "text/plain|image|application.pdf" );
				intent.addCategory( Intent.CATEGORY_OPENABLE );
				this.startActivityForResult(
						Intent.createChooser( getFileChooserIntent(), "Select File" ),
						SELECT_IMAGE );

				break;

			case R.id.txtTakePic:
				updateImageDialog.dismiss();
				Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

				if ( takePictureIntent.resolveActivity( getActivity().getPackageManager() ) != null ) {
					// Create the File where the photo should go
					File photoFile = null;
					try {
						photoFile = createImageFile();
						if ( photoFile != null ) {
							takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,
							                            FileProvider.getUriForFile( getActivity(),
							                                                        BuildConfig.APPLICATION_ID + ".provider",
							                                                        createImageFile() ) );
							startActivityForResult( takePictureIntent, REQUEST_CAMERA );
						}
					}
					catch ( IOException ex ) {
						// Error occurred while creating the File
						ex.printStackTrace();
					}
					// Continue only if the File was successfully created

				}

				break;
		}
	}

	private void selectImage() {

		updateImageDialog = new Dialog( getActivity() );
		TextView txtUploadPic, txtTakePic, txtCancel;

		updateImageDialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
		updateImageDialog.setContentView( R.layout.dialog_select_image );
		updateImageDialog.getWindow().setLayout( MATCH_PARENT, WRAP_CONTENT );

		txtUploadPic = ( TextView ) updateImageDialog.findViewById( R.id.txtUploadPic );
		txtTakePic = ( TextView ) updateImageDialog.findViewById( R.id.txtTakePic );
		txtCancel = ( TextView ) updateImageDialog.findViewById( R.id.txtCancel );

		txtUploadPic.setOnClickListener( this );
		txtTakePic.setOnClickListener( this );
		txtCancel.setOnClickListener( this );

		updateImageDialog.show();
	}

	public void upLoadRCdoc( final int galleryCode, final int cameraCode ) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder( getActivity() );
		alertDialog.setTitle( "Pictures Option" );
		alertDialog.setIcon( getResources().getDrawable( R.drawable.ic_image ) );
		alertDialog.setPositiveButton( "GALLARY", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				String manufactures = android.os.Build.MANUFACTURER;
				/*if ( manufactures.equalsIgnoreCase( "samsung" ) ) {
					intent = new Intent( "com.sec.android.app.myfiles.PICK_DATA" );
					intent.putExtra( "CONTENT_TYPE", "text/plain|image|application.pdf" );
					intent.addCategory( Intent.CATEGORY_DEFAULT );
				}
				else {
					intent = new Intent( Intent.ACTION_GET_CONTENT );
					intent.setType( "text/plain|image|application.pdf" );
					intent.addCategory( Intent.CATEGORY_OPENABLE );
				}*/
				Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
				intent.setType( "text/plain|image|application.pdf" );
				intent.addCategory( Intent.CATEGORY_OPENABLE );
				startActivityForResult(
						Intent.createChooser( getFileChooserIntent(), "Select File" ),
						galleryCode );
//				startActivityForResult( getFileChooserIntent(), galleryCode );
			}
		} );
		alertDialog.setNegativeButton( "CAMERA", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				if ( ImageDecoding.isDeviceSupportCamera( getActivity() ) ) {
					Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

					if ( takePictureIntent.resolveActivity( getActivity().getPackageManager() ) != null ) {
						// Create the File where the photo should go
						File photoFile = null;
						try {
							photoFile = createImageFile();
							if ( photoFile != null ) {
								takePictureIntent.putExtra( MediaStore.EXTRA_OUTPUT,
								                            FileProvider.getUriForFile( getActivity(),
								                                                        BuildConfig.APPLICATION_ID + ".provider",
								                                                        createImageFile() ) );
								startActivityForResult( takePictureIntent, cameraCode );
							}
						}
						catch ( IOException ex ) {
							// Error occurred while creating the File
							ex.printStackTrace();
						}
						// Continue only if the File was successfully created

					}
				}
			}
		} );
		alertDialog.show();
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

	public Uri getImageUri( Context inContext, Bitmap inImage ) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress( Bitmap.CompressFormat.JPEG, 100, bytes );
		String path = MediaStore.Images.Media.insertImage( inContext.getContentResolver(), inImage, "Title", null );
		return Uri.parse( path );
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );

		String path = "";
		if ( resultCode == Activity.RESULT_OK ) {
			Bitmap imageBitmap = null;
			switch ( requestCode ) {

				case 10:

					uri = imageToUploadUri;
					String selectedImagePath = uri.getPath();
					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName1.setText( file.getName() );

					break;

				case 1:

					Uri selectedImageUri = data.getData();
					selectedImagePath = uriToFilename( selectedImageUri );

					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName1.setText( file.getName() );

					break;

				case 20:

					uri = imageToUploadUri;
					selectedImagePath = uri.getPath();
					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName2.setText( file.getName() );

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

				case 30:
					uri = imageToUploadUri;
					selectedImagePath = uri.getPath();
					file = new File( selectedImagePath );

					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName3.setText( file.getName() );

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

	/*	switch ( requestCode ) {

			case REQUEST_CAMERA:
				if ( resultCode == Activity.RESULT_OK ) {
					uri = imageToUploadUri;

					uri = imageToUploadUri;
					Uri    selectedImage     = imageToUploadUri;
					String selectedImagePath = uri.getPath();
				}

				break;
			case SELECT_IMAGE:
				if ( resultCode == Activity.RESULT_OK ) {
					*//*Uri      selectedImageUri = data.getData();
					String[] projection1      = { MediaStore.MediaColumns.DATA };
					CursorLoader cursorLoader = new CursorLoader( getActivity(), selectedImageUri, projection1, null, null,
					                                              null );
					Cursor cursor       = cursorLoader.loadInBackground();
					int    column_index = cursor.getColumnIndexOrThrow( MediaStore.MediaColumns.DATA );
					cursor.moveToFirst();
					String selectedImagePath = cursor.getString( column_index );

					uri = data.getData();*//*


					File file = new File( selectedImagePath );
					fileByte = convertFileToByteArray( file );
					base64File = Base64.encodeToString( fileByte, Base64.DEFAULT );

					fileKeyValuePair1.setKey( file.getName() );
					fileKeyValuePair1.setValue( base64File );

					txtFileName1.setText( file.getName() );
				}


				break;
		}*/
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

	private void createCase() {

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
//				Log.d( "success", response.body().getCaseResponseBody().getCaseResponse().getCreateCaseResult() );

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

					spnContractNo.setSelection( 0 );
					txtDescription.setText( "" );
				}
			}

			@Override
			public void onFailure( Call< CreateCaseResponseEnvelope > call, Throwable t ) {
				progressDialog.dismiss();
				t.printStackTrace();
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

	private String getFileNameByUri( Context context, Uri uri ) {
		String filepath = "";//default fileName
		//Uri filePathUri = uri;
		File file;
		if ( uri.getScheme().toString().compareTo( "content" ) == 0 ) {
			Cursor cursor       = context.getContentResolver().query( uri, new String[]{ android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION }, null, null, null );
			int    column_index = cursor.getColumnIndexOrThrow( android.provider.MediaStore.Images.ImageColumns.DATA );

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

	/*public String getRealPathFromURI( Uri contentUri ) {

		String   path   = null;
		String[] proj   = { MediaStore.MediaColumns.DATA };
		Cursor   cursor = getActivity().getContentResolver().query( contentUri, proj, null, null, null );
		if ( cursor.moveToFirst() ) {
			int column_index = cursor.getColumnIndexOrThrow( MediaStore.MediaColumns.DATA );
			path = cursor.getString( column_index );
		}
		cursor.close();
		return path;
	}*/

	public String getRealPathFromURI( Uri contentUri ) {
		String[] path   = { MediaStore.Images.Media.DATA };
		Cursor   cursor = getActivity().getContentResolver().query( contentUri, path, null, null, null );
		cursor.moveToFirst();
		String picturePath = cursor.getString( cursor.getColumnIndex( path[0] ) );
		cursor.close();
		return picturePath;
	}

	public String getPath( Uri uri ) {
		String[] projection   = { MediaStore.Images.Media.DATA };
		Cursor   cursor       = getActivity().getContentResolver().query( uri, projection, null, null, null );
		int      column_index = cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
		cursor.moveToFirst();
		int    columnIndex = cursor.getColumnIndex( projection[0] );
		String filePath    = cursor.getString( columnIndex );
		String path        = cursor.getString( column_index );
		cursor.close();
		return path;
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp     = new SimpleDateFormat( "yyyyMMdd_HHmmss" ).format( new Date() );
		String imageFileName = "JPEG_" + timeStamp + "_";
		File   storageDir    = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
		File image = File.createTempFile( imageFileName,  /* prefix */
		                                  ".jpg",         /* suffix */
		                                  storageDir      /* directory */
		);
		imageToUploadUri = Uri.fromFile( image );
		return image;
	}

	public static class RealPathUtil {
		@SuppressLint( "NewApi" )
		public static String getRealPathFromURI_API19( Context context, Uri uri ) {
			Log.e( "uri", uri.getPath() );
			String filePath = "";
			if ( DocumentsContract.isDocumentUri( context, uri ) ) {
				String wholeID = DocumentsContract.getDocumentId( uri );
				Log.e( "wholeID", wholeID );
// Split at colon, use second item in the array
				String[] splits = wholeID.split( ":" );
				if ( splits.length == 2 ) {
					String id = splits[1];

					String[] column = { MediaStore.Images.Media.DATA };
// where id is equal to
					String sel = MediaStore.Images.Media._ID + "=?";
					Cursor cursor = context.getContentResolver().query( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					                                                    column, sel, new String[]{ id }, null );
					int columnIndex = cursor.getColumnIndex( column[0] );
					if ( cursor.moveToFirst() ) {
						filePath = cursor.getString( columnIndex );
					}
					cursor.close();
				}
			}
			else {
				filePath = uri.getPath();
			}
			return filePath;
		}

		public static String getRealPathFromURI_BelowAPI11( Context context, Uri contentUri ) {
			String[] proj   = { MediaStore.Images.Media.DATA };
			Cursor   cursor = context.getContentResolver().query( contentUri, proj, null, null, null );
			int column_index
					= cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
			cursor.moveToFirst();
			return cursor.getString( column_index );
		}

		@SuppressLint( "NewApi" )
		public static String getRealPathFromURI_API11to18( Context context, Uri contentUri ) {
			String[] proj   = { MediaStore.Images.Media.DATA };
			String   result = null;
			CursorLoader cursorLoader = new CursorLoader(
					context,
					contentUri, proj, null, null, null );
			Cursor cursor = cursorLoader.loadInBackground();
			if ( cursor != null ) {
				int column_index =
						cursor.getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
				cursor.moveToFirst();
				result = cursor.getString( column_index );
			}
			return result;
		}
	}

}

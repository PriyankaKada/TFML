package com.tfml.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.ContractResponseModel.ContractModel;
import com.tfml.model.uploadRcResponseModel.RcUploadDataInputModel;
import com.tfml.model.uploadRcResponseModel.RcUploadResponseModel;
import com.tfml.util.ImageDecoding;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RcUpdateFragment extends Fragment implements View.OnClickListener {

	static final int REQUEST_TAKE_PHOTO = 1;
	LinearLayout ll_uploadimg;
	Uri          selectedImage;
	TmflApi      tmflApi;
	String       imgPhotoUrl, imgExt;
	int                        mtype;
	RcUploadDataInputModel     rcUploadDataInputModel;
	ArrayList< ContractModel > modelArrayList;
	String                     contractNo;
	TextView                   txt_repaymentmode, txt_emiamount, txt_dueamount, txt_duedate, txt_rc_update_no;
	MultipartBody.Part body = null;
	private EditText       edt_rc_no;
	private Button         btnRcUpload;
	private View           view;
	private ImageView      img_upload;
	private Spinner        spnContractNo;
	private List< String > contractLst;
	private int    itemindex     = 0;
	private String datavalue     = "";
	private String rcNo          = "";
	private String dueDate       = "";
	private String repaymentMode = "";
	private String currentEmi    = "";
	private String overdue       = "";


	public static String trimLeadingZeros( String source ) {
		for ( int i = 0; i < source.length(); ++i ) {
			char c = source.charAt( i );
			if ( c != '0' && !Character.isSpaceChar( c ) ) {
				return source.substring( i );
			}
		}
		return null;
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_rc_update, container, false );
		tmflApi = ApiService.getInstance().call();
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
//		((AppCompatActivity )getActivity()).getSupportActionBar().setTitle( R.string.rc_update);

		modelArrayList =
				( ArrayList< ContractModel > ) bundle.getSerializable( "datamodel" );
		datavalue = ( String ) bundle.getString( "datamodelvalue" );
		datavalue = ( String ) bundle.getString( "datamodelvalue" );
		rcNo = ( String ) bundle.getString( "RCNO" );
		dueDate = ( String ) bundle.getString( "DUEDATE" );
		repaymentMode = ( String ) bundle.getString( "REPAYMENT" );
		currentEmi = ( String ) bundle.getString( "CURRENTEMI" );
		overdue = ( String ) bundle.get( "OVERDUEAMT" );
		init();

		return view;
	}

	public void init() {

		spnContractNo = ( Spinner ) view.findViewById( R.id.spnContractNo );
		contractLst = new ArrayList< String >();
		if ( modelArrayList.size() > 0 ) {

			for ( int i = 0; i < modelArrayList.size(); i++ ) {
				contractLst.add(trimLeadingZeros( modelArrayList.get( i ).getUsrConNo() ) );
			}

			ArrayAdapter< String > madapter = new ArrayAdapter< String >( getActivity(), R.layout.spinner_row, contractLst ) {

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
//         spnContractNo.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,contractLst));


		edt_rc_no = ( EditText ) view.findViewById( R.id.edt_rc_no );
		btnRcUpload = ( Button ) view.findViewById( R.id.btn_rc_upload );
		img_upload = ( ImageView ) view.findViewById( R.id.img_upload );
		ll_uploadimg = ( LinearLayout ) view.findViewById( R.id.ll_uploadimage );
		txt_rc_update_no = ( TextView ) view.findViewById( R.id.txt_rc_update_no );
		txt_repaymentmode = ( TextView ) view.findViewById( R.id.txt_repaymentmode );
		txt_emiamount = ( TextView ) view.findViewById( R.id.txt_emiamount );
		txt_dueamount = ( TextView ) view.findViewById( R.id.txt_dueamount );
		txt_duedate = ( TextView ) view.findViewById( R.id.txt_duedate );
		if ( rcNo != null ) {
			txt_rc_update_no.setText( rcNo );
		}
		if ( repaymentMode != null ) {
			txt_repaymentmode.setText( repaymentMode );
		}
		if ( dueDate != null ) {
			txt_duedate.setText( dueDate );
		}
		if ( currentEmi != null ) {
			txt_emiamount.setText( currentEmi );
		}
		if ( overdue != null ) {
			txt_dueamount.setText( overdue );
		}
		SetFonts.setFonts( getActivity(), btnRcUpload, 2 );

		spnContractNo.post( new Runnable() {
			@Override
			public void run() {

			}
		} );

		spnContractNo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
				contractNo = spnContractNo.getSelectedItem().toString();
				itemindex = position;
				ContractModel model = modelArrayList.get( itemindex );
				setData( model );
			}

			@Override
			public void onNothingSelected( AdapterView< ? > parent ) {

			}
		} );
		btnRcUpload.setOnClickListener( this );
	}

	private void setData( ContractModel model ) {
		txt_rc_update_no.setText( model.getRcNumber() == null ? "" : model.getRcNumber().toString() );
		txt_duedate.setText( model.getDueDate() == null ? "" : model.getDueDate().toString() );
		txt_emiamount.setText( model.getDueAmount() == null ? "" : "Rs. " + model.getDueAmount().toString() );
		txt_repaymentmode.setText( model.getPdcFlag() == null ? "" : model.getPdcFlag().toString() );
		txt_dueamount.setText( model.getTotalCurrentDue() == null ? "" : "Rs." + model.getTotalCurrentDue().toString() );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.btn_rc_upload:

				if ( !TextUtils.isEmpty( edt_rc_no.getText().toString() ) ) {
					mtype = REQUEST_TAKE_PHOTO;
					ll_uploadimg.setVisibility( View.VISIBLE );
					upLoadRCdoc();
				}
				else {
					ll_uploadimg.setVisibility( View.GONE );
					Toast.makeText( getActivity(), "Please Enter RC Number", Toast.LENGTH_SHORT ).show();
				}
				break;
		}
	}

	public void upLoadRCdoc() {

		AlertDialog.Builder alertDialog = new AlertDialog.Builder( getActivity() );
		alertDialog.setTitle( "Pictures Option" );
		alertDialog.setIcon( getResources().getDrawable( R.drawable.ic_image ) );
		alertDialog.setPositiveButton( "GALLARY", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				startActivityForResult( new Intent( Intent.ACTION_PICK,
				                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI ), 1 );
			}
		} );
		alertDialog.setNegativeButton( "CAMERA", new DialogInterface.OnClickListener() {
			@Override
			public void onClick( DialogInterface dialog, int which ) {
				if ( ImageDecoding.isDeviceSupportCamera( getActivity() ) ) {
					Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
					selectedImage = ImageDecoding.getOutputMediaFileUri( 1 );
					intent.putExtra( MediaStore.EXTRA_OUTPUT, selectedImage );
					startActivityForResult( intent, 0 );
				}
			}
		} );
		alertDialog.show();
	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		super.onActivityResult( requestCode, resultCode, data );
		String path = "";
		switch ( requestCode ) {
			case 0:
				if ( resultCode == Activity.RESULT_OK ) {
					path = selectedImage.getPath();
				}
				break;

			case 1:
				if ( resultCode == Activity.RESULT_OK ) {
					selectedImage = data.getData();
					path = getRealPathFromURI( selectedImage );
				}
				break;
		}

		if ( selectedImage != null ) {
			if ( mtype == 1 ) {
				imgPhotoUrl = path;
				img_upload.setImageBitmap( ImageDecoding.decodeBitmapFromFile( imgPhotoUrl, 100, 100 ) );
				imgExt = imgPhotoUrl.substring( imgPhotoUrl.lastIndexOf( "." ) + 1, imgPhotoUrl.length() );
				Log.e( "ImageUrl", imgPhotoUrl );
				rcUploadDataInputModel = new RcUploadDataInputModel();
				rcUploadDataInputModel.setUserId( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
				rcUploadDataInputModel.setContractNo( contractNo );
				rcUploadDataInputModel.setRcNo( edt_rc_no.getText().toString() );
				File file = new File( imgPhotoUrl );
				rcUploadDataInputModel.setImage( file );
				RequestBody image = RequestBody.create( MediaType.parse( "image/*" ), file );
				body = MultipartBody.Part.createFormData( "image", file.getName(), image );
			}

			if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
				CommonUtils.showProgressDialog( getActivity(), "Please Wait Uploading Data..." );

				callRcUploadData( rcUploadDataInputModel, body );
			}
			else {
				CommonUtils.showAlert1( getActivity(), "", "No Internet Connection", false );
			}

		}
	}

	public String getRealPathFromURI( Uri contentUri ) {
		String[] path   = { MediaStore.Images.Media.DATA };
		Cursor   cursor = getActivity().getContentResolver().query( contentUri, path, null, null, null );
		cursor.moveToFirst();
		String picturePath = cursor.getString( cursor.getColumnIndex( path[0] ) );
		cursor.close();
		return picturePath;
	}


	public void callRcUploadData( RcUploadDataInputModel rcUploadDataInputModel, MultipartBody.Part body ) {
		HashMap< String, RequestBody > params = callmapmethod();
		tmflApi.getRcUploadData( params, body ).enqueue( new Callback< RcUploadResponseModel >() {
			@Override
			public void onResponse( Call< RcUploadResponseModel > call, Response< RcUploadResponseModel > response ) {
				//       Toast.makeText(getActivity(),""+response.body().getStatus().toString(),Toast.LENGTH_LONG).show();
				Log.e( "response", new Gson().toJson( response.body() ) + "" );

				if ( response.body().getStatus().contains( "success" ) ) {
					CommonUtils.closeProgressDialog();
					edt_rc_no.setText( "" );
					img_upload.setImageDrawable( null );
					CommonUtils.showAlert1( getActivity(), "", "RC Uploaded Successfully", false );
				}
				else {
					//   Log.e("getApplyloanErr", response.body().getMessages());
					CommonUtils.closeProgressDialog();
					CommonUtils.showAlert1( getActivity(), "", response.body().getMessages(), false );
				}
			}

			@Override
			public void onFailure( Call< RcUploadResponseModel > call, Throwable t ) {
				Log.e( "error", t.getMessage() );
				CommonUtils.closeProgressDialog();
			}
		} );


	}

	private HashMap< String, RequestBody > callmapmethod() {
		HashMap< String, RequestBody > params = new HashMap<>();
		// add another part within the multipart request
		RequestBody contractno =
				RequestBody.create(
						MediaType.parse( "text/plain" ), rcUploadDataInputModel.getContractNo() );
		params.put( "contract_no", contractno );
		RequestBody rcno =
				RequestBody.create(
						MediaType.parse( "text/plain" ), rcUploadDataInputModel.getRcNo() );
		params.put( "rc_no", rcno );

		RequestBody uid =
				RequestBody.create(
						MediaType.parse( "text/plain" ), rcUploadDataInputModel.getUserId() );
		params.put( "user_id", uid );

		return params;
	}


}

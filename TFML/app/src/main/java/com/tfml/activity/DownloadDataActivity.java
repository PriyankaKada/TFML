package com.tfml.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tfml.R;
import com.tfml.adapter.DownloadAdapter;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.downloadResponseModel.DownloadResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadDataActivity extends DrawerBaseActivity implements View.OnClickListener {
	ListView  lstDownloadList;
	TmflApi   tmflApi;
	ImageView img_download_back, img_drawer, imgResult;
	String fileUrl;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		View    view    = getLayoutInflater().inflate( R.layout.activity_download_data, frameLayout );
		Toolbar toolbar = ( Toolbar ) view.findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		getSupportActionBar().setTitle( "" );
		lstDownloadList = ( ListView ) findViewById( R.id.lst_download_data );
		img_download_back = ( ImageView ) findViewById( R.id.img_download_back );
		img_drawer = ( ImageView ) findViewById( R.id.img_drawer_download );
		imgResult = ( ImageView ) findViewById( R.id.img_result );
		img_download_back.setOnClickListener( this );
		img_drawer.setOnClickListener( this );
		Bundle bundle = this.getIntent().getExtras();
		if ( bundle != null ) {
			fileUrl = bundle.getString( "URL" );
		}

		init();

	}

	public void init() {

		if ( CommonUtils.isNetworkAvailable( this ) ) {
			CommonUtils.showProgressDialog( this, "Getting Your Information" );

			downloadData();
		}
		else {
			CommonUtils.showAlert1( this, "Network Error", "Please Check Network Connection", false );
		}


	}


	public void downloadData() {
		tmflApi = ApiService.getInstance().call();

		tmflApi.getDownloadResponse().enqueue( new Callback< DownloadResponse >() {
			@Override
			public void onResponse( Call< DownloadResponse > call, Response< DownloadResponse > response ) {
				CommonUtils.closeProgressDialog();
				Log.e( "DownloadResponse", new Gson().toJson( response.body() ) );
				List< com.tfml.model.downloadResponseModel.Datum > body = response.body().getData();
				lstDownloadList.setAdapter( new DownloadAdapter( DownloadDataActivity.this, body ) );
				callDownloadImageFromUri( fileUrl );
			}

			@Override
			public void onFailure( Call< DownloadResponse > call, Throwable t ) {
				CommonUtils.closeProgressDialog();
				Log.e( "Resp", "Error" );
			}
		} );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.img_download_back:
				startActivity( new Intent( DownloadDataActivity.this, ContractActivity.class ) );
				finish();
				break;
			case R.id.img_drawer_download:
				//    Toast.makeText(getBaseContext(), "I AM IN DRAWER",Toast.LENGTH_SHORT).show();
				openDrawer();
				break;
		}
	}


	public void callDownloadImageFromUri( String URL ) {


		Picasso.with( this ).load( URL ).into( new Target() {
			@Override
			public void onBitmapLoaded( Bitmap bitmap, Picasso.LoadedFrom from ) {
				Log.d( "abc", "onbitmap loaded called" );
				imgResult.setImageBitmap( bitmap );
				String path = Environment.getExternalStorageDirectory().toString()
						+ "/TMFL/Download/" + "Tmfl.jpg";

				FileOutputStream outputStream = null;
				try {
					File file = new File( path );
					outputStream = new FileOutputStream( file );

					bitmap.compress( Bitmap.CompressFormat.JPEG, 100, outputStream );
					outputStream.flush();
					outputStream.close();
				}
				catch ( Exception e ) {
					e.printStackTrace();
				}
			}

			@Override
			public void onBitmapFailed( Drawable errorDrawable ) {
				Log.d( "abc", "onbitmap failed called" );
			}

			@Override
			public void onPrepareLoad( Drawable placeHolderDrawable ) {
				Log.d( "abc", "onbitmap prepareload called" );
			}
		} );

	}


}

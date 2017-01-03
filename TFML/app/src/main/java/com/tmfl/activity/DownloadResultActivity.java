package com.tmfl.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tmfl.R;
import com.tmfl.auth.TmflApi;

import java.io.File;
import java.io.FileOutputStream;

public class DownloadResultActivity extends BaseActivity {

	ImageView imgResult;
	String    fileUrl;
	TmflApi   tmflApi;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_download_result );
		Bundle bundle = this.getIntent().getExtras();
		fileUrl = bundle.getString( "URL" );
		init();
	}

	public void init() {
		imgResult = ( ImageView ) findViewById( R.id.img_download );
		callDownloadImageFromUri( fileUrl );
	}

	public void callDownloadImageFromUri( String URL ) {


		Picasso.with( this ).load( URL ).into( new Target() {
			@Override
			public void onBitmapLoaded( Bitmap bitmap, Picasso.LoadedFrom from ) {
				Log.d( "abc", "onbitmap loaded called" );
				imgResult.setImageBitmap( bitmap );
				String path = Environment.getExternalStorageDirectory().toString()
						+ "/TMFL/Download/" + "Tmfl.jpg";
				//  String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Tmfl.jpg";
				//  Log.d("abcc","apth "+path);

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

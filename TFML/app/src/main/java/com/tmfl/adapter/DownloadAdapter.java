package com.tmfl.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.activity.DownloadDataActivity;
import com.tmfl.model.downloadResponseModel.Datum;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by webwerks on 1/9/16.
 */

public class DownloadAdapter extends BaseAdapter {
	List< Datum > downloadList;
	Context       context;

	public DownloadAdapter( Context context, List< Datum > downloadList ) {
		this.context = context;
		this.downloadList = downloadList;
	}

	@Override
	public int getCount() {
		return downloadList.size();
	}

	@Override
	public Object getItem( int position ) {
		return downloadList.get( position );
	}

	@Override
	public long getItemId( int position ) {
		return position;
	}

	@Override
	public View getView( final int position, View convertView, ViewGroup parent ) {
		Holder holder = null;
		if ( convertView == null ) {
			LayoutInflater layoutInflater = ( ( Activity ) context ).getLayoutInflater();
			convertView = layoutInflater.inflate( R.layout.download_item_list, null );
			holder = new Holder();
			holder.txtFile = ( TextView ) convertView.findViewById( R.id.txt_file_name );
			holder.txtFile.setPaintFlags( Paint.UNDERLINE_TEXT_FLAG );
			convertView.setTag( holder );
		}
		else

		{
			holder = ( DownloadAdapter.Holder ) convertView.getTag();
		}
		holder.txtFile.setText( downloadList.get( position ).getName() );
		holder.txtFile.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				String fileUrl        = downloadList.get( position ).getFile();
				Intent downloadIntent = new Intent( context, DownloadDataActivity.class );
				downloadIntent.putExtra( "URL", fileUrl );
				Log.d( "fileUrl", fileUrl );

				Uri uri = Uri.parse( fileUrl );

				String mime = fileUrl.substring( fileUrl.lastIndexOf( "." ), fileUrl.length() );

				Log.d( "mime type", mime );

				DownloadManager.Request request = new DownloadManager.Request( uri );
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
				request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "TFML/Downloads"
						+ SystemClock.currentThreadTimeMillis() + mime );

				DownloadManager manager           = ( DownloadManager ) context.getSystemService( Context.DOWNLOAD_SERVICE );
				Long            downloadReference = manager.enqueue( request );
				try {
					manager.openDownloadedFile( downloadReference );
				}
				catch ( FileNotFoundException e ) {
					e.printStackTrace();
				}
				manager.enqueue( request );

			}
		} );
		return convertView;
	}

	public class Holder {
		TextView txtFile;
	}
}

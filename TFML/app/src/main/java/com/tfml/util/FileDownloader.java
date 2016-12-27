package com.tfml.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by webwerks on 4/10/16.
 */

public class FileDownloader {
	private static final int MEGABYTE = 1024 * 1024;

	public static void downloadFile( String fileUrl, File directory ) {
		try {

			URL               url           = new URL( fileUrl );
			HttpURLConnection urlConnection = ( HttpURLConnection ) url.openConnection();
			//urlConnection.setRequestMethod("GET");
			//urlConnection.setDoOutput(true);
			urlConnection.connect();

			InputStream      inputStream      = urlConnection.getInputStream();
			FileOutputStream fileOutputStream = new FileOutputStream( directory );
			int              totalSize        = urlConnection.getContentLength();

			byte[] buffer       = new byte[MEGABYTE];
			int    bufferLength = 0;
			while ( ( bufferLength = inputStream.read( buffer ) ) > 0 ) {
				fileOutputStream.write( buffer, 0, bufferLength );
			}
			fileOutputStream.close();
		}
		catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}
		catch ( MalformedURLException e ) {
			e.printStackTrace();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static void downloadPdfFile( String url, File outputFile ) {
		try {
			URL           u             = new URL( url );
			URLConnection conn          = u.openConnection();
			int           contentLength = conn.getContentLength();

			DataInputStream stream = new DataInputStream( u.openStream() );

			byte[] buffer = new byte[contentLength];
			stream.readFully( buffer );
			stream.close();

			DataOutputStream fos = new DataOutputStream( new FileOutputStream( outputFile ) );
			fos.write( buffer );
			fos.flush();
			fos.close();
		}
		catch ( FileNotFoundException e ) {
			return; // swallow a 404
		}
		catch ( IOException e ) {
			return; // swallow a 404
		}
	}

}

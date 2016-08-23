package com.tfml.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.tfml.R;


/**
 * Created by Satyawan on 1/8/16.
 */
public class CommonUtils
{
	private static ProgressDialog mProgress;

	/**
	 * Checks Network Availabiity
	 *
	 * @param ctx
	 * @return
	 */
	public static boolean isNetworkAvailable( Context ctx )
	{
		ConnectivityManager cm = ( ConnectivityManager ) ctx.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo         ni = cm.getActiveNetworkInfo();
		return ni != null;
	}

	/**
	 * Shows Progress Bar
	 *
	 * @param ctx
	 * @param message
	 */
	public static void showProgressDialog( Context ctx, String message )
	{
		/**
		 * In Marshmallow the progressbar takes Accent color & the current accent color is white,
		 * so the dialog appears as not having the progressbar.
		 * Apply style to change the color of the progressbar.
		 */

		if(mProgress == null)
		mProgress = new ProgressDialog(ctx, R.style.AppCompatProgressDialogStyle );
		mProgress.setMessage( message );
		mProgress.setCancelable( false );
		mProgress.show();

		/**
		 * By applying the custom style, the progressdialog width was set to wrap content.
		 * So Make it Match parent.
		 */
		Window window = mProgress.getWindow();
		window.setLayout( WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
	}

	/**
	 * Dismisses the Progress Bar
	 */
	public static void closeProgressDialog()
	{
		if( mProgress != null ){
			mProgress.dismiss();
			mProgress = null;
		}
	}

	/**
	 * Shows 1 button Dialog box
	 *
	 * @param ctx
	 * @param title
	 * @param message
	 * @param cancelable
	 */
	public static void showAlert1( Context ctx, String title, String message, boolean cancelable )
	{
		new AlertDialog.Builder( ctx )
				.setTitle( title )
				.setCancelable( cancelable )
				.setMessage( message )
				.setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener()
				{
					public void onClick( DialogInterface dialog, int which )
					{
						// do nothing..
					}
				} ).show();
	}





}
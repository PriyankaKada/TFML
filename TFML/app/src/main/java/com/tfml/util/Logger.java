package com.tfml.util;

import android.util.Log;

/**
 * Created by webwerks on 19/10/16.
 */

public class Logger {
	private static final String TAG = "TMFL";

	public static void i( Class clazz, String msg ) {

		Log.i( TAG, clazz.getSimpleName() + " -> " + msg );
	}

	public static void e( Class clazz, String msg ) {

		Log.e( TAG, clazz.getSimpleName() + " -> " + msg );
	}

	public static void d( Class clazz, String msg ) {

		Log.d( TAG, clazz.getSimpleName() + " -> " + msg );
	}

	public static void v( Class clazz, String msg ) {

		Log.v( TAG, clazz.getSimpleName() + " -> " + msg );
	}

}

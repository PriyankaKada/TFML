package com.tmfl.util;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by satyawan on 29/9/16.
 */

public class AppController extends Application {
	public static AppController mInstances;

	public static final AppController getInstance() {
		return mInstances;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Stetho.initializeWithDefaults( this );
		mInstances = this;
	}

}

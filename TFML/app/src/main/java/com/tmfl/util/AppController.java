package com.tmfl.util;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

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
		Fabric.with(this, new Crashlytics());

		Stetho.initializeWithDefaults( this );
		mInstances = this;
	}

}

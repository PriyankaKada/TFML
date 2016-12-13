package com.tmfl.util;

import android.app.Application;

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
		mInstances = this;
	}

}

package com.tfml.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tfml.auth.Constant;

/**
 * Created by satyawan 04/08/2016.
 */
public class PreferenceHelper {
	public static final String SOAPSTATMENTOFACCOUNTRESPONSE = "responseModel";
	public static final String API_TOKEN                     = "api_token";
	public static final String USER_ID                       = "user_id";
	public static final String FLAG_LOGGED_OUT               = "logout";
	public static final String ISLOGIN                       = "Login";
	public static final String CONTRACT_NO                   = "contractNo";
	public static final String EMAIL                         = "email";
	private static SharedPreferences.Editor preferenceEditor;
	private static SharedPreferences        pref;

	public static synchronized final void insertString( String key, String value ) {
		getPreferences().edit().putString( key, value ).commit();
	}

	public static synchronized final void insertBoolean( String key, boolean value ) {
		getPreferences().edit().putBoolean( key, value ).commit();
	}

	public synchronized static final void remove( String key ) {
		getPreferences().edit().remove( key ).commit();
	}

	public synchronized static final void clear() {
		getPreferences().edit().clear().commit();
	}

	public synchronized static final String getString( String key ) {
		return getPreferences().getString( key, null );
	}

	public static final boolean getBoolean( String key ) {
		return getPreferences().getBoolean( key, false );
	}

	public synchronized static final SharedPreferences getPreferences() {
		return AppController.getInstance().getSharedPreferences( Constant.LOGIN_PREF, Context.MODE_PRIVATE );
	}

	public static synchronized final void insertObject( String key, Object model ) {
		getPreferences().edit().putString( key, new Gson().toJson( model ) ).commit();
	}

	public static synchronized final Object getObject( String key, Class< ? > modelClass ) {
		return new Gson().fromJson( getPreferences().getString( key, null ), modelClass );
	}

	public static synchronized final boolean contain( String key ) {
		return getPreferences().contains( key );
	}

}
package com.tmfl.model.forgotPasswordResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 4/10/16.
 */

public class ForgotInputModel {
	@SerializedName( "user_id" )
	String UserID;

	public String getUserID() {
		return UserID;
	}

	public void setUserID( String userID ) {
		UserID = userID;
	}

}

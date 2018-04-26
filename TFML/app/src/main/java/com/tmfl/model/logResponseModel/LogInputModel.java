package com.tmfl.model.logResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 5/10/16.
 */

public class LogInputModel {
	@SerializedName( "user_id" )
	public String user_id;
	@SerializedName( "api_token" )
	public String api_token;

	public String getApi_token() {
		return api_token;
	}

	public void setApi_token( String api_token ) {
		this.api_token = api_token;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id( String user_id ) {
		this.user_id = user_id;
	}


}

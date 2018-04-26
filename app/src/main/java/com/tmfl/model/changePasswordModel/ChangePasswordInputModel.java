package com.tmfl.model.changePasswordModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 10/10/16.
 */

public class ChangePasswordInputModel {
	@SerializedName( "user_id" )
	public String user_id;
	@SerializedName( "old_password" )
	public String old_password;
	@SerializedName( "new_password" )
	public String new_password;
	@SerializedName( "api_token" )
	public String api_token;

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password( String new_password ) {
		this.new_password = new_password;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id( String user_id ) {
		this.user_id = user_id;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password( String old_password ) {
		this.old_password = old_password;
	}

	public String getApi_token() {
		return api_token;
	}

	public void setApi_token( String api_token ) {
		this.api_token = api_token;
	}


}

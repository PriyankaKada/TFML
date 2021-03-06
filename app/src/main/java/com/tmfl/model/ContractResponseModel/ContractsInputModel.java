package com.tmfl.model.ContractResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 1/10/16.
 */

public class ContractsInputModel {

	@SerializedName( "user_id" )
	public int user_id;


	@SerializedName( "api_token" )
	public String api_token;


	public int getUser_id() {
		return user_id;
	}

	public void setUser_id( int user_id ) {
		this.user_id = user_id;
	}

	public String getApi_token() {
		return api_token;
	}

	public void setApi_token( String api_token ) {
		this.api_token = api_token;
	}
}

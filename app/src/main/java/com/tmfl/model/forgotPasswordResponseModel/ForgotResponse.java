package com.tmfl.model.forgotPasswordResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 4/10/16.
 */

public class ForgotResponse {
	@SerializedName( "status" )
	@Expose
	private String status;
	@SerializedName( "data" )
	@Expose
	private String data;
	/**
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status
	 */
	public void setStatus( String status ) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}

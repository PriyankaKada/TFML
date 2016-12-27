package com.tfml.model.loginResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 2/10/16.
 */

public class LoginResponseModel {

	@SerializedName( "status" )
	@Expose
	private String status;
	@SerializedName( "data" )
	@Expose
	private Data   data;

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

	/**
	 * @return The data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data The data
	 */
	public void setData( Data data ) {
		this.data = data;
	}
}

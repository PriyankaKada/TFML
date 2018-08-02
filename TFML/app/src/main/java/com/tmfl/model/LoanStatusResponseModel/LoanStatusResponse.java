package com.tmfl.model.LoanStatusResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 8/8/16.
 */

public class LoanStatusResponse {
	@SerializedName( "status" )
	@Expose
	private String status;
	@SerializedName( "error" )
	@Expose
	private String error;

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
	 * @return The error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error The error
	 */
	public void setError( String error ) {
		this.error = error;
	}

}

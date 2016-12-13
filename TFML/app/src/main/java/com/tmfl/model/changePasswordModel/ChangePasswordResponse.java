package com.tmfl.model.changePasswordModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 10/10/16.
 */

public class ChangePasswordResponse {
	@SerializedName( "status" )
	@Expose
	private String status;
	@SerializedName( "error" )
	@Expose
	private List< String > error = new ArrayList< String >();

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
	public List< String > getError() {
		return error;
	}

	/**
	 * @param error The error
	 */
	public void setError( List< String > error ) {
		this.error = error;
	}
}

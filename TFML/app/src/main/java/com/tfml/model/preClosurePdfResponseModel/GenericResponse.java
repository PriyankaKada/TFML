package com.tfml.model.preClosurePdfResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 13/10/16.
 */

public class GenericResponse {
	@SerializedName( "status" )
	@Expose
	private String status;
	@SerializedName( "error" )
	@Expose
	private String error;

	public String getStatus() {
		return status;
	}

	public void setStatus( String status ) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError( String error ) {
		this.error = error;
	}


}

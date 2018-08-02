package com.tmfl.model.QuickcallResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 5/8/16.
 */

public class QuickCallResponse {
	@SerializedName( "data" )
	@Expose
	private Data   data;
	@SerializedName( "status" )
	private String status;
	@SerializedName( "error" )
	private ErrorData error;

	public ErrorData getError() {
		return error;
	}

	public void setError( ErrorData error ) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus( String status ) {
		this.status = status;
	}

	public Data getData() {
		return data;
	}

	public void setData( Data data ) {
		this.data = data;
	}

}

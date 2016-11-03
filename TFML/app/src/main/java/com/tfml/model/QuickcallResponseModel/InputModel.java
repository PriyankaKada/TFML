package com.tfml.model.QuickcallResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 5/8/16.
 */

public class InputModel {
	@SerializedName( "mobile_no" )
	@Expose()
	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber( String mobileNumber ) {
		this.mobileNumber = mobileNumber;
	}
}

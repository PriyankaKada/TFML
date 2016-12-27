package com.tfml.model.QuickcallResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 14/10/16.
 */

public class Data {
	@SerializedName( "otp" )
	@Expose
	private String otp;

	/**
	 * @return The otp
	 */
	public String getOtp() {
		return otp;
	}

	/**
	 * @param otp The otp
	 */
	public void setOtp( String otp ) {
		this.otp = otp;
	}
}

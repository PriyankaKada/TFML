package com.tmfl.model.LoanStatusResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 8/8/16.
 */

public class LoanStatusInputModel {
	@SerializedName( "otp" )
	@Expose
	private String otpNumber;
	@SerializedName( "mobile_no" )
	@Expose()
	private String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber( String mobileNumber ) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtpNumber() {
		return otpNumber;
	}

	public void setOtpNumber( String otpNumber ) {
		this.otpNumber = otpNumber;
	}


}

package com.tmfl.model.QuickcallResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by webwerks on 16/1/17.
 */
public class ErrorData {

	@SerializedName( "mobile_no" )
	@Expose
	private List< String > mobileNo = null;

	public List< String > getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo( List< String > mobileNo ) {
		this.mobileNo = mobileNo;
	}


}

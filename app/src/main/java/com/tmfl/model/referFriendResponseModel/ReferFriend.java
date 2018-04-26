package com.tmfl.model.referFriendResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 22/2/17.
 */
public class ReferFriend {
	@SerializedName( "name" )
	@Expose
	private String firstName;

	@SerializedName( "mobile_number" )
	@Expose()
	private String mobileNumber;

	@SerializedName( "refered_by" )
	@Expose
	private String referedBy;

	@SerializedName( "api_token" )
	@Expose
	private String  apiToken;





	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber( String mobileNumber ) {
		this.mobileNumber = mobileNumber;
	}


	public String getReferedBy() {
		return referedBy;
	}

	public void setReferedBy( String referedBy ) {
		this.referedBy = referedBy;
	}


	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken( String apiToken ) {
		this.apiToken = apiToken;
	}
}

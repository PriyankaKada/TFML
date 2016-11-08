package com.tfml.model.socialResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 23/8/16.
 */

public class ContactListResponseModel {

	@SerializedName( "emailId" )
	@Expose
	private String email;
	@SerializedName( "whatsapp_no" )
	@Expose
	private String whatsappNo;
	@SerializedName( "phone_no" )
	@Expose
	private String phoneNo;

	/**
	 * @return The emailId
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The emailId
	 */
	public void setEmail( String email ) {
		this.email = email;
	}

	/**
	 * @return The whatsappNo
	 */
	public String getWhatsappNo() {
		return whatsappNo;
	}

	/**
	 * @param whatsappNo The whatsapp_no
	 */
	public void setWhatsappNo( String whatsappNo ) {
		this.whatsappNo = whatsappNo;
	}

	/**
	 * @return The phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo The phone_no
	 */
	public void setPhoneNo( String phoneNo ) {
		this.phoneNo = phoneNo;
	}

}

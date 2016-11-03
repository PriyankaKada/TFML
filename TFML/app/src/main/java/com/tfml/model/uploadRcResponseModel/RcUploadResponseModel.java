package com.tfml.model.uploadRcResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 1/9/16.
 */

public class RcUploadResponseModel {

	@SerializedName( "status" )
	@Expose
	private String status;
	@SerializedName( "messages" )
	@Expose
	private String messages;

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
	 * @return The messages
	 */
	public String getMessages() {
		return messages;
	}

	/**
	 * @param messages The messages
	 */
	public void setMessages( String messages ) {
		this.messages = messages;
	}

}

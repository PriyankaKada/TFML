package com.tmfl.complaintnetwork.uploaddoc.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 8/2/17.
 */

@Root( name = "soap12:Envelope", strict = false )
public class UploadDocResponseEnvelope {

	@Element( name = "Body", required = false )
	public UploadDocResponseBody responseBody;

	public UploadDocResponseBody getResponseBody() {
		return responseBody;
	}

	public void setResponseBody( UploadDocResponseBody responseBody ) {
		this.responseBody = responseBody;
	}
}
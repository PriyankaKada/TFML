package com.tmfl.complaintnetwork.uploaddoc.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 9/2/17.
 */

@Root( name = "soap12:Body", strict = false )
public class UploadDocResponseBody {

	@Element( name = "UploadDocResponse", required = false )
	public UploadDocResponse response;

	public UploadDocResponse getResponse() {
		return response;
	}

	public void setResponse( UploadDocResponse response ) {
		this.response = response;
	}
}

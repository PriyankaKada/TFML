package com.tmfl.complaintnetwork.uploaddoc.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 9/2/17.
 */
@Root( name = "UploadDocResponse", strict = false )
@Namespace( reference = "http://tempuri.org/" )
public class UploadDocResponse {

	@Element( name = "UploadDocResult", required = false )
	public String result;

	public String getResult() {
		return result;
	}

	public void setResult( String result ) {
		this.result = result;
	}
}

package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "soap12:Body", strict = false )
public class UploadDocReqBody {

	@Element( name = "SaveBytesToFile", required = false )
	public UploadDocData uploadDocData;

	public UploadDocData getUploadDocData() {
		return uploadDocData;
	}

	public void setUploadDocData( UploadDocData uploadDocData ) {
		this.uploadDocData = uploadDocData;
	}

}

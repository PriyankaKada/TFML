package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "soap12:Body", strict = false )
public class UploadDocReqBody {

	@Element( name = "UploadDoc", required = false )
	public UploadDocReqData uploadDocData;

	public UploadDocReqData getUploadDocData() {
		return uploadDocData;
	}

	public void setUploadDocData( UploadDocReqData uploadDocData ) {
		this.uploadDocData = uploadDocData;
	}

}

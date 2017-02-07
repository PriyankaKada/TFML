package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "SaveBytesToFile", strict = false )
public class UploadDocData {

	@Element( name = "caseId", required = false )
	public String caseId;

	@ElementList( name = "files" )
	public UploadFiles uploadFiles;

	public UploadFiles getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles( UploadFiles uploadFiles ) {
		this.uploadFiles = uploadFiles;
	}
}

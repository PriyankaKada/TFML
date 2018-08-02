package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 * Created by Sandeep on 7/2/17.
 */
public class UploadFiles {

	@ElementList( name = "KeyValuePairOfStringArrayOfByte", required = false )
	public List< FileKeyValuePair > fileKeyValuePair;

	public List< FileKeyValuePair > getFileKeyValuePair() {
		return fileKeyValuePair;
	}

	public void setFileKeyValuePair( List< FileKeyValuePair > fileKeyValuePair ) {
		this.fileKeyValuePair = fileKeyValuePair;
	}

}

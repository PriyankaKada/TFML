package com.tmfl.complaintnetwork.createcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "files" )
public class AttachFiles {

	@Element( name = "KeyValuePairOfStringArrayOfByte", required = false )
	public FileKeyValuePair fileKeyValuePair;

	public FileKeyValuePair getFileKeyValuePair() {
		return fileKeyValuePair;
	}

	public void setFileKeyValuePair( FileKeyValuePair fileKeyValuePair ) {
		this.fileKeyValuePair = fileKeyValuePair;
	}

}

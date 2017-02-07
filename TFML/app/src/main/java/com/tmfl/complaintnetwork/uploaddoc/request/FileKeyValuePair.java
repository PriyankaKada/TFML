package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.Element;

/**
 * Created by Sandeep on 7/2/17.
 */

public class FileKeyValuePair {

	@Element( name = "key" )
	public String key;

	@Element( name = "value" )
	public byte[] value;

	public String getKey() {
		return key;
	}

	public void setKey( String key ) {
		this.key = key;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue( byte[] value ) {
		this.value = value;
	}
}

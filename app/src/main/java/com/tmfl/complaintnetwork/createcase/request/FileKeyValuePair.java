package com.tmfl.complaintnetwork.createcase.request;

import org.simpleframework.xml.Element;

/**
 * Created by Sandeep on 7/2/17.
 */

public class FileKeyValuePair {

	@Element( name = "Key", required = false )
	public String key;

	@Element( name = "Value", required = false )
	public String value;

	public FileKeyValuePair( String key, String value ) {
		this.key = key;
		this.value = value;
	}

	public FileKeyValuePair() {

	}

	public String getKey() {
		return key;
	}

	public void setKey( String key ) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue( String value ) {
		this.value = value;
	}
}

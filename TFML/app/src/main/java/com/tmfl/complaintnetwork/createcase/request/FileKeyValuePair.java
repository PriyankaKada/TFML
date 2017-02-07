package com.tmfl.complaintnetwork.createcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "KeyValuePairOfStringArrayOfByte" )
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

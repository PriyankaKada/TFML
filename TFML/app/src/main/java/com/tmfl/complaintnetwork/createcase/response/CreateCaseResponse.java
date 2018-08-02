package com.tmfl.complaintnetwork.createcase.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "CreateCase_CustOneResponse" )
@Namespace( reference = "http://tempuri.org/" )
public class CreateCaseResponse {

	@Element( name = "CreateCase_CustOneResult" )
	String createCaseResult;

	public String getCreateCaseResult() {
		return createCaseResult;
	}

	public void setCreateCaseResult( String createCaseResult ) {
		this.createCaseResult = createCaseResult;
	}
}

package com.tmfl.complaintnetwork.findcase.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 6/2/17.
 */
@Root( name = "FindCaseResponse" )
@Namespace( reference = "http://tempuri.org/" )
public class FindCaseResponse {

	@Element( name = "FindCaseResult", required = false )
	String findCaseResult;

	public String getFindCaseResult() {
		return findCaseResult;
	}

	public void setFindCaseResult( String findCaseResult ) {
		this.findCaseResult = findCaseResult;
	}

	@Override
	public String toString() {
		return "FindCaseResponse{" +
				"findCaseResult='" + findCaseResult + '\'' +
				'}';
	}
}
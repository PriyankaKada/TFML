package com.tmfl.complaintnetwork.findcase.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 6/2/17.
 */
@Root( name = "soap:Body" )
public class FindCaseResponseBody {

	@Element( name = "FindCaseResponse", required = false )
	FindCaseResponse findCaseResponse;

	public FindCaseResponse getFindCaseResponse() {
		return findCaseResponse;
	}

	public void setFindCaseResponse( FindCaseResponse findCaseResponse ) {
		this.findCaseResponse = findCaseResponse;
	}
}

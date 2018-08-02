package com.tmfl.complaintnetwork.createcase.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "soap:Body", strict = false )
public class CreateCaseResponseBody {

	@Element( name = "CreateCase_CustOneResponse", required = false )
	CreateCaseResponse caseResponse;

	public CreateCaseResponse getCaseResponse() {
		return caseResponse;
	}

	public void setCaseResponse( CreateCaseResponse caseResponse ) {
		this.caseResponse = caseResponse;
	}
}

package com.tmfl.complaintnetwork.createcase.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */


@Root( name = "soap:Envelope" )
public class CreateCaseResponseEnvelope {

	@Element( name = "Body", required = false )
	CreateCaseResponseBody caseResponseBody;

	public CreateCaseResponseBody getCaseResponseBody() {
		return caseResponseBody;
	}

	public void setCaseResponseBody( CreateCaseResponseBody caseResponseBody ) {
		this.caseResponseBody = caseResponseBody;
	}
}

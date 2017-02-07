package com.tmfl.complaintnetwork.findcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 6/2/17.
 */
@Root( name = "soap12:Envelope" )
@NamespaceList( {
		@Namespace( prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance" ),
		@Namespace( prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema" ),
		@Namespace( prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope" )
} )

public class FindCaseRequestEnvelope {

	@Element( name = "soap12:Body", required = false )
	public FindCaseBody caseBody;

	public FindCaseBody getCaseBody() {
		return caseBody;
	}

	public void setCaseBody( FindCaseBody caseBody ) {
		this.caseBody = caseBody;
	}
}

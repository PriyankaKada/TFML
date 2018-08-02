package com.tmfl.complaintnetwork.findcase.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Sandeep on 6/2/17.
 */
@Root( name = "soap:Envelope" )
public class FindCaseResponseEnvelope implements Serializable {

	@Element( name = "Body", required = false )
	FindCaseResponseBody findCaseBody;

	public FindCaseResponseBody getFindCaseBody() {
		return findCaseBody;
	}

	public void setFindCaseBody( FindCaseResponseBody findCaseBody ) {
		this.findCaseBody = findCaseBody;
	}
}

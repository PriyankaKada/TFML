package com.tmfl.complaintnetwork.findcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Sandeep on 6/2/17.
 */
@Root( name = "soap12:Body", strict = false )
public class FindCaseBody implements Serializable {

	@Element( name = "FindCase", required = false )
	FindCaseData reqData;

	public FindCaseData getReqData() {
		return reqData;
	}

	public void setReqData( FindCaseData reqData ) {
		this.reqData = reqData;
	}
}

package com.tmfl.complaintnetwork.createcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "soap12:Body", strict = false )
public class CreateCaseReqBody {

	@Element( name = "CreateCase_CustOne", required = false )
	public CreateCaseReqData createCaseReqData;

	public CreateCaseReqData getCreateCaseReqData() {
		return createCaseReqData;
	}

	public void setCreateCaseReqData( CreateCaseReqData createCaseReqData ) {
		this.createCaseReqData = createCaseReqData;
	}

}

package com.tfml.model.soapModel.preClosureRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by webwerks on 1/10/16.
 */
@Root( name = "soapenv:Body", strict = false )
public class ReqBody {
	@Element( name = "urn:Z_TERMINALDUES", required = false )
	ReqData reqData;

	public ReqData getReqData() {
		return reqData;
	}

	public void setReqData( ReqData reqData ) {
		this.reqData = reqData;
	}
}


package com.tmfl.model.soapModel.preClosureRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by webwerks on 1/10/16.
 */
@Root( name = "soapenv:Envelope" )
@NamespaceList( {
		@Namespace( prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/" ),
		@Namespace( prefix = "urn", reference = "urn:sap-com:document:sap:rfc:functions" )
} )
public class RequestEnvelope {
	@Element( name = "soapenv:Body", required = false )
	ReqBody reqBody;

	public ReqBody getReqBody() {
		return reqBody;
	}

	public void setReqBody( ReqBody reqBody ) {
		this.reqBody = reqBody;
	}

}

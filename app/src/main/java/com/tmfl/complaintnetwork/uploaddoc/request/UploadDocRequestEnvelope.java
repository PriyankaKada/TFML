package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "soap12:Envelope" )
@NamespaceList( {
		@Namespace( prefix = "xsi", reference = "http://www.w3.org/2001/XMLSchema-instance" ),
		@Namespace( prefix = "xsd", reference = "http://www.w3.org/2001/XMLSchema" ),
		@Namespace( prefix = "soap12", reference = "http://www.w3.org/2003/05/soap-envelope" )
} )

public class UploadDocRequestEnvelope {

	@Element( name = "soap12:Body", required = false )
	public UploadDocReqBody uploadDocReqBody;

	public UploadDocReqBody getUploadDocReqBody() {
		return uploadDocReqBody;
	}

	public void setUploadDocReqBody( UploadDocReqBody uploadDocReqBody ) {
		this.uploadDocReqBody = uploadDocReqBody;
	}
}

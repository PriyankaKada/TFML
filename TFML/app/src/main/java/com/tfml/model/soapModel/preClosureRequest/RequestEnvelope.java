package com.tfml.model.soapModel.preClosureRequest;

import com.tfml.model.soapModel.request.*;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

/**
 * Created by webwerks on 1/10/16.
 */
@Root(name = "soapenv:Envelope")
@NamespaceList({
        @Namespace( prefix = "soapenv", reference = "http://schemas.xmlsoap.org/soap/envelope/"),
        @Namespace( prefix = "urn", reference = "urn:sap-com:document:sap:rfc:functions")
})
public class RequestEnvelope {
    @Element(name = "soapenv:Body", required = false)
    com.tfml.model.soapModel.request.ReqBody reqBody;


    public com.tfml.model.soapModel.request.ReqBody getReqBody() {
        return reqBody;
    }

    public void setReqBody(com.tfml.model.soapModel.request.ReqBody reqBody) {
        this.reqBody = reqBody;
    }
}

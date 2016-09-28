package com.tfml.model.soapModel.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Pravin Borate on 23/9/16.
 */

@Root(name = "soapenv:Body", strict = false)
public class ReqBody {

    @Element(name = "urn:ZCIS",required = false)
    ReqData reqData;

    public ReqData getReqData() {
        return reqData;
    }

    public void setReqData(ReqData reqData) {
        this.reqData = reqData;
    }
}

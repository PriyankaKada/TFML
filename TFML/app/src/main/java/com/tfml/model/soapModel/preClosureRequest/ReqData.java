package com.tfml.model.soapModel.preClosureRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by webwerks on 1/10/16.
 */

@Root(name = "urn:Z_TERMINALDUES", strict = false)
public class ReqData {
    @Element(name = "CONTRACTNO", required = false)
    String contactId;

    @Element(name = "REQDATE", required = false)
    String reqDate;

    @Element(name = "ADJUST_SD", required = false)
    String adustSd;


    public String getAdustSd() {
        return adustSd;
    }

    public void setAdustSd(String adustSd) {
        this.adustSd = adustSd;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

}

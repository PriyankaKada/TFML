package com.tfml.model.soapModel.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Pravin Borate on 23/9/16.
 */

@Root(name = "urn:ZCIS", strict = false)
public class ReqData {

    @Element(name = "CONTRACTNO", required = false)
    String contactId;

    @Element(name = "REQDATE", required = false)
    String REQDATE;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getREQDATE() {
        return REQDATE;
    }

    public void setREQDATE(String REQDATE) {
        this.REQDATE = REQDATE;
    }
}

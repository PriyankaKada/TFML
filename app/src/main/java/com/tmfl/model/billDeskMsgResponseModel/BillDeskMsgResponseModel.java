package com.tmfl.model.billDeskMsgResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webworks on 4/1/17.
 */

public class BillDeskMsgResponseModel {
    @SerializedName( "msg" )
    @Expose
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

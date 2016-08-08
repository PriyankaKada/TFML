package com.tfml.model.QuickcallResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 5/8/16.
 */

public class Error {
    @SerializedName("mobile_no")
    @Expose
    private List<String> mobileNo = new ArrayList<String>();

    /**
     *
     * @return
     * The mobileNo
     */
    public List<String> getMobileNo() {
        return mobileNo;
    }

    /**
     *
     * @param mobileNo
     * The mobile_no
     */
    public void setMobileNo(List<String> mobileNo) {
        this.mobileNo = mobileNo;
    }
}

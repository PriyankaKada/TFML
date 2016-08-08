package com.tfml.model.referFriendResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tfml.model.applyLoanResponseModel.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 1/8/16.
 */

public class ReferFriendResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errors")
    @Expose
    private List<String> errors = new ArrayList<String>();

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The errors
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


}

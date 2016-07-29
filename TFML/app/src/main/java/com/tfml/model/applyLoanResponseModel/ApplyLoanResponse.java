package com.tfml.model.applyLoanResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 29/7/16.
 */

public class ApplyLoanResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errors")
    @Expose
    private Errors errors;

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
    public Errors getErrors() {
        return errors;
    }

    /**
     *
     * @param errors
     * The errors
     */
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

}

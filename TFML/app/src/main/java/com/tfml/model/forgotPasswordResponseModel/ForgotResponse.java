package com.tfml.model.forgotPasswordResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 4/10/16.
 */

public class ForgotResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}

package com.tfml.model.preClosurePdfResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 3/10/16.
 */

public class PreClosureStmtPdfResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("filepath")
    @Expose
    private String filepath;

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
     * The filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     *
     * @param filepath
     * The filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

}

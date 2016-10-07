package com.tfml.model.uploadRcResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

/**
 * Created by webwerks on 1/9/16.
 */

public class RcUploadDataInputModel implements Serializable{
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("contract_no")
    @Expose
    private String contractNo;
    @SerializedName("rc_no")
    @Expose
    private String rcNo;
    @SerializedName("image")
    @Expose
    private File image;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getRcNo() {
        return rcNo;
    }

    public void setRcNo(String rcNo) {
        this.rcNo = rcNo;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    /*@Override
    public String toString() {
        return "RcUploadDataInputModel{" +
                "userId='" + userId + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", rcNo='" + rcNo + '\'' +
                ", image=" + image +
                '}';
    }*/
}

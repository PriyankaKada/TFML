package com.tfml.model.forgotPasswordResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 4/10/16.
 */

public class ForgotInputModel {
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    @SerializedName("user_id")
    String UserID;

}

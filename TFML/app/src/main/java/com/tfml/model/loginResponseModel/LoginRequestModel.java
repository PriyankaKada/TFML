package com.tfml.model.loginResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 2/10/16.
 */

public class LoginRequestModel {
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("password")
    public String password;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}

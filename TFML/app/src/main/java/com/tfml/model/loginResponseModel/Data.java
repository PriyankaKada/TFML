package com.tfml.model.loginResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 2/10/16.
 */

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("usr_type")
    @Expose
    private String usrType;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("mname")
    @Expose
    private Object mname;
    @SerializedName("lname")
    @Expose
    private Object lname;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private Object address2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincd")
    @Expose
    private String pincd;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("res_phone")
    @Expose
    private Object resPhone;
    @SerializedName("off_phone")
    @Expose
    private Object offPhone;
    @SerializedName("pwd")
    @Expose
    private String pwd;
    @SerializedName("api_token")
    @Expose
    private String apiToken;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsrType() {
        return usrType;
    }

    public void setUsrType(String usrType) {
        this.usrType = usrType;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Object getMname() {
        return mname;
    }

    public void setMname(Object mname) {
        this.mname = mname;
    }

    public Object getLname() {
        return lname;
    }

    public void setLname(Object lname) {
        this.lname = lname;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincd() {
        return pincd;
    }

    public void setPincd(String pincd) {
        this.pincd = pincd;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getResPhone() {
        return resPhone;
    }

    public void setResPhone(Object resPhone) {
        this.resPhone = resPhone;
    }

    public Object getOffPhone() {
        return offPhone;
    }

    public void setOffPhone(Object offPhone) {
        this.offPhone = offPhone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}

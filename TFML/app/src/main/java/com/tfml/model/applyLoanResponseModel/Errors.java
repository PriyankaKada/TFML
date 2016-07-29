package com.tfml.model.applyLoanResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 29/7/16.
 */

public class Errors {

    @SerializedName("first_name")
    @Expose
    private  List<String> firstName=new ArrayList<String>();

    @SerializedName("last_name")
    @Expose
    private  List<String> lastName=new ArrayList<String>();

    @SerializedName("mobile_number")
    @Expose
    private  List<String> mobileNumber=new ArrayList<String>();

    @SerializedName("landline_number")
    @Expose
    private  List<String> landlineNumber=new ArrayList<String>();

    @SerializedName("product_id")
    @Expose
    private List<String>productId=new ArrayList<String>();

    @SerializedName("branch_state")
    @Expose
    private List<String>branchState=new ArrayList<String>();

    @SerializedName("branch_city")
    @Expose
    private List<String> branchCity=new ArrayList<String>();

    @SerializedName("branch")
    @Expose
    private List<String>  branch=new ArrayList<String>();

    @SerializedName("email_address")
    @Expose
    private List<String>  emailAddress=new ArrayList<String>();

    @SerializedName("state")
    @Expose
    private List<String>  state=new ArrayList<String>();

    @SerializedName("city")
    @Expose
    private List<String>  city=new ArrayList<String>();

    @SerializedName("pincode")
    @Expose
    private List<String>  pinCode=new ArrayList<String>();

    @SerializedName("lead_type")
    @Expose
    private List<String>  leadType=new ArrayList<String>();

    @SerializedName("organisation_name")
    @Expose
    private List<String>  organisatioName=new ArrayList<String>();

    @SerializedName("vehicle_type")
    @Expose
    private List<String>  vehicleType=new ArrayList<String>();

    public List<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(List<String> firstName) {
        this.firstName = firstName;
    }

    public List<String> getLastName() {
        return lastName;
    }

    public void setLastName(List<String> lastName) {
        this.lastName = lastName;
    }

    public List<String> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(List<String> mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<String> getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(List<String> landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public List<String> getProductId() {
        return productId;
    }

    public void setProductId(List<String> productId) {
        this.productId = productId;
    }

    public List<String> getBranchState() {
        return branchState;
    }

    public void setBranchState(List<String> branchState) {
        this.branchState = branchState;
    }

    public List<String> getBranchCity() {
        return branchCity;
    }

    public void setBranchCity(List<String> branchCity) {
        this.branchCity = branchCity;
    }

    public List<String> getBranch() {
        return branch;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public List<String> getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(List<String> emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<String> getState() {
        return state;
    }

    public void setState(List<String> state) {
        this.state = state;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }

    public List<String> getPinCode() {
        return pinCode;
    }

    public void setPinCode(List<String> pinCode) {
        this.pinCode = pinCode;
    }

    public List<String> getLeadType() {
        return leadType;
    }

    public void setLeadType(List<String> leadType) {
        this.leadType = leadType;
    }

    public List<String> getOrganisatioName() {
        return organisatioName;
    }

    public void setOrganisatioName(List<String> organisatioName) {
        this.organisatioName = organisatioName;
    }

    public List<String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(List<String> vehicleType) {
        this.vehicleType = vehicleType;
    }





}

package com.tmfl.model.referFriendResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 1/8/16.
 */

public class ReferFriendInputModel {
	@SerializedName( "first_name" )
	@Expose
	private String firstName;
	@SerializedName( "last_name" )
	@Expose
	private String lastName;
	@SerializedName( "mobile_number" )
	@Expose()
	private String mobileNumber;
	@SerializedName( "landline_number" )
	@Expose
	private String landlineNumber;
	@SerializedName( "product_id" )
	@Expose
	private String productId;

	@SerializedName( "branch_state" )
	@Expose()
	private String branchState;

	@SerializedName( "branch_city" )
	@Expose()
	private String branchCity;
	@SerializedName( "branch" )
	@Expose()
	private String branch;
	@SerializedName( "email_address" )
	@Expose
	private String emailAddress;
	@SerializedName( "state" )
	@Expose
	private String state;
	@SerializedName( "city" )
	@Expose
	private String city;
	@SerializedName( "pincode" )
	@Expose
	private String pincode;
	@SerializedName( "lead_type" )
	@Expose
	private String leadType;
	@SerializedName( "organisation_name" )
	@Expose
	private String organisationName;
	@SerializedName( "vehicle_type" )
	private String vehicalType;
	@SerializedName( "refered_by" )
	@Expose
	private String referedBy;
	@SerializedName( "user_id" )
	@Expose
	private String userId;
	@SerializedName( "offer_id" )
	private String offerId;

	public String getUserId() {
		return userId;
	}

	public void setUserId( String userId ) {
		this.userId = userId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId( String offerId ) {
		this.offerId = offerId;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName( String organisationName ) {
		this.organisationName = organisationName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber( String mobileNumber ) {
		this.mobileNumber = mobileNumber;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber( String landlineNumber ) {
		this.landlineNumber = landlineNumber;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId( String productId ) {
		this.productId = productId;
	}

	public String getBranchState() {
		return branchState;
	}

	public void setBranchState( String branchState ) {
		this.branchState = branchState;
	}

	public String getBranchCity() {
		return branchCity;
	}

	public void setBranchCity( String branchCity ) {
		this.branchCity = branchCity;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch( String branch ) {
		this.branch = branch;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress( String emailAddress ) {
		this.emailAddress = emailAddress;
	}

	public String getState() {
		return state;
	}

	public void setState( String state ) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity( String city ) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode( String pincode ) {
		this.pincode = pincode;
	}

	public String getLeadType() {
		return leadType;
	}

	public void setLeadType( String leadType ) {
		this.leadType = leadType;
	}

	public String getVehicalType() {
		return vehicalType;
	}

	public void setVehicalType( String vehicalType ) {
		this.vehicalType = vehicalType;
	}

	public String getReferedBy() {
		return referedBy;
	}

	public void setReferedBy( String referedBy ) {
		this.referedBy = referedBy;

	}
}

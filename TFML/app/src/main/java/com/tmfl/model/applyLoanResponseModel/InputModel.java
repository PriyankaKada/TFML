package com.tmfl.model.applyLoanResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 1/8/16.
 */

public class InputModel {
	@SerializedName( "first_name" )
	@Expose
	private String firstName;
	@SerializedName( "last_name" )
	@Expose
	private String lastName;
	@SerializedName( "mobile_number" )
	@Expose()
	private String mobileNumber;
	@SerializedName( "product_id" )
	@Expose
	private String productId;
	@SerializedName( "branch_state" )
	@Expose()
	private String branchState;
	@SerializedName( "branch_city" )
	@Expose()
	private String branchCity;
	@SerializedName( "lead_type" )
	@Expose
	private String leadType;
	@SerializedName( "organisation_name" )
	@Expose
	private String organisationName;
	@SerializedName( "vehicle_type" )
	private String vehicalType;
	@SerializedName( "user_id" )
	private String userId;
	@SerializedName( "offer_id" )
	private String offerId;

	@Override
	public String toString() {
		return "InputModel{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", mobileNumber='" + mobileNumber + '\'' +
				", productId='" + productId + '\'' +
				", branchState='" + branchState + '\'' +
				", branchCity='" + branchCity + '\'' +
				", leadType='" + leadType + '\'' +
				", organisationName='" + organisationName + '\'' +
				", vehicalType='" + vehicalType + '\'' +
				", userId='" + userId + '\'' +
				", offerId='" + offerId + '\'' +
				'}';
	}

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


}

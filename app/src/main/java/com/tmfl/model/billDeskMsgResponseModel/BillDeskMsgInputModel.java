package com.tmfl.model.billDeskMsgResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webworks on 4/1/17.
 */

public class BillDeskMsgInputModel {
	@SerializedName( "contracts" )
	public String contracts;
	@SerializedName( "customer_id" )
	public String customer_id;
	@SerializedName( "mobile_no" )
	public String mobile_no;

	@SerializedName( "api_token" )
	public String api_token;

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id( String customer_id ) {
		this.customer_id = customer_id;
	}

	public String getContracts() {
		return contracts;
	}

	public void setContracts( String contracts ) {
		this.contracts = contracts;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no( String mobile_no ) {
		this.mobile_no = mobile_no;
	}

	public String getApi_token() {
		return api_token;
	}

	public void setApi_token( String api_token ) {
		this.api_token = api_token;
	}

	@Override
	public String toString() {
		return "BillDeskMsgInputModel{" +
				"api_token='" + api_token + '\'' +
				", contracts='" + contracts + '\'' +
				", customer_id='" + customer_id + '\'' +
				", mobile_no='" + mobile_no + '\'' +
				'}';
	}
}

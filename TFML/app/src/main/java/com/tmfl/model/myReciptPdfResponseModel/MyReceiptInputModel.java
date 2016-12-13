package com.tmfl.model.myReciptPdfResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 10/10/16.
 */

public class MyReceiptInputModel {
	@SerializedName( "contract_no" )
	public String contract_no;
	@SerializedName( "api_token" )
	public String api_token;
	@SerializedName( "request_date" )
	public String request_date;
	@SerializedName( "receipt_no" )
	public String receipt_no;

	public String getRequest_date() {
		return request_date;
	}

	public void setRequest_date( String request_date ) {
		this.request_date = request_date;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no( String contract_no ) {
		this.contract_no = contract_no;
	}

	public String getApi_token() {
		return api_token;
	}

	public void setApi_token( String api_token ) {
		this.api_token = api_token;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no( String receipt_no ) {
		this.receipt_no = receipt_no;
	}


}

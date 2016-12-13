package com.tmfl.model.preClosurePdfResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 3/10/16.
 */

public class PreClosureInputModel {
	@SerializedName( "contract_no" )
	public String contractNo;
	@SerializedName( "api_token" )
	public String apiToken;
	@SerializedName( "request_date" )
	public String requestDate;

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate( String requestDate ) {
		this.requestDate = requestDate;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken( String apiToken ) {
		this.apiToken = apiToken;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo( String contractNo ) {
		this.contractNo = contractNo;
	}
}

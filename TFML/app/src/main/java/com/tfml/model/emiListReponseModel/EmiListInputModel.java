package com.tfml.model.emiListReponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 6/10/16.
 */

public class EmiListInputModel {
	@SerializedName( "contract_no" )
	@Expose
	public String contractNo;
	@SerializedName( "api_token" )
	@Expose
	public String apiToken;

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

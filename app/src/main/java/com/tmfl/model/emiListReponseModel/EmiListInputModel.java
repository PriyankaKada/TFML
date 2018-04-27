package com.tmfl.model.emiListReponseModel;

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
	@SerializedName( "contract_type" )
	@Expose
	public int contract_type;

	public int getContract_type() {
		return contract_type;
	}

	public void setContract_type(int contract_type) {
		this.contract_type = contract_type;
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

	@Override
	public String toString() {
		return "EmiListInputModel{" +
				"contractNo='" + contractNo + '\'' +
				", apiToken='" + apiToken + '\'' +
				'}';
	}
}

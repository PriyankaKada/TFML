package com.tfml.model.ContractResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 1/10/16.
 */

public class ContractsResponseModel {


	@SerializedName( "data" )
	@Expose
	private ContractsDataModel data;

	/**
	 * @return The data
	 */
	public ContractsDataModel getData() {
		return data;
	}

	/**
	 * @param data The data
	 */
	public void setData( ContractsDataModel data ) {
		this.data = data;
	}
}

package com.tfml.model.ContractResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 1/10/16.
 */

public class ActiveContractsModel {


	@SerializedName( "contracts" )
	@Expose
	private List< ContractModel > contracts = new ArrayList< ContractModel >();
	@SerializedName( "count" )
	@Expose
	private Integer count;

	/**
	 * @return The contracts
	 */
	public List< ContractModel > getContracts() {
		return contracts;
	}

	/**
	 * @param contracts The contracts
	 */
	public void setContracts( List< ContractModel > contracts ) {
		this.contracts = contracts;
	}

	/**
	 * @return The count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count The count
	 */
	public void setCount( Integer count ) {
		this.count = count;
	}
}

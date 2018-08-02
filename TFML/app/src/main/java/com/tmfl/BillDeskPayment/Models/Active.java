package com.tmfl.BillDeskPayment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by webwerks on 2/1/17.
 */
public class Active {
	@SerializedName("contracts")
	@Expose
	private List<Contract> contracts = null;
	@SerializedName("count")
	@Expose
	private Integer count;

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}

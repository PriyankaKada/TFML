package com.tmfl.BillDeskPayment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 2/1/17.
 */
public class Example
{
	@SerializedName("data")
	@Expose
	private Data data;

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}

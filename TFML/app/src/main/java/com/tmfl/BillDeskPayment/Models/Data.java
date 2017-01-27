package com.tmfl.BillDeskPayment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 2/1/17.
 */
public class Data {

	@SerializedName( "total" )
	@Expose
	private Integer    total;
	@SerializedName( "active" )
	@Expose
	private Active     active;
	@SerializedName( "terminated" )
	@Expose
	private Terminated terminated;

	public Integer getTotal() {
		return total;
	}

	public void setTotal( Integer total ) {
		this.total = total;
	}

	public Active getActive() {
		return active;
	}

	public void setActive( Active active ) {
		this.active = active;
	}

	public Terminated getTerminated() {
		return terminated;
	}

	public void setTerminated( Terminated terminated ) {
		this.terminated = terminated;
	}
}

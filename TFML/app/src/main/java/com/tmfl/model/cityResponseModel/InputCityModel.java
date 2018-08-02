package com.tmfl.model.cityResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 24/8/16.
 */

public class InputCityModel {
	@SerializedName( "state_id" )
	@Expose
	private String stateId;

	public String getStateId() {
		return stateId;
	}

	public void setStateId( String stateId ) {
		this.stateId = stateId;
	}


}

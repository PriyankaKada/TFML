package com.tmfl.model.branchResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 24/8/16.
 */

public class InputBranchModel {
	@SerializedName( "city_id" )
	@Expose
	private String cityId;

	public String getCityId() {
		return cityId;
	}

	public void setCityId( String cityId ) {
		this.cityId = cityId;
	}

}

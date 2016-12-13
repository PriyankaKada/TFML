package com.tmfl.model.branchResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 13/9/16.
 */

public class InputBranchState {

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

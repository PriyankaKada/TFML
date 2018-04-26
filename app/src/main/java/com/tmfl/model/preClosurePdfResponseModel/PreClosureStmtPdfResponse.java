package com.tmfl.model.preClosurePdfResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 3/10/16.
 */

public class PreClosureStmtPdfResponse extends GenericResponse {
	@SerializedName( "filepath" )
	@Expose
	private String filepath;

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath( String filepath ) {
		this.filepath = filepath;
	}
}

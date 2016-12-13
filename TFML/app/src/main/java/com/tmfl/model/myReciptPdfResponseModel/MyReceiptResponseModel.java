package com.tmfl.model.myReciptPdfResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 10/10/16.
 */

public class MyReceiptResponseModel extends GenericResponse {
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

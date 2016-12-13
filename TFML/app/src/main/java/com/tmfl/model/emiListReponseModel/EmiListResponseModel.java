package com.tmfl.model.emiListReponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 6/10/16.
 */

public class EmiListResponseModel extends GenericResponse {

	@SerializedName( "data" )
	@Expose
	private List< Datum > data = new ArrayList< Datum >();

	/**
	 *
	 * @return
	 * The status
	 */

	/**
	 * @return The data
	 */
	public List< Datum > getData() {
		return data;
	}

	/**
	 * @param data The data
	 */
	public void setData( List< Datum > data ) {
		this.data = data;
	}
}

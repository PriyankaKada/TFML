package com.tmfl.model.cityResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 25/8/16.
 */

public class CityResponseModel {
	@Expose
	private String id;
	@SerializedName( "name" )
	@Expose
	private String name;

	/**
	 * @return The id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id
	 */
	public void setId( String id ) {
		this.id = id;
	}

	/**
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name
	 */
	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}

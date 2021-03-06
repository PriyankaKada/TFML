package com.tmfl.model.branchResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 24/8/16.
 */

public class BranchResponseModel {

	@SerializedName( "terr_territoryid" )
	private String terrTerritoryid;
	@SerializedName( "terr_caption" )
	private String terrCaption;
	@SerializedName( "terr_latitude" )
	private String terrLatitude;
	@SerializedName( "terr_longitude" )
	private String terrLongitude;
	@SerializedName( "terr_address" )
	private String terrAddress;

	public String getTerrLongitude() {
		return terrLongitude;
	}

	public void setTerrLongitude( String terrLongitude ) {
		this.terrLongitude = terrLongitude;
	}

	public String getTerrLatitude() {
		return terrLatitude;
	}

	public void setTerrLatitude( String terrLatitude ) {
		this.terrLatitude = terrLatitude;
	}

	/**
	 * @return The terrTerritoryid
	 */
	public String getTerrTerritoryid() {
		return terrTerritoryid;
	}

	/**
	 * @param terrTerritoryid The terr_territoryid
	 */
	public void setTerrTerritoryid( String terrTerritoryid ) {
		this.terrTerritoryid = terrTerritoryid;
	}

	/**
	 * @return The terrCaption
	 */
	public String getTerrCaption() {
		return terrCaption;
	}

	/**
	 * @param terrCaption The terr_caption
	 */
	public void setTerrCaption( String terrCaption ) {
		this.terrCaption = terrCaption;
	}

	public String getTerrAddress() {
		return terrAddress;
	}

	public void setTerrAddress( String terrAddress ) {
		this.terrAddress = terrAddress;
	}
}

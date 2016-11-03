package com.tfml.model.bannerResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 29/7/16.
 */

public class BannerlistResponse {
	@SerializedName( "status" )
	@Expose
	private String  status;
	@SerializedName( "banners" )
	@Expose
	private Banners banners;

	/**
	 * @return The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status The status
	 */
	public void setStatus( String status ) {
		this.status = status;
	}

	/**
	 * @return The banners
	 */
	public Banners getBanners() {
		return banners;
	}

	/**
	 * @param banners The banners
	 */
	public void setBanners( Banners banners ) {
		this.banners = banners;
	}

}

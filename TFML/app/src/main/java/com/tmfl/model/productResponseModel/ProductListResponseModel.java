package com.tmfl.model.productResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 23/8/16.
 */

public class ProductListResponseModel {
	@SerializedName( "prod_productid" )
	@Expose
	private String prodProductid;
	@SerializedName( "prod_name" )
	@Expose
	private String prodName;

	/**
	 * @return The prodProductid
	 */
	public String getProdProductid() {
		return prodProductid;
	}

	/**
	 * @param prodProductid The prod_productid
	 */
	public void setProdProductid( String prodProductid ) {
		this.prodProductid = prodProductid;
	}

	/**
	 * @return The prodName
	 */
	public String getProdName() {
		return prodName;
	}

	/**
	 * @param prodName The prod_name
	 */
	public void setProdName( String prodName ) {
		this.prodName = prodName;
	}


}

package com.tfml.model.schemesResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by webwerks on 15/12/16.
 */
public class OfferData {


	@SerializedName( "NEW" )
	@Expose
	private List< NewOfferData >  nEW  = null;
	@SerializedName( "USED" )
	@Expose
	private List< UsedOfferData > uSED = null;

	public List< NewOfferData > getNEW() {
		return nEW;
	}

	public void setNEW( List< NewOfferData > nEW ) {
		this.nEW = nEW;
	}

	public List< UsedOfferData > getUSED() {
		return uSED;
	}

	public void setUSED( List< UsedOfferData > uSED ) {
		this.uSED = uSED;
	}

}

package com.tfml.model.branchResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 24/8/16.
 */

public class BranchResponseModel {

    @SerializedName("terr_territoryid")
    @Expose
    private String terrTerritoryid;
    @SerializedName("terr_caption")
    @Expose
    private String terrCaption;

    /**
     * @return The terrTerritoryid
     */
    public String getTerrTerritoryid() {
        return terrTerritoryid;
    }

    /**
     * @param terrTerritoryid The terr_territoryid
     */
    public void setTerrTerritoryid(String terrTerritoryid) {
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
    public void setTerrCaption(String terrCaption) {
        this.terrCaption = terrCaption;
    }

}

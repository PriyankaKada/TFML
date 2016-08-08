package com.tfml.model.bannerResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 1/8/16.
 */

public class Banners {
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("to")
    @Expose
    private Integer to;

    /**
     *
     * @return
     * The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     * The current_page
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The from
     */
    public Integer getFrom() {
        return from;
    }

    /**
     *
     * @param from
     * The from
     */
    public void setFrom(Integer from) {
        this.from = from;
    }

    /**
     *
     * @return
     * The perPage
     */
    public Integer getPerPage() {
        return perPage;
    }

    /**
     *
     * @param perPage
     * The per_page
     */
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    /**
     *
     * @return
     * The to
     */
    public Integer getTo() {
        return to;
    }

    /**
     *
     * @param to
     * The to
     */
    public void setTo(Integer to) {
        this.to = to;
    }
}

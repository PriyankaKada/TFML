package com.tfml.model.schemesResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by webwerks on 29/7/16.
 */

public class Datum implements Serializable {

	@SerializedName( "id" )
	@Expose
	private Integer id;
	@SerializedName( "title" )
	@Expose
	private String  title;
	@SerializedName( "image" )
	@Expose
	private String  image;
	@SerializedName( "short_description" )
	@Expose
	private String  shortDescription;
	@SerializedName( "status" )
	@Expose
	private Integer status;
	@SerializedName( "created_at" )
	@Expose
	private String  createdAt;
	@SerializedName( "updated_at" )
	@Expose
	private String  updatedAt;
	@SerializedName( "deleted_at" )
	@Expose
	private Object  deletedAt;

	@Override
	public String toString() {
		return title;
	}

	/**
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id The id
	 */
	public void setId( Integer id ) {
		this.id = id;
	}

	/**
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title
	 */
	public void setTitle( String title ) {
		this.title = title;
	}

	/**
	 * @return The image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image The image
	 */
	public void setImage( String image ) {
		this.image = image;
	}

	/**
	 * @return The shortDescription
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * @param shortDescription The short_description
	 */
	public void setShortDescription( String shortDescription ) {
		this.shortDescription = shortDescription;
	}

	/**
	 * @return The status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status The status
	 */
	public void setStatus( Integer status ) {
		this.status = status;
	}

	/**
	 * @return The createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt The created_at
	 */
	public void setCreatedAt( String createdAt ) {
		this.createdAt = createdAt;
	}

	/**
	 * @return The updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt The updated_at
	 */
	public void setUpdatedAt( String updatedAt ) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return The deletedAt
	 */
	public Object getDeletedAt() {
		return deletedAt;
	}

	/**
	 * @param deletedAt The deleted_at
	 */
	public void setDeletedAt( Object deletedAt ) {
		this.deletedAt = deletedAt;
	}
}

package com.tmfl.complaintnetwork.findcase.response;

/**
 * Created by Sandeep on 7/2/17.
 */
public class Case {

	private String Description;

	private String CaseId;

	private String Casestage;

	private String CreatedDate;

	public String getDescription() {
		return Description;
	}

	public void setDescription( String Description ) {
		this.Description = Description;
	}

	public String getCaseId() {
		return CaseId;
	}

	public void setCaseId( String CaseId ) {
		this.CaseId = CaseId;
	}

	public String getCasestage() {
		return Casestage;
	}

	public void setCasestage( String Casestage ) {
		this.Casestage = Casestage;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate( String CreatedDate ) {
		this.CreatedDate = CreatedDate;
	}

	@Override
	public String toString() {
		return "ClassPojo [Description = " + Description + ", CaseId = " + CaseId + ", Casestage = " + Casestage + ", CreatedDate = " + CreatedDate + "]";
	}
}

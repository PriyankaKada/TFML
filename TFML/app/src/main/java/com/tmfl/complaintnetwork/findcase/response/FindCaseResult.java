package com.tmfl.complaintnetwork.findcase.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sandeep on 7/2/17.
 */
public class FindCaseResult {

	private String CaseCount;
	private String Result;
	private String Message;
	@SerializedName( "case" )
	private Object cases;

	public String getCaseCount() {
		return CaseCount;
	}

	public void setCaseCount( String caseCount ) {
		CaseCount = caseCount;
	}

	public String getResult() {
		return Result;
	}

	public void setResult( String Result ) {
		this.Result = Result;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage( String Message ) {
		this.Message = Message;
	}

	public Object getCase() {
		return cases;
	}

	public void setCase( Object cases ) {
		this.cases = cases;
	}

	@Override
	public String toString() {
		return "ClassPojo [Result = " + Result + ", Message = " + Message + ", case = " + cases + "]";
	}
}

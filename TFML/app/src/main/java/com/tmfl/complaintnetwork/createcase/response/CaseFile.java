package com.tmfl.complaintnetwork.createcase.response;

/**
 * Created by Sandeep on 8/2/17.
 */

public class CaseFile {
	private String Result;

	private String caseId;

	private String Message;
	private String Error;

	public String getError() {
		return Error;
	}

	public void setError( String error ) {
		Error = error;
	}

	public String getResult() {
		return Result;
	}

	public void setResult( String Result ) {
		this.Result = Result;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId( String caseId ) {
		this.caseId = caseId;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage( String Message ) {
		this.Message = Message;
	}

	@Override
	public String toString() {
		return "ClassPojo [Result = " + Result + ", caseId = " + caseId + ", Message = " + Message + "]";
	}
}

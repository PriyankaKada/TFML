package com.tmfl.complaintnetwork.findcase.response;

import java.util.List;

/**
 * Created by Sandeep on 7/2/17.
 */
public class FindCaseResult {

	private String Result;

	private String Message;

	private List< Case > cases;

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

	public List< Case > getCase() {
		return cases;
	}

	public void setCase( List< Case > cases ) {
		this.cases = cases;
	}

	@Override
	public String toString() {
		return "ClassPojo [Result = " + Result + ", Message = " + Message + ", case = " + cases +"]" ;
	}
}

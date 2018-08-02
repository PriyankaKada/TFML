package com.tmfl.complaintnetwork.findcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 6/2/17.
 */
@Root( name = "FindCase", strict = false )

@Namespace( reference = "http://tempuri.org/" )
public class FindCaseData {

	@Element( name = "caseid" )
	String caseId;

	@Element( name = "contractno" )
	String contractNo;

	@Element( name = "startdate" )
	String startDate;

	@Element( name = "enddate" )
	String endDate;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId( String caseId ) {
		this.caseId = caseId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo( String contractNo ) {
		this.contractNo = contractNo;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate( String endDate ) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate( String startDate ) {
		this.startDate = startDate;
	}
}

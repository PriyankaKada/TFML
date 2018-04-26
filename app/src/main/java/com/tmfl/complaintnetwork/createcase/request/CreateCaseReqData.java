package com.tmfl.complaintnetwork.createcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "CreateCase_CustOne" )
@Namespace( reference = "http://tempuri.org/" )
public class CreateCaseReqData {

	@Element( name = "contrNo", required = false )
	public String contractNo;

	@Element( name = "caseDesc", required = false )
	public String caseDescription;

	@Element( name = "att1", required = false )
	public FileKeyValuePair attachFiles1;

	@Element( name = "att2", required = false )
	public FileKeyValuePair attachFiles2;

	@Element( name = "att3", required = false )
	public FileKeyValuePair attachFiles3;

	public FileKeyValuePair getAttachFiles3() {
		return attachFiles3;
	}

	public void setAttachFiles3( FileKeyValuePair attachFiles3 ) {
		this.attachFiles3 = attachFiles3;
	}

	public FileKeyValuePair getAttachFiles2() {
		return attachFiles2;
	}

	public void setAttachFiles2( FileKeyValuePair attachFiles2 ) {
		this.attachFiles2 = attachFiles2;
	}

	public FileKeyValuePair getAttachFiles1() {
		return attachFiles1;
	}

	public void setAttachFiles1( FileKeyValuePair attachFiles1 ) {
		this.attachFiles1 = attachFiles1;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription( String caseDescription ) {
		this.caseDescription = caseDescription;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo( String contractNo ) {
		this.contractNo = contractNo;
	}


}

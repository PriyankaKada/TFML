package com.tmfl.complaintnetwork.createcase.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "CreateCase_CustOne" )
public class CreateCaseReqData {

	@Element( name = "contrNo", required = false )
	public String contractNo;

	@Element( name = "caseDesc", required = false )
	public String caseDescription;

	@Element( name = "files" )
	public AttachFiles attachFiles;

	public AttachFiles getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles( AttachFiles attachFiles ) {
		this.attachFiles = attachFiles;
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

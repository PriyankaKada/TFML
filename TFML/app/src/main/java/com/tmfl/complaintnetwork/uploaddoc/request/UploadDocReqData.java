package com.tmfl.complaintnetwork.uploaddoc.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Sandeep on 7/2/17.
 */

@Root( name = "UploadDoc", strict = false )
public class UploadDocReqData {

	@Element( name = "caseID", required = false )
	public String caseId;

	@Element( name = "att1", required = false )
	public FileKeyValuePair attachFiles1;

	@Element( name = "att2", required = false )
	public FileKeyValuePair attachFiles2;

	@Element( name = "att3", required = false )
	public FileKeyValuePair attachFiles3;

	public FileKeyValuePair getAttachFiles1() {
		return attachFiles1;
	}

	public void setAttachFiles1( FileKeyValuePair attachFiles1 ) {
		this.attachFiles1 = attachFiles1;
	}

	public FileKeyValuePair getAttachFiles2() {
		return attachFiles2;
	}

	public void setAttachFiles2( FileKeyValuePair attachFiles2 ) {
		this.attachFiles2 = attachFiles2;
	}

	public FileKeyValuePair getAttachFiles3() {
		return attachFiles3;
	}

	public void setAttachFiles3( FileKeyValuePair attachFiles3 ) {
		this.attachFiles3 = attachFiles3;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId( String caseId ) {
		this.caseId = caseId;
	}

}

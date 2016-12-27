package com.tfml.model.soapModel.preClousreResponse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by webwerks on 1/10/16.
 */
@Root( name = "SOAP:Envelope" )
public class ResponseEnvelope {

	@Element( name = "Header", required = false )
	String header;


	@Element( name = "Body", required = false )
	Body body;


	@Attribute( name = "SOAP", required = false )
	String sOAP;


	public String getHeader() {
		return this.header;
	}

	public void setHeader( String _value ) {
		this.header = _value;
	}


	public Body getBody() {
		return this.body;
	}

	public void setBody( Body _value ) {
		this.body = _value;
	}


	public String getSOAP() {
		return this.sOAP;
	}

	public void setSOAP( String _value ) {
		this.sOAP = _value;
	}


	public static class Body {

		@Element( name = "Z_TERMINALDUES.Response", required = false )
		Z_TERMINALDUESResponse z_TERMINALDUESResponse;


		public Z_TERMINALDUESResponse getZ_TERMINALDUESResponse() {
			return this.z_TERMINALDUESResponse;
		}

		public void setZ_TERMINALDUESResponse( Z_TERMINALDUESResponse _value ) {
			this.z_TERMINALDUESResponse = _value;
		}


	}

	public static class Z_TERMINALDUESResponse {

		@ElementList( name = "I_DTL", required = false )
		List< Item > i_DTL;


		@Attribute( name = "ns0", required = false )
		String ns0;


		public List< Item > getI_DTL() {
			return this.i_DTL;
		}

		public void setI_DTL( List< Item > _value ) {
			this.i_DTL = _value;
		}


		public String getNs0() {
			return this.ns0;
		}

		public void setNs0( String _value ) {
			this.ns0 = _value;
		}


	}

	public static class Item {

		@Element( name = "CONTRACTNO", required = false )
		String cONTRACTNO;


		@Element( name = "DESCP", required = false )
		String dESCP;


		@Element( name = "DUE", required = false )
		String dUE;


		@Element( name = "REC", required = false )
		String rEC;


		@Element( name = "NET", required = false )
		String nET;


		public String getCONTRACTNO() {
			return this.cONTRACTNO;
		}

		public void setCONTRACTNO( String _value ) {
			this.cONTRACTNO = _value;
		}


		public String getDESCP() {
			return this.dESCP;
		}

		public void setDESCP( String _value ) {
			this.dESCP = _value;
		}


		public String getDUE() {
			return this.dUE;
		}

		public void setDUE( String _value ) {
			this.dUE = _value;
		}


		public String getREC() {
			return this.rEC;
		}

		public void setREC( String _value ) {
			this.rEC = _value;
		}


		public String getNET() {
			return this.nET;
		}

		public void setNET( String _value ) {
			this.nET = _value;
		}


	}
}

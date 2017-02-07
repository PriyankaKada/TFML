package com.tmfl.complaintnetwork.findcase;

import com.tmfl.complaintnetwork.findcase.response.Case;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep on 7/2/17.
 */
public class XMLPullParserHandler {
	List< Case > employees;
	private Case   employee;
	private String text;

	public XMLPullParserHandler() {
		employees = new ArrayList<>();
	}

	public List< Case > getEmployees() {
		return employees;
	}

	public List< Case > parse( InputStream is ) {
		XmlPullParserFactory factory = null;
		XmlPullParser        parser  = null;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware( true );
			parser = factory.newPullParser();

			parser.setInput( new StringReader( "<case><CaseId>1804428</CaseId><Casestage>Assigned</Casestage><CreatedDate>2/6/2017 12:51:08 PM</CreatedDate><Description>Test Case Description</Description></case><case><CaseId>1804428</CaseId><Casestage>Assigned</Casestage><CreatedDate>2/6/2017 12:51:08 PM</CreatedDate><Description>Test Case Description</Description></case><Result>1</Result><Message>Cases successfully retrieved.</Message>" ) );

			int eventType = parser.getEventType();
			while ( eventType != XmlPullParser.END_DOCUMENT ) {
				String tagname = parser.getName();
				switch ( eventType ) {
					case XmlPullParser.START_TAG:
						if ( tagname.equalsIgnoreCase( "employee" ) ) {
							// create a new instance of employee
							employee = new Case();
						}

						break;

					case XmlPullParser.TEXT:
						text = parser.getText();

						break;

					case XmlPullParser.END_TAG:
						if ( tagname.equalsIgnoreCase( "case" ) ) {
							// add employee object to list
							employees.add( employee );
						}
						else if ( tagname.equalsIgnoreCase( "caseId" ) ) {
							employee.setCaseId( text );
						}
						else if ( tagname.equalsIgnoreCase( "Casestage" ) ) {
							employee.setCasestage( ( text ) );
						}
						else if ( tagname.equalsIgnoreCase( "CreatedDate" ) ) {
							employee.setCreatedDate( text );
						}
						else if ( tagname.equalsIgnoreCase( "Description" ) ) {
							employee.setDescription( text );
						}

						break;

					default:

						break;
				}
				eventType = parser.next();
			}

		}
		catch ( XmlPullParserException | IOException e ) {
			e.printStackTrace();
		}

		return employees;
	}
}
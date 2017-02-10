package com.tmfl.complaintnetwork;

import com.tmfl.complaintnetwork.createcase.response.CaseFile;
import com.tmfl.complaintnetwork.createcase.response.ParsedResponse;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Sandeep on 8/2/17.
 */
public class XMLPullParser {

	private ParsedResponse parsedResponse;
	private CaseFile       caseFile;
	private String         text;
	private String         response;

	public XMLPullParser( String response ) {
		this.response = response;
		parsedResponse = new ParsedResponse();
	}


	public ParsedResponse parse() {
		XmlPullParserFactory factory = null;
		XmlPullParser        parser  = null;
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware( true );
			parser = factory.newPullParser();

			parser.setInput( new StringReader( response ) );

			int eventType = parser.getEventType();
			while ( eventType != XmlPullParser.END_DOCUMENT ) {
				String tagname = parser.getName();
				switch ( eventType ) {
					case XmlPullParser.START_TAG:
						if ( tagname.equalsIgnoreCase( "case" ) ) {
							// create a new instance of employee
							caseFile = new CaseFile();
						}

						break;

					case XmlPullParser.TEXT:
						text = parser.getText();

						break;

					case XmlPullParser.END_TAG:
						if ( tagname.equalsIgnoreCase( "case" ) ) {
							// add employee object to list
							parsedResponse.setCaseFile( caseFile );
						}
						else if ( tagname.equalsIgnoreCase( "caseId" ) ) {
							caseFile.setCaseId( text );
						}
						else if ( tagname.equalsIgnoreCase( "Message" ) ) {
							caseFile.setMessage( ( text ) );
						}
						else if ( tagname.equalsIgnoreCase( "Result" ) ) {
							caseFile.setResult( text );
						}
						else if ( tagname.equalsIgnoreCase( "Error" ) ) {
							caseFile.setError( text );
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

		return parsedResponse;
	}

}

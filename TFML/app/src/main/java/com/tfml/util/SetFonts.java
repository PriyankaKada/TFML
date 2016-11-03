package com.tfml.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Satyawan on 19-aug-16.
 */
public class SetFonts {


	public static int CASE_LATO_BLACK = 1;

	public static int CASE_LATO_BOLD = 2;

	public static int CASE_LATO_BOLD_ITALIC = 3;

	public static int CASE_LATO_LIGHT = 4;

	public static int CASE_LATO_REGULAR = 5;


	public static void setFonts( Context context, View view, int fontId ) {
		Typeface typeface = null;
		if ( view instanceof TextView ) {
			switch ( fontId ) {
				case 1:
					typeface = typeface.createFromAsset( context.getAssets(), "Lato-Black.ttf" );
					( ( TextView ) view ).setTypeface( typeface );
					break;
				case 2:
					typeface = typeface.createFromAsset( context.getAssets(), "Lato-Bold.ttf" );
					( ( TextView ) view ).setTypeface( typeface );
					break;
				case 3:
					typeface = typeface.createFromAsset( context.getAssets(), "Lato-BoldItalic.ttf" );
					( ( TextView ) view ).setTypeface( typeface );
					break;
				case 4:
					typeface = typeface.createFromAsset( context.getAssets(), "Lato-Light.ttf" );
					( ( TextView ) view ).setTypeface( typeface );
					break;
				case 5:
					typeface = typeface.createFromAsset( context.getAssets(), "Lato-Regular.ttf" );
					( ( TextView ) view ).setTypeface( typeface );
					break;

			}
		}
	}
}

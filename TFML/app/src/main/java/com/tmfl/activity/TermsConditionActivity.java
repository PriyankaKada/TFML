package com.tmfl.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.tmfl.R;

/**
 * Created by Sandeep on 23/1/17.
 */
public class TermsConditionActivity extends BaseActivity {

	private WebView webView;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_terms_cond );

		webView = ( WebView ) findViewById( R.id.webView );
		webView.getSettings().setJavaScriptEnabled( true );

		String terms     = getIntent().getStringExtra( "Terms" );
		String termsCond = null;
		if ( terms.equalsIgnoreCase( "1" ) ) {
			termsCond = "http://staging.php-dev.in:8844/tatamotors/public/Docs/Online_Payment_Terms_Conditions_Legal_27Dec2016.pdf";
		}
		else if ( terms.equalsIgnoreCase( "2" ) ) {
			termsCond = "http://staging.php-dev.in:8844/tatamotors/public/Docs/Privacy_Policy_Legal_27Dec2016.pdf";
		}
		webView.loadUrl( "http://drive.google.com/viewerng/viewer?embedded=true&url=" + termsCond );
	}
}

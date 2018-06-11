package com.tmfl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.adapter.SchemesListAdapter;
import com.tmfl.model.schemesResponseModel.NewOfferData;
import com.tmfl.model.schemesResponseModel.SchemesResponse;
import com.tmfl.model.schemesResponseModel.UsedOfferData;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;
import java.util.List;


public class NewSchemeFragment extends Fragment implements View.OnClickListener {

	private final int NEW_OFFER = 1, USED_OFFER = 2;
	TextView txtNewSchemesHeader, txtNewOffers, txtUsedOffers;
	List< NewOfferData > newOfferList;
	SchemesResponse      response;
	private ListView              lstNewSchemes;
	private List< UsedOfferData > usedOfferList;
WebView mWebView;
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.fragment_new_scheme, container, false );


		mWebView = (WebView) view.findViewById(R.id.webView);
		mWebView.loadUrl("https://www.tmf.co.in/offers");

		// Enable Javascript
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		// Force links and redirects to open in the WebView instead of in a browser
		mWebView.setWebViewClient(new WebViewClient());


		initView( view );

		txtNewOffers.setOnClickListener( this );
		txtUsedOffers.setOnClickListener( this );


		return view;
	}

	private void initView( View view ) {
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();
		response = ( SchemesResponse ) PreferenceHelper.getObject( "Scheme response", SchemesResponse.class );

		newOfferList = new ArrayList<>();
		usedOfferList = new ArrayList<>();
		newOfferList = response.getOfferData().getNEW();
		usedOfferList = response.getOfferData().getUSED();

		txtNewOffers = ( TextView ) view.findViewById( R.id.txtNewOffers );
		txtUsedOffers = ( TextView ) view.findViewById( R.id.txtUsedOffers );

	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
		lstNewSchemes = ( ListView ) view.findViewById( R.id.lst_new_schemes );
		txtNewSchemesHeader = ( TextView ) view.findViewById( R.id.new_schemes_header );
		SetFonts.setFonts( getActivity(), txtNewSchemesHeader, 2 );

		if ( newOfferList != null && newOfferList.size() > 0 ) {
			newOfferLayout();
			lstNewSchemes.setAdapter( new SchemesListAdapter( getActivity(), newOfferList, usedOfferList, NEW_OFFER ) );

		}


	}

	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {
			case R.id.txtNewOffers:

				newOfferLayout();

				if ( newOfferList != null && newOfferList.size() > 0 ) {
					lstNewSchemes.setAdapter( new SchemesListAdapter( getActivity(), newOfferList, usedOfferList, NEW_OFFER ) );
				}

				break;

			case R.id.txtUsedOffers:

				usedOfferLayout();
				if ( usedOfferList != null && usedOfferList.size() > 0 ) {
					lstNewSchemes.setAdapter( new SchemesListAdapter( getActivity(), newOfferList, usedOfferList, USED_OFFER ) );
				}

				break;
		}

	}

	public void newOfferLayout() {

		txtNewOffers.setBackgroundColor( getResources().getColor( R.color.white ) );
		txtUsedOffers.setBackgroundColor( getResources().getColor( R.color.tab_bg ) );
		txtNewOffers.setTextColor( getResources().getColor( R.color.tab_bg ) );
		txtUsedOffers.setTextColor( getResources().getColor( R.color.white ) );

	}

	public void usedOfferLayout() {

		txtNewOffers.setBackgroundColor( getResources().getColor( R.color.tab_bg ) );
		txtUsedOffers.setBackgroundColor( getResources().getColor( R.color.white ) );
		txtNewOffers.setTextColor( getResources().getColor( R.color.white ) );
		txtUsedOffers.setTextColor( getResources().getColor( R.color.tab_bg ) );

	}
}


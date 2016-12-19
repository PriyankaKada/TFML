package com.tmfl.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmfl.R;
import com.tmfl.adapter.SchemesPagerAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.fragment.ApplyLoanFragment;
import com.tmfl.fragment.NewSchemeFragment;
import com.tmfl.fragment.ReferFriendFragment;
import com.tmfl.model.schemesResponseModel.NewOfferData;
import com.tmfl.model.schemesResponseModel.SchemesResponse;
import com.tmfl.model.schemesResponseModel.UsedOfferData;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchemesActivity extends BaseActivity implements View.OnClickListener {
	Toolbar   toolbarschemes;
	TabLayout tabLayout;
	View      view1, view2, view3;
	TmflApi   tmflApi;
	ViewPager viewPager;
	Bundle    bundle1;
	private TextView  txtschemestitle;
	private ImageView imgtoolbarhome, imgSocial;
	private List< NewOfferData > newOfferList;
	private LinearLayout         llSchemes, llApplyLoan, linReferFriend;
	private SchemesPagerAdapter   adapter;
	private List< UsedOfferData > usedOfferList;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_schemes );

		newOfferList = new ArrayList<>();
		usedOfferList = new ArrayList<>();
		toolbarschemes = ( Toolbar ) findViewById( R.id.toolbar_schemes );
		txtschemestitle = ( TextView ) findViewById( R.id.txt_schemes_title );
		imgtoolbarhome = ( ImageView ) findViewById( R.id.img_toolbar_home );
		imgSocial = ( ImageView ) findViewById( R.id.img_social );
		llApplyLoan = ( LinearLayout ) findViewById( R.id.llApplyLoan );
		llSchemes = ( LinearLayout ) findViewById( R.id.llSchemes );
		linReferFriend = ( LinearLayout ) findViewById( R.id.linReferFriend );

		llApplyLoan.setOnClickListener( this );
		llSchemes.setOnClickListener( this );
		linReferFriend.setOnClickListener( this );
		imgtoolbarhome.setOnClickListener( this );
		imgSocial.setOnClickListener( this );

		view1 = findViewById( R.id.view1 );
		view2 = findViewById( R.id.view2 );
		view3 = findViewById( R.id.view3 );

		bundle1 = getIntent().getExtras();
		/*String isLoggedIn = bundle1.getString( "LOGGED_IN" );
		if ( isLoggedIn.equalsIgnoreCase( "true" ) ) {
			view3.setVisibility( View.VISIBLE );
			linReferFriend.setVisibility( View.VISIBLE );
		}
		else {
			view3.setVisibility( View.GONE );
			linReferFriend.setVisibility( View.GONE );
		}*/

		tmflApi = ApiService.getInstance().call();

		if ( CommonUtils.isNetworkAvailable( SchemesActivity.this ) ) {
			CommonUtils.showProgressDialog( this, "Please wait..." );
			callSchemesResponseModel();
		}
		else {
			Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}

		viewPager = ( ViewPager ) findViewById( R.id.pager );

		SetFonts.setFonts( this, txtschemestitle, 2 );
	}

	private void setupViewPager( ViewPager viewPager ) {
		List< Fragment > fragList = new ArrayList<>();
		System.out.println( "------------dgdsgdf----------->" + newOfferList );

		final Fragment newSchemeFragment   = new NewSchemeFragment();
		final Fragment applyLoanFragment   = new ApplyLoanFragment();
		final Fragment referFriendFragment = new ReferFriendFragment();

		fragList.add( newSchemeFragment );
		fragList.add( applyLoanFragment );

		String loggedIn = bundle1.getString( "LOGGED_IN" );
		if ( loggedIn.equalsIgnoreCase( "true" ) ) {
			fragList.add( referFriendFragment );
		}

		Log.d( "fraglistsize", String.valueOf( fragList.size() ) );
		adapter = new SchemesPagerAdapter( getSupportFragmentManager(), fragList );
		viewPager.setAdapter( adapter );
		viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

			}

			@Override
			public void onPageSelected( int position ) {
				setTitle( getPageTitle( position ) );

				switch ( position ) {

					case 0:
						view1.setVisibility( View.VISIBLE );
						view2.setVisibility( View.INVISIBLE );
						view3.setVisibility( View.INVISIBLE );
//						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;

					case 1:
						view1.setVisibility( View.INVISIBLE );
						view2.setVisibility( View.VISIBLE );
						view3.setVisibility( View.INVISIBLE );
//						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;

					case 3:

						view1.setVisibility( View.INVISIBLE );
						view2.setVisibility( View.INVISIBLE );
						String isLoggedIn = bundle1.getString( "LOGGED_IN" );
						if ( isLoggedIn.equalsIgnoreCase( "true" ) ) {
							view3.setVisibility( View.VISIBLE );
						}
						else {
							view3.setVisibility( View.INVISIBLE );
						}

						break;

					default:
//						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
//						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
					/*	view1.setVisibility( View.INVISIBLE );
						view2.setVisibility( View.INVISIBLE );
						view3.setVisibility( View.VISIBLE );*/
//						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged( int state ) {

			}
		} );

		String myTabselected = bundle1.getString( "TAB_SELECTED" );

		if ( myTabselected.equalsIgnoreCase( Constant.ISSCHEMASTABSELECT ) ) {
			viewPager.setCurrentItem( 0 );
			view1.setVisibility( View.VISIBLE );
			view2.setVisibility( View.INVISIBLE );
			view3.setVisibility( View.INVISIBLE );
		}
		else if ( myTabselected.equalsIgnoreCase( Constant.ISAPPLYLOANSELECT ) ) {
			viewPager.setCurrentItem( 1 );
			view1.setVisibility( View.INVISIBLE );
			view2.setVisibility( View.VISIBLE );
			view3.setVisibility( View.INVISIBLE );
		}
		else if ( myTabselected.equalsIgnoreCase( Constant.ISREFERFREINDSELECT ) ) {

			String isLoggedIn = bundle1.getString( "LOGGED_IN" );
			if ( isLoggedIn.equalsIgnoreCase( "true" ) ) {
				view3.setVisibility( View.VISIBLE );
			}
			else {
				view3.setVisibility( View.GONE );
			}
			view2.setVisibility( View.INVISIBLE );
			view1.setVisibility( View.INVISIBLE );

			viewPager.setCurrentItem( 2 );
		}
	}

	public void setTitle( String name ) {
		txtschemestitle.setText( name );
	}

	public String getPageTitle( int position ) {
		switch ( position ) {
			case 0:
				return "New Offers";
			case 1:
				return "Apply Loans";
			case 2:
				return "Refer Friend";
			default:
				return "";
		}
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.img_toolbar_home:

				if ( PreferenceHelper.getBoolean( "SaveLogin" ) ) {
					startActivity( new Intent( SchemesActivity.this, ContractActivity.class ) );
					finish();
				}
				else {
					startActivity( new Intent( SchemesActivity.this, BannerActivity.class ) );
					finish();
				}

				break;
			case R.id.img_social:
				socialDialog();

				break;

			case R.id.llSchemes:

				view1.setVisibility( View.VISIBLE );
				view2.setVisibility( View.INVISIBLE );
				view3.setVisibility( View.INVISIBLE );
				viewPager.setCurrentItem( 0 );

				break;

			case R.id.llApplyLoan:

				view2.setVisibility( View.VISIBLE );
				view1.setVisibility( View.INVISIBLE );
				view3.setVisibility( View.INVISIBLE );
				viewPager.setCurrentItem( 1 );

				break;

			case R.id.linReferFriend:

				String isLoggedIn = bundle1.getString( "LOGGED_IN" );
				if ( isLoggedIn.equalsIgnoreCase( "true" ) ) {
					view3.setVisibility( View.VISIBLE );
				}
				else {
					view3.setVisibility( View.GONE );
				}
				view2.setVisibility( View.INVISIBLE );
				view1.setVisibility( View.INVISIBLE );
				viewPager.setCurrentItem( 2 );

				break;
		}
	}

	public void socialDialog() {
		imgSocial.setVisibility( View.INVISIBLE );
		final Dialog socialDialog = new Dialog( SchemesActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar );
		socialDialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
		socialDialog.setContentView( R.layout.dialog_social );
		WindowManager.LayoutParams params = socialDialog.getWindow().getAttributes();
		params.y = 5;
		params.x = 5;
		params.gravity = Gravity.TOP | Gravity.RIGHT;
		socialDialog.getWindow().setAttributes( params );
		socialDialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
		socialDialog.setCancelable( true );

		final ImageView imgMessage   = ( ImageView ) socialDialog.findViewById( R.id.imgmsg );
		final ImageView imgMap       = ( ImageView ) socialDialog.findViewById( R.id.imgmap );
		final ImageView imgWhatsApp  = ( ImageView ) socialDialog.findViewById( R.id.imgwhatsapp );
		final ImageView imgPhoneCall = ( ImageView ) socialDialog.findViewById( R.id.imgcall );
		final ImageView imgcancel    = ( ImageView ) socialDialog.findViewById( R.id.imgcancel );

		imgcancel.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				socialDialog.dismiss();
				imgSocial.setVisibility( View.VISIBLE );
			}
		} );
		imgMessage.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				sendMail();
			}
		} );
		imgPhoneCall.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				dialPhoneCall();
			}
		} );
		imgWhatsApp.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				sendWhatsAppMsg();
			}
		} );

		socialDialog.show();
	}

	public void sendWhatsAppMsg() {
		boolean isWhatsappInstalled = whatsappInstalledOrNot( "com.whatsapp" );
		if ( isWhatsappInstalled ) {
			Intent waIntent = new Intent( Intent.ACTION_SEND );
			waIntent.setType( "text/plain" );
			String text = "Welcome to TFML";
			waIntent.setPackage( "com.whatsapp" );
			if ( waIntent != null ) {
				waIntent.putExtra( Intent.EXTRA_TEXT, text );//
				startActivity( Intent.createChooser( waIntent, "Share with" ) );
			}
			else {
				Toast.makeText( this, "WhatsApp not Installed", Toast.LENGTH_SHORT )
						.show();
			}
		}
		else {
			Toast.makeText( this, "WhatsApp not Installed",
			                Toast.LENGTH_SHORT ).show();
			Uri    uri        = Uri.parse( "market://details?id=com.whatsapp" );
			Intent goToMarket = new Intent( Intent.ACTION_VIEW, uri );
			startActivity( goToMarket );
		}
	}

	private boolean whatsappInstalledOrNot( String uri ) {
		PackageManager pm            = getPackageManager();
		boolean        app_installed = false;
		try {
			pm.getPackageInfo( uri, PackageManager.GET_ACTIVITIES );
			app_installed = true;
		}
		catch ( PackageManager.NameNotFoundException e ) {
			app_installed = false;
		}
		return app_installed;
	}

	public void dialPhoneCall() {
		Intent callIntent = new Intent( Intent.ACTION_DIAL );
		callIntent.setData( Uri.parse( "tel:18002090188" ) );
		startActivity( callIntent );
	}

	public void sendMail() {
		Log.i( "Send emailId", "" );
		String[] TO          = { "" };
		String[] CC          = { "" };
		Intent   emailIntent = new Intent( Intent.ACTION_SEND );

		emailIntent.setData( Uri.parse( "mailto:" ) );
		emailIntent.setType( "text/plain" );
		emailIntent.putExtra( Intent.EXTRA_EMAIL, TO );
		emailIntent.putExtra( Intent.EXTRA_CC, CC );
		emailIntent.putExtra( Intent.EXTRA_SUBJECT, "Your subject" );
		emailIntent.putExtra( Intent.EXTRA_TEXT, "Email message goes here" );

		try {
			startActivity( Intent.createChooser( emailIntent, "Send mail..." ) );
			finish();
			Log.i( "Finish sending emailId...", "" );
		}
		catch ( android.content.ActivityNotFoundException ex ) {
			Toast.makeText( SchemesActivity.this, "There is no emailId client installed.", Toast.LENGTH_SHORT ).show();
		}
	}

	public void callSchemesResponseModel() {

		tmflApi.getSchemesResponse().enqueue( new Callback< SchemesResponse >() {
			@Override
			public void onResponse( Call< SchemesResponse > call, Response< SchemesResponse > response ) {

				CommonUtils.closeProgressDialog();
				Log.e( "SchemesResponse", new Gson().toJson( response.body() ) );
				System.out.println( "----------Response-------------" + newOfferList );

				newOfferList = response.body().getOfferData().getNEW();
				usedOfferList = response.body().getOfferData().getUSED();

				SchemesResponse model = response.body();
				if ( model != null ) {
					PreferenceHelper.insertObject( "Scheme response", model );
					setupViewPager( viewPager );
				}
			}

			@Override
			public void onFailure( Call< SchemesResponse > call, Throwable t ) {
				Log.e( "Resp", t.getMessage() + "  " + new Gson().toJson( call.toString() ) );
				CommonUtils.closeProgressDialog();
			}
		} );
	}
}

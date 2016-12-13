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
import com.tmfl.model.schemesResponseModel.Datum;
import com.tmfl.model.schemesResponseModel.SchemesResponse;
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
	private ArrayList< Datum > datumArrayList;
	private LinearLayout       llSchemes, llApplyLoan;
	private SchemesPagerAdapter adapter;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_schemes );

		datumArrayList = new ArrayList<>();
		toolbarschemes = ( Toolbar ) findViewById( R.id.toolbar_schemes );
		txtschemestitle = ( TextView ) findViewById( R.id.txt_schemes_title );
		imgtoolbarhome = ( ImageView ) findViewById( R.id.img_toolbar_home );
		imgSocial = ( ImageView ) findViewById( R.id.img_social );
		llApplyLoan = ( LinearLayout ) findViewById( R.id.llApplyLoan );
		llSchemes = ( LinearLayout ) findViewById( R.id.llSchemes );

		llApplyLoan.setOnClickListener( this );
		llSchemes.setOnClickListener( this );
		viewPager = ( ViewPager ) findViewById( R.id.pager );
		setupViewPager( viewPager );

		view1 = findViewById( R.id.view1 );
		view2 = findViewById( R.id.view2 );
		view3 = findViewById( R.id.view3 );

		bundle1 = getIntent().getExtras();
		String myTabselected = bundle1.getString( "TAB_SELECTED" );

		assert myTabselected != null;
		if ( myTabselected.equalsIgnoreCase( Constant.ISSCHEMASTABSELECT ) ) {
			viewPager.setCurrentItem( 0 );
		}
		else if ( myTabselected.equalsIgnoreCase( Constant.ISAPPLYLOANSELECT ) ) {
			viewPager.setCurrentItem( 1 );
		}

		tmflApi = ApiService.getInstance().call();
		if ( CommonUtils.isNetworkAvailable( SchemesActivity.this ) ) {
			callSchemesResponseModel();
		}
		else {
			Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
		SetFonts.setFonts( this, txtschemestitle, 2 );
	}

//	private void setupTabIcons() {
//
//		TextView tabOne = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
//		tabOne.setText( "Offers" );
//		SetFonts.setFonts( this, tabOne, 2 );
//		tabOne.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_scheme_non_selected, 0, 0 );
//		tabLayout.getTabAt( 0 ).setCustomView( tabOne );
//
//		TextView tabTwo = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
//		tabTwo.setText( "Apply Loan" );
//		SetFonts.setFonts( this, tabTwo, 2 );
//		tabTwo.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_apply_loan_non_selected, 0, 0 );
//		tabLayout.getTabAt( 1 ).setCustomView( tabTwo );
//
//		/*TextView tabThree = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
//		tabThree.setText( "Refer Friend" );
//		SetFonts.setFonts( this, tabThree, 2 );
//		tabThree.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_refer_friends_non_selected, 0, 0 );
//		tabLayout.getTabAt( 2 ).setCustomView( tabThree );*/
//	}

	private void setupViewPager( ViewPager viewPager ) {
		List< Fragment > fragList = new ArrayList<>();
		System.out.println( "------------dgdsgdf----------->" + datumArrayList );

		final Fragment newSchemeFragment = new NewSchemeFragment();
		final Fragment applyLoanFragment = new ApplyLoanFragment();
//		Fragment referFriendFragment = new ReferFriendFragment();

//		adapter.addFrag( applyLoanFragment, "Apply Loan" );
//		adapter.addFrag( referFriendFragment, "Refer Friends" );
		fragList.add( newSchemeFragment );
		fragList.add( applyLoanFragment );

		Log.d( "fraglistsize", String.valueOf( fragList.size() ) );
		adapter = new SchemesPagerAdapter( getSupportFragmentManager(), fragList );
		viewPager.setAdapter( adapter );
		viewPager.setCurrentItem( 0 );
		viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

			}

			@Override
			public void onPageSelected( int position ) {
				setTitle( getPageTitle( position ) );

				switch ( position ) {

					case 0:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
//						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;

					case 1:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_white );
//						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;

					/*case 2:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						break;*/

					default:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
//						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged( int state ) {

			}
		} );

		// Give the TabLayout the ViewPager
		/*tabLayout = ( TabLayout ) findViewById( R.id.tab_layout );
		tabLayout.setupWithViewPager( viewPager );
		String myTabselected = bundle1.getString( "TAB_SELECTED" );
		imgtoolbarhome.setOnClickListener( this );
//		setupTabIcons();
		imgSocial.setOnClickListener( this );

		if ( myTabselected.equals( "Offers" ) ) {
			tabLayout.getTabAt( 0 ).select();
		}
		else if ( myTabselected.equals( "ApplyLoan" ) ) {
			tabLayout.getTabAt( 1 ).select();
		}*/
		/*else if ( myTabselected.equals( "ReferFriend" ) ) {
			tabLayout.getTabAt( 2 ).select();
		}*/
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
//			case 2:
//				return "Refer Friends";
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
				viewPager.setCurrentItem( 0 );

				break;

			case R.id.llApplyLoan:

				view2.setVisibility( View.VISIBLE );
				view1.setVisibility( View.INVISIBLE );
				viewPager.setCurrentItem( 1 );

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

				Log.e( "Respobnse", new Gson().toJson( response.body() ) );
				CommonUtils.closeProgressDialog();
				Log.e( "SchemesResponse", new Gson().toJson( response.body() ) );
				System.out.println( "----------Response------------->" + datumArrayList );

				datumArrayList = response.body().getData();

				SchemesResponse model = response.body();
				if ( model != null ) {
					PreferenceHelper.insertObject( "Scheme response", model );
				}
			}

			@Override
			public void onFailure( Call< SchemesResponse > call, Throwable t ) {
				//  Log.e("Resp", "Error");
				CommonUtils.closeProgressDialog();
			}
		} );
	}
}

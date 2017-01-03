package com.tmfl.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.adapter.EmiPagerAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.fragment.EmiPatternFragment;
import com.tmfl.fragment.PreClosureFragment;
import com.tmfl.fragment.RcUpdateFragment;
import com.tmfl.fragment.StatementOfAccountFragment;
import com.tmfl.model.ContractResponseModel.ContractModel;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;

public class EmiActivity extends DrawerBaseActivity implements View.OnClickListener {

	Toolbar   toolbarEmi;
	ImageView imgEmiBack, imgDrawer;
	TextView     txtEmiName;
	TabLayout    emiTabLayout;
	ViewPager    viewPager;
	DrawerLayout drawerLayout;
	View         view1, view2, view3, view4;
	ArrayList< ContractModel > modelArrayList;
	String datavalue = "";

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_emi );
		toolbarEmi = ( Toolbar ) findViewById( R.id.toolbar_emi );
		setSupportActionBar( toolbarEmi );
		getSupportActionBar().setTitle( "" );
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		modelArrayList = ( ArrayList< ContractModel > ) bundle.getSerializable( "datamodel" );
		datavalue = bundle.getString( "datamodelvalue" );
		init();

	}

	public void init() {

		imgEmiBack = ( ImageView ) findViewById( R.id.img_emi_back );
		txtEmiName = ( TextView ) findViewById( R.id.txt_titel_emi );
		viewPager = ( ViewPager ) findViewById( R.id.pager );

		view1 = findViewById( R.id.view1 );
		view2 = findViewById( R.id.view2 );
		view3 = findViewById( R.id.view3 );
		view4 = findViewById( R.id.view4 );

		emiTabLayout = ( TabLayout ) findViewById( R.id.tab_layout_emi );
		setupViewPager( viewPager );
		emiTabLayout.setupWithViewPager( viewPager );
		imgEmiBack = ( ImageView ) findViewById( R.id.img_emi_back );
		imgDrawer = ( ImageView ) findViewById( R.id.img_drawer_emi );
		setupTabIcon();
		imgEmiBack.setOnClickListener( this );
		imgDrawer.setOnClickListener( this );

		String page = PreferenceHelper.getString( Constant.SHOW_PAGE );

		switch ( page ) {

			case Constant.EMI_PATTERN:

				PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, false );
				viewPager.setCurrentItem( 0 );

				break;

			case Constant.STATEMENT_OF_ACCOUNT:

				viewPager.setCurrentItem( 1 );

				break;

			case Constant.RC_UPDATE:

				viewPager.setCurrentItem( 2 );

				break;

			case Constant.PRECLOSURE:

				viewPager.setCurrentItem( 3 );

				break;

			case Constant.RECEIPT:

				PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, true );
				viewPager.setCurrentItem( 0 );

				break;
		}
	}

	private void setupViewPager( ViewPager viewPager ) {

		EmiPagerAdapter adapter                    = new EmiPagerAdapter( getSupportFragmentManager() );
		Fragment        emiPatternFrag             = new EmiPatternFragment();
		Fragment        statementOfAccountFragment = new StatementOfAccountFragment();
		Fragment        rcUpdateFragment           = new RcUpdateFragment();
		Fragment        closureFragment            = new PreClosureFragment();

		Bundle bundle = new Bundle();
		bundle.putSerializable( "datamodel", modelArrayList );
		bundle.putString( "datamodelvalue", datavalue );

		adapter.addFrag( emiPatternFrag, "EMI pattern" );
		adapter.addFrag( statementOfAccountFragment, "Statement of account" );
		adapter.addFrag( rcUpdateFragment, "RC update" );
		adapter.addFrag( closureFragment, "Pre-closure statement" );

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
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

						break;

					case 1:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

						break;

					case 2:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

						break;

					case 3:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_white );

						break;

					default:
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

						break;
				}
			}

			@Override
			public void onPageScrollStateChanged( int state ) {

			}
		} );
	}

	public String getPageTitle( int position ) {

		switch ( position ) {
			case 0:
				return "EMI Pattern";
			case 1:
				return "Statement of Account";
			case 2:
				return "RC Update";
			case 3:
				return "Preclosure";
			default:
				return "";

		}
	}

	public void setupTabIcon() {

		TextView tabOne = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabOne.setText( "EMI pattern" );
		SetFonts.setFonts( this, tabOne, 2 );
		emiTabLayout.getTabAt( 0 ).setCustomView( tabOne );

		TextView tabTwo = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabTwo.setText( "Statement of account" );
		SetFonts.setFonts( this, tabTwo, 2 );
		emiTabLayout.getTabAt( 1 ).setCustomView( tabTwo );

		TextView tabThree = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabThree.setText( "RC update" );
		SetFonts.setFonts( this, tabThree, 2 );
		emiTabLayout.getTabAt( 2 ).setCustomView( tabThree );

		TextView tabFour = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabFour.setText( "Pre-closure statement" );
		SetFonts.setFonts( this, tabFour, 2 );
		emiTabLayout.getTabAt( 3 ).setCustomView( tabFour );

	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.img_emi_back:
				finish();
				break;

			case R.id.img_drawer_emi:
				openDrawer();
				break;
		}
	}
}

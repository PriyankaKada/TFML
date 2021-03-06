package com.tmfl.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.tmfl.fragment.EmiDetailFragment;
import com.tmfl.fragment.EmiPatternFragment;
import com.tmfl.fragment.PreClosureFragment;
import com.tmfl.fragment.RcUpdateFragment;
import com.tmfl.fragment.StatementOfAccountFragment;
import com.tmfl.model.ContractResponseModel.ContractModel;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;

public class EmiActivity extends DrawerBaseActivity implements View.OnClickListener {

	public static TextView txtEmiName;
	Toolbar   toolbarEmi;
	ImageView imgEmiBack, imgDrawerEmi;
	TabLayout    emiTabLayout;
	ViewPager    viewPager;
	DrawerLayout drawerLayout;
	View         view1, view2, view3, view4;
	ArrayList< ContractModel > modelArrayList;
	FragmentManager            fragmentManager;
	FragmentTransaction        fragmentTransaction;
	EmiDetailFragment          emiDetailFragment;
	String datavalue = "";

	@Override
	protected void onCreate( Bundle savedInstanceState ) {

		super.onCreate( savedInstanceState );
//		setContentView( R.layout.activity_emi );
		View view = getLayoutInflater().inflate( R.layout.activity_emi, frameLayout );
		toolbarEmi = ( Toolbar ) view.findViewById( R.id.toolbar_emi );
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
		txtEmiName = ( TextView ) toolbarEmi.findViewById( R.id.txt_titel_emi );
		viewPager = ( ViewPager ) findViewById( R.id.pager );
		view1 = findViewById( R.id.view1 );
		view2 = findViewById( R.id.view2 );
		view3 = findViewById( R.id.view3 );
		view4 = findViewById( R.id.view4 );

		emiTabLayout = ( TabLayout ) findViewById( R.id.tab_layout_emi );
		setupViewPager( viewPager );
		emiTabLayout.setupWithViewPager( viewPager );
		imgEmiBack = ( ImageView ) findViewById( R.id.img_emi_back );
		imgDrawerEmi = ( ImageView ) findViewById( R.id.img_drawer_emi );
		setupTabIcon();
		imgEmiBack.setOnClickListener( this );
		imgDrawerEmi.setOnClickListener( this );

		String page = PreferenceHelper.getString( Constant.SHOW_PAGE );

		switch ( page ) {

			case Constant.EMI_PATTERN:

				txtEmiName.setText( "EMI Schedule" );
				PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, false );
				viewPager.setCurrentItem( 0, true );

				break;

			case Constant.STATEMENT_OF_ACCOUNT:


				txtEmiName.setText( "Statement of Account" );
				viewPager.setCurrentItem( 1, true );

				break;

			case Constant.RC_UPDATE:

				txtEmiName.setText( "RC Update" );
				viewPager.setCurrentItem( 2, true );

				break;

			case Constant.PRECLOSURE:

				txtEmiName.setText( "Preclosure" );
				viewPager.setCurrentItem( 3, true );

				break;

			case Constant.RECEIPT:
				txtEmiName.setText( "Receipts" );
				PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, true );
				viewPager.setCurrentItem( 0, true );

				break;
		}
	}

	private void setupViewPager( final ViewPager viewPager ) {

		final EmiPagerAdapter adapter                    = new EmiPagerAdapter( getSupportFragmentManager() );
		Fragment              emiPatternFrag             = new EmiPatternFragment();
		Fragment              statementOfAccountFragment = new StatementOfAccountFragment();
		Fragment              rcUpdateFragment           = new RcUpdateFragment();
		Fragment              closureFragment            = new PreClosureFragment();

		Bundle bundle = new Bundle();
		bundle.putSerializable( "datamodel", modelArrayList );
		bundle.putString( "datamodelvalue", datavalue );

		adapter.addFrag( emiPatternFrag, "EMI Pattern" );
		adapter.addFrag( statementOfAccountFragment, "Statement of Account" );
		adapter.addFrag( rcUpdateFragment, "RC Update" );
		adapter.addFrag( closureFragment, "Pre-closure Statement" );

		viewPager.setAdapter( adapter );
		//viewPager.setCurrentItem( 0 );
		viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

			}


			@Override
			public void onPageSelected( int position ) {
				//setTitle( getPageTitle( position ) );

				switch ( position ) {

					case 0:

						if ( PreferenceHelper.getBoolean( Constant.SHOW_RECEIPT ) ) {
							txtEmiName.setText( "Receipts" );
						}
						else {
							txtEmiName.setText( "EMI Schedule" );
							/*fragmentManager = getSupportFragmentManager();
							fragmentTransaction = fragmentManager.beginTransaction();
							emiDetailFragment = new EmiDetailFragment();
							fragmentTransaction.replace( R.id.frm_emi_detail, emiDetailFragment );
							fragmentTransaction.commit();*/
							String backStackEmiPatternFrag = EmiPatternFragment.class.getName();
							getSupportFragmentManager().popBackStackImmediate( backStackEmiPatternFrag, 0 );
						}
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

						break;

					case 1:
						PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, false );
						txtEmiName.setText( "Statement of Account" );
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						break;
					case 2:
						PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, false );
						txtEmiName.setText( "RC Update" );
						view1.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view2.setBackgroundResource( R.drawable.selector_tab_indicator_blue );
						view3.setBackgroundResource( R.drawable.selector_tab_indicator_white );
						view4.setBackgroundResource( R.drawable.selector_tab_indicator_blue );

						break;

					case 3:
						PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, false );
						txtEmiName.setText( "Pre-closure Statement" );
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
				return "Pre-closure Statement";
			default:
				return "";

		}
	}

	public void setupTabIcon() {

		TextView tabOne = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabOne.setText( "EMI Schedule" );
		SetFonts.setFonts( this, tabOne, 2 );
		emiTabLayout.getTabAt( 0 ).setCustomView( tabOne );

		TextView tabTwo = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabTwo.setText( "Statement of Account" );
		SetFonts.setFonts( this, tabTwo, 2 );
		emiTabLayout.getTabAt( 1 ).setCustomView( tabTwo );

		TextView tabThree = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabThree.setText( "RC Update" );
		SetFonts.setFonts( this, tabThree, 2 );
		emiTabLayout.getTabAt( 2 ).setCustomView( tabThree );

		TextView tabFour = ( TextView ) LayoutInflater.from( this ).inflate( R.layout.custom_tab, null );
		tabFour.setText( "Pre-closure Statement" );
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

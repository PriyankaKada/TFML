package com.tfml.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 4/8/16.
 */

public class SchemesPagerAdapter extends FragmentPagerAdapter {

	private List< String >   mFragmentTitleList = new ArrayList<>();
	private List< Fragment > mFragmentList      = new ArrayList<>();


	public SchemesPagerAdapter( FragmentManager fm, List< Fragment > fragList ) {
		super( fm );
		mFragmentList = fragList;
		// this.context = context;
	}

	@Override
	public Fragment getItem( int position ) {

		return mFragmentList.get( position );
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	@Override
	public CharSequence getPageTitle( int position ) {
		return mFragmentTitleList.get( position );
	}

	public void addFrag( Fragment fragment, String title ) {
		mFragmentList.add( fragment );
		mFragmentTitleList.add( title );
	}
}

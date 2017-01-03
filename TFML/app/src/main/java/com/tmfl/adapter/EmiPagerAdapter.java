package com.tmfl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 16/8/16.
 */

public class EmiPagerAdapter extends FragmentPagerAdapter {

	private final List< Fragment > mFragmentList      = new ArrayList<>();
	private final List< String >   mFragmentTitleList = new ArrayList<>();

	public EmiPagerAdapter( FragmentManager fm ) {
		super( fm );
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
		return super.getPageTitle( position );
	}

	public void addFrag( Fragment fragment, String title ) {
		mFragmentList.add( fragment );
		mFragmentTitleList.add( title );
	}
}

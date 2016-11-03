package com.tfml.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.bannerResponseModel.Datum;

import java.util.ArrayList;

/**
 * Created by webwerks on 2/8/16.
 */

public class BannerAdapter extends PagerAdapter {
	ArrayList< Datum > bannerlist;
	BannerlistResponse bannerlistResponse;
	private Context mContext;

	public BannerAdapter( Context mContext, ArrayList< Datum > bannerlist ) {
		this.mContext = mContext;
		this.bannerlist = bannerlist;
	}

	@Override
	public int getCount() {
		return bannerlist.size();
	}

	@Override
	public boolean isViewFromObject( View view, Object object ) {
		return view == ( ( LinearLayout ) object );
	}

	@Override
	public Object instantiateItem( ViewGroup container, int position ) {
		View itemView = LayoutInflater.from( mContext ).inflate( R.layout.swipe_fragment, container, false );

		ImageView imageView = ( ImageView ) itemView.findViewById( R.id.imageView );
		Picasso.with( mContext ).load( String.valueOf( "" + bannerlist.get( position ).getImage() ) ).into( imageView );
		Log.e( "instantiateItem", "" + "http://staging.php-dev.in:8844/tatamotors/public/" + bannerlist.get( position ).getImage() );
		container.addView( itemView );

		return itemView;
	}

	@Override
	public void destroyItem( ViewGroup container, int position, Object object ) {
		container.removeView( ( LinearLayout ) object );
	}
}

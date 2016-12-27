package com.tfml.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.model.schemesResponseModel.NewOfferData;
import com.tfml.model.schemesResponseModel.UsedOfferData;
import com.tfml.util.SetFonts;

import java.util.List;

/**
 * Created by webwerks on 4/8/16.
 */

public class SchemesListAdapter extends BaseAdapter {


	private static LayoutInflater inflater = null;
	Context context;
	private List< NewOfferData >  newOfferData;
	private List< UsedOfferData > usedOfferData;
	private int                   offerType;

	public SchemesListAdapter( Context context, List< NewOfferData > newOfferDataList, List< UsedOfferData > usedOfferDataList, int offerType ) {
		this.context = context;
		this.newOfferData = newOfferDataList;
		this.usedOfferData = usedOfferDataList;
		this.offerType = offerType;
	}

	@Override
	public int getCount() {

		if ( offerType == 1 ) {
			return newOfferData.size();
		}
		else {
			return usedOfferData.size();
		}
	}

	@Override
	public Object getItem( int position ) {

		if ( offerType == 1 ) {
			return newOfferData.get( position );
		}
		else {
			return usedOfferData.get( position );
		}
	}

	@Override
	public long getItemId( int position ) {
		return position;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {

		Holder holder = null;
		if ( convertView == null ) {
			LayoutInflater inflater = ( ( Activity ) context ).getLayoutInflater();
			convertView = inflater.inflate( R.layout.new_schemes_item_list, null );
			holder = new Holder();
			holder.txt_title = ( TextView ) convertView.findViewById( R.id.txt_title );
			holder.txt_description = ( TextView ) convertView.findViewById( R.id.txt_description );
			holder.img_new_schemes = ( ImageView ) convertView.findViewById( R.id.img_new_schemes );
			SetFonts.setFonts( context, holder.txt_title, 2 );
			SetFonts.setFonts( context, holder.txt_description, 5 );
			convertView.setTag( holder );
		}
		else {
			holder = ( Holder ) convertView.getTag();
		}

		if ( offerType == 1 ) {
			holder.txt_title.setText( newOfferData.get( position ).getTitle() );
			holder.txt_description.setText( newOfferData.get( position ).getShortDescription() );
			Picasso.with( this.context ).load( newOfferData.get( position ).getImage() ).into( holder.img_new_schemes );
		}
		else {
			holder.txt_title.setText( usedOfferData.get( position ).getTitle() );
			holder.txt_description.setText( usedOfferData.get( position ).getShortDescription() );
			Picasso.with( this.context ).load( usedOfferData.get( position ).getImage() ).into( holder.img_new_schemes );
		}

		return convertView;
	}

	public class Holder {
		TextView txt_title, txt_description;
		ImageView img_new_schemes;
	}
}

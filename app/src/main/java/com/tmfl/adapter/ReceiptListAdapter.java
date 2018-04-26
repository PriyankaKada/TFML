package com.tmfl.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.model.soapModel.response.ResponseEnvelope;
import com.tmfl.util.SetFonts;


/**
 * Created by webwerks on 29/9/16.
 */

public class ReceiptListAdapter extends BaseAdapter {
	ResponseEnvelope.Body responseEnvelope;
	Context               context;

	public ReceiptListAdapter( Context context, ResponseEnvelope.Body responseEnvelope ) {
		this.context = context;
		this.responseEnvelope = responseEnvelope;
	}

	@Override
	public int getCount() {
		return responseEnvelope.getZCISResponse().getI_REC().size();
	}

	@Override
	public Object getItem( int position ) {
		return responseEnvelope.getZCISResponse().getI_REC().get( position );
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
			convertView = inflater.inflate( R.layout.item_my_receipt, null );
			holder = new Holder();
			holder.txtReceiptDate = ( TextView ) convertView.findViewById( R.id.txtReceiptDate );
			holder.txtReceiptAmount = ( TextView ) convertView.findViewById( R.id.txtAmount );
			holder.img_expand = ( ImageView ) convertView.findViewById( R.id.img_expand );
			//   holder.txtReceiptNo=(TextView) convertView.findViewById(R.id.txtReceiptNo);
			holder.txtInstNo = ( TextView ) convertView.findViewById( R.id.txtInstNo );
			holder.txtType = ( TextView ) convertView.findViewById( R.id.txtType );
			holder.txtMode = ( TextView ) convertView.findViewById( R.id.txtMode );
			holder.txtBank = ( TextView ) convertView.findViewById( R.id.txtBank );
			holder.imgPdf = ( ImageView ) convertView.findViewById( R.id.imgPdf );
			holder.lexpandList = ( LinearLayout ) convertView.findViewById( R.id.ll_expanded_layout );
			SetFonts.setFonts( context, holder.txtReceiptDate, 5 );
			SetFonts.setFonts( context, holder.txtReceiptAmount, 5 );
			SetFonts.setFonts( context, holder.txtReceiptNo, 5 );
			SetFonts.setFonts( context, holder.txtInstNo, 5 );
			SetFonts.setFonts( context, holder.txtMode, 5 );
			SetFonts.setFonts( context, holder.txtBank, 5 );
			SetFonts.setFonts( context, holder.txtType, 5 );
			convertView.setTag( holder );
		}
		else

		{
			holder = ( Holder ) convertView.getTag();
		}
		holder.txtReceiptDate.setText( responseEnvelope.getZCISResponse().getI_REC().get( position ).getZFBDT() == null ? "" : responseEnvelope.getZCISResponse().getI_REC().get( position ).getZFBDT() + " / " + responseEnvelope.getZCISResponse().getI_REC().get( position ).getBELNR() == null ? "" : responseEnvelope.getZCISResponse().getI_REC().get( position ).getBELNR() );
		holder.txtReceiptAmount.setText( responseEnvelope.getZCISResponse().getI_REC().get( position ).getDMBTR() == null ? "" : responseEnvelope.getZCISResponse().getI_REC().get( position ).getDMBTR() );
		//   holder.txtReceiptNo.setText(responseEnvelope.getZCISResponse().getI_REC().get(i).getBELNR());
		holder.txtInstNo.setText( responseEnvelope.getZCISResponse().getI_REC().get( position ).getCHECT() == null ? "" : responseEnvelope.getZCISResponse().getI_REC().get( position ).getCHECT() );
		holder.txtMode.setText( responseEnvelope.getZCISResponse().getI_REC().get( position ).getSHKZG() == null ? "" : responseEnvelope.getZCISResponse().getI_REC().get( position ).getSHKZG() );
		holder.txtType.setText( responseEnvelope.getZCISResponse().getI_REC().get( position ).getANBWA() == null ? "" : responseEnvelope.getZCISResponse().getI_REC().get( position ).getANBWA() );

		final Holder finalHolder = holder;
		holder.img_expand.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				if ( finalHolder.lexpandList.getVisibility() != View.VISIBLE ) {
					finalHolder.lexpandList.setVisibility( View.VISIBLE );
				}
				else {
					finalHolder.lexpandList.setVisibility( View.GONE );
				}

			}
		} );
		return convertView;


	}

	public class Holder {
		TextView txtReceiptDate, txtReceiptAmount, txtReceiptNo, txtInstNo, txtMode, txtType, txtBank;
		ImageView img_expand, imgPdf;
		LinearLayout lexpandList;

	}
}





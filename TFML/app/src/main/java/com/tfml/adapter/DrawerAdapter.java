package com.tfml.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfml.R;

/**
 * Created by webwerks on 24/8/16.
 */
public class DrawerAdapter  extends BaseAdapter
{
	String [] items;
	Context   context;
	int []    icons;
	private static LayoutInflater inflater =null;

	public DrawerAdapter( Context ctx, String[] items, int[] icons) {
		this.items =items;
		context = ctx;
		this.icons=icons;
		inflater = ( LayoutInflater )context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class Holder
	{
		TextView  tv;
		ImageView img;
		View dividerView;
	}
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		Holder holder=new Holder();
		View rowView;
		rowView = inflater.inflate( R.layout.item_navigation, null);
		holder.tv=(TextView) rowView.findViewById( R.id.txv_menu_text);
		holder.img=(ImageView) rowView.findViewById( R.id.imv_menu_icon);
		holder.dividerView=(View)rowView.findViewById(R.id.dividerView);
		holder.tv.setText(items[position]);
		holder.img.setImageResource(icons[position]);

		if(position == 6){
			rowView.setBackgroundResource( R.color.tab_bg );
			holder.img.setVisibility( View.GONE );
			holder.tv.setTextColor(Color.parseColor("#FFFFFF"));
			holder.tv.setTextSize(20);
			holder.dividerView.setBackgroundColor(Color.parseColor("#004c92"));

		}else if ( position > 6 )
		{
			holder.dividerView.setBackgroundColor(Color.parseColor("#6699CC"));
			rowView.setBackgroundResource( R.color.tab_bg );
			holder.img.setVisibility( View.VISIBLE );
			holder.tv.setTextColor(Color.parseColor("#FFFFFF"));


		}else{
			rowView.setBackgroundResource( R.color.white );
			holder.img.setVisibility( View.VISIBLE );
		}

		return rowView;
	}
}
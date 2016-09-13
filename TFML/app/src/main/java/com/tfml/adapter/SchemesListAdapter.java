package com.tfml.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tfml.R;
import com.tfml.model.schemesResponseModel.Datum;
import com.tfml.util.SetFonts;

import java.util.List;

/**
 * Created by webwerks on 4/8/16.
 */

public class SchemesListAdapter extends BaseAdapter {


    private List<Datum> data;
    Context context;
    private static LayoutInflater inflater=null;

    public SchemesListAdapter(Context context,List<Datum> data) {
        this.context=context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder=null;
        if(convertView==null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate( R.layout.new_schemes_item_list, null);
            holder=new Holder();
            holder.txt_title=(TextView) convertView.findViewById(R.id.txt_title);
            holder.txt_description=(TextView)convertView.findViewById(R.id.txt_description);
            holder.img_new_schemes=(ImageView) convertView.findViewById(R.id.img_new_schemes);
            SetFonts.setFonts(context,holder.txt_title,2);
            SetFonts.setFonts(context,holder.txt_description,5);
            convertView.setTag(holder);
        }
        else
            holder=(Holder)convertView.getTag();
            holder.txt_title.setText(data.get(position).getTitle());
            holder.txt_description.setText(data.get(position).getShortDescription());
            Picasso.with(this.context).load(data.get(position).getImage()).into(holder.img_new_schemes);
        Log.e("Getimagurl",""+data.get(position).getImage());

        return convertView;

    }
    public class Holder
    {
        TextView txt_title,txt_description;
        ImageView img_new_schemes;
    }
}

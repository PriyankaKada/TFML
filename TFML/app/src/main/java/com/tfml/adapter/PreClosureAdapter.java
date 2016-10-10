package com.tfml.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.soapModel.preClousreResponse.ResponseEnvelope;


/**
 * Created by webwerks on 1/10/16.
 */

public class PreClosureAdapter extends BaseAdapter {
    Context context;
    ResponseEnvelope.Body responseEnvelope;

    public PreClosureAdapter(Context context, ResponseEnvelope.Body responseEnvelope) {
        this.context = context;
        this.responseEnvelope = responseEnvelope;
    }

    @Override
    public int getCount() {
        return responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().size();
    }

    @Override
    public Object getItem(int position) {
        return responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      Holder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.preclousure_item_detil, null);
            holder=new Holder();
            holder.txtDesc=(TextView)convertView.findViewById(R.id.txtDesc);
            holder.txtDueAmt=(TextView)convertView.findViewById(R.id.txtDueAmount);
            holder.txtNetBal=(TextView)convertView.findViewById(R.id.txtNetBal);
            holder.txtRecAmt=(TextView)convertView.findViewById(R.id.txtRecAmt);
            convertView.setTag(holder);
        }
        else
        {
            holder=(Holder) convertView.getTag();

        }
       /* if(responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get(position).getDESCP().equals("TOTAL"))
        {

        }
        else
        {

        }*/
        holder.txtDesc.setText(responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get(position).getDESCP());
        holder.txtDueAmt.setText(responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get(position).getDUE());
        holder.txtNetBal.setText(responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get(position).getNET());
        holder.txtRecAmt.setText(responseEnvelope.getZ_TERMINALDUESResponse().getI_DTL().get(position).getREC());
        return  convertView;
    }
    public class Holder
    {
        TextView txtDesc,txtDueAmt,txtNetBal,txtRecAmt;
    }

}

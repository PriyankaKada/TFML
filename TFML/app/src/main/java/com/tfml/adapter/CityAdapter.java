package com.tfml.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.cityResponseModel.CityResponseModel;

import java.util.List;

/**
 * Created by webwerks on 25/8/16.
 */

public class CityAdapter extends ArrayAdapter<CityResponseModel> {
    Context context;
    public CityAdapter(Context context,List<CityResponseModel> data) {
        super(context, 0,data);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.simple_city_spinner, parent, false);
        }
        TextView cityName = (TextView) row.findViewById(R.id.txtCity);
        cityName.setText(getItem(position).getName());
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}

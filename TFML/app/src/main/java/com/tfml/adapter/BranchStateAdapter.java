package com.tfml.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.stateResponseModel.BranchStateResponseModel;

import java.util.List;

/**
 * Created by webwerks on 24/8/16.
 */

public class BranchStateAdapter extends ArrayAdapter<BranchStateResponseModel> {
    Context context;

    public BranchStateAdapter(Context context, List<BranchStateResponseModel> data) {
        super(context, 0, data);
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.layout_spinner_textview, parent, false);
        }
        try {
            TextView stateName = (TextView) row.findViewById(R.id.txtValue);
            stateName.setText(getItem(position).getTerrCaption());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

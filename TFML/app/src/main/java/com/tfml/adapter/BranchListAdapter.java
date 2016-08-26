package com.tfml.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.branchResponseModel.BranchResponseModel;

import java.util.List;

/**
 * Created by webwerks on 24/8/16.
 */

public class BranchListAdapter extends ArrayAdapter<BranchResponseModel> {
    Context context;
    public BranchListAdapter(Context context, List<BranchResponseModel> data) {
        super(context, 0,data);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.simple_branch_city_spinner, parent, false);
        }
        TextView branchName = (TextView) row.findViewById(R.id.txtBranchCity);
        branchName.setText(getItem(position).getTerrCaption());
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}

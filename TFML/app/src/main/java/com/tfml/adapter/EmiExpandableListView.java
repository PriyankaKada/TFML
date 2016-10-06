package com.tfml.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.emiListReponseModel.Datum;

import java.util.ArrayList;

/**
 * Created by Pravin Borate on 6/10/16.
 */
public class EmiExpandableListView extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<Datum> parentData;
    ArrayList<Datum> childData;
    LayoutInflater layoutInflater;
    EmiViewHolder emiViewHolder;


    public EmiExpandableListView(Context context, ArrayList<Datum> parent,ArrayList<Datum> child){

        mContext=context;
        parentData=parent;
        childData=child;
        layoutInflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getGroupCount() {
        return parentData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parentData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        emiViewHolder=new EmiViewHolder();
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.group_row_emi_detail, null);

        }
        emiViewHolder.txtEmiDate = (TextView) convertView.findViewById(R.id.txtEmiDate);
        emiViewHolder.txtEmiAmount = (TextView) convertView.findViewById(R.id.txtEmiAmount);
        emiViewHolder.txtCount=(TextView)convertView.findViewById(R.id.txtCount);

        if((groupPosition+1)<=9){
            emiViewHolder.txtCount.setText("0"+(groupPosition+1));
        }
        else {
            emiViewHolder.txtCount.setText(""+(groupPosition+1));
        }

        //Format date here
        if(parentData.get(groupPosition).getADATE()!=null)
            emiViewHolder.txtEmiDate.setText(parentData.get(groupPosition).getADATE().toString());

        if(parentData.get(groupPosition).getAMOUNT()!=null)
            emiViewHolder.txtEmiAmount.setText(parentData.get(groupPosition).getAMOUNT().toString());
        return convertView;
    }

    EmiChildViewHolder emiChildViewHolder=null;

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        emiChildViewHolder=new EmiChildViewHolder();
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.child_row_emi_details,null);

        }
        emiChildViewHolder.txtEmiInsurance=(TextView)convertView.findViewById(R.id.txtEmiInsurance);
        emiChildViewHolder.txtEmiInterest=(TextView)convertView.findViewById(R.id.txtEmiInterest);
        emiChildViewHolder.txtEmiOsPrinciple=(TextView)convertView.findViewById(R.id.txtEmiOsPrinciple);
        emiChildViewHolder.txtEmiPrinciple=(TextView)convertView.findViewById(R.id.txtEmiPrinciple);
        emiChildViewHolder.txtEmiInsurance.setText(childData.get(groupPosition).getINSAMT().toString());
        emiChildViewHolder.txtEmiInterest.setText(childData.get(groupPosition).getFINCHRG());
        emiChildViewHolder.txtEmiOsPrinciple.setText(childData.get(groupPosition).getPRIAMT());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public class EmiViewHolder {
        TextView txtEmiDate, txtEmiAmount,txtCount;

    }
    public class EmiChildViewHolder{

        TextView txtEmiInterest,txtEmiPrinciple,txtEmiInsurance,txtEmiOsPrinciple;

    }
}

package com.tfml.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.SetFonts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by webwerks on 10/4/16.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    HashMap<String, ArrayList<ResponseEnvelope.Item>> hashMap;
    Context context;
    ArrayList<ResponseEnvelope.Item> Childar;
    MyGroupHolder grpholder = null;
    String headerTitle;
    ArrayList<String> groupar = new ArrayList<>();
    ArrayList<Double> amountar = new ArrayList<Double>();

    ArrayList<String> titlear = new ArrayList<>();

    public MyExpandableListAdapter(Context mcontext, HashMap<String, ArrayList<ResponseEnvelope.Item>> mhashMap, ArrayList<String> mgroupar, ArrayList<Double> mamountar) {
        this.context = mcontext;
        this.hashMap = mhashMap;
        this.titlear = mgroupar;
        this.amountar = mamountar;
        dividelistmap(hashMap);
    }

    private void dividelistmap(HashMap<String, ArrayList<ResponseEnvelope.Item>> map) {
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            groupar.add((String) pair.getKey());
            Childar = (ArrayList<ResponseEnvelope.Item>) pair.getValue();
            Log.e("Size ", "" + Childar.size());
            Log.e("groupar size ", "" + groupar.size());

            for (int i = 0; i < Childar.size(); i++) {
                ResponseEnvelope.Item item = Childar.get(i);
                Log.e("item ", "" + item.getBLDAT());
            }


        }

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return hashMap.get(this.groupar.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("NewApi")
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ResponseEnvelope.Item item = (ResponseEnvelope.Item) getChild(groupPosition, childPosition);
        MyHolder holder = null;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_my_receipt, null);
            holder = new MyHolder();
            holder.txtReceiptDate = (TextView) convertView.findViewById(R.id.txtReceiptDate);
            holder.txtReceiptAmount = (TextView) convertView.findViewById(R.id.txtAmount);
            holder.txtAmount2 = (TextView) convertView.findViewById(R.id.txtAmount2);
            holder.img_expand = (ImageView) convertView.findViewById(R.id.img_expand);
//            holder.txtReceiptNo=(TextView) convertView.findViewById(R.id.txtReceiptNo);
            holder.txtInstNo = (TextView) convertView.findViewById(R.id.txtInstNo);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.txtMode = (TextView) convertView.findViewById(R.id.txtMode);
            holder.txtBank = (TextView) convertView.findViewById(R.id.txtBank);
            holder.imgPdf = (ImageView) convertView.findViewById(R.id.imgPdf);
            holder.lexpandList = (LinearLayout) convertView.findViewById(R.id.ll_expanded_layout);
            holder.ll_header_row = (LinearLayout) convertView.findViewById(R.id.ll_header_row);
            SetFonts.setFonts(context, holder.txtReceiptDate, 5);
            SetFonts.setFonts(context, holder.txtReceiptAmount, 5);
            SetFonts.setFonts(context, holder.txtAmount2, 5);
//            SetFonts.setFonts(context,holder.txtReceiptNo,5);
            SetFonts.setFonts(context, holder.txtInstNo, 5);
            SetFonts.setFonts(context, holder.txtMode, 5);
            SetFonts.setFonts(context, holder.txtBank, 5);
            SetFonts.setFonts(context, holder.txtType, 5);
            convertView.setTag(holder);
        } else
            holder = (MyHolder) convertView.getTag();
        holder.txtReceiptDate.setText(item.getZFBDT() + " / " + item.getBELNR());
        holder.ll_header_row.setVisibility(View.VISIBLE);
        String amountstr = item.getDMBTR();
        holder.txtReceiptAmount.setText("Rs."+amountstr);
        holder.lexpandList.setBackground(context.getDrawable(R.drawable.list_row));
        holder.txtInstNo.setText(item.getCHECT());
        holder.txtMode.setText(item.getSHKZG());
        holder.txtType.setText(item.getANBWA());
        holder.lexpandList.setVisibility(View.VISIBLE);
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return hashMap.get(this.groupar.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.groupar.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.groupar.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        headerTitle = (String) getGroup(groupPosition);
        grpholder = new MyGroupHolder();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_row, null);
        }
        grpholder.txtReceiptDate = (TextView) convertView
                .findViewById(R.id.txtReceiptDate);
        grpholder.txtReceiptAmount = (TextView) convertView
                .findViewById(R.id.txtAmount);

        if (titlear.size() == amountar.size()) {
            grpholder.txtReceiptDate.setText(titlear.get(groupPosition));
            grpholder.txtReceiptAmount.setText("Rs."+String.valueOf(amountar.get(groupPosition)));
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class MyHolder {
        TextView txtReceiptDate, txtReceiptAmount, txtAmount2, txtReceiptNo, txtInstNo, txtMode, txtType, txtBank;
        ImageView img_expand, imgPdf;
        LinearLayout lexpandList, ll_header_row, ll_daterow;
    }

    public class MyGroupHolder {
        TextView txtReceiptDate, txtReceiptAmount;
        ImageView img_expand;
        LinearLayout ll_header_row, ll_daterow;
    }
}

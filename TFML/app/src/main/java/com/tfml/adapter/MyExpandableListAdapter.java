package com.tfml.adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.activity.LoginActivity;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.myReciptPdfResponseModel.MyReceiptInputModel;
import com.tfml.model.myReciptPdfResponseModel.MyReceiptResponseModel;
import com.tfml.model.soapModel.response.ResponseEnvelope;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webwerks on 10/4/16.
 */
public class MyExpandableListAdapter  extends BaseExpandableListAdapter implements ExpandableListAdapter{
    Map<String, ArrayList<ResponseEnvelope.Item>> hashMap;
    Context context;
    ArrayList<ResponseEnvelope.Item> Childar;
    MyGroupHolder grpholder = null;
    String headerTitle;
    ArrayList<String> groupar = new ArrayList<>();
    ArrayList<Double> amountar = new ArrayList<Double>();

    ArrayList<String> titlear = new ArrayList<>();
    MyReceiptInputModel myReceiptInputModel;
    MyReceiptResponseModel myReceiptResponseModel;
    String rcDate,rcNo;
    TmflApi tmflApi;
    String strPathUrl;

    public MyExpandableListAdapter(Context mcontext, Map<String, ArrayList<ResponseEnvelope.Item>> mhashMap, ArrayList<String> mgroupar, ArrayList<Double> mamountar) {
    this.context = mcontext;
    this.hashMap = mhashMap;
    this.titlear = mgroupar;
    this.amountar = mamountar;
       dividelistmap(hashMap);
    }

    private void dividelistmap(Map<String, ArrayList<ResponseEnvelope.Item>> map) {
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            groupar.add((String) pair.getKey());
            Childar = (ArrayList<ResponseEnvelope.Item>) pair.getValue();

            for (int i =0; i< Childar.size(); i++){
                ResponseEnvelope.Item item = Childar.get(i);
//                Log.e("item adapter date",""+item.getBLDAT());
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
        MyHolder holder=null;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_my_receipt, null);
            holder=new MyHolder();
            holder.txtReceiptDate=(TextView) convertView.findViewById(R.id.txtReceiptDate);
            holder.txtReceiptAmount=(TextView)convertView.findViewById(R.id.txtAmount);
            holder.txtAmount2=(TextView)convertView.findViewById(R.id.txtAmount2);
            holder.img_expand=(ImageView) convertView.findViewById(R.id.img_expand);
          //  holder.txtReceiptNo=(TextView) convertView.findViewById(R.id.txtReceiptNo);
            holder.txtInstNo=(TextView)convertView.findViewById(R.id.txtInstNo);
            holder.txtType=(TextView)convertView.findViewById(R.id.txtType);
            holder.txtMode=(TextView)convertView.findViewById(R.id.txtMode);
            holder.txtBank=(TextView)convertView.findViewById(R.id.txtBank);
            holder.imgPdf=(ImageView) convertView.findViewById(R.id.imgPdf);
            holder.lexpandList=(LinearLayout)convertView.findViewById(R.id.ll_expanded_layout) ;
            holder.ll_header_row = (LinearLayout)convertView.findViewById(R.id.ll_header_row) ;
            SetFonts.setFonts(context,holder.txtReceiptDate,5);
            SetFonts.setFonts(context,holder.txtReceiptAmount,5);
            SetFonts.setFonts(context,holder.txtAmount2,5);
            SetFonts.setFonts(context,holder.txtReceiptNo,5);
            SetFonts.setFonts(context,holder.txtInstNo,5);
            SetFonts.setFonts(context,holder.txtMode,5);
            SetFonts.setFonts(context,holder.txtBank,5);
            SetFonts.setFonts(context,holder.txtType,5);
            convertView.setTag(holder);
        }
        else
        holder = (MyHolder) convertView.getTag();

        rcDate=item.getZFBDT()==null?"":item.getZFBDT().toString();
        rcNo=item.getBELNR()==null?"":item.getBELNR().toString();
        holder.txtReceiptDate.setText(rcDate + " / " + rcNo);
        holder.ll_header_row.setVisibility(View.VISIBLE);
        String amountstr = item.getDMBTR()==null?"":item.getDMBTR().toString() ;
        holder.txtReceiptAmount.setText("Rs."+amountstr);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            holder.lexpandList.setBackground(ContextCompat.getDrawable(context,R.drawable.list_row));
        }
        else
        {
            holder.lexpandList.setBackground(context.getDrawable(R.drawable.list_row));
        }

        holder.txtInstNo.setText(item.getCHECT()==null?"":item.getCHECT().toString());
        holder.txtMode.setText(item.getSHKZG()==null?"":item.getSHKZG().toString());
        holder.txtType.setText(item.getANBWA()==null?"":item.getANBWA().toString());
        holder.imgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myReceiptInputModel=new MyReceiptInputModel();
                myReceiptResponseModel=new MyReceiptResponseModel();
                myReceiptInputModel.setApi_token(PreferenceHelper.getString(PreferenceHelper.API_TOKEN));
                myReceiptInputModel.setContract_no(PreferenceHelper.getString(PreferenceHelper.CONTRACT_NO));
                myReceiptInputModel.setReceipt_no(rcNo);
                myReceiptInputModel.setRequest_date(rcDate);
                Log.e("ReceiptIPMODEL",""+myReceiptInputModel.getApi_token()+"Contract no"+myReceiptInputModel.getContract_no()+"RC"+myReceiptInputModel.getReceipt_no()+"RCDATAE"+myReceiptInputModel.getRequest_date());
                  if(CommonUtils.isNetworkAvailable(context))
                {
                    CommonUtils.showProgressDialog(context,"File downloading...");
                    callDownloadData(myReceiptInputModel);
                }
                else
                {
                    Toast.makeText(context, "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
      /*  if(groupar.size()>=5)
        {
            return 5;
        }
        else
        {
            return groupar.size();
        }*/

            return groupar.size();



    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        headerTitle = (String) getGroup(groupPosition);
        grpholder = new MyGroupHolder();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_row, null);
        }
        grpholder.txtReceiptDate = (TextView) convertView
                .findViewById(R.id.txtReceiptDate);
        grpholder.txtReceiptAmount = (TextView) convertView
                .findViewById(R.id.txtAmount);
        grpholder.img_expand = (ImageView) convertView
                .findViewById(R.id.img_expand);

        if (titlear.size() == amountar.size()){
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
    public class MyHolder
    {
        TextView txtReceiptDate,txtReceiptAmount,txtAmount2,txtReceiptNo,txtInstNo,txtMode,txtType,txtBank;
        ImageView img_expand,imgPdf;
        LinearLayout lexpandList,ll_header_row,ll_daterow;
    }
    public class MyGroupHolder
    {
        TextView txtReceiptDate,txtReceiptAmount;
        ImageView img_expand;
        LinearLayout ll_header_row,ll_daterow;
    }

    public void callDownloadData(MyReceiptInputModel myReceiptInputModel)
    {
        tmflApi= ApiService.getInstance().call();
        tmflApi.getMyReceiptResponse(myReceiptInputModel).enqueue(new Callback<MyReceiptResponseModel>() {
            @Override
            public void onResponse(Call<MyReceiptResponseModel> call, Response<MyReceiptResponseModel> response) {
                if (response.body().getStatus().contains("Success")&&response.body().getFilepath() != null)
                {
                    CommonUtils.closeProgressDialog();
                    Log.e("File Path", "" + response.body().getFilepath());
                    strPathUrl = response.body().getFilepath().toString();
                    Uri uri = Uri.parse(strPathUrl);
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ReceiptPDF" + SystemClock.currentThreadTimeMillis());
                    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
                else
                {
                   if(response.body().getError().toString().contains("Unauthorized Access Token"))
                    {
                        Toast.makeText(context,"User Logged in from another Device",Toast.LENGTH_LONG).show();
                        CommonUtils.closeProgressDialog();
                        Intent loginIntent=new Intent(context, LoginActivity.class);
                        context.startActivity(loginIntent);

                    }
                    else
                   {
                       CommonUtils.closeProgressDialog();
                       Toast.makeText(context,"Server Under Maintenance,Please try after Sometime ",Toast.LENGTH_LONG).show();
                   }

                }

            }


            @Override
            public void onFailure(Call<MyReceiptResponseModel> call, Throwable t) {
                CommonUtils.closeProgressDialog();
            }
        });
    }
}

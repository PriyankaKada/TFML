package com.tfml.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.activity.ContractActivity;
import com.tfml.activity.EmiActivity;
import com.tfml.common.CommonUtils;
import com.tfml.model.ContractResponseModel.ContractModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by webwerks on 1/10/16.
 */



public class ContractsListAdapter extends ArrayAdapter<ContractModel> {

    Context mContext;
    TextView txt_contract_no,txt_rc_no,txt_product_name,txt_current_emi_amount,
            txt_last_payment_date,txt_next_due_date,txt_overdue_amount,txt_repayment_mode;

    TextView txtContractNoTerm,txt_rc_no_term,txt_terminated_name,txt_terminated_date;
    Button btn_pay_emi,btn_more_detail;
    ImageView imgDownload;
    public ContractsListAdapter(Context context, ArrayList<ContractModel> objects) {
        super(context, 0, objects);
        mContext=context;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ContractModel model=getItem(position);

        if(model==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.header_terminated_contract,parent,false);
        }else if(model.getContractStatus().equalsIgnoreCase("L")){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.row_active_contract,parent,false);
            txt_product_name= (TextView) convertView.findViewById(R.id.txt_product_name);
            txt_contract_no= (TextView) convertView.findViewById(R.id.txt_contract_no);
            txt_rc_no= (TextView) convertView.findViewById(R.id.txt_rc_no);
            txt_current_emi_amount= (TextView) convertView.findViewById(R.id.txt_current_emi_amount);
            txt_next_due_date= (TextView) convertView.findViewById(R.id.txt_next_due_date);
            txt_overdue_amount= (TextView) convertView.findViewById(R.id.txt_overdue_amount);
            txt_repayment_mode= (TextView) convertView.findViewById(R.id.txt_repayment_mode);
            txt_last_payment_date=(TextView)convertView.findViewById(R.id.txt_last_payment_date);
            btn_pay_emi=(Button) convertView.findViewById(R.id.btn_pay_emi);
            btn_more_detail=(Button)convertView.findViewById(R.id.btn_more_detail);
            imgDownload=(ImageView)convertView.findViewById(R.id.img_download);
            if(model.getProduct()!=null)
            {
               txt_product_name.setText(model.getProduct().toString());
            }
             if(model.getUsrConNo()!=null)
            {
                txt_contract_no.setText(model.getUsrConNo().toString());
            }
             if(model.getRcNumber()!=null)
            {
                txt_rc_no.setText(model.getRcNumber().toString());
            }
            if(model.getTotalCurrentDue()!=null)
            {
                txt_overdue_amount.setText(model.getTotalCurrentDue().toString());
            }
             if(model.getPdcFlag()!=null)
            {
                txt_repayment_mode.setText(model.getPdcFlag().toString());
            }
            if(model.getDueDate()!=null)
            {
                txt_next_due_date.setText(model.getDueDate().toString());
            }

            if(model.getDueAmount()!=null)
            {
                txt_current_emi_amount.setText(model.getDueAmount().toString());
            }


              btn_more_detail.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      mContext.startActivity(new Intent(mContext, EmiActivity.class));
                  }
              });
           /* imgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/

        }else if(model.getContractStatus().equalsIgnoreCase("T")){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.row_terminated_contract,parent,false);
            txtContractNoTerm=(TextView)convertView.findViewById(R.id.txtContractNoTerm);
            txt_rc_no_term=(TextView)convertView.findViewById(R.id.txt_rc_no_term);
            txt_terminated_name=(TextView)convertView.findViewById(R.id.txt_terminated_name);
            txt_terminated_date=(TextView)convertView.findViewById(R.id.txt_terminated_date);
            if(model.getContractStatusDate()!=null)
            {
                txt_terminated_date.setText(model.getContractStatusDate().toString());
            }

        }
        return convertView;
    }



//
//    class ViewHolder{
//
//        TextView txt_contract_no,txt_rc_no,txt_contract_name,txt_current_emi_amount,txt_last_payment_date,txt_next_due_date,txt_overdue_amount,txt_repayment_mode;
//        TextView txtContractNoTerm,txt_rc_no_term,txt_terminated_name,txt_terminated_date;
//
//        public ViewHolder(View view,boolean isTerminated){
//
//            if(isTerminated){
//
//            }else {
//
//            }
//        }
//
//
//
//
//
//    }
}

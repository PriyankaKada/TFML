package com.tfml.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.util.DatePickerDialog;
import com.tfml.util.DatePickerFragment;
import com.tfml.util.SetFonts;

import java.util.Calendar;


public class StatementOfAccountFragment extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateChangeListener{
     private TextView txtAccDate;
     private Button btnSubmit,btnBasicDetail,btnFinanceDetail;
     private ImageView btnDownload;
     private View view;
     private DatePickerFragment date;
     private FrameLayout frmAccDetail;
     BasicDetailFragment  frmBasicAccDetail;
     FinanceDetailFragment frmFinanceDetail;
     FragmentManager fragmentManager;
     FragmentTransaction fragmentTransaction;
     LinearLayout linAccDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_statment_of_account, container, false);
        init();
        return  view;
    }

    public void init()
    {
        txtAccDate=(TextView)view.findViewById(R.id.txt_acc_select_date);
        btnSubmit=(Button)view.findViewById(R.id.btn_submit);
        btnDownload=(ImageView)view.findViewById(R.id.img_download);
        btnBasicDetail=(Button)view.findViewById(R.id.btn_basic_detail);
        btnFinanceDetail=(Button)view.findViewById(R.id.btn_finance_detail);
        frmAccDetail=(FrameLayout)view.findViewById(R.id.frm_acc_detail);
        linAccDetail=(LinearLayout)view.findViewById(R.id.lin_acc_detail);
        SetFonts.setFonts(getActivity(),btnSubmit,2);
        date=new DatePickerFragment();
        btnSubmit.setOnClickListener(this);
        txtAccDate.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnBasicDetail.setOnClickListener(this);
        btnFinanceDetail.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_acc_select_date:
                selectDate();
                break;
            case R.id.btn_submit:
                if(!TextUtils.isEmpty(txtAccDate.toString()))
                {
                    linAccDetail.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(getActivity(),"Please Select Date",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.img_download:
                break;
            case R.id.btn_basic_detail:
                setColorButtonBasic();
                fragmentManager =getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frmBasicAccDetail = new BasicDetailFragment();
                fragmentTransaction.add(R.id.frm_acc_detail, frmBasicAccDetail);
                fragmentTransaction.commit();

                break;
            case R.id.btn_finance_detail:
                setColorButtonFinance();
                fragmentManager =getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                frmFinanceDetail = new FinanceDetailFragment();
                fragmentTransaction.add(R.id.frm_acc_detail, frmFinanceDetail);
                fragmentTransaction.commit();
                break;

        }
    }

    public void setColorButtonBasic()
    {
        btnBasicDetail.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        btnBasicDetail.setTextColor(ContextCompat.getColor(getActivity(),R.color.tab_bg));
        btnFinanceDetail.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.tab_bg));
        btnFinanceDetail.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
    }
    public void setColorButtonFinance()
    {
        btnFinanceDetail.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        btnFinanceDetail.setTextColor(ContextCompat.getColor(getActivity(),R.color.tab_bg));
        btnBasicDetail.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.tab_bg));
        btnBasicDetail.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
    }
    @Override
    public void onDateChange(String date, String picker) {
        if(picker.equalsIgnoreCase(" ")){
            txtAccDate.setText(txtAccDate.getText().toString() + " " + date);
        }
    }

    public void selectDate() {
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }
    /**
     * The Ondate.
     */
    android.app.DatePickerDialog.OnDateSetListener ondate = new android.app.DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            txtAccDate.setText((dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + year);
            //dob = ((monthOfYear + 1) > 9 ? (monthOfYear + 1) :("0"+(monthOfYear + 1))) + "/" + (dayOfMonth > 9 ? dayOfMonth : "0"+dayOfMonth) + "/" + year;

        }
    };

}

package com.tfml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.util.DatePickerDialog;
import com.tfml.util.DatePickerFragment;

import java.util.Calendar;

public class StatementOfAccountFragment extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateChangeListener{
     private TextView txtAccDate;
     private Button btnSubmit;
     private ImageView btnDownload;
     private View view;
    private DatePickerFragment date;
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
        date=new DatePickerFragment();
        btnSubmit.setOnClickListener(this);
        txtAccDate.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txt_acc_select_date:
                selectDate();
                break;
            case R.id.btn_submit:
                break;
            case R.id.img_download:
                break;

        }
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

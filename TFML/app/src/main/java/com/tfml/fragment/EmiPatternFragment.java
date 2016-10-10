package com.tfml.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.model.ContractResponseModel.ContractModel;
import com.tfml.util.PreferenceHelper;

import java.util.ArrayList;

public class EmiPatternFragment extends Fragment {

    FrameLayout frmEmiPattern;
    View view;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    EmiDetailFragment emiDetailFragment;
    Spinner spnContractNo;
    private ArrayList<String> contractLst;
    ArrayList<ContractModel> modelArrayList;
    TextView txt_repaymentmode, txt_emiamount, txt_dueamount, txt_duedate,txt_rc_no;
    int itemindex = 0;
    private String datavalue = "";
    private String rcNo = "";
    private String dueDate = "";
    private String repaymentMode = "";
    private String currentEmi = "";
    private String overdue="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_emi_pattern, container, false);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        modelArrayList =
                (ArrayList<ContractModel>) bundle.getSerializable("datamodel");
        datavalue = (String) bundle.getString("datamodelvalue");
        rcNo = (String) bundle.getString("RCNO");
        dueDate = (String) bundle.getString("DUEDATE");
        repaymentMode = (String) bundle.getString("REPAYMENT");
        currentEmi = (String) bundle.getString("CURRENTEMI");
        overdue=(String)bundle.get("OVERDUEAMT");

        init();
        return view;
    }

    public void init() {
        spnContractNo = (Spinner) view.findViewById(R.id.spnContractNo);

        contractLst = new ArrayList<String>();
        if (modelArrayList.size() > 0) {
            contractLst.add(datavalue);
            for (int i = 0; i < modelArrayList.size(); i++) {
                ContractModel model = modelArrayList.get(i);
                if (model != null)
                    contractLst.add(model.getUsrConNo());

            }
        }

        spnContractNo.setSelection(1);
//         spnContractNo.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.spinner_row,contractLst));


        ArrayAdapter<String> madapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_row, contractLst) {

            @Override
            public boolean isEnabled(int position) {
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(Color.BLACK);
                return view;
            }
        };
        madapter.setDropDownViewResource(R.layout.spinner_item);
        spnContractNo.setAdapter(madapter);
        madapter.notifyDataSetChanged();
        txt_rc_no=(TextView)view.findViewById(R.id.txt_rc_no);
        frmEmiPattern = (FrameLayout) view.findViewById(R.id.frm_emi_detail);
        txt_repaymentmode = (TextView) view.findViewById(R.id.txt_repaymentmode);
        txt_emiamount = (TextView) view.findViewById(R.id.txt_emiamount);
        txt_dueamount = (TextView) view.findViewById(R.id.txt_dueamount);
        txt_duedate = (TextView) view.findViewById(R.id.txt_duedate);
        if (rcNo != null)
            txt_rc_no.setText(rcNo);
        if (repaymentMode != null)
            txt_repaymentmode.setText(repaymentMode);
        if (dueDate != null)
            txt_duedate.setText(dueDate);
        if (currentEmi != null)
            txt_emiamount.setText(currentEmi);
        if(overdue!=null)
        {
            txt_dueamount.setText(overdue);
        }

        spnContractNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemindex = position;
                if (itemindex > 0) {
                    ContractModel model = modelArrayList.get(itemindex);
                    setData(model);

                    PreferenceHelper.insertString(PreferenceHelper.CONTRACT_NO,modelArrayList.get(position).getUsrConNo());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frm_emi_detail, new MyReceiptFragment()).commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
              loadEmiDetail();

    }


    private void setData(ContractModel model) {

        txt_rc_no.setText(model.getRcNumber()==null?"":model.getRcNumber().toString());
        txt_duedate.setText(model.getDueDate()==null?"":model.getDueDate().toString());
        txt_emiamount.setText(model.getDueAmount()==null?"":"Rs. "+model.getDueAmount().toString());
        txt_repaymentmode.setText(model.getPdcFlag()==null?"":model.getPdcFlag().toString());
        txt_dueamount.setText(model.getTotalCurrentDue()==null?"":"Rs."+model.getTotalCurrentDue().toString());

    }

    public void loadEmiDetail() {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        emiDetailFragment = new EmiDetailFragment();
        fragmentTransaction.add(R.id.frm_emi_detail, emiDetailFragment);
        fragmentTransaction.commit();

    }
}

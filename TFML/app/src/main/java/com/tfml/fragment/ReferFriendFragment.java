package com.tfml.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.tfml.R;
import com.tfml.activity.SchemesActivity;
import com.tfml.auth.TfmlApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.Validation;
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendInputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReferFriendFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener
{
    private EditText txtFirstName,txtLastName,txtMobileNumber,txtLandlineNumber,txtEmailAddress,txtOrgnizationName;
    private Spinner spnProduct,spSelectBranchState,spSelectBranchCity,spSelectBranch,spSelectCity,spSelectState,spSelectPinCode;
    private CheckBox chkLeadTypeIndividual,chkLeadTypeOrganizational,chkVecTypeCommercial,chkVechTypeRefinance,ChkVechPassanger;
    private Button btnCancel,btnReferFriends;
    private List<String > proList,branchStateList,branchCityList,branchList,cityList,stateList,pinCodeList;
    String strLeadTypechk="";
    String strVechicalType="";
    TfmlApi tfmlApi;
    ReferFriendInputModel inputReferFriendModel;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_refer_friend, container, false);
        tfmlApi = ApiService.getInstance().call();
        init();
        proList=new ArrayList<String>();
        proList.add("Select Product");
        proList.add("0245");
        proList.add("0246");
        proList.add("0247");
        proList.add("0250");
        spnProduct.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,proList));
        branchStateList=new ArrayList<String>();
        branchStateList.add("Select Branch State");
        branchStateList.add("MH");
        branchStateList.add("KA");
        branchStateList.add("UP");
        branchStateList.add("Kerla");
        branchStateList.add("AP");
        spSelectBranchState.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,branchStateList));
        branchCityList=new ArrayList<String>();
        branchCityList.add("Select Branch City");
        branchCityList.add("Mumbai");
        branchCityList.add("Benglaru");
        branchCityList.add("Lucknow");
        branchCityList.add("TiruanantPuram");
        branchCityList.add("Hydrabad");
        spSelectBranchCity.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,branchCityList));
        branchList=new ArrayList<String>();
        branchList.add("Select Branch");
        branchList.add("Mumbai");
        branchList.add("Hydrabad");
        branchCityList.add("Kerla");
        spSelectBranch.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,branchList));
        cityList=new ArrayList<String>();
        cityList.add("Select City");
        cityList.add("Mumabai");
        cityList.add("Delhi");
        cityList.add("Kolkatta");
        cityList.add("Keral");
        spSelectCity.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,cityList));
        stateList=new ArrayList<String>();
        stateList.add("Select State");
        stateList.add("MH");
        stateList.add("KA");
        stateList.add("MP");
        stateList.add("AP");
        spSelectState.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,stateList));
        pinCodeList=new ArrayList<String>();
        pinCodeList.add("Select Pincode");
        pinCodeList.add("123456");
        pinCodeList.add("987654");
        pinCodeList.add("876567");
        spSelectPinCode.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,pinCodeList));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void init()
    {
        txtFirstName=(EditText)view.findViewById(R.id.edt_first_name);
        txtLastName=(EditText)view.findViewById(R.id.edt_last_name);
        txtMobileNumber=(EditText)view.findViewById(R.id.edt_mobile_no);
        txtLandlineNumber=(EditText)view.findViewById(R.id.edt_landline_no);
        txtEmailAddress=(EditText)view.findViewById(R.id.edt_email_address);
        txtOrgnizationName=(EditText)view.findViewById(R.id.edt_orgnization_name);
        spnProduct=(Spinner)view.findViewById(R.id.sp_select_product);
        spnProduct.setOnItemSelectedListener(this);
        spSelectBranchState=(Spinner)view.findViewById(R.id.sp_select_branch_state);
        spSelectBranchCity=(Spinner)view.findViewById(R.id.sp_select_branch_city);
        spSelectBranch=(Spinner)view.findViewById(R.id.sp_select_branch);
        spSelectState=(Spinner)view.findViewById(R.id.sp_select_state);
        spSelectCity=(Spinner)view.findViewById(R.id.sp_select_city);
        spSelectPinCode=(Spinner)view.findViewById(R.id.sp_select_pincode);
        chkLeadTypeIndividual=(CheckBox)view.findViewById(R.id.chk_individual);
        chkLeadTypeOrganizational=(CheckBox)view.findViewById(R.id.chk_organization);
        chkVecTypeCommercial=(CheckBox)view.findViewById(R.id.chk_commercial);
        chkVechTypeRefinance=(CheckBox)view.findViewById(R.id.chk_refinance);
        ChkVechPassanger=(CheckBox)view.findViewById(R.id.chk_passenger);
        btnCancel=(Button)view.findViewById(R.id.btn_cancel);
        btnReferFriends=(Button)view.findViewById(R.id.btn_refer_friends);
        spnProduct.setSelection(1);
        spSelectCity.setSelection(1);
        spSelectState.setSelection(1);
        spSelectPinCode.setSelection(1);
        spSelectBranch.setSelection(1);
        inputReferFriendModel=new ReferFriendInputModel();
        if(chkLeadTypeIndividual.isChecked())
        {
            strLeadTypechk="Individual";
            inputReferFriendModel.setLeadType(strLeadTypechk);
        }
        if(chkLeadTypeOrganizational.isChecked())
        {
            strLeadTypechk="Organizational";
            inputReferFriendModel.setLeadType(strLeadTypechk);
        }
        // txtOrgnizationName.setText(strLeadTypechk);
        if(chkVecTypeCommercial.isChecked())
        {
            strVechicalType="Commercial";
            inputReferFriendModel.setVehicalType(strVechicalType);
        }
        if(chkVechTypeRefinance.isChecked())
        {
            strVechicalType="Refinance";
            inputReferFriendModel.setVehicalType(strVechicalType);
        }
        if(ChkVechPassanger.isChecked())
        {
            strVechicalType="Passanger";
            inputReferFriendModel.setVehicalType(strVechicalType);
        }

        btnCancel.setOnClickListener(this);
        btnReferFriends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_cancel:
                getActivity().finish();
                break;
            case R.id.btn_refer_friends:
                if(CommonUtils.isNetworkAvailable(getActivity()))
                {
                    callSubmit();

                }
                else
                {
                    CommonUtils.showAlert1(getActivity(),"","No Internet Connection",false);
                }
                break;

        }
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(txtFirstName,"Please enter your First name")) ret = false;
        if (!Validation.hasText(txtLastName,"Please enter your Last name")) ret = false;
        if(!Validation.hasText(txtMobileNumber,"Please enter mobile number")) ret=false;
        if (!Validation.hasText(txtLandlineNumber,"Please enter landline number")) ret = false;
        if(!Validation.isValidEmail(txtEmailAddress.getText().toString()))ret=false;
        return ret;
    }

    public void callSubmit()
    {
        if(checkValidation())
        {
            CommonUtils.showProgressDialog(getActivity(),"Pleas Wait.....");
            callReferFriendService();
            loadReferFriendsResponse(inputReferFriendModel);

        }else
        {
            CommonUtils.showAlert1(getActivity(),"","Please Fill the Required Detail",false);
        }
    }

    public void callReferFriendService()
    {

        inputReferFriendModel.setFirstName(txtFirstName.getText().toString());
        inputReferFriendModel.setLastName(txtLastName.getText().toString());
        inputReferFriendModel.setMobileNumber(txtMobileNumber.getText().toString());
        inputReferFriendModel.setLandlineNumber(txtLandlineNumber.getText().toString());
        inputReferFriendModel.setEmailAddress(txtEmailAddress.getText().toString());
        inputReferFriendModel.setProductId(spnProduct.getSelectedItem().toString());
        inputReferFriendModel.setBranchState(spSelectBranchState.getSelectedItem().toString());
        inputReferFriendModel.setBranchCity(spSelectBranchCity.getSelectedItem().toString());
        inputReferFriendModel.setBranch(spSelectBranch.getSelectedItem().toString());
        inputReferFriendModel.setEmailAddress(txtEmailAddress.getText().toString());
        inputReferFriendModel.setState(spSelectState.getSelectedItem().toString());
        inputReferFriendModel.setCity(spSelectCity.getSelectedItem().toString());
        inputReferFriendModel.setPincode(spSelectPinCode.getSelectedItem().toString());
        inputReferFriendModel.setLeadType(strLeadTypechk);
        inputReferFriendModel.setOrganisationName(txtOrgnizationName.getText().toString());
        inputReferFriendModel.setVehicalType(strVechicalType);
        inputReferFriendModel.setReferedBy("2");

    }

    public void loadReferFriendsResponse(ReferFriendInputModel inputReferFriendModel)
    {
        Log.e("getFirstName",inputReferFriendModel.getFirstName());
        Log.e("getLastName",inputReferFriendModel.getLastName());
        Log.e("getMobileNumber",inputReferFriendModel.getMobileNumber());
        Log.e("getLandlineNumber",inputReferFriendModel.getLandlineNumber());
        Log.e("getProductId",inputReferFriendModel.getProductId());
        Log.e("getBranch",inputReferFriendModel.getBranch());
        Log.e("getBranchCity",inputReferFriendModel.getBranchCity());
        Log.e("getBranchState",inputReferFriendModel.getBranchState());
        Log.e("getEmailAddress",inputReferFriendModel.getEmailAddress());
        Log.e("getLeadType",strLeadTypechk);
        Log.e("getPincode",inputReferFriendModel.getPincode());
        Log.e("getOrganisationName",inputReferFriendModel.getOrganisationName());
        Log.e("getVehicalType",strVechicalType);
        Log.e("getCity",inputReferFriendModel.getCity());
        Log.e("getState",inputReferFriendModel.getState());
        Log.e("getReferFriends",inputReferFriendModel.getReferedBy());

        tfmlApi.getFriendResponse(inputReferFriendModel).enqueue(new Callback<ReferFriendResponseModel>() {
            @Override
            public void onResponse(Call<ReferFriendResponseModel> call, Response<ReferFriendResponseModel> response) {
                CommonUtils.closeProgressDialog();
                if(response.body().getStatus().contains("success"))
                {
                    Log.e("getFriendResponse",response.body().getStatus());
                    CommonUtils.showAlert1(getActivity(),"Refer Friends","Thanks for this approach",false);
                }
                else
                {
                    Log.e("getErr",response.body().getErrors().get(0));
                }

            }

            @Override
            public void onFailure(Call<ReferFriendResponseModel> call, Throwable t) {
                CommonUtils.closeProgressDialog();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

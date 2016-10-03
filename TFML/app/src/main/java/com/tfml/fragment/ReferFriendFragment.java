package com.tfml.fragment;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;
import com.tfml.common.Validation;
import com.tfml.model.branchResponseModel.BranchResponseModel;
import com.tfml.model.branchResponseModel.InputBranchModel;
import com.tfml.model.cityResponseModel.BranchCityResponseModel;
import com.tfml.model.cityResponseModel.CityResponseModel;
import com.tfml.model.cityResponseModel.InputCityModel;
import com.tfml.model.productResponseModel.ProductListResponseModel;
import com.tfml.model.referFriendResponseModel.ReferFriendInputModel;
import com.tfml.model.referFriendResponseModel.ReferFriendResponseModel;
import com.tfml.model.schemesResponseModel.Datum;
import com.tfml.model.schemesResponseModel.SchemesResponse;
import com.tfml.model.stateResponseModel.BranchStateResponseModel;
import com.tfml.model.stateResponseModel.StateResponseModel;
import com.tfml.util.PreferenceHelper;
import com.tfml.util.SetFonts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReferFriendFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    private EditText txtFirstName, txtLastName, txtMobileNumber, txtLandlineNumber, txtEmailAddress, txtOrgnizationName, txtPincode;
    private Spinner spnProduct, spSelectBranchState, spSelectBranchCity, spSelectBranch, spSelectCity, spSelectState, spOffers;
    private RadioButton rdbLeadTypeIndividual, rdbLeadTypeOrganizational, rdbVecTypeCommercial, rdbVechTypeRefinance, rdbVechPassanger;
    private Button btnCancel, btnReferFriends;
    private List<String> branchStateList, branchCityList, branchList, cityList, stateList;
    private RadioGroup radioGroupLeadType, radioGroupVehicleType;
    String strLeadTypechk = "";
    String strVechicalType = "";
    TmflApi tmflApi;
    ReferFriendInputModel inputReferFriendModel;
    InputCityModel inputCityModel;
    InputBranchModel inputBranchModel;
    String productCode, branchStateCode, branchCityCode, branchCode, stateCode, cityCode;
    List<Datum> arDatumList;
    SchemesResponse response;
    ArrayList<String> spOfferList;
    String strUserid;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_refer_friend, container, false);
        tmflApi = ApiService.getInstance().call();
        init();

        response = (SchemesResponse) PreferenceHelper.getObject("Scheme response", SchemesResponse.class);

        arDatumList = response.getData();

        spOfferList = new ArrayList<>();

        for (int i = 0; i < arDatumList.size(); i++) {
            spOfferList.add(response.getData().get(i).getTitle());
        }
        spOffers.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spOfferList));

        branchStateList = new ArrayList<String>();
        branchStateList.add("Select Branch State");
        spSelectBranchState.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, branchStateList));
        branchCityList = new ArrayList<String>();
        branchCityList.add("Select Branch City");
        spSelectBranchCity.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, branchCityList));

        branchList = new ArrayList<String>();
        branchList.add("Select Branch");
        spSelectBranch.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, branchList));

        stateList = new ArrayList<String>();
        stateList.add("Select State");
        spSelectState.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stateList));

        cityList = new ArrayList<String>();
        cityList.add("Select City");
        spSelectCity.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cityList));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void init() {
        txtFirstName = (EditText) view.findViewById(R.id.edt_first_name);
        txtLastName = (EditText) view.findViewById(R.id.edt_last_name);
        txtMobileNumber = (EditText) view.findViewById(R.id.edt_mobile_no);
        txtLandlineNumber = (EditText) view.findViewById(R.id.edt_landline_no);
        txtEmailAddress = (EditText) view.findViewById(R.id.edt_email_address);
        txtOrgnizationName = (EditText) view.findViewById(R.id.edt_orgnization_name);
        txtPincode = (EditText) view.findViewById(R.id.edt_pincode);
        spnProduct = (Spinner) view.findViewById(R.id.sp_select_product);
        spnProduct.setOnItemSelectedListener(this);
        CommonUtils.showProgressDialog(getActivity(), "Please Wait.....");
        SocialUtil.getProductListData(getActivity(), spnProduct);
        spSelectBranchState = (Spinner) view.findViewById(R.id.sp_select_branch_state);
        spSelectBranchState.setOnItemSelectedListener(this);
        CommonUtils.showProgressDialog(getActivity(), "Please Wait.....");
        SocialUtil.getBranchStateListData(getActivity(), spSelectBranchState, "Select branch state");
        spSelectBranchCity = (Spinner) view.findViewById(R.id.sp_select_branch_city);
        spSelectBranchCity.setOnItemSelectedListener(this);
        spSelectBranch = (Spinner) view.findViewById(R.id.sp_select_branch);
        spSelectBranch.setOnItemSelectedListener(this);
        spSelectState = (Spinner) view.findViewById(R.id.sp_select_state);
        spSelectState.setOnItemSelectedListener(this);
        CommonUtils.showProgressDialog(getActivity(), "Please Wait.....");
        SocialUtil.getStateListData(getActivity(), spSelectState, "Select state");
        spSelectCity = (Spinner) view.findViewById(R.id.sp_select_city);
        spSelectCity.setOnItemSelectedListener(this);
        spOffers = (Spinner) view.findViewById(R.id.sp_offers);
        radioGroupLeadType = (RadioGroup) view.findViewById(R.id.radio_group_lead_type);
        radioGroupVehicleType = (RadioGroup) view.findViewById(R.id.radio_group_vehicle_type);
        radioGroupLeadType.setOnCheckedChangeListener(this);
        radioGroupVehicleType.setOnCheckedChangeListener(this);
        rdbLeadTypeIndividual = (RadioButton) view.findViewById(R.id.rdb_individual);
        rdbLeadTypeOrganizational = (RadioButton) view.findViewById(R.id.rdb_organization);
        rdbVecTypeCommercial = (RadioButton) view.findViewById(R.id.rdb_commercial);
        rdbVechTypeRefinance = (RadioButton) view.findViewById(R.id.rdb_refinance);
        rdbVechPassanger = (RadioButton) view.findViewById(R.id.rdb_passenger);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnReferFriends = (Button) view.findViewById(R.id.btn_refer_friends);
        SetFonts.setFonts(getActivity(), btnCancel, 2);
        SetFonts.setFonts(getActivity(), btnReferFriends, 2);

        spnProduct.setSelection(1);
        spSelectCity.setSelection(1);
        spSelectState.setSelection(1);
        spSelectBranch.setSelection(1);
        inputReferFriendModel = new ReferFriendInputModel();
        inputCityModel = new InputCityModel();
        inputBranchModel = new InputBranchModel();
        btnCancel.setOnClickListener(this);
        btnReferFriends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                getActivity().finish();
                break;
            case R.id.btn_refer_friends:
                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    callSubmit();
                } else {
                    CommonUtils.showAlert1(getActivity(), "", "No Internet Connection", false);
                }
                break;

        }
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(txtFirstName, "Please enter your First name")) ret = false;
        if (!Validation.hasText(txtLastName, "Please enter your Last name")) ret = false;
        if (!Validation.hasText(txtMobileNumber, "Please enter mobile number")) ret = false;
        if (!Validation.hasText(txtLandlineNumber, "Please enter landline number")) ret = false;
        if (!Validation.isValidEmail(txtEmailAddress.getText().toString())) ret = false;
        return ret;
    }

    public void callSubmit() {
        if (checkValidation()) {
            CommonUtils.showProgressDialog(getActivity(), "Pleas Wait.....");
            callReferFriendService();
            loadReferFriendsResponse(inputReferFriendModel);

        } else {
            CommonUtils.showAlert1(getActivity(), "", "Please Fill the Required Detail", false);
        }
    }

    public void callReferFriendService() {
        strUserid=PreferenceHelper.getString(PreferenceHelper.USER_ID);
        inputReferFriendModel.setUserId(strUserid);
        inputReferFriendModel.setFirstName(txtFirstName.getText().toString());
        inputReferFriendModel.setLastName(txtLastName.getText().toString());
        inputReferFriendModel.setMobileNumber(txtMobileNumber.getText().toString());
        inputReferFriendModel.setLandlineNumber(txtLandlineNumber.getText().toString());
        inputReferFriendModel.setEmailAddress(txtEmailAddress.getText().toString());
        if (productCode != null && productCode != "-1") {
            inputReferFriendModel.setProductId(productCode);
        } else {
            Toast.makeText(getContext(), "Please Select Product Type", Toast.LENGTH_SHORT).show();
        }
        if (branchStateCode != null && branchStateCode != "-1") {
            inputReferFriendModel.setBranchState(branchStateCode);

        } else {
            Toast.makeText(getContext(), "Please Select Branch State", Toast.LENGTH_SHORT).show();
        }
        if (branchCityCode != null && branchCityCode != "-1") {
            inputReferFriendModel.setBranchCity(branchCityCode);
        } else {
            Toast.makeText(getContext(), "Please Select Branch City", Toast.LENGTH_SHORT).show();
        }

        if (branchCode != null && branchCode != "-1") {
            inputReferFriendModel.setBranch(branchCode);
        } else {
            Toast.makeText(getContext(), "Please Select Branch", Toast.LENGTH_SHORT).show();
        }

        if (stateCode != null && stateCode != "-1") {
            inputReferFriendModel.setState(stateCode);

        } else {
            Toast.makeText(getContext(), "Please Select State", Toast.LENGTH_SHORT).show();
        }


        if (cityCode != null && cityCode != "-1") {
            inputReferFriendModel.setCity(cityCode);
        } else {
            Toast.makeText(getContext(), "Please Select City", Toast.LENGTH_SHORT).show();
        }


        inputReferFriendModel.setEmailAddress(txtEmailAddress.getText().toString());
        inputReferFriendModel.setPincode(txtPincode.getText().toString());
        inputReferFriendModel.setLeadType(strLeadTypechk);
        inputReferFriendModel.setOrganisationName(txtOrgnizationName.getText().toString());
        inputReferFriendModel.setVehicalType(strVechicalType);
        inputReferFriendModel.setReferedBy("2");

    }

    public void loadReferFriendsResponse(ReferFriendInputModel inputReferFriendModel) {
        Log.e("getFirstName", inputReferFriendModel.getFirstName());
        Log.e("getLastName", inputReferFriendModel.getLastName());
        Log.e("getMobileNumber", inputReferFriendModel.getMobileNumber());
        Log.e("getLandlineNumber", inputReferFriendModel.getLandlineNumber());
        Log.e("getProductId", inputReferFriendModel.getProductId());
        Log.e("getBranch", inputReferFriendModel.getBranch());
        Log.e("getBranchCity", inputReferFriendModel.getBranchCity());
        Log.e("getBranchState", inputReferFriendModel.getBranchState());
        Log.e("getEmailAddress", inputReferFriendModel.getEmailAddress());
        Log.e("getLeadType", strLeadTypechk);
        Log.e("getPincode", inputReferFriendModel.getPincode());
        Log.e("getOrganisationName", inputReferFriendModel.getOrganisationName());
        Log.e("getVehicalType", strVechicalType);
        Log.e("getCity", inputReferFriendModel.getCity());
        Log.e("getState", inputReferFriendModel.getState());
        Log.e("getReferFriends", inputReferFriendModel.getReferedBy());

        tmflApi.getFriendResponse(inputReferFriendModel).enqueue(new Callback<ReferFriendResponseModel>() {
            @Override
            public void onResponse(Call<ReferFriendResponseModel> call, Response<ReferFriendResponseModel> response) {
                CommonUtils.closeProgressDialog();
                if (response.body().getStatus().contains("success")) {
                    Log.e("getFriendResponse", response.body().getStatus());
                    CommonUtils.showAlert1(getActivity(), "Refer Friends", "Thanks for this approach", false);
                } else {
                    Log.e("getErr", response.body().getErrors().get(0));
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
        switch (parent.getId()) {
            case R.id.sp_select_product:
                if (position != 0) {
                    productCode = ((ProductListResponseModel) parent.getItemAtPosition(position)).getProdProductid();
                }
                break;
            case R.id.sp_select_branch_state:

                if (position != 0) {
                    branchStateCode = ((BranchStateResponseModel) parent.getItemAtPosition(position)).getTerrTerritoryid();
                    Log.e("BRANCHSTATECODE", branchStateCode);
                    inputCityModel.setStateId(branchStateCode);
                    CommonUtils.showProgressDialog(getActivity(), "Please Wait.....");
                    SocialUtil.getBranchCityListData(getActivity(), spSelectBranchCity, inputCityModel, "Select Branch City");
                    break;
                }
            case R.id.sp_select_branch_city:
                if (position != 0) {
                    branchCityCode = ((BranchCityResponseModel) parent.getItemAtPosition(position)).getTerrTerritoryid();
                    inputBranchModel.setCityId(branchCityCode);
                    CommonUtils.showProgressDialog(getActivity(), "Please Wait.....");
                    SocialUtil.getBranchList(getActivity(), spSelectBranch, inputBranchModel, "Select Branch");
                    break;
                }
            case R.id.sp_select_branch:
                if (position != 0) {
                    branchCode = ((BranchResponseModel) parent.getItemAtPosition(position)).getTerrTerritoryid();

                }
                break;
            case R.id.sp_select_state:
                if (position != 0) {
                    stateCode = ((StateResponseModel) parent.getItemAtPosition(position)).getId();
                    Log.e("STATECODE", stateCode);
                    inputCityModel.setStateId(stateCode);
                    CommonUtils.showProgressDialog(getActivity(), "Please Wait.....");
                    SocialUtil.getCityListData(getActivity(), spSelectCity, inputCityModel, "Select City");
                }

                break;
            case R.id.sp_select_city:
                if (position != 0) {
                    cityCode = ((CityResponseModel) parent.getItemAtPosition(position)).getId();
                    Log.e("BranchCityCode", cityCode);
                    inputBranchModel.setCityId(cityCode);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            case R.id.rdb_organization:
                if (checkedId == R.id.rdb_organization) {
                    strLeadTypechk = "Organizational";
                    inputReferFriendModel.setLeadType(strLeadTypechk);

                }
                break;
            case R.id.rdb_individual:
                if (checkedId == R.id.rdb_individual) {
                    strLeadTypechk = "Individual";
                    inputReferFriendModel.setLeadType(strLeadTypechk);
                }
                break;
            case R.id.rdb_commercial:
                if (checkedId == R.id.rdb_commercial) {
                    strVechicalType = "Commercial";
                    inputReferFriendModel.setVehicalType(strVechicalType);
                }
                break;
            case R.id.rdb_refinance:
                if (checkedId == R.id.rdb_refinance) {
                    strVechicalType = "Refinance";
                    inputReferFriendModel.setVehicalType(strVechicalType);
                }
                break;
            case R.id.rdb_passenger:
                if (checkedId == R.id.rdb_passenger) {
                    strVechicalType = "Passanger";
                    inputReferFriendModel.setVehicalType(strVechicalType);
                }
                break;
        }
    }
}

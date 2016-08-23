package com.tfml.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.EGLDisplay;
import android.os.Build;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.activity.SchemesActivity;
import com.tfml.adapter.ProductAdapter;
import com.tfml.auth.TfmlApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;
import com.tfml.common.Validation;
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.productResponseModel.ProductListResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApplyLoanFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener{

   private EditText txtFirstName,txtLastName,txtMobileNumber,txtLandlineNumber,txtEmailAddress,txtOrgnizationName;
   private Spinner spnProduct,spSelectBranchState,spSelectBranchCity,spSelectBranch,spSelectCity,spSelectState,spSelectPinCode;
   private   CheckBox chkLeadTypeIndividual,chkLeadTypeOrganizational,chkVecTypeCommercial,chkVechTypeRefinance,ChkVechPassanger;
   private   Button btnCancel,btnApplyLoan;
   private View view;
   private List<String >branchStateList,branchCityList,branchList,cityList,stateList,pinCodeList;
    String strLeadTypechk="";
    String strVechicalType="";
    TfmlApi tfmlApi;
    InputModel inputLoanModel;
    String ProductCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_apply_loan, container, false);
        tfmlApi = ApiService.getInstance().call();
        init();
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
        cityList.add("Mumbai");
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
        SocialUtil.getProductListData(getActivity(),spnProduct);
        //getProductListData();
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
        btnApplyLoan=(Button)view.findViewById(R.id.btn_apply_laon);
        spnProduct.setSelection(1);
        spSelectCity.setSelection(1);
        spSelectState.setSelection(1);
        spSelectPinCode.setSelection(1);
        spSelectBranch.setSelection(1);
        inputLoanModel=new InputModel();
        if(chkLeadTypeIndividual.isChecked())
        {
            strLeadTypechk="Individual";
            inputLoanModel.setLeadType(strLeadTypechk);
        }
        if(chkLeadTypeOrganizational.isChecked())
        {
            strLeadTypechk="Organizational";
            inputLoanModel.setLeadType(strLeadTypechk);
        }
       // txtOrgnizationName.setText(strLeadTypechk);
        if(chkVecTypeCommercial.isChecked())
        {
            strVechicalType="Commercial";
            inputLoanModel.setVehicalType(strVechicalType);
        }
        if(chkVechTypeRefinance.isChecked())
        {
            strVechicalType="Refinance";
            inputLoanModel.setVehicalType(strVechicalType);
        }
        if(ChkVechPassanger.isChecked())
        {
            strVechicalType="Passanger";
            inputLoanModel.setVehicalType(strVechicalType);
        }

        btnCancel.setOnClickListener(this);
        btnApplyLoan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_cancel:
                getActivity().finish();
                break;
            case R.id.btn_apply_laon:
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
          callApplyLoanService();
          loadApplyLoanResponse(inputLoanModel);

      }else
      {
          CommonUtils.showAlert1(getActivity(),"","Please Fill the Required Detail",false);
      }
    }

    public void callApplyLoanService()
    {

        inputLoanModel.setFirstName(txtFirstName.getText().toString());
        inputLoanModel.setLastName(txtLastName.getText().toString());
        inputLoanModel.setMobileNumber(txtMobileNumber.getText().toString());
        inputLoanModel.setLandlineNumber(txtLandlineNumber.getText().toString());
        inputLoanModel.setEmailAddress(txtEmailAddress.getText().toString());
        if(ProductCode!=null && ProductCode !="-1")
        {
            inputLoanModel.setProductId(ProductCode);
        }
        else
        {
            Toast.makeText(getContext(),"Please Select Product Type",Toast.LENGTH_SHORT).show();
        }

        inputLoanModel.setBranchState(spSelectBranchState.getSelectedItem().toString());
        inputLoanModel.setBranchCity(spSelectBranchCity.getSelectedItem().toString());
        inputLoanModel.setBranch(spSelectBranch.getSelectedItem().toString());
        inputLoanModel.setEmailAddress(txtEmailAddress.getText().toString());
        inputLoanModel.setState(spSelectState.getSelectedItem().toString());
        inputLoanModel.setCity(spSelectCity.getSelectedItem().toString());
        inputLoanModel.setPincode(spSelectPinCode.getSelectedItem().toString());
        inputLoanModel.setLeadType(strLeadTypechk);
        inputLoanModel.setOrganisationName(txtOrgnizationName.getText().toString());
        inputLoanModel.setVehicalType(strVechicalType);

    }

    public void loadApplyLoanResponse(InputModel inputmodel)
    {
       Log.e("getFirstName",inputmodel.getFirstName());
        Log.e("getLastName",inputmodel.getLastName());
        Log.e("getMobileNumber",inputmodel.getMobileNumber());
        Log.e("getLandlineNumber",inputmodel.getLandlineNumber());
        Log.e("getProductId",inputmodel.getProductId());
        Log.e("getBranch",inputmodel.getBranch());
        Log.e("getBranchCity",inputmodel.getBranchCity());
        Log.e("getBranchState",inputmodel.getBranchState());
        Log.e("getEmailAddress",inputmodel.getEmailAddress());
        Log.e("getLeadType",inputmodel.getLeadType());
        Log.e("getPincode",inputmodel.getPincode());
        Log.e("getLeadType",inputmodel.getLeadType());
        Log.e("getOrganisationName",inputmodel.getOrganisationName());
        Log.e("getVehicalType",inputmodel.getVehicalType());
        Log.e("getCity",inputmodel.getCity());
        Log.e("getState",inputmodel.getState());
        tfmlApi.getApplyLoanResponse(inputmodel).enqueue(new Callback<ApplyLoanResponse>() {
            @Override
            public void onResponse(Call<ApplyLoanResponse> call, Response<ApplyLoanResponse> response) {
                CommonUtils.closeProgressDialog();
                if(response.body().getStatus().contains("success"))
                {
                    Log.e("getApplyLoanResponse",response.body().getStatus());
                    CommonUtils.showAlert1(getActivity(),"","Apply Loan Succesfully",false);
                }
                else
                {
                    Log.e("getApplyloanErr",response.body().getErrors().get(0));
                }

            }

            @Override
            public void onFailure(Call<ApplyLoanResponse> call, Throwable t) {
                CommonUtils.closeProgressDialog();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0)
        {
            ProductCode=((ProductListResponseModel)parent.getItemAtPosition(position)).getProdProductid();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

 /* public void getProductListData()
  {
      Log.e("ProductResponse","getProduct");

      if( CommonUtils.isNetworkAvailable(getActivity()))
      {
          tfmlApi.getProductList().enqueue(new Callback<List<ProductListResponseModel>>() {
              @Override
              public void onResponse(Call<List<ProductListResponseModel>> call, Response<List<ProductListResponseModel>> response) {
                Log.e("Response",response.body().size()+"");

                  if(response!=null)
                  {
                      ProductListResponseModel model =new ProductListResponseModel();
                      model.setProdName("Select product");
                      model.setProdProductid("-1");
                      response.body().add(0,model);
                      spnProduct.setAdapter(new ProductAdapter(getActivity(),response.body()));
                      for(int i=0;i<response.body().size();i++)
                      {
                          if (response.equals(response.body().get(i).getProdName())) {
                              spnProduct.setSelection(i);

                          }
                      }

                  }

              }

              @Override
              public void onFailure(Call<List<ProductListResponseModel>> call, Throwable t) {
                  Log.e("Response",t.getMessage()+"");
              }
          });
      }
      else
      {
          CommonUtils.showAlert1(getActivity(),"","No Internet Connection",false);
      }
  }*/



}

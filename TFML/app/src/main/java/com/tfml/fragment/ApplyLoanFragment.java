package com.tfml.fragment;

import android.content.Intent;
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
import com.tfml.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tfml.model.applyLoanResponseModel.InputModel;
import com.tfml.model.branchResponseModel.BranchResponseModel;
import com.tfml.model.branchResponseModel.InputBranchModel;
import com.tfml.model.cityResponseModel.BranchCityResponseModel;
import com.tfml.model.cityResponseModel.CityResponseModel;
import com.tfml.model.cityResponseModel.InputCityModel;
import com.tfml.model.productResponseModel.ProductListResponseModel;
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


public class ApplyLoanFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {

	String strLeadTypechk  = "";
	String strVechicalType = "";
	TmflApi          tmflApi;
	InputModel       inputLoanModel;
	InputCityModel   inputCityModel;
	InputBranchModel inputBranchModel;
	String           productCode, branchStateCode, branchCityCode, branchCode, stateCode, cityCode, strOfferId, strUserid;
	List< Datum >   arDatumList;
	SchemesResponse response;
	private EditText txtFirstName, txtLastName, txtMobileNumber, txtLandlineNumber, txtEmailAddress, txtOrgnizationName, txtPincode;
	private Spinner spnProduct, spSelectBranchState, spSelectBranchCity, spSelectBranch, spSelectCity, spSelectState, spOffers;
	private RadioButton rdbLeadTypeIndividual, rdbLeadTypeOrganizational, rdbVecTypeCommercial, rdbVechTypeRefinance, rdbVechPassanger;
	private Button btnCancel, btnApplyLoan;
	private View       view;
	private RadioGroup radioGroupLeadType, radioGroupVehicleType;
	private List< String > branchStateList, branchCityList, branchList, cityList, stateList, spOfferList;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_apply_loan, container, false );
		tmflApi = ApiService.getInstance().call();
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();

		response = ( SchemesResponse ) PreferenceHelper.getObject( "Scheme response", SchemesResponse.class );

		arDatumList = response.getData();

		init();
		spOfferList = new ArrayList<>();

		for ( int i = 0; i < arDatumList.size(); i++ ) {
			spOfferList.add( response.getData().get( i ).getTitle() );
		}
		spOffers.setAdapter( new ArrayAdapter< Datum >( getActivity(), R.layout.layout_spinner_textview, arDatumList ) );
		branchStateList = new ArrayList< String >();
		branchStateList.add( "Select Branch State" );
		spSelectBranchState.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.layout_spinner_textview, branchStateList ) );
		branchCityList = new ArrayList< String >();
		branchCityList.add( "Select Branch City" );
		spSelectBranchCity.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.layout_spinner_textview, branchCityList ) );

		branchList = new ArrayList< String >();
		branchList.add( "Select Branch" );
		spSelectBranch.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.layout_spinner_textview, branchList ) );

		stateList = new ArrayList< String >();
		stateList.add( "Select State" );
		spSelectState.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.layout_spinner_textview, stateList ) );

		cityList = new ArrayList< String >();
		cityList.add( "Select City" );
		spSelectCity.setAdapter( new ArrayAdapter< String >( getActivity(), R.layout.layout_spinner_textview, cityList ) );


		return view;
	}


	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
	}

	public void init() {
		txtFirstName = ( EditText ) view.findViewById( R.id.edt_first_name );
		txtLastName = ( EditText ) view.findViewById( R.id.edt_last_name );
		txtMobileNumber = ( EditText ) view.findViewById( R.id.edt_mobile_no );
		txtLandlineNumber = ( EditText ) view.findViewById( R.id.edt_landline_no );
		txtEmailAddress = ( EditText ) view.findViewById( R.id.edt_email_address );
		txtOrgnizationName = ( EditText ) view.findViewById( R.id.edt_orgnization_name );
		txtPincode = ( EditText ) view.findViewById( R.id.edt_pincode );
		spnProduct = ( Spinner ) view.findViewById( R.id.sp_select_product );
		spnProduct.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( getActivity(), "Please Wait....." );
		SocialUtil.getProductListData( getActivity(), spnProduct );
		spSelectBranchState = ( Spinner ) view.findViewById( R.id.sp_select_branch_state );
		spSelectBranchState.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( getActivity(), "Please Wait....." );
		SocialUtil.getBranchStateListData( getActivity(), spSelectBranchState, "Select branch state" );
		spSelectBranchCity = ( Spinner ) view.findViewById( R.id.sp_select_branch_city );
		spSelectBranchCity.setOnItemSelectedListener( this );
		spSelectBranch = ( Spinner ) view.findViewById( R.id.sp_select_branch );
		spSelectBranch.setOnItemSelectedListener( this );
		spSelectState = ( Spinner ) view.findViewById( R.id.sp_select_state );
		spSelectState.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( getActivity(), "Please Wait....." );
		SocialUtil.getStateListData( getActivity(), spSelectState, "Select state" );
		spSelectCity = ( Spinner ) view.findViewById( R.id.sp_select_city );
		spSelectCity.setOnItemSelectedListener( this );
		spOffers = ( Spinner ) view.findViewById( R.id.sp_offers );
		spOffers.setOnItemSelectedListener( this );
		//spSelectPinCode = (Spinner) view.findViewById(R.id.sp_select_pincode);
		radioGroupLeadType = ( RadioGroup ) view.findViewById( R.id.radio_group_lead_type );
		radioGroupVehicleType = ( RadioGroup ) view.findViewById( R.id.radio_group_vehicle_type );
		radioGroupLeadType.setOnCheckedChangeListener( this );
		radioGroupVehicleType.setOnCheckedChangeListener( this );
		rdbLeadTypeIndividual = ( RadioButton ) view.findViewById( R.id.rdb_individual );
		rdbLeadTypeOrganizational = ( RadioButton ) view.findViewById( R.id.rdb_organization );
		rdbVecTypeCommercial = ( RadioButton ) view.findViewById( R.id.rdb_commercial );
		rdbVechTypeRefinance = ( RadioButton ) view.findViewById( R.id.rdb_refinance );
		rdbVechPassanger = ( RadioButton ) view.findViewById( R.id.rdb_passenger );
		btnCancel = ( Button ) view.findViewById( R.id.btn_cancel );
		btnApplyLoan = ( Button ) view.findViewById( R.id.btn_apply_laon );
		SetFonts.setFonts( getActivity(), btnCancel, 2 );
		SetFonts.setFonts( getActivity(), btnApplyLoan, 2 );
		inputLoanModel = new InputModel();
		inputCityModel = new InputCityModel();
		inputBranchModel = new InputBranchModel();
		spnProduct.setSelection( 1 );
		spSelectBranchState.setSelection( 1 );
		spSelectCity.setSelection( 1 );
		spSelectState.setSelection( 1 );
		// spSelectPinCode.setSelection(1);
		spSelectBranch.setSelection( 1 );
		btnCancel.setOnClickListener( this );
		btnApplyLoan.setOnClickListener( this );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.btn_cancel:
				getActivity().finish();
				break;
			case R.id.btn_apply_laon:
				if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
					callSubmit();

				}
				else {
					Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}
				break;

		}
	}

	private boolean checkValidation() {
		boolean ret = true;
		if ( !Validation.hasText( txtFirstName, "Please enter your First name" ) ) {
			ret = false;
		}
		if ( !Validation.hasText( txtLastName, "Please enter your Last name" ) ) {
			ret = false;
		}
		if ( !Validation.hasText( txtMobileNumber, "Please enter mobile number" ) ) {
			ret = false;
		}
		if ( !Validation.hasText( txtLandlineNumber, "Please enter landline number" ) ) {
			ret = false;
		}
		if ( !Validation.isValidEmail( txtEmailAddress.getText().toString() ) ) {
			ret = false;
		}
		if ( !Validation.hasText( txtPincode, "Please enter Pincode number" ) ) {
			ret = false;
		}
		return ret;
	}

	public void callSubmit() {
		if ( checkValidation() ) {
			CommonUtils.showProgressDialog( getActivity(), "Pleas Wait....." );
			callApplyLoanService();
			loadApplyLoanResponse( inputLoanModel );

		}
		else {
			Toast.makeText( getActivity(), "Please Fill the Required Detail", Toast.LENGTH_SHORT ).show();
		}
	}

	public void callApplyLoanService() {
		strUserid = PreferenceHelper.getString( PreferenceHelper.USER_ID );
		inputLoanModel.setUserId( strUserid );
		inputLoanModel.setOfferId( strOfferId );
		inputLoanModel.setFirstName( txtFirstName.getText().toString() );
		inputLoanModel.setLastName( txtLastName.getText().toString() );
		inputLoanModel.setMobileNumber( txtMobileNumber.getText().toString() );
		inputLoanModel.setLandlineNumber( txtLandlineNumber.getText().toString() );
		inputLoanModel.setEmailAddress( txtEmailAddress.getText().toString() );
		if ( productCode != null && productCode != "-1" ) {
			inputLoanModel.setProductId( productCode );
		}
		else {
			Toast.makeText( getContext(), "Please Select Product Type", Toast.LENGTH_SHORT ).show();
		}

		if ( branchStateCode != null && branchStateCode != "-1" ) {
			inputLoanModel.setBranchState( branchStateCode );

		}
		else {
			Toast.makeText( getContext(), "Please Select Branch State", Toast.LENGTH_SHORT ).show();
		}
		if ( branchCityCode != null && branchCityCode != "-1" ) {
			inputLoanModel.setBranchCity( branchCityCode );
		}
		else {
			Toast.makeText( getContext(), "Please Select Branch City", Toast.LENGTH_SHORT ).show();
		}

		if ( branchCode != null && branchCode != "-1" ) {
			inputLoanModel.setBranch( branchCode );
		}
		else {
			Toast.makeText( getContext(), "Please Select Branch", Toast.LENGTH_SHORT ).show();
		}

		if ( stateCode != null && stateCode != "-1" ) {
			inputLoanModel.setState( stateCode );

		}
		else {
			Toast.makeText( getContext(), "Please Select State", Toast.LENGTH_SHORT ).show();
		}


		if ( cityCode != null && cityCode != "-1" ) {
			inputLoanModel.setCity( cityCode );
		}
		else {
			Toast.makeText( getContext(), "Please Select City", Toast.LENGTH_SHORT ).show();
		}

		inputLoanModel.setEmailAddress( txtEmailAddress.getText().toString() );
		inputLoanModel.setPincode( txtPincode.getText().toString() );
		inputLoanModel.setLeadType( strLeadTypechk );
		inputLoanModel.setOrganisationName( txtOrgnizationName.getText().toString() );
		inputLoanModel.setVehicalType( strVechicalType );

	}

	public void loadApplyLoanResponse( InputModel inputmodel ) {
		Log.e( "getFirstName", inputmodel.getFirstName() );
		Log.e( "getLastName", inputmodel.getLastName() );
		Log.e( "getMobileNumber", inputmodel.getMobileNumber() );
		Log.e( "getLandlineNumber", inputmodel.getLandlineNumber() );
		Log.e( "getProductId", inputmodel.getProductId() );
		Log.e( "getBranch", inputmodel.getBranch() );
		Log.e( "getBranchCity", inputmodel.getBranchCity() );
		Log.e( "getBranchState", inputmodel.getBranchState() );
		Log.e( "getEmailAddress", inputmodel.getEmailAddress() );
		Log.e( "getLeadType", inputmodel.getLeadType() );
		Log.e( "getPincode", inputmodel.getPincode() );
		Log.e( "getLeadType", inputmodel.getLeadType() );
		Log.e( "getOrganisationName", inputmodel.getOrganisationName() );
		Log.e( "getVehicalType", inputmodel.getVehicalType() );
		Log.e( "getCity", inputmodel.getCity() );
		Log.e( "getState", inputmodel.getState() );
		Log.e( "getUserID", inputmodel.getUserId() );
		Log.e( "getOfferId", inputmodel.getOfferId() );
		tmflApi.getApplyLoanResponse( inputmodel ).enqueue( new Callback< ApplyLoanResponse >() {
			@Override
			public void onResponse( Call< ApplyLoanResponse > call, Response< ApplyLoanResponse > response ) {
				CommonUtils.closeProgressDialog();
				if ( response.body().getStatus().contains( "success" ) ) {
					Log.e( "getApplyLoanResponse", response.body().getStatus() );
					CommonUtils.showAlert1( getActivity(), "", "Apply Loan Successfully", false );
				}
				else {
					Toast.makeText( getActivity(), response.body().getErrors().get( 0 ), Toast.LENGTH_LONG ).show();
					Log.e( "getApplyloanErr", response.body().getErrors().get( 0 ) );
				}

			}

			@Override
			public void onFailure( Call< ApplyLoanResponse > call, Throwable t ) {
				CommonUtils.closeProgressDialog();
			}
		} );
	}


	@Override
	public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
		switch ( parent.getId() ) {
			case R.id.sp_select_product:
				if ( position != 0 ) {
					productCode = ( ( ProductListResponseModel ) parent.getItemAtPosition( position ) ).getProdProductid();
				}
				break;
			case R.id.sp_select_branch_state:

				if ( position != 0 ) {
					branchStateCode = ( ( BranchStateResponseModel ) parent.getItemAtPosition( position ) ).getTerrTerritoryid();
					Log.e( "BRANCHSTATECODE", branchStateCode );
					inputCityModel.setStateId( branchStateCode );
					CommonUtils.showProgressDialog( getActivity(), "Please Wait....." );
					SocialUtil.getBranchCityListData( getActivity(), spSelectBranchCity, inputCityModel, "Select Branch City" );
					break;
				}
			case R.id.sp_select_branch_city:
				if ( position != 0 ) {
					branchCityCode = ( ( BranchCityResponseModel ) parent.getItemAtPosition( position ) ).getTerrTerritoryid();
					inputBranchModel.setCityId( branchCityCode );
					CommonUtils.showProgressDialog( getActivity(), "Please Wait....." );
					SocialUtil.getBranchList( getActivity(), spSelectBranch, inputBranchModel, "Select Branch" );
					break;
				}
			case R.id.sp_select_branch:
				if ( position != 0 ) {
					branchCode = ( ( BranchResponseModel ) parent.getItemAtPosition( position ) ).getTerrTerritoryid();

				}
				break;
			case R.id.sp_select_state:
				if ( position != 0 ) {
					stateCode = ( ( StateResponseModel ) parent.getItemAtPosition( position ) ).getId();
					Log.e( "STATECODE", stateCode );
					inputCityModel.setStateId( stateCode );
					CommonUtils.showProgressDialog( getActivity(), "Please Wait....." );
					SocialUtil.getCityListData( getActivity(), spSelectCity, inputCityModel, "Select City" );
				}

				break;
			case R.id.sp_select_city:
				if ( position != 0 ) {
					cityCode = ( ( CityResponseModel ) parent.getItemAtPosition( position ) ).getId();
					Log.e( "BranchCityCode", cityCode );

				}
				break;
			case R.id.sp_offers:

				strOfferId = String.valueOf( arDatumList.get( position ).getId() );
				Log.d( "offer id", strOfferId );

				break;
		}

	}

	@Override
	public void onNothingSelected( AdapterView< ? > parent ) {

	}


	@Override
	public void onCheckedChanged( RadioGroup group, int checkedId ) {
		switch ( group.getId() ) {
			case R.id.rdb_organization:
				if ( checkedId == R.id.rdb_organization ) {
					strLeadTypechk = "Organizational";
					inputLoanModel.setLeadType( strLeadTypechk );

				}
				break;
			case R.id.rdb_individual:
				if ( checkedId == R.id.rdb_individual ) {
					strLeadTypechk = "Individual";
					inputLoanModel.setLeadType( strLeadTypechk );
				}
				break;
			case R.id.rdb_commercial:
				if ( checkedId == R.id.rdb_commercial ) {
					strVechicalType = "Commercial";
					inputLoanModel.setVehicalType( strVechicalType );
				}
				break;
			case R.id.rdb_refinance:
				if ( checkedId == R.id.rdb_refinance ) {
					strVechicalType = "Refinance";
					inputLoanModel.setVehicalType( strVechicalType );
				}
				break;
			case R.id.rdb_passenger:
				if ( checkedId == R.id.rdb_passenger ) {
					strVechicalType = "Passanger";
					inputLoanModel.setVehicalType( strVechicalType );
				}
				break;
		}
	}
}

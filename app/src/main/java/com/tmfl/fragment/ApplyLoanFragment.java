package com.tmfl.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmfl.R;
import com.tmfl.adapter.BranchCityAdapter;
import com.tmfl.adapter.BranchListAdapter;
import com.tmfl.adapter.BranchStateAdapter;
import com.tmfl.adapter.CityAdapter;
import com.tmfl.adapter.StateAdapter;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SocialUtil;
import com.tmfl.common.Validation;
import com.tmfl.model.applyLoanResponseModel.ApplyLoanResponse;
import com.tmfl.model.applyLoanResponseModel.InputModel;
import com.tmfl.model.branchResponseModel.BranchResponseModel;
import com.tmfl.model.branchResponseModel.InputBranchModel;
import com.tmfl.model.cityResponseModel.BranchCityResponseModel;
import com.tmfl.model.cityResponseModel.CityResponseModel;
import com.tmfl.model.cityResponseModel.InputCityModel;
import com.tmfl.model.productResponseModel.ProductListResponseModel;
import com.tmfl.model.schemesResponseModel.NewOfferData;
import com.tmfl.model.schemesResponseModel.SchemesResponse;
import com.tmfl.model.schemesResponseModel.UsedOfferData;
import com.tmfl.model.stateResponseModel.BranchStateResponseModel;
import com.tmfl.model.stateResponseModel.StateResponseModel;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApplyLoanFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, TextView.OnEditorActionListener, CompoundButton.OnCheckedChangeListener {

	String strLeadTypechk  = "";
	String strVechicalType = "";
	TmflApi          tmflApi;
	InputModel       inputLoanModel;
	InputCityModel   inputCityModel;
	InputBranchModel inputBranchModel;
	String           productCode, branchStateCode, branchCityCode, branchCode, stateCode, cityCode, strOfferId, strUserid;
//	List< NewOfferData >  newOfferList;
//	List< UsedOfferData > usedOfferList;
//	SchemesResponse       response;
	int                   offer;
	List< UsedOfferData > usedOfferListNew = new ArrayList<>();
	List< NewOfferData >  newOfferListNew  = new ArrayList<>();
	CheckBox checkBox;
	private EditText edtFirstName, edtLastName, edtMobileNumber, edtLandlineNumber, edtEmailAddress, edtOrgnizationName, edtCode;
	private Spinner spnProduct, spSelectBranchState, spSelectBranchCity, spSelectBranch, spSelectCity, spSelectState, spOffers;
	private RadioButton rdbLeadTypeIndividual, rdbLeadTypeOrganizational, rdbVecTypeCommercial, rdbVechTypeRefinance, rdbVechPassanger;
	private Button btnCancel, btnApplyLoan;
	private View       view;
	private RadioGroup radioGroupLeadType, radioGroupVehicleType;
	private List< String > branchStateList, branchCityList, branchList, cityList, stateList, spOfferList;
	private RadioButton rdNewOffers, rdUsedOffers;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_apply_loan, container, false );
		tmflApi = ApiService.getInstance().call();
		Intent intent = getActivity().getIntent();
		Bundle bundle = intent.getExtras();

//		response = ( SchemesResponse ) PreferenceHelper.getObject( "Scheme response", SchemesResponse.class );
//
//		newOfferList = new ArrayList<>();
//		usedOfferList = new ArrayList<>();
//
//		newOfferList = response.getOfferData().getNEW();
//		usedOfferList = response.getOfferData().getUSED();

		init();
		spOfferList = new ArrayList<>();

//		for ( int i = 0; i < newOfferList.size(); i++ ) {
//			spOfferList.add( response.getOfferData().getNEW().get( i ).getTitle() );
//		}

		NewOfferData datum = new NewOfferData();
		datum.setTitle( "Select Offers" );
		newOfferListNew.add( datum );
//		newOfferListNew.addAll( this.newOfferList );

		UsedOfferData usedOfferData = new UsedOfferData();
		usedOfferData.setTitle( "Select Offers" );
		usedOfferListNew.add( usedOfferData );
//		usedOfferListNew.addAll( this.usedOfferList );

		spOffers.setAdapter( new ArrayAdapter<>( getActivity(), R.layout.layout_spinner_textview, newOfferListNew ) );

		branchStateList = new ArrayList< String >();

		BranchResponseModel branchResponseModel = new BranchResponseModel();
		branchResponseModel.setTerrCaption( "Select Branch" );
		branchResponseModel.setTerrTerritoryid( "-1" );
		List< BranchResponseModel > dummyBranchList = new ArrayList<>();
		dummyBranchList.add( 0, branchResponseModel );
		spSelectBranch.setAdapter( new BranchListAdapter( getActivity(), dummyBranchList ) );

		BranchCityResponseModel branchCityResponseModel = new BranchCityResponseModel();
		branchCityResponseModel.setTerrCaption( "Select City" );
		branchCityResponseModel.setTerrTerritoryid( "-1" );
		List< BranchCityResponseModel > dummyBranchCityList = new ArrayList<>();
		dummyBranchCityList.add( 0, branchCityResponseModel );
		spSelectBranchCity.setAdapter( new BranchCityAdapter( getActivity(), dummyBranchCityList ) );

		BranchStateResponseModel branchStateResponseModel = new BranchStateResponseModel();
		branchStateResponseModel.setTerrCaption( "Select Branch State" );
		branchStateResponseModel.setTerrTerritoryid( "-1" );
		List< BranchStateResponseModel > dummyBranchStateList = new ArrayList<>();
		dummyBranchStateList.add( 0, branchStateResponseModel );
		spSelectBranchState.setAdapter( new BranchStateAdapter( getActivity(), dummyBranchStateList ) );

		StateResponseModel stateResponseModel = new StateResponseModel();
		stateResponseModel.setName( "Select State" );
		stateResponseModel.setId( "-1" );
		List< StateResponseModel > dummyStatList = new ArrayList<>();
		dummyStatList.add( 0, stateResponseModel );
		spSelectState.setAdapter( new StateAdapter( getActivity(), dummyStatList ) );
		spSelectState.setVisibility( View.GONE );

		CityResponseModel cityModel = new CityResponseModel();
		cityModel.setName( "Select City" );
		cityModel.setId( "-1" );

		List< CityResponseModel > dummyCityList = new ArrayList();
		dummyCityList.add( 0, cityModel );
		spSelectCity.setAdapter( new CityAdapter( getActivity(), dummyCityList ) );

		return view;
	}


	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
	}

	public void init() {

		checkBox = ( CheckBox ) view.findViewById( R.id.chkTermsCond );

		edtFirstName = ( EditText ) view.findViewById( R.id.edt_first_name );
		edtLastName = ( EditText ) view.findViewById( R.id.edt_last_name );
		edtMobileNumber = ( EditText ) view.findViewById( R.id.edt_mobile_no );
		edtLandlineNumber = ( EditText ) view.findViewById( R.id.edt_landline_no );
		edtEmailAddress = ( EditText ) view.findViewById( R.id.edt_email_address );
		edtOrgnizationName = ( EditText ) view.findViewById( R.id.edt_orgnization_name );
		edtCode = ( EditText ) view.findViewById( R.id.edt_pincode );
		spnProduct = ( Spinner ) view.findViewById( R.id.sp_select_product );
		spnProduct.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
		SocialUtil.getProductListData( getActivity(), spnProduct );
		spSelectBranchState = ( Spinner ) view.findViewById( R.id.sp_select_branch_state );
		spSelectBranchState.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
		SocialUtil.getBranchStateListData( getActivity(), spSelectBranchState, "Select State" );
		spSelectBranchCity = ( Spinner ) view.findViewById( R.id.sp_select_branch_city );
		spSelectBranchCity.setOnItemSelectedListener( this );
		spSelectBranch = ( Spinner ) view.findViewById( R.id.sp_select_branch );
		spSelectBranch.setOnItemSelectedListener( this );
		spSelectState = ( Spinner ) view.findViewById( R.id.sp_select_state );
		spSelectState.setOnItemSelectedListener( this );
		CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
		SocialUtil.getStateListData( getActivity(), spSelectState, "Select state" );
		spSelectCity = ( Spinner ) view.findViewById( R.id.sp_select_city );
		spSelectCity.setOnItemSelectedListener( this );
		spSelectCity.setVisibility( View.GONE );
		spOffers = ( Spinner ) view.findViewById( R.id.sp_offers );
		spOffers.setOnItemSelectedListener( this );
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

		rdNewOffers = ( RadioButton ) view.findViewById( R.id.rdNewOffers );
		rdUsedOffers = ( RadioButton ) view.findViewById( R.id.rdUsedOffers );
		rdNewOffers.setOnCheckedChangeListener( this );


		rdNewOffers.setChecked( true );
		//rdUsedOffers.setOnCheckedChangeListener( this );

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
		edtMobileNumber.setOnEditorActionListener( this );
		edtLandlineNumber.setOnEditorActionListener( this );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.btn_cancel:
				getActivity().onBackPressed();
				break;
			case R.id.btn_apply_laon:
				if ( CommonUtils.isNetworkAvailable( getActivity()) ) {

					if(checkBox.isChecked())
					callSubmit();
					else {
						Toast.makeText(getActivity(), "Please Accept terms and conditions", Toast.LENGTH_SHORT).show();
					}
				}
				else {
					Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}
				break;
		}
	}

	private boolean checkValidation() {
		boolean ret = true;
		if ( !Validation.hasText( edtFirstName, "Please enter your First name" ) ) {
			ret = false;
		}
		else if ( !Validation.hasText( edtLastName, "Please enter your Last name" ) ) {
			ret = false;
		}
		else if ( !Validation.hasText( edtMobileNumber, "Please enter mobile number" ) ) {
			ret = false;
		}
		/*else if ( !Validation.isValidEmail( edtEmailAddress.getText().toString() ) ) {
			edtEmailAddress.setError( "Please Enter Email Address" );
			ret = false;
		}
		else if ( !Validation.hasText( edtCode, "Please enter Pincode number" ) ) {
			ret = false;
		}*/

		/*else if ( edtCode.getText().toString().length() != 6 ) {
			edtCode.setError( "Pincode should be of 6 digits!" );
			ret = false;
		}*/

		else if ( spnProduct.getSelectedItemPosition() == 0 ) {
			Toast.makeText( getActivity(), "Please Select A Product", Toast.LENGTH_SHORT ).show();
			ret = false;
		}
		/*else if ( spSelectBranch.getSelectedItemPosition() == 0 ) {
			ret = false;
		}
		else if ( spSelectBranchState.getSelectedItemPosition() == 0 ) {
			ret = false;
		}D
		else if ( spSelectBranchCity.getSelectedItemPosition() == 0 ) {
			ret = false;
		}*/

		else if ( !checkBox.isChecked() ) {
			Toast.makeText( getActivity(), "You must agree to the terms first!", Toast.LENGTH_SHORT ).show();
			ret = false;
		}

		return ret;
	}

	public void callSubmit() {
		if ( checkValidation() ) {
			CommonUtils.showProgressDialog( getActivity(), "Processing your request.Please wait.." );
			callApplyLoanService();
			loadApplyLoanResponse( inputLoanModel );

		}
		/*else {
			Toast.makeText( getActivity(), "Please Fill the Required Detail", Toast.LENGTH_SHORT ).show();
			CommonUtils.closeProgressDialog();
		}*/
	}

	public void callApplyLoanService() {
		strUserid = PreferenceHelper.getString( PreferenceHelper.USER_ID );
		if ( strUserid != null ) {
			inputLoanModel.setUserId( strUserid );
		}
		else {
			inputLoanModel.setUserId( "0" );
		}

		inputLoanModel.setOfferId( strOfferId );
		inputLoanModel.setFirstName( edtFirstName.getText().toString() );
		inputLoanModel.setLastName( edtLastName.getText().toString() );
		inputLoanModel.setMobileNumber( edtMobileNumber.getText().toString() );
//		inputLoanModel.setLandlineNumber( edtLandlineNumber.getText().toString() );
//		inputLoanModel.setEmailAddress( edtEmailAddress.getText().toString() );
		if ( productCode != null && productCode != "-1" ) {
			inputLoanModel.setProductId( productCode );
		}

		if ( branchStateCode != null && branchStateCode != "-1" ) {
			inputLoanModel.setBranchState( branchStateCode );
		}

		if ( branchCityCode != null && branchCityCode != "-1" ) {
			inputLoanModel.setBranchCity( branchCityCode );
		}

		/*if ( branchCode != null && branchCode != "-1" ) {
			inputLoanModel.setBranch( branchCode );
		}*/

		/*if ( stateCode != null && stateCode != "-1" ) {
			inputLoanModel.setState( String.valueOf( 0 ) );
		}

		if ( cityCode != null && cityCode != "-1" ) {
			inputLoanModel.setCity( String.valueOf( 0 ) );
		}*/

//		inputLoanModel.setEmailAddress( edtEmailAddress.getText().toString() );
//		inputLoanModel.setPincode( edtCode.getText().toString() );
		inputLoanModel.setLeadType( strLeadTypechk );
		inputLoanModel.setOrganisationName( edtOrgnizationName.getText().toString() );
		inputLoanModel.setVehicalType( strVechicalType );

	}

	public void loadApplyLoanResponse( InputModel inputmodel ) {

		Log.d( "request", inputmodel.toString() );
		tmflApi.getApplyLoanResponse( inputmodel ).enqueue( new Callback< ApplyLoanResponse >() {
			@Override
			public void onResponse( Call< ApplyLoanResponse > call, Response< ApplyLoanResponse > response ) {
				CommonUtils.closeProgressDialog();
				Log.e( "ApplyLoanResponse", new Gson().toJson( response.body() ) );
				if ( response.body() != null ) {
					if ( response.body().getStatus().contains( "success" ) ) {
						Log.e( "getApplyLoanResponse", response.body().getStatus() );
						showAlert( getActivity(), "", "Thank You for your interest in Tata Product. Our representative will contact you shortly.", true );
					}
					else {
						Toast.makeText( getActivity(), response.body().getErrors().get( 0 ), Toast.LENGTH_LONG ).show();
						//  Log.e("getApplyloanErr", response.body().getErrors().get(0));
						CommonUtils.closeProgressDialog();
					}
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
					CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
					SocialUtil.getBranchCityListData( getActivity(), spSelectBranchCity, inputCityModel, "Select City" );
					break;
				}
			case R.id.sp_select_branch_city:
				if ( position != 0 ) {
					branchCityCode = ( ( BranchCityResponseModel ) parent.getItemAtPosition( position ) ).getTerrTerritoryid();
					inputBranchModel.setCityId( branchCityCode );
					CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
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
					CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
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

				if ( offer == 1 ) {
					strOfferId = String.valueOf( ( ( NewOfferData ) parent.getItemAtPosition( position ) ).getId() );
				}
				else if ( offer == 2 ) {
					strOfferId = String.valueOf( ( ( UsedOfferData ) parent.getItemAtPosition( position ) ).getId() );
				}

				break;
		}

	}

	@Override
	public void onNothingSelected( AdapterView< ? > parent ) {

	}


	@Override
	public void onCheckedChanged( RadioGroup group, int checkedId ) {

		Log.e( "inside check ", " inside check " );
		switch ( group.getCheckedRadioButtonId() ) {
			case R.id.rdb_organization:
				if ( checkedId == R.id.rdb_organization ) {
					strLeadTypechk = "Organizational";
					inputLoanModel.setLeadType( strLeadTypechk );
					edtOrgnizationName.setVisibility( View.VISIBLE );
				}
				break;
			case R.id.rdb_individual:
				if ( checkedId == R.id.rdb_individual ) {
					edtOrgnizationName.setVisibility( View.GONE );
					strLeadTypechk = "0";
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
					strVechicalType = "Passenger";
					inputLoanModel.setVehicalType( strVechicalType );
				}
				break;
		}
	}

	public void clearData() {
		edtFirstName.setText( "" );
		edtLastName.setText( "" );
		edtMobileNumber.setText( "" );
		edtLandlineNumber.setText( "" );
		edtEmailAddress.setText( "" );
		edtOrgnizationName.setText( "" );
		edtCode.setText( "" );
		radioGroupLeadType.clearCheck();
		radioGroupVehicleType.clearCheck();
		spnProduct.setSelection( 0 );
		spSelectBranchState.setSelection( 0 );
		spSelectBranchCity.setSelection( 0 );
		spSelectBranch.setSelection( 0 );
		spSelectCity.setSelection( 0 );
		spSelectState.setSelection( 0 );
		spOffers.setSelection( 0 );
		checkBox.setChecked( false );
	}


	private void hideKeyboard() {
		// Check if no view has focus:
		View view = getActivity().getCurrentFocus();
		if ( view != null ) {
			InputMethodManager inputManager = ( InputMethodManager ) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE );
			inputManager.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
		}
	}


	public void showAlert( Context ctx, String title, String message, boolean cancelable ) {
		new AlertDialog.Builder( ctx )
				.setTitle( title )
				.setCancelable( cancelable )
				.setMessage( message )
				.setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int which ) {
						clearData();
					}
				} ).show();
	}


	@Override
	public boolean onEditorAction( TextView v, int actionId, KeyEvent event ) {
		if ( actionId == EditorInfo.IME_ACTION_GO ) {
			//Handle go key click
			hideKeyboard();
			return true;
		}

		return false;
	}

	@Override
	public void onCheckedChanged( CompoundButton compoundButton, boolean b ) {

		if ( b ) {

			offer = 1;
//			spOffers.setAdapter( new ArrayAdapter<>( getActivity(), R.layout.layout_spinner_textview, newOfferListNew ) );
		}
		else {
			offer = 2;
//			spOffers.setAdapter( new ArrayAdapter<>( getActivity(), R.layout.layout_spinner_textview, usedOfferListNew ) );

		}
	}
}

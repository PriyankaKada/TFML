package com.tfml.fragment;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.adapter.BranchCityAdapter;
import com.tfml.adapter.BranchListAdapter;
import com.tfml.adapter.BranchStateAdapter;
import com.tfml.adapter.CityAdapter;
import com.tfml.adapter.StateAdapter;
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


public class ReferFriendFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener,EditText.OnEditorActionListener {

    private EditText  edtFirstName, edtLastName, edtMobileNumber, edtLandlineNumber, edtEmailAddress, edtOrgnizationName, edtCode;
    private Spinner spnProduct, spSelectBranchState, spSelectBranchCity, spSelectBranch, spSelectCity, spSelectState, spOffers;
    private RadioButton rdbLeadTypeIndividual, rdbLeadTypeOrganizational, rdbVecTypeCommercial, rdbVechTypeRefinance, rdbVechPassanger;
    private Button btnCancel, btnReferFriends;
    private List<String> branchStateList, branchCityList, branchList, cityList, stateList;
    private RadioGroup radioGroupLeadType, radioGroupVehicleType;
	String strLeadTypechk  = "";
	String strVechicalType = "";
	TmflApi               tmflApi;
	ReferFriendInputModel inputReferFriendModel;
	InputCityModel        inputCityModel;
	InputBranchModel      inputBranchModel;
	String                productCode, branchStateCode, branchCityCode, branchCode, stateCode, cityCode;
	List< Datum >       arDatumList;
	SchemesResponse     response;
	ArrayList< String > spOfferList;
	String              strUserid, strOfferId;
	View view;

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_refer_friend, container, false );
		tmflApi = ApiService.getInstance().call();
		init();

		response = ( SchemesResponse ) PreferenceHelper.getObject( "Scheme response", SchemesResponse.class );

		arDatumList = response.getData();

		spOfferList = new ArrayList<>();

		for ( int i = 0; i < arDatumList.size(); i++ ) {
			spOfferList.add( response.getData().get( i ).getTitle() );
		}
		spOffers.setAdapter( new ArrayAdapter< Datum >( getActivity(), R.layout.layout_spinner_textview, arDatumList ) );

		BranchResponseModel branchResponseModel=new BranchResponseModel();
		branchResponseModel.setTerrCaption("Select Branch");
		branchResponseModel.setTerrTerritoryid("-1");
		List<BranchResponseModel>dummyBranchList=new ArrayList<>();
		dummyBranchList.add(0,branchResponseModel);
		spSelectBranch.setAdapter(new BranchListAdapter(getActivity(),dummyBranchList));

		BranchCityResponseModel branchCityResponseModel=new BranchCityResponseModel();
		branchCityResponseModel.setTerrCaption("Select Branch City");
		branchCityResponseModel.setTerrTerritoryid("-1");
		List<BranchCityResponseModel>dummyBranchCityList=new ArrayList<>();
		dummyBranchCityList.add(0,branchCityResponseModel);
		spSelectBranchCity.setAdapter(new BranchCityAdapter(getActivity(),dummyBranchCityList));
		BranchStateResponseModel branchStateResponseModel=new BranchStateResponseModel();
		branchStateResponseModel.setTerrCaption("Select Branch State");
		branchStateResponseModel.setTerrTerritoryid("-1");
		List<BranchStateResponseModel>dummyBranchStateList=new ArrayList<>();
		dummyBranchStateList.add(0,branchStateResponseModel);
		spSelectBranchState.setAdapter(new BranchStateAdapter(getActivity(),dummyBranchStateList));

		StateResponseModel stateResponseModel=new StateResponseModel();
		stateResponseModel.setName("Select State");
		stateResponseModel.setId("-1");
		List<StateResponseModel>dummyStatList=new ArrayList<>();
		dummyStatList.add(0,stateResponseModel);
		spSelectState.setAdapter(new StateAdapter(getActivity(),dummyStatList));

		CityResponseModel cityModel=new CityResponseModel();
		cityModel.setName("Select City");
		cityModel.setId("-1");

		List<CityResponseModel> dummyCityList=new ArrayList();
		dummyCityList.add(0,cityModel);
		spSelectCity.setAdapter(new CityAdapter(getActivity(),dummyCityList));
		return view;
	}

	@Override
	public void onViewCreated( View view, @Nullable Bundle savedInstanceState ) {
		super.onViewCreated( view, savedInstanceState );
	}

	public void init() {
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
		SocialUtil.getBranchStateListData( getActivity(), spSelectBranchState, "Select branch state" );
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
		btnReferFriends = ( Button ) view.findViewById( R.id.btn_refer_friends );
		SetFonts.setFonts( getActivity(), btnCancel, 2 );
		SetFonts.setFonts( getActivity(), btnReferFriends, 2 );

		spnProduct.setSelection( 1 );
		spSelectCity.setSelection( 1 );
		spSelectState.setSelection( 1 );
		spSelectBranch.setSelection( 1 );
		inputReferFriendModel = new ReferFriendInputModel();
		inputCityModel = new InputCityModel();
		inputBranchModel = new InputBranchModel();
		btnCancel.setOnClickListener( this );
		btnReferFriends.setOnClickListener( this );
		edtMobileNumber.setOnEditorActionListener(this);
		edtLandlineNumber.setOnEditorActionListener(this);
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.btn_cancel:
				getActivity().onBackPressed();
				break;
			case R.id.btn_refer_friends:
				if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
					callSubmit();
				}
				else {
					CommonUtils.showAlert1( getActivity(), "", "No Internet Connection", false );
				}
				break;

		}
	}

	private boolean checkValidation() {
		boolean ret = true;
		if ( !Validation.hasText( edtFirstName, "Please enter your First name" ) ) {
			ret = false;
		}
		if ( !Validation.hasText( edtLastName, "Please enter your Last name" ) ) {
			ret = false;
		}
		if ( !Validation.hasText( edtMobileNumber, "Please enter mobile number" ) ) {
			ret = false;
		}
		if ( !Validation.hasText( edtLandlineNumber, "Please enter landline number" ) ) {
			ret = false;
		}
		if ( !Validation.isValidEmail( edtEmailAddress.getText().toString() ) ) {
			ret = false;
		}
		return ret;
	}

	public void callSubmit() {
		if ( checkValidation() ) {
			CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
			callReferFriendService();
			loadReferFriendsResponse( inputReferFriendModel );
		}
		else {
			Toast.makeText(getActivity(), "Please Fill the Required Detail", Toast.LENGTH_SHORT).show();
			CommonUtils.closeProgressDialog();
		}
	}

	public void callReferFriendService() {
		strUserid = PreferenceHelper.getString(PreferenceHelper.USER_ID);
		if(strUserid!=null)
		{
			inputReferFriendModel.setUserId(strUserid);
		}
		else
		{
			inputReferFriendModel.setUserId("0");
		}
		inputReferFriendModel.setOfferId( strOfferId );
		inputReferFriendModel.setFirstName( edtFirstName.getText().toString() );
		inputReferFriendModel.setLastName( edtLastName.getText().toString() );
		inputReferFriendModel.setMobileNumber( edtMobileNumber.getText().toString() );
		inputReferFriendModel.setLandlineNumber(edtLandlineNumber.getText().toString() );
		inputReferFriendModel.setEmailAddress( edtEmailAddress.getText().toString() );
		if ( productCode != null && productCode != "-1" ) {
			inputReferFriendModel.setProductId( productCode );
		}
		/*else {
			Toast.makeText( getContext(), "Please Select Product Type", Toast.LENGTH_SHORT ).show();
		}*/
		if ( branchStateCode != null && branchStateCode != "-1" ) {
			inputReferFriendModel.setBranchState( branchStateCode );

		}
		/*else {
			Toast.makeText( getContext(), "Please Select Branch State", Toast.LENGTH_SHORT ).show();
		}*/
		if ( branchCityCode != null && branchCityCode != "-1" ) {
			inputReferFriendModel.setBranchCity( branchCityCode );
		}
		/*else {
			Toast.makeText( getContext(), "Please Select Branch City", Toast.LENGTH_SHORT ).show();
		}*/

		if ( branchCode != null && branchCode != "-1" ) {
			inputReferFriendModel.setBranch( branchCode );
		}
		/*else {
			Toast.makeText( getContext(), "Please Select Branch", Toast.LENGTH_SHORT ).show();
		}*/

		if ( stateCode != null && stateCode != "-1" ) {
			inputReferFriendModel.setState( stateCode );

		}
		/*else {
			Toast.makeText( getContext(), "Please Select State", Toast.LENGTH_SHORT ).show();
		}*/


		if ( cityCode != null && cityCode != "-1" ) {
			inputReferFriendModel.setCity( cityCode );
		}
		/*else {
			Toast.makeText( getContext(), "Please Select City", Toast.LENGTH_SHORT ).show();
		}*/


		inputReferFriendModel.setEmailAddress( edtEmailAddress.getText().toString() );
		inputReferFriendModel.setPincode( edtCode.getText().toString() );
		inputReferFriendModel.setLeadType( strLeadTypechk );
		inputReferFriendModel.setOrganisationName( edtOrgnizationName.getText().toString() );
		inputReferFriendModel.setVehicalType( strVechicalType );
		if((PreferenceHelper.getString(PreferenceHelper.USER_ID))!=null)
		{
			inputReferFriendModel.setReferedBy(PreferenceHelper.getString(PreferenceHelper.USER_ID) );
		}
		else
		{
			inputReferFriendModel.setReferedBy("0");
		}


	}

	public void loadReferFriendsResponse( ReferFriendInputModel inputReferFriendModel ) {
		/*Log.e( "getFirstName", inputReferFriendModel.getFirstName() );
		Log.e( "getLastName", inputReferFriendModel.getLastName() );
		Log.e( "getMobileNumber", inputReferFriendModel.getMobileNumber() );
		Log.e( "getLandlineNumber", inputReferFriendModel.getLandlineNumber() );
		Log.e( "getProductId", inputReferFriendModel.getProductId() );
		Log.e( "getBranch", inputReferFriendModel.getBranch() );
		Log.e( "getBranchCity", inputReferFriendModel.getBranchCity() );
		Log.e( "getBranchState", inputReferFriendModel.getBranchState() );
		Log.e( "getEmailAddress", inputReferFriendModel.getEmailAddress() );
		Log.e( "getLeadType", strLeadTypechk );
		Log.e( "getPincode", inputReferFriendModel.getPincode() );
		Log.e( "getOrganisationName", inputReferFriendModel.getOrganisationName() );
		Log.e( "getVehicalType", strVechicalType );
		Log.e( "getCity", inputReferFriendModel.getCity() );
		Log.e( "getState", inputReferFriendModel.getState() );
		Log.e( "getReferFriends", inputReferFriendModel.getReferedBy() );
*/
		tmflApi.getFriendResponse( inputReferFriendModel ).enqueue( new Callback< ReferFriendResponseModel >() {
			@Override
			public void onResponse( Call< ReferFriendResponseModel > call, Response< ReferFriendResponseModel > response ) {
				CommonUtils.closeProgressDialog();
				if(response.body()!=null)
				{
					if ( response.body().getStatus().contains( "success" ) ) {
						Log.e( "getFriendResponse", response.body().getStatus() );
						showAlert( getActivity(), "Refer Friends", "Thanks for this approach", true );

					}
					else {
						Toast.makeText( getActivity(), response.body().getErrors().get( 0 ), Toast.LENGTH_LONG ).show();
					}
				}


			}

			@Override
			public void onFailure( Call< ReferFriendResponseModel > call, Throwable t ) {
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
					SocialUtil.getBranchCityListData( getActivity(), spSelectBranchCity, inputCityModel, "Select Branch City" );
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
					inputBranchModel.setCityId( cityCode );
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
		switch (group.getCheckedRadioButtonId() ) {
			case R.id.rdb_organization:
				if ( checkedId == R.id.rdb_organization ) {
					strLeadTypechk = "Organizational";
					inputReferFriendModel.setLeadType( strLeadTypechk );
					edtOrgnizationName.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.rdb_individual:
				if ( checkedId == R.id.rdb_individual ) {
					strLeadTypechk = "0";
					inputReferFriendModel.setLeadType( strLeadTypechk );
					edtOrgnizationName.setVisibility(View.GONE);
				}
				break;
			case R.id.rdb_commercial:
				if ( checkedId == R.id.rdb_commercial ) {
					strVechicalType = "Commercial";
					inputReferFriendModel.setVehicalType( strVechicalType );
				}
				break;
			case R.id.rdb_refinance:
				if ( checkedId == R.id.rdb_refinance ) {
					strVechicalType = "Refinance";
					inputReferFriendModel.setVehicalType( strVechicalType );
				}
				break;
			case R.id.rdb_passenger:
				if ( checkedId == R.id.rdb_passenger ) {
					strVechicalType = "Passenger";
					inputReferFriendModel.setVehicalType( strVechicalType );
				}
				break;
		}
	}
	public void clearData()
	{
		edtFirstName.setText("");
		edtLastName.setText("");
		edtMobileNumber.setText("");
		edtLandlineNumber.setText("");
		edtEmailAddress.setText("");
		edtOrgnizationName.setText("");
		edtCode.setText("");
		radioGroupLeadType.clearCheck();
		radioGroupVehicleType.clearCheck();
		spnProduct.setSelection(0);
		spSelectBranchState.setSelection(0);
		spSelectBranchCity.setSelection(0);
		spSelectBranch.setSelection(0);
		spSelectCity.setSelection(0);
		spSelectState.setSelection(0);
		spOffers.setSelection(0);
	}

	private void hideKeyboard() {
		// Check if no view has focus:
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public void showAlert( Context ctx, String title, String message, boolean cancelable )
	{
		new AlertDialog.Builder( ctx )
				.setTitle( title )
				.setCancelable( cancelable )
				.setMessage( message )
				.setPositiveButton( android.R.string.yes, new DialogInterface.OnClickListener()
				{
					public void onClick( DialogInterface dialog, int which )
					{
						clearData();
					}
				} ).show();
	}


	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_GO) {
			//Handle go key click
			hideKeyboard();
			return true;
		}
		return false;
	}
}

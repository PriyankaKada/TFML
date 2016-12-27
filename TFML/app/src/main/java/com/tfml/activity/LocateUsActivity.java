package com.tfml.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tfml.R;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;
import com.tfml.model.branchResponseModel.InputBranchState;
import com.tfml.model.stateResponseModel.BranchStateResponseModel;
import com.tfml.util.SetFonts;

import java.util.ArrayList;
import java.util.List;

public class LocateUsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, OnMapReadyCallback, LocationListener {
	ImageView imgBack;
	TextView  txtTitle;
	Spinner   spnState, spnBranch;
	String stateCode, branchCode;
	InputBranchState inputBranchState;
	WebView          webview;
	private List< String > stateList, branchList;
	private GoogleMap       map;
	private MapFragment     mapFragment;
	private String          provider;
	private LocationManager locationManager;
	private double          latitude;
	private double          longitude;

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.dialog_branch_locator );

		init();

		locationManager = ( LocationManager ) getSystemService( LOCATION_SERVICE );
		boolean enabled = locationManager
				.isProviderEnabled( LocationManager.GPS_PROVIDER );

/*		check if enabled and if not send user to the GSP settings
		Better solution would be to display a dialog and suggesting to
		go to the settings*/
		if ( !enabled ) {
			Intent intent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
			startActivity( intent );
		}

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider( criteria, false );
		if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		Location location = locationManager.getLastKnownLocation( provider );

		if ( location != null ) {
			System.out.println( "Provider " + provider + " has been selected." );
			onLocationChanged( location );
		}

		mapFragment = ( MapFragment ) getFragmentManager()
				.findFragmentById( R.id.map );
		mapFragment.getMapAsync( this );

		stateList.add( "Select State" );
		spnState.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, stateList ) );
		branchList.add( "Select Branch" );
		spnBranch.setAdapter( new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, branchList ) );
	}

	public void init() {

		txtTitle = ( TextView ) findViewById( R.id.txt_toolbar_title );
		imgBack = ( ImageView ) findViewById( R.id.img_map_back );
		spnState = ( Spinner ) findViewById( R.id.sp_select_state );
		spnState.setOnItemSelectedListener( this );

		stateList = new ArrayList< String >();
		branchList = new ArrayList< String >();

		CommonUtils.showProgressDialog( this, "Please Wait....." );
		SocialUtil.getBranchStateListData( this, spnState, "Select state" );
		spnBranch = ( Spinner ) findViewById( R.id.sp_select_branch_data );
		spnBranch.setOnItemSelectedListener( this );
		SetFonts.setFonts( this, imgBack, 2 );
		imgBack.setOnClickListener( this );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.img_map_back:
				onBackPressed();
				break;
		}
	}

	@Override
	public void onItemSelected( AdapterView< ? > parent, View view, int position, long id ) {
		switch ( parent.getId() ) {
			case R.id.sp_select_state:
				if ( position != 0 ) {
					stateCode = ( ( BranchStateResponseModel ) parent.getItemAtPosition( position ) ).getTerrTerritoryid();
					Log.e( "STATECODE", stateCode );
					inputBranchState = new InputBranchState();
					inputBranchState.setStateId( stateCode );
					CommonUtils.showProgressDialog( this, "Getting Your Information" );
					SocialUtil.getStateBranchList( this, spnBranch, inputBranchState, "Select Branch" );
				}

				break;
			case R.id.sp_select_branch_data:
				if ( position != 0 ) {
					/*webview.setWebViewClient( new WebViewClient() );
					webview.getSettings().setJavaScriptEnabled( true );
					if ( CommonUtils.isNetworkAvailable( LocateUsActivity.this ) ) {
						webview.loadUrl( "https://www.google.co.in/maps/search/Tata+Motors+in+india/@19.1384285,72.9847334,13z/data=!3m1!4b1" );
					}
					else {
						Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
					}*/
				}

				break;
		}
	}

	@Override
	public void onNothingSelected( AdapterView< ? > parent ) {

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
//		locationManager.requestLocationUpdates( provider, 400, 1, this );
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.removeUpdates( this );
	}

	@Override
	public void onMapReady( GoogleMap map ) {


		map.addMarker( new MarkerOptions()
				               .title( "Sydney" )
				               .snippet( "The most populous city in Australia." )
				               .position( new LatLng( latitude, longitude ) ) );

		if ( ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		map.setMyLocationEnabled( true );

	}

	@Override
	public void onLocationChanged( Location location ) {

		latitude = location.getLatitude();
		longitude = location.getLongitude();

	}

	@Override
	public void onStatusChanged( String s, int i, Bundle bundle ) {

	}

	@Override
	public void onProviderEnabled( String s ) {

	}

	@Override
	public void onProviderDisabled( String s ) {

	}
}

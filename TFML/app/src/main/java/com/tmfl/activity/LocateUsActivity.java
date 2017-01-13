package com.tmfl.activity;

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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tmfl.R;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SocialUtil;
import com.tmfl.model.branchResponseModel.BranchResponseModel;
import com.tmfl.model.branchResponseModel.InputBranchState;
import com.tmfl.model.stateResponseModel.BranchStateResponseModel;
import com.tmfl.util.SetFonts;

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
		boolean enabled = locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER );

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
		spnState.setAdapter( new ArrayAdapter<>( this, R.layout.layout_spinner_textview, stateList ) );
		branchList.add( "Select Branch" );
		spnBranch.setAdapter( new ArrayAdapter<>( this, R.layout.layout_spinner_textview, branchList ) );
	}

	public void init() {

		txtTitle = ( TextView ) findViewById( R.id.txt_toolbar_title );
		imgBack = ( ImageView ) findViewById( R.id.img_map_back );
		spnState = ( Spinner ) findViewById( R.id.sp_select_state );
		spnState.setOnItemSelectedListener( this );

		stateList = new ArrayList<>();
		branchList = new ArrayList<>();

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

					if ( !( ( BranchResponseModel ) parent.getItemAtPosition( position ) ).getTerrLatitude().equalsIgnoreCase( "" )
							&& ( ( BranchResponseModel ) parent.getItemAtPosition( position ) ).getTerrLatitude().equalsIgnoreCase( "" ) ) {
						latitude = Double.parseDouble( ( ( BranchResponseModel ) parent.getItemAtPosition( position ) ).getTerrLatitude() );
						longitude = Double.parseDouble( ( ( BranchResponseModel ) parent.getItemAtPosition( position ) ).getTerrLongitude() );
					}
					else {
						Toast.makeText( this, "Invalid location!", Toast.LENGTH_SHORT ).show();
					}


					map.addMarker( new MarkerOptions()
							               .draggable( true )
							               .position( new LatLng( latitude, longitude ) ) );

					map.moveCamera( CameraUpdateFactory.newLatLngZoom( new LatLng( latitude, longitude ), 13 ) );
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
		this.map = map;


	/*	map.addMarker( new MarkerOptions()
				               .title( "Sydney" )
				               .snippet( "The most populous city in Australia." )
				               .position( new LatLng( latitude, longitude ) ) );
*/
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

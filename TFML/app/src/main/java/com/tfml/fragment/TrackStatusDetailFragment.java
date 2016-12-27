package com.tfml.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfml.R;

/**
 * Created by webwerks on 14/12/16.
 */
public class TrackStatusDetailFragment extends Fragment {

	private TextView txtStatusValue, txtVehicleTypeValue, txtVehicleModelValue, txtBusinessProcessValue, txtQRCValue, txtQueryRequestValue,
			txtDateCaseRegValue, txtRemarksValue, txtActualResolutionDateValue, txtExpectedResolutionDateValue;

	@Override
	public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
		View view = inflater.inflate( R.layout.fragment_track_status_detail, container, false );

		initView( view );

		return view;
	}

	private void initView( View view ) {

		txtStatusValue = ( TextView ) view.findViewById( R.id.txtStatusValue );
		txtVehicleTypeValue = ( TextView ) view.findViewById( R.id.txtVehicleTypeValue );
		txtVehicleModelValue = ( TextView ) view.findViewById( R.id.txtVehicleModelValue );
		txtBusinessProcessValue = ( TextView ) view.findViewById( R.id.txtBusinessProcessValue );
		txtQRCValue = ( TextView ) view.findViewById( R.id.txtQRCValue );
		txtQueryRequestValue = ( TextView ) view.findViewById( R.id.txtQueryRequestValue );
		txtDateCaseRegValue = ( TextView ) view.findViewById( R.id.txtDateCaseRegValue );
		txtRemarksValue = ( TextView ) view.findViewById( R.id.txtRemarksValue );
		txtActualResolutionDateValue = ( TextView ) view.findViewById( R.id.txtActualResolutionDateValue );
		txtExpectedResolutionDateValue = ( TextView ) view.findViewById( R.id.txtExpectedResolutionDateValue );

	}
}

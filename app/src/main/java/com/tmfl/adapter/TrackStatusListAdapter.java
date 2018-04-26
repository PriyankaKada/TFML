package com.tmfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tmfl.R;

import java.util.List;

/**
 * Created by Sandeep on 6/2/17.
 */
public class TrackStatusListAdapter extends ArrayAdapter {
	private Context mContext;

	public TrackStatusListAdapter( Context context, int resource, List objects ) {
		super( context, resource, objects );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {

		ViewHolder holder;
		if ( convertView == null ) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from( mContext ).inflate( R.layout.complaints_list_item, parent, false );
		}


		return convertView;
	}

	private class ViewHolder {
//		private TextView txtCaseId,txtReqComplaintDate, txtDesc,
	}
}

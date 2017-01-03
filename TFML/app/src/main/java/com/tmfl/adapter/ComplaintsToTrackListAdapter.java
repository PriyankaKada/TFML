package com.tmfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tmfl.R;

import java.util.List;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class ComplaintsToTrackListAdapter extends ArrayAdapter {

	private Context mContext;


	public ComplaintsToTrackListAdapter( Context context, int resource, List objects ) {
		super( context, resource, objects );
		mContext = context;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem( int i ) {
		return null;
	}

	@Override
	public long getItemId( int i ) {
		return 0;
	}

	@Override
	public View getView( int i, View convertView, ViewGroup viewGroup ) {

		ViewHolder holder = null;
		if ( convertView == null ) {

			convertView = LayoutInflater.from( mContext ).inflate( R.layout.complaints_list_item, null );
			holder = new ViewHolder();

			holder.txtCaseId = ( TextView ) convertView.findViewById( R.id.txtCaseId );
			holder.txtReqComplaintDate = ( TextView ) convertView.findViewById( R.id.txtReqComplaintDate );
			holder.txtDesc = ( TextView ) convertView.findViewById( R.id.txtDesc );
			holder.txtUploadFile = ( TextView ) convertView.findViewById( R.id.txtUploadFile );

			convertView.setTag( holder );
		}
		else {
			holder = ( ViewHolder ) convertView.getTag();
		}

		return null;
	}

	public class ViewHolder {
		private TextView txtCaseId, txtReqComplaintDate, txtDesc, txtUploadFile;
	}
}

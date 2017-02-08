package com.tmfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.complaintnetwork.findcase.response.Case;

import java.util.List;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class ComplaintsToTrackListAdapter extends ArrayAdapter< Case > {

	private Context      mContext;
	private List< Case > caseList;


	public ComplaintsToTrackListAdapter( Context context, int resource, List< Case > objects ) {
		super( context, resource, objects );
		mContext = context;
		caseList = objects;
	}

	@Override
	public int getCount() {
		return caseList.size();
	}

	@Override
	public View getView( int position, View convertView, ViewGroup viewGroup ) {

		ViewHolder holder = null;

		Case mCase = getItem( position );

		if ( convertView == null ) {

			convertView = LayoutInflater.from( mContext ).inflate( R.layout.complaints_list_item, null );
			holder = new ViewHolder();

			holder.txtCaseId = ( TextView ) convertView.findViewById( R.id.txtCaseId );
			holder.txtReqComplaintDate = ( TextView ) convertView.findViewById( R.id.txtReqComplaintDate );
			holder.txtDesc = ( TextView ) convertView.findViewById( R.id.txtDesc );
			holder.txtUploadFile = ( TextView ) convertView.findViewById( R.id.txtUploadFile );
			holder.txtCaseStage = ( TextView ) convertView.findViewById( R.id.txtCaseStage );

			convertView.setTag( holder );
		}
		else {
			holder = ( ViewHolder ) convertView.getTag();
		}


		holder.txtCaseId.setText( mCase.getCaseId() );
		holder.txtDesc.setText( mCase.getDescription() );
		holder.txtReqComplaintDate.setText( mCase.getCreatedDate() );
		holder.txtCaseStage.setText( mCase.getCasestage() );

		return convertView;
	}

	public class ViewHolder {
		private TextView txtCaseId, txtReqComplaintDate, txtDesc, txtCaseStage, txtUploadFile;
	}
}

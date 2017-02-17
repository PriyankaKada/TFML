package com.tmfl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.tmfl.R;
import com.tmfl.complaintnetwork.uploaddoc.UploadFileInterface;

import java.util.List;

/**
 * Created by webwerks1 on 12/12/16.
 */

public class ComplaintsToTrackListAdapter extends ArrayAdapter< LinkedTreeMap > {

	private Context               mContext;
	private List< LinkedTreeMap > caseList;
	private UploadFileInterface   fileInterface;

	public ComplaintsToTrackListAdapter( Context context, int resource, List< LinkedTreeMap > objects, UploadFileInterface fileInterface ) {
		super( context, resource, objects );
		mContext = context;
		caseList = objects;
		this.fileInterface = fileInterface;
	}

	@Override
	public int getCount() {
		return caseList.size();
	}

	@Override
	public View getView( int position, View convertView, ViewGroup viewGroup ) {

		ViewHolder holder = null;

		final LinkedTreeMap mCase = getItem( position );

		if ( convertView == null ) {

			convertView = LayoutInflater.from( mContext ).inflate( R.layout.complaints_list_item, null );
			holder = new ViewHolder();

			holder.txtCaseId = ( TextView ) convertView.findViewById( R.id.txtCaseId );
			holder.txtReqComplaintDate = ( TextView ) convertView.findViewById( R.id.txtReqComplaintDate );
			holder.txtDesc = ( TextView ) convertView.findViewById( R.id.txtDesc );
			holder.txtCaseStage = ( TextView ) convertView.findViewById( R.id.txtCaseStage );
			holder.imgUploadFile = ( ImageView ) convertView.findViewById( R.id.imgUploadFile1 );
			convertView.setTag( holder );
		}
		else {
			holder = ( ViewHolder ) convertView.getTag();
		}

		holder.txtCaseId.setText( mCase.get( "CaseId" ).toString().substring( 0, mCase.get( "CaseId" ).toString().indexOf( "." ) ) );
		holder.txtDesc.setText( mCase.get( "Description" ).toString() );
		holder.txtReqComplaintDate.setText( mCase.get( "CreatedDate" ).toString() );
		holder.txtCaseStage.setText( mCase.get( "Casestage" ).toString() );
		holder.imgUploadFile.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View view ) {
				fileInterface.uploadFile( mCase.get( "CaseId" ).toString().substring( 0, mCase.get( "CaseId" ).toString().indexOf( "." ) ) );
			}
		} );

		return convertView;
	}

	public class ViewHolder {
		private TextView txtCaseId, txtReqComplaintDate, txtDesc, txtCaseStage, txtUploadFile;
		private ImageView imgUploadFile;
	}
}

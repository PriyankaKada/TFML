package com.tmfl.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmfl.R;
import com.tmfl.complaintnetwork.findcase.response.Case;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by Sandeep on 9/2/17.
 */
public class UploadFileDialog extends Dialog implements View.OnClickListener {

	Context  mContext;
	String   caseId;
	TextView txtFileName1, txtFileName2, txtFileName3, txtUploadFile, txtCancel;
	ImageView imgFile1, imgFile2, imgFile3;

	public UploadFileDialog( Context context, Case mCase ) {
		super( context );
		mContext = context;
		caseId = mCase.getCaseId();

		initView();
	}

	private void initView() {

		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.dialog_upload_file );
		getWindow().setLayout( MATCH_PARENT, WRAP_CONTENT );
		setCancelable( false );

		txtFileName1 = ( TextView ) findViewById( R.id.txtFileName1 );
		txtFileName2 = ( TextView ) findViewById( R.id.txtFileName2 );
		txtFileName3 = ( TextView ) findViewById( R.id.txtFileName3 );
		txtUploadFile = ( TextView ) findViewById( R.id.txtUpload );
		txtCancel = ( TextView ) findViewById( R.id.txtCancel );

		imgFile1 = ( ImageView ) findViewById( R.id.imgUploadFile1 );
		imgFile2 = ( ImageView ) findViewById( R.id.imgUploadFile2 );
		imgFile3 = ( ImageView ) findViewById( R.id.imgUploadFile3 );

		imgFile1.setOnClickListener( this );
		imgFile2.setOnClickListener( this );
		imgFile3.setOnClickListener( this );
		txtCancel.setOnClickListener( this );
		txtUploadFile.setOnClickListener( this );


	}


	@Override
	public void onClick( View view ) {

		switch ( view.getId() ) {

			case R.id.imgUploadFile1:


				break;

			case R.id.imgUploadFile2:

				break;

			case R.id.imgUploadFile3:

				break;

//			case R.id.txtUploadFile:
//
//				break;

			case R.id.txtCancel:

				break;
		}

	}


}

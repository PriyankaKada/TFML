package com.tmfl.adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.R;
import com.tmfl.activity.LoginActivity;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.model.myReciptPdfResponseModel.MyReceiptInputModel;
import com.tmfl.model.myReciptPdfResponseModel.MyReceiptResponseModel;
import com.tmfl.model.soapModel.response.ResponseEnvelope;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by webwerks on 10/4/16.
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {
	Map< String, ArrayList< ResponseEnvelope.Item > > hashMap;
	Context                                           context;
	ArrayList< ResponseEnvelope.Item >                Childar;
	MyGroupHolder grpholder = null;
	String headerTitle;
	ArrayList< String > groupar  = new ArrayList<>();
	ArrayList< Double > amountar = new ArrayList< Double >();
	ArrayList< String > titlear  = new ArrayList<>();
	MyReceiptInputModel    myReceiptInputModel;
	MyReceiptResponseModel myReceiptResponseModel;
	String                 rcDate, rcNo;
	TmflApi tmflApi;
	String  strPathUrl;

	public MyExpandableListAdapter( Context mcontext, Map< String, ArrayList< ResponseEnvelope.Item > > mhashMap, ArrayList< String > mgroupar, ArrayList< Double > mamountar ) {
		this.context = mcontext;
		this.hashMap = mhashMap;
		this.titlear = mgroupar;
		this.amountar = mamountar;
		dividelistmap( hashMap );
	}

	public static void longInfo( String str ) {
		if ( str.length() > 4000 ) {
			Log.i( "response TAG", str.substring( 0, 4000 ) );
			longInfo( str.substring( 4000 ) );
		}
		else {
			Log.i( "response TAG", str );
		}
	}

	private void dividelistmap( Map< String, ArrayList< ResponseEnvelope.Item > > map ) {
		Iterator it = hashMap.entrySet().iterator();
		while ( it.hasNext() ) {
			Map.Entry pair = ( Map.Entry ) it.next();
			groupar.add( ( String ) pair.getKey() );
			Childar = ( ArrayList< ResponseEnvelope.Item > ) pair.getValue();

			for ( int i = 0; i < Childar.size(); i++ ) {
				ResponseEnvelope.Item item = Childar.get( i );
//                Log.e("item adapter date",""+item.getBLDAT());
			}
		}
	}

	@Override
	public Object getChild( int groupPosition, int childPosititon ) {
		return hashMap.get( this.groupar.get( groupPosition ) ).get( childPosititon );
	}

	@Override
	public long getChildId( int groupPosition, int childPosition ) {
		return childPosition;
	}

	@SuppressLint( "NewApi" )
	@Override
	public View getChildView( int groupPosition, final int childPosition,
	                          boolean isLastChild, View convertView, ViewGroup parent ) {

		ResponseEnvelope.Item item   = ( ResponseEnvelope.Item ) getChild( groupPosition, childPosition );
		MyHolder              holder = null;

		if ( convertView == null ) {
			LayoutInflater infalInflater = ( LayoutInflater ) context
					.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			convertView = infalInflater.inflate( R.layout.item_my_receipt, null );
			holder = new MyHolder();
			holder.txtReceiptDate = ( TextView ) convertView.findViewById( R.id.txtReceiptDate );
			holder.txtReceiptAmount = ( TextView ) convertView.findViewById( R.id.txtAmount );
			holder.txtAmount2 = ( TextView ) convertView.findViewById( R.id.txtAmount2 );
			holder.img_expand = ( ImageView ) convertView.findViewById( R.id.img_expand );
			//  holder.txtReceiptNo=(TextView) convertView.findViewById(R.id.txtReceiptNo);
			holder.txtInstNo = ( TextView ) convertView.findViewById( R.id.txtInstNo );
			holder.txtType = ( TextView ) convertView.findViewById( R.id.txtType );
			holder.txtMode = ( TextView ) convertView.findViewById( R.id.txtMode );
			holder.txtBank = ( TextView ) convertView.findViewById( R.id.txtBank );
			holder.imgPdf = ( ImageView ) convertView.findViewById( R.id.imgPdf );
			holder.lexpandList = ( LinearLayout ) convertView.findViewById( R.id.ll_expanded_layout );
			holder.ll_header_row = ( LinearLayout ) convertView.findViewById( R.id.ll_header_row );
			SetFonts.setFonts( context, holder.txtReceiptDate, 5 );
			SetFonts.setFonts( context, holder.txtReceiptAmount, 5 );
			SetFonts.setFonts( context, holder.txtAmount2, 5 );
			SetFonts.setFonts( context, holder.txtReceiptNo, 5 );
			SetFonts.setFonts( context, holder.txtInstNo, 5 );
			SetFonts.setFonts( context, holder.txtMode, 5 );
			SetFonts.setFonts( context, holder.txtBank, 5 );
			SetFonts.setFonts( context, holder.txtType, 5 );
			convertView.setTag( holder );
		}
		else {
			holder = ( MyHolder ) convertView.getTag();
		}

//		holder.txtReceiptDate.setText( rcDate + " / " + rcNo );
		rcDate = item.getZFBDT() == null ? "" : item.getZFBDT().toString();/*
		        if (model != null){}
                    contractLst.add(model.getUsrConNo());
*/

		rcNo = item.getBELNR() == null ? "" : item.getBELNR().toString();
		holder.ll_header_row.setVisibility( View.VISIBLE );
		String amountstr = item.getDMBTR() == null ? "" : item.getDMBTR().toString();
		holder.txtReceiptAmount.setText( "Rs." + amountstr );

		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {

			holder.lexpandList.setBackground( ContextCompat.getDrawable( context, R.drawable.list_row ) );
		}
		else {
			holder.lexpandList.setBackground( context.getDrawable( R.drawable.list_row ) );
		}
//		holder.txtInstNo.setText( item.getCHECT() == null ? "" : item.getCHECT().toString() );
//		holder.txtMode.setText( item.getSHKZG() == null ? "" : item.getSHKZG().toString() );

		if ( item.getANBWA() != null ) {
			holder.txtType.setText( setType( item.getANBWA() ) );
		}
		else {
			holder.txtType.setText( "-" );
		}
		holder.lexpandList.setVisibility( View.VISIBLE );
		return convertView;
	}

	@Override
	public int getChildrenCount( int groupPosition ) {
		return hashMap.get( this.groupar.get( groupPosition ) )
				.size();
	}

	@Override
	public Object getGroup( int groupPosition ) {
		return this.groupar.get( groupPosition );
	}

	@Override
	public int getGroupCount() {
		return groupar.size();
	}

	@Override
	public long getGroupId( int groupPosition ) {
		return groupPosition;
	}

	@Override
	public View getGroupView( final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent ) {
		final ResponseEnvelope.Item item = ( ResponseEnvelope.Item ) getChild( groupPosition, 0 );
		headerTitle = ( String ) getGroup( groupPosition );
		grpholder = new MyGroupHolder();
		if ( convertView == null ) {
			LayoutInflater infalInflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
			convertView = infalInflater.inflate( R.layout.group_row, null );
		}
		grpholder.txtReceiptDate = ( TextView ) convertView.findViewById( R.id.txtReceiptDate );
		grpholder.txtInstDate = ( TextView ) convertView.findViewById( R.id.txtInstDate );
		grpholder.txtReceiptAmount = ( TextView ) convertView.findViewById( R.id.txtAmount );
		grpholder.txtBank = ( TextView ) convertView.findViewById( R.id.txtBank );
		grpholder.txtInstNo = ( TextView ) convertView.findViewById( R.id.txtInstNo );
		grpholder.txtMode = ( TextView ) convertView.findViewById( R.id.txtMode );
		grpholder.img_expand = ( ImageView ) convertView.findViewById( R.id.img_expand );
		grpholder.imgPdf = ( ImageView ) convertView.findViewById( R.id.imgPdf );

		grpholder.imgPdf.setVisibility( View.GONE );
		grpholder.imgPdf.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				myReceiptInputModel = new MyReceiptInputModel();
				myReceiptResponseModel = new MyReceiptResponseModel();
				myReceiptInputModel.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
				myReceiptInputModel.setContract_no( PreferenceHelper.getString( PreferenceHelper.CONTRACT_NO ) );
				myReceiptInputModel.setReceipt_no( item.getBELNR() );
				myReceiptInputModel.setRequest_date( item.getZFBDT() );
				Log.e( "ReceiptIPMODEL", "" + myReceiptInputModel.getApi_token() + "Contract no" + myReceiptInputModel.getContract_no() + "RC" + myReceiptInputModel.getReceipt_no() + "RCDATAE" + myReceiptInputModel.getRequest_date() );
				if ( CommonUtils.isNetworkAvailable( context ) ) {
					CommonUtils.showProgressDialog( context, "File downloading..." );
					callDownloadData( myReceiptInputModel );
				}
				else {
					Toast.makeText( context, "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
				}
			}
		} );

		DateFormat outputFormatter1;
		String     instDateFinal = null;

		if ( titlear.size() == amountar.size() ) {

			DateFormat inputFormatter1 = new SimpleDateFormat( "yyyy-MM-dd" );
			Date       date1           = null;
			Date       instDate        = null;
			try {
				instDate = inputFormatter1.parse( item.getINST_DATE() );
				date1 = inputFormatter1.parse( titlear.get( groupPosition ) );
				Log.d( "reciept", titlear.get( groupPosition ) );
			}
			catch ( ParseException e ) {
				e.printStackTrace();
			}


			outputFormatter1 = new SimpleDateFormat( "dd-MM-yyyy" );
			String output1 = outputFormatter1.format( date1 );
			instDateFinal = outputFormatter1.format( instDate );

			grpholder.txtReceiptDate.setText( output1 + "/" + item.getBELNR() + "" );
			grpholder.txtReceiptAmount.setText( "Rs." + String.valueOf( amountar.get( groupPosition ) ) );
		}

		SetFonts.setFonts( context, grpholder.txtInstNo, 5 );
		SetFonts.setFonts( context, grpholder.txtMode, 5 );
		SetFonts.setFonts( context, grpholder.txtBank, 5 );


		grpholder.txtInstNo.setText( instDateFinal == null ? "-" : instDateFinal );
		grpholder.txtInstDate.setText( instDateFinal == null ? "-" : instDateFinal );

		if ( item.getSHKZG() != null ) {
			grpholder.txtMode.setText( setMode( item.getINST_TYPE() ) );
		}
		else {
			grpholder.txtMode.setText( "-" );
		}
		grpholder.txtBank.setText( "-" );
//		grpholder.txtInstNo.setText( Childar.get( groupPosition ).getCHECT() );
//		grpholder.txtMode.setText( Childar.get( groupPosition ).getSHKZG() );

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable( int groupPosition, int childPosition ) {
		return true;
	}

	public void callDownloadData( MyReceiptInputModel myReceiptInputModel ) {
		tmflApi = ApiService.getInstance().call();
		tmflApi.getMyReceiptResponse( myReceiptInputModel ).enqueue( new Callback< MyReceiptResponseModel >() {
			@Override
			public void onResponse( Call< MyReceiptResponseModel > call, Response< MyReceiptResponseModel > response ) {
				if ( response.body().getStatus().contains( "Success" ) && response.body().getFilepath() != null ) {
					CommonUtils.closeProgressDialog();
					Log.e( "File Path", "" + response.body().getFilepath() );
					strPathUrl = response.body().getFilepath().toString();
					Uri                     uri     = Uri.parse( strPathUrl );
					DownloadManager.Request request = new DownloadManager.Request( uri );
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility( DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED );
					request.setDestinationInExternalPublicDir( Environment.DIRECTORY_DOWNLOADS, "ReceiptPDF" + SystemClock.currentThreadTimeMillis() );
					DownloadManager manager = ( DownloadManager ) context.getSystemService( Context.DOWNLOAD_SERVICE );
					manager.enqueue( request );
				}
				else {
					if ( response.body().getError().toString().contains( "Unauthorized Access Token" ) ) {
						Toast.makeText( context, "User Logged in from another Device", Toast.LENGTH_LONG ).show();
						CommonUtils.closeProgressDialog();
						Intent loginIntent = new Intent( context, LoginActivity.class );
						context.startActivity( loginIntent );

					}
					else {
						CommonUtils.closeProgressDialog();
						Toast.makeText( context, "Server Under Maintenance,Please try after Sometime ", Toast.LENGTH_LONG ).show();
					}
				}
			}

			@Override
			public void onFailure( Call< MyReceiptResponseModel > call, Throwable t ) {
				CommonUtils.closeProgressDialog();
			}
		} );
	}

	public String setMode( String mode ) {
		switch ( mode ) {
			case "C":
				return "CASH";

			case "H":
				return "CHEQUE";

			case "D":
				return "DD";

			case "R":
				return "RTGS";
			default:
				return "-";
		}
	}

	public String setType( String type ) {
		switch ( type ) {
			case "ZAC":
				return "Statement of Account charges";

			case "ZAE":
				return "Advance EMI";

			case "ZAN":
				return "Duplicate NOC Charges";

			case "ZAS":
				return "Additional Service Charges";

			case "ZBC":
				return "Bank Charges";

			case "ZCC":
				return "Cancellation Charges";

			case "ZCO":
				return "Collection Agency Charges";

			case "ZDC":
				return "Document Charges";

			case "ZDP":
				return "Down Payment";

			case "ZFI":
				return "FI-3 Agency Charges";

			case "ZFP":
				return "Settlement amount";

			case "ZHC":
				return "Holding Charges";

			case "ZHP":
				return "Installment amount";

			case "ZIN":
				return "1st Year Insurance Charges";

			case "ZIP":
				return "Insurance Provision";

			case "ZLE":
				return "Legal Expenses";

			case "ZLM":
				return "Lease Management Fees";

			case "ZLP":
				return "Overdue Interest";

			case "ZLT":
				return "Lease Tax";

			case "ZOE":
				return "Other Expenses";

			case "ZOM":
				return "Option Money";

			case "ZPC":
				return "Residual Value Processing Charges";

			case "ZPD":
				return "Post Disbursal Document";

			case "ZPK":
				return "Parking Charges";

			case "ZPT":
				return "PMT Charges";

			case "ZRA":
				return "RTO Agent Charges";

			case "ZRC":
				return "Repossession Charges";

			case "ZRE":
				return "Retainer Charges";

			case "ZRO":
				return "RTO Office Payments";

			case "ZRT":
				return "RTO Charges";

			case "ZRV":
				return "Residual Value";

			case "ZRZ":
				return "ROC charges";

			case "ZSC":
				return "Service Charges";

			case "ZSD":
				return "Security Deposit";

			case "ZSE":
				return "Service Tax on Expenses";

			case "ZSL":
				return "Sale Amount";

			case "ZSR":
				return "Stamp Recovery";

			case "ZST":
				return "Sales Tax";

			case "ZSW":
				return "Swapping charges";

			case "ZVT":
				return "Value Added Tax";

			case "ZZZ":
				return "Collections (Gross)";

			case "ZMI":
				return "Income";

			case "ZTR":
				return "Terminal Dues Billing";

			case "ZWO":
				return "Write-off";

			default:
				return "-";
		}
	}

	public class MyHolder {
		TextView txtReceiptDate, txtReceiptAmount, txtAmount2, txtReceiptNo, txtInstNo, txtMode, txtType, txtBank;
		ImageView img_expand, imgPdf;
		LinearLayout lexpandList, ll_header_row, ll_daterow;
	}

	public class MyGroupHolder {
		TextView txtReceiptDate, txtReceiptAmount, txtInstNo, txtMode, txtBank, txtInstDate;
		ImageView img_expand, imgPdf;
		LinearLayout ll_header_row, ll_daterow;
	}
}

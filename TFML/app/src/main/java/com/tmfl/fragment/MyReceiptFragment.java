package com.tmfl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tmfl.R;
import com.tmfl.activity.EmiActivity;
import com.tmfl.activity.LoginActivity;
import com.tmfl.adapter.MyExpandableListAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SoapApiService;
import com.tmfl.model.logResponseModel.LogInputModel;
import com.tmfl.model.logResponseModel.LogResponseModel;
import com.tmfl.model.soapModel.request.ReqBody;
import com.tmfl.model.soapModel.request.ReqData;
import com.tmfl.model.soapModel.request.RequestEnvelpe;
import com.tmfl.model.soapModel.response.ResponseEnvelope;
import com.tmfl.util.PreferenceHelper;
import com.tmfl.util.SetFonts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReceiptFragment extends Fragment implements View.OnClickListener {
	Button                btnBack;
	View                  view;
	ListView              lstReceipt;
	ExpandableListView    expandableListView;
	Date                  date;
	String                modifiedDate;
	ResponseEnvelope.Body responseEnvelope;
	LogInputModel         logInputModel;
	LogResponseModel      logResponseModel;
	HashMap< String, ArrayList< ResponseEnvelope.Item > > hashMap = new HashMap< String, ArrayList< ResponseEnvelope.Item > >();
	TmflApi tmflApi, tmflLogin;

	public static void longInfo( String str ) {
		if ( str.length() > 4000 ) {
			Log.i( "response reciept", str.substring( 0, 4000 ) );
			longInfo( str.substring( 4000 ) );
		}
		else {
			Log.i( "response reciept", str );
		}
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
		// Inflate the layout for this fragment
		view = inflater.inflate( R.layout.fragment_my_receipt, container, false );
		tmflLogin = ApiService.getInstance().call();
		logInputModel = new LogInputModel();
		logResponseModel = new LogResponseModel();
		init();
		return view;
	}


	public void init() {
		lstReceipt = ( ListView ) view.findViewById( R.id.lst_my_receipt );
		expandableListView = ( ExpandableListView ) view.findViewById( R.id.expandableListView );
		btnBack = ( Button ) view.findViewById( R.id.btn_back );
		( ( EmiActivity ) getActivity() ).txtEmiName.setText( "Receipts" );
		btnBack.setOnClickListener( this );
		SetFonts.setFonts( getActivity(), btnBack, 2 );
		date = new Date();
		modifiedDate = new SimpleDateFormat( "yyyy-MM-dd" ).format( date );

		Log.e( "ModifiedDate", modifiedDate );
		if ( modifiedDate != null ) {
			callCheckLogin();
		}
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.btn_back:
				PreferenceHelper.insertBoolean( Constant.SHOW_RECEIPT, false );
				getActivity().getSupportFragmentManager().beginTransaction().replace( R.id.frm_emi_detail, new EmiDetailFragment() ).commit();
				break;
		}
	}

	public void callCheckLogin() {
		if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
			CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
			logInputModel.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
			logInputModel.setUser_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
			callLogService( logInputModel );
		}
		else {
			Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
	}

	public void callLogService( LogInputModel logInputModel ) {
		tmflLogin.getLogResponse( logInputModel ).enqueue( new Callback< LogResponseModel >() {
			@Override
			public void onResponse( Call< LogResponseModel > call, Response< LogResponseModel > response ) {
				Log.e( "isLogin", new Gson().toJson( response.body() ) );
				CommonUtils.closeProgressDialog();

				if ( response.body().getStatus().toString().contains( "Success" ) ) {
					if ( getActivity() != null ) {
						if ( CommonUtils.isNetworkAvailable( getActivity() ) ) {
							CommonUtils.showProgressDialog( getActivity(), "Getting Your Information" );
							callSoapRequest();
						}
						else {
							Toast.makeText( getActivity(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
						}
					}
				}
				else {
					Toast.makeText( getActivity(), "User Logged in from another Device", Toast.LENGTH_LONG ).show();
					Intent loginIntent = new Intent( getActivity(), LoginActivity.class );
					getActivity().startActivity( loginIntent );
				}
			}

			@Override
			public void onFailure( Call< LogResponseModel > call, Throwable t ) {
				Log.e( "ERROR", t.getMessage() );
			}
		} );
	}

	public void callSoapRequest() {
		RequestEnvelpe requestEnvelpe = new RequestEnvelpe();
		final ReqBody  reqBody        = new ReqBody();
		ReqData        reqData        = new ReqData();
		if ( PreferenceHelper.getString( PreferenceHelper.CONTRACT_NO ) != null ) {
			Log.e( "SelectedContractNo", PreferenceHelper.getString( PreferenceHelper.CONTRACT_NO ) );
			reqData.setContactId( PreferenceHelper.getString( PreferenceHelper.CONTRACT_NO ) );
		}
		else {
			reqData.setContactId( PreferenceHelper.getString( PreferenceHelper.CONTRACT_NO ) );
		}
		reqData.setREQDATE( modifiedDate );
		reqBody.setReqData( reqData );
		requestEnvelpe.setReqBody( reqBody );
		tmflApi = SoapApiService.getInstance().call();
		try {
			tmflApi.callStmtAcRequest( requestEnvelpe ).enqueue( new Callback< ResponseEnvelope >() {
                @Override
                public void onResponse( Call< ResponseEnvelope > call, Response< ResponseEnvelope > response ) {
                    CommonUtils.closeProgressDialog();
                    if ( response.body() != null ) {
                        responseEnvelope = response.body().getBody();

                        if ( responseEnvelope != null ) {
                            longInfo( new Gson().toJson( responseEnvelope ) );
                            Log.e( "SIZE ", response.body().getBody().getZCISResponse().getI_REC().size() + "" );

                            if ( response.body().getBody().getZCISResponse().getI_REC().size() != 0 ) {
                                LinkedHashMap< String, ArrayList< ResponseEnvelope.Item > > hashMap = new LinkedHashMap< String, ArrayList< ResponseEnvelope.Item > >();


                                List< String > itemsCategory = new ArrayList< String >();
                                for ( ResponseEnvelope.Item item : response.body().getBody().getZCISResponse().getI_REC() ) {
                                    itemsCategory.add( item.getBELNR() );
                                }
                                List< String > categories = new ArrayList< String >();

                                List< ResponseEnvelope.Item > items = response.body().getBody().getZCISResponse().getI_REC();
                                Collections.sort( items, new Comparator< ResponseEnvelope.Item >() {

                                    @Override
                                    public int compare( ResponseEnvelope.Item lhs, ResponseEnvelope.Item rhs ) {
                                        SimpleDateFormat form  = new SimpleDateFormat( "yyyy-MM-dd" );
                                        Date             date1 = null;
                                        Date             date2 = null;

                                        try {
                                            date1 = form.parse( lhs.getZFBDT() );
                                            date2 = form.parse( rhs.getZFBDT() );
                                        }
                                        catch ( Exception e ) {
                                            e.printStackTrace();
                                        }
                                        return date2.compareTo( date1 );
                                    }
                                } );

                                for ( int i = 0; i < items.size(); i++ ) {
                                    Log.d( "sorted dates", items.get( i ).getZFBDT() );
                                }

                                for ( ResponseEnvelope.Item item : items ) {
                                    if ( !isDataAvailabe( categories, item.getBELNR() ) ) {
                                        categories.add( item.getBELNR() );
                                        Log.e( " receipt ", item.getBELNR() );
                                        hashMap.put( item.getBELNR(), new ArrayList< ResponseEnvelope.Item >() );
                                    }

                                    Log.e( "date and receipt ", item.getBELNR() + "    " + item.getZFBDT() );
                                    hashMap.get( item.getBELNR() ).add( item );
                                }


                                ArrayList< String > groupar  = new ArrayList<>();
                                ArrayList< Double > amountar = new ArrayList< Double >();
                                Log.e( "HashMap ", new Gson().toJson( hashMap ) + "" );

                                longInfo( new Gson().toJson( hashMap ) );

                                Iterator it = hashMap.entrySet().iterator();

                                for ( String key : hashMap.keySet() ) {
                                    Double                             amount = 0.00;
                                    ArrayList< ResponseEnvelope.Item > itemss = hashMap.get( key );
                                    for ( int i = 0; i < itemss.size(); i++ ) {
                                        amount = amount + Double.parseDouble( itemss.get( i ).getDMBTR() );
                                        if ( i == 0 ) {
                                            groupar.add( itemss.get( i ).getZFBDT() + " / " + itemss.get( i ).getBELNR() );
                                        }
                                    }
                                    amountar.add( amount );
                                }

                                MyExpandableListAdapter expandableListAdapter = new MyExpandableListAdapter( getActivity(), hashMap, groupar, amountar );
                                expandableListView.setAdapter( expandableListAdapter );
                            }
                        }
                    }
                    else {
                        Toast.makeText( getActivity(), "Server Under Maintenance,Please try after Sometime ", Toast.LENGTH_LONG ).show();
                    }
                }

                private boolean isDataAvailabe( List< String > stringList, String data ) {
                    for ( String str : stringList ) {
                        if ( data.equalsIgnoreCase( str ) ) {
                            return true;
                        }
                    }

                    return false;
                }

                @Override
                public void onFailure( Call< ResponseEnvelope > call, Throwable t ) {
                    Log.e( "Response ", "" + t.getLocalizedMessage() );
                    CommonUtils.closeProgressDialog();
                }
            } );
		} catch (Exception e) {
			e.printStackTrace();
			CommonUtils.closeProgressDialog();
			Toast.makeText(getContext(), "Try After Some time", Toast.LENGTH_SHORT).show();
		}
	}
}

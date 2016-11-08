package com.tfml.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.adapter.BranchCityAdapter;
import com.tfml.adapter.BranchListAdapter;
import com.tfml.adapter.BranchStateAdapter;
import com.tfml.adapter.CityAdapter;
import com.tfml.adapter.ProductAdapter;
import com.tfml.adapter.StateAdapter;
import com.tfml.auth.TmflApi;
import com.tfml.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tfml.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tfml.model.branchResponseModel.BranchResponseModel;
import com.tfml.model.branchResponseModel.InputBranchModel;
import com.tfml.model.branchResponseModel.InputBranchState;
import com.tfml.model.cityResponseModel.BranchCityResponseModel;
import com.tfml.model.cityResponseModel.CityResponseModel;
import com.tfml.model.cityResponseModel.InputCityModel;
import com.tfml.model.productResponseModel.ProductListResponseModel;
import com.tfml.model.socialResponseModel.ContactListResponseModel;
import com.tfml.model.stateResponseModel.BranchStateResponseModel;
import com.tfml.model.stateResponseModel.StateResponseModel;
import com.tfml.util.SetFonts;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by webwerks on 12/8/16.
 */

public class SocialUtil {

	public static TmflApi tmflApi;
	public static String  email, whatsAppNo, phoneNo;


	public static void dialPhoneCall( Context context, String phoneNo ) {
		Intent callIntent = new Intent( Intent.ACTION_DIAL );
		callIntent.setData( Uri.parse( "tel:" + phoneNo ) );
		context.startActivity( callIntent );

	}

	public static void sendMail( Context context, String email ) {

		Intent intent = new Intent( Intent.ACTION_SENDTO );
		intent.setType( "message/rfc822" );
		List< ResolveInfo > resInfo = context.getPackageManager().queryIntentActivities( intent, 0 );
		intent.setData( Uri.parse( "mailto:" + email ) );
//		intent.putExtra( Intent.EXTRA_EMAIL, new String[]{ email } );
		intent.putExtra( Intent.EXTRA_TEXT, "Welcome to TFML" );

		context.startActivity( Intent.createChooser( intent, "Choose an Email client :" ) );

//		if ( !resInfo.isEmpty() ) {
//			for ( ResolveInfo info : resInfo ) {
//				if ( info.activityInfo.packageName.toLowerCase().contains( "email" ) || info.activityInfo.name.toLowerCase().contains( "email" ) ) {
//					intent.putExtra( android.content.Intent.EXTRA_TEXT, "Welcome to TMFL" );
//					intent.putExtra( Intent.EXTRA_EMAIL, new String[]{ email } );
//					intent.setPackage( info.activityInfo.packageName );
//					context.startActivity( Intent.createChooser( intent, "Sending mail Through TMFL" ) );
//				}
//			}
//		}
	}

	public static void sendWhatsAppMsg( Context context, String whatsAppNo ) {

		boolean isWhatsappInstalled = whatsappInstalledOrNot( "com.whatsapp", context );
		if ( isWhatsappInstalled ) {
			Uri    uri        = Uri.parse( "smsto:" + whatsAppNo );
			Intent sendIntent = new Intent( Intent.ACTION_SENDTO, uri );
			sendIntent.putExtra( Intent.EXTRA_TEXT, "Hai Good Morning" );
		   /* sendIntent.setType("text/plain");*/
			sendIntent.setPackage( "com.whatsapp" );
			context.startActivity( sendIntent );
		}
		else {
			Toast.makeText( context, "WhatsApp not Installed",
			                Toast.LENGTH_SHORT ).show();
			Uri    uri        = Uri.parse( "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en" );
			Intent goToMarket = new Intent( Intent.ACTION_VIEW, uri );
			context.startActivity( goToMarket );
		}

	}

	private static boolean whatsappInstalledOrNot( String uri, Context context ) {
		PackageManager pm            = context.getPackageManager();
		boolean        app_installed = false;
		try {
			pm.getPackageInfo( uri, PackageManager.GET_ACTIVITIES );
			app_installed = true;
		}
		catch ( PackageManager.NameNotFoundException e ) {
			app_installed = false;
		}
		return app_installed;
	}

	public static void loanStatusDialog( final Context context, final LinearLayout linloanstatus, final View selectedView ) {
		final Dialog loanstatusdialog = new Dialog( context, android.R.style.Theme_Holo_Dialog_NoActionBar );

		loanstatusdialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
		loanstatusdialog.setContentView( R.layout.dialog_laon_status );
		WindowManager.LayoutParams params = loanstatusdialog.getWindow().getAttributes();
		params.y = 120;
		params.x = 120;
		params.gravity = Gravity.BOTTOM | Gravity.CENTER;
		loanstatusdialog.getWindow().setAttributes( params );
		loanstatusdialog.setCancelable( true );
		loanstatusdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
		final EditText edtmobileno   = ( EditText ) loanstatusdialog.findViewById( R.id.edt_mobile_no );
		final EditText edtotpno      = ( EditText ) loanstatusdialog.findViewById( R.id.edt_otp_no );
		final Button   btnloanstatus = ( Button ) loanstatusdialog.findViewById( R.id.btn_loan_status );
		SetFonts.setFonts( context, btnloanstatus, 2 );
		btnloanstatus.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {

				String monumber  = edtmobileno.getText().toString();
				String otpnumber = edtotpno.getText().toString();
				if ( TextUtils.isEmpty( monumber ) ) {
					Toast.makeText( context, "Please Enter Mobile Number", Toast.LENGTH_SHORT ).show();
				}
				if ( TextUtils.isEmpty( otpnumber ) ) {
					Toast.makeText( context, "Please Enter OTP Number", Toast.LENGTH_SHORT ).show();
				}
				if ( !TextUtils.isEmpty( monumber ) && !TextUtils.isEmpty( otpnumber ) ) {
					LoanStatusInputModel loanStatusInputModel = new LoanStatusInputModel();
					loanStatusInputModel.setOtpNumber( otpnumber );
					loanStatusInputModel.setMobileNumber( monumber );
					CallLoanStatusModel( loanStatusInputModel, context );

				}
				else {
					Toast.makeText( context, "Please Enter Mobile Number and OTP Number", Toast.LENGTH_SHORT ).show();
				}


			}
		} );
		loanstatusdialog.show();
		loanstatusdialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel( DialogInterface dialog ) {
				linloanstatus.setBackgroundColor( Color.parseColor( "#004c92" ) );
				selectedView.setVisibility( View.INVISIBLE );
			}
		} );
	}

	public static void CallLoanStatusModel( LoanStatusInputModel loanStatusInputModel, final Context context ) {
		tmflApi = ApiService.getInstance().call();

		tmflApi.getOtpResponse( loanStatusInputModel ).enqueue( new Callback< LoanStatusResponse >() {
			@Override
			public void onResponse( Call< LoanStatusResponse > call, Response< LoanStatusResponse > response ) {
				if ( response.body().getStatus().contains( "success" ) ) {
					Log.e( "CallLoanStatusModel", response.body().getStatus() );
					Toast.makeText( context, "Get Application Loan Process ", Toast.LENGTH_SHORT ).show();
				}
				if ( response.body().getStatus().contains( "error" ) ) {
					Log.e( "CallLoanStatusModel", response.body().getError() );
					Toast.makeText( context, response.body().getError(), Toast.LENGTH_SHORT ).show();
				}
				else {
					Toast.makeText( context, "Get Application Loan Process ", Toast.LENGTH_SHORT ).show();
				}

			}

			@Override
			public void onFailure( Call< LoanStatusResponse > call, Throwable t ) {

			}
		} );
	}


	public static void getContactList() {

		tmflApi = ApiService.getInstance().call();
		tmflApi.getContactList().enqueue( new Callback< ContactListResponseModel >() {
			@Override
			public void onResponse( Call< ContactListResponseModel > call, Response< ContactListResponseModel > response ) {
				//  Log.e("getContactList",response.body().);
				if ( response != null ) {
					email = response.body().getEmail().toString();
					whatsAppNo = response.body().getWhatsappNo().toString();
					phoneNo = response.body().getPhoneNo().toString();
					Log.e( "getContactlist", email + "" + '\t' + whatsAppNo + "" + phoneNo );
				}
			}

			@Override
			public void onFailure( Call< ContactListResponseModel > call, Throwable t ) {
				Log.e( "Error", "ERROR RESPONSE" );
			}
		} );

	}

	public static void getProductListData( final Context context, final Spinner spnProduct ) {
		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			tmflApi.getProductList().enqueue( new Callback< List< ProductListResponseModel > >() {
				@Override
				public void onResponse( Call< List< ProductListResponseModel > > call, Response< List< ProductListResponseModel > > response ) {
					Log.e( "ProductResponse", response.body().size() + "" );
					CommonUtils.closeProgressDialog();
					if ( response != null ) {
						ProductListResponseModel model = new ProductListResponseModel();
						model.setProdName( "Select product" );
						model.setProdProductid( "-1" );
						response.body().add( 0, model );
						spnProduct.setAdapter( new ProductAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getProdName() ) ) {
								spnProduct.setSelection( i );

							}
						}

					}

				}

				@Override
				public void onFailure( Call< List< ProductListResponseModel > > call, Throwable t ) {
					Log.e( "Response", t.getMessage() + "" );
					CommonUtils.closeProgressDialog();
				}
			} );
		}
		else {
			CommonUtils.showAlert1( context, "", "No Internet Connection", false );
			CommonUtils.closeProgressDialog();
		}
	}


	public static void getBranchStateListData( final Context context, final Spinner spnBranchState, final String captionText ) {
		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			tmflApi.getBranchStateList().enqueue( new Callback< List< BranchStateResponseModel > >() {
				@Override
				public void onResponse( Call< List< BranchStateResponseModel > > call, Response< List< BranchStateResponseModel > > response ) {
					Log.e( "BranchStateListDataResp", response.body().size() + "" );
					CommonUtils.closeProgressDialog();
					if ( response != null ) {
						BranchStateResponseModel stateModel = new BranchStateResponseModel();
						stateModel.setTerrCaption( captionText );
						stateModel.setTerrTerritoryid( "-1" );
						response.body().add( 0, stateModel );
						spnBranchState.setAdapter( new BranchStateAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getTerrCaption() ) ) {
								spnBranchState.setSelection( i );
							}
						}
					}
				}

				@Override
				public void onFailure( Call< List< BranchStateResponseModel > > call, Throwable t ) {
					Log.e( "ErrorResponse", t.getMessage() + "" );
					CommonUtils.closeProgressDialog();
				}
			} );
		}
		else {
			CommonUtils.showAlert1( context, "", "No Internet Connection", false );
			CommonUtils.closeProgressDialog();
		}

	}


	public static void getStateListData( final Context context, final Spinner spnState, final String caption ) {
		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			tmflApi.getStateListData().enqueue( new Callback< List< StateResponseModel > >() {
				@Override
				public void onResponse( Call< List< StateResponseModel > > call, Response< List< StateResponseModel > > response ) {
					Log.e( "StateListDataResponse", response.body().size() + "" );
					CommonUtils.closeProgressDialog();
					if ( response != null ) {
						StateResponseModel stateModel = new StateResponseModel();
						stateModel.setName( caption );
						stateModel.setId( "-1" );
						response.body().add( 0, stateModel );
						spnState.setAdapter( new StateAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getName() ) ) {
								spnState.setSelection( i );
							}
						}
					}
				}

				@Override
				public void onFailure( Call< List< StateResponseModel > > call, Throwable t ) {
					CommonUtils.closeProgressDialog();
				}

			} );
		}
		else {
			CommonUtils.showAlert1( context, "", "No Internet Connection", false );
			CommonUtils.closeProgressDialog();
		}
	}

	public static void getBranchCityListData( final Context context, final Spinner spnCity, final InputCityModel inputCityModel, final String caption ) {
		Log.e( "CITYCLAL", "CITY" );
		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			tmflApi.getBranchCityList( inputCityModel ).enqueue( new Callback< List< BranchCityResponseModel > >() {
				@Override
				public void onResponse( Call< List< BranchCityResponseModel > > call, Response< List< BranchCityResponseModel > > response ) {
					Log.e( "CityListDataResponse", response.body().size() + "" );
					CommonUtils.closeProgressDialog();
					if ( response != null ) {
						BranchCityResponseModel cityResponseModel = new BranchCityResponseModel();
						cityResponseModel.setTerrCaption( caption );
						cityResponseModel.setTerrTerritoryid( "-1" );
						response.body().add( 0, cityResponseModel );
						spnCity.setAdapter( new BranchCityAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getTerrCaption() ) ) {
								spnCity.setSelection( i );
							}
						}
					}

				}

				@Override
				public void onFailure( Call< List< BranchCityResponseModel > > call, Throwable t ) {
					Log.e( "ErrorResponse", t.getMessage() + "" );
					CommonUtils.closeProgressDialog();
				}
			} );

		}
		else {
			CommonUtils.showAlert1( context, "", "No Internet Connection", false );
			CommonUtils.closeProgressDialog();
		}
	}

	public static void getCityListData( final Context context, final Spinner spnCity, final InputCityModel inputCityModel, final String caption ) {
		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			tmflApi.getCityListData( inputCityModel ).enqueue( new Callback< List< CityResponseModel > >() {
				@Override
				public void onResponse( Call< List< CityResponseModel > > call, Response< List< CityResponseModel > > response ) {
					Log.e( "CityListDataResponse", response.body().size() + "" );
					CommonUtils.closeProgressDialog();
					if ( response != null ) {
						CityResponseModel cityResponseModel = new CityResponseModel();
						cityResponseModel.setName( caption );
						cityResponseModel.setId( "-1" );
						response.body().add( 0, cityResponseModel );
						spnCity.setAdapter( new CityAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getName() ) ) {
								spnCity.setSelection( i );
							}
						}
					}
				}

				@Override
				public void onFailure( Call< List< CityResponseModel > > call, Throwable t ) {
					CommonUtils.closeProgressDialog();

				}
			} );
		}
		else {
			CommonUtils.showAlert1( context, "", "No Internet Connection", false );
			CommonUtils.closeProgressDialog();
		}
	}


	public static void getBranchList( final Context context, final Spinner spnBranch, final InputBranchModel inputBranchModel, final String caption ) {
		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			tmflApi.getBranchList( inputBranchModel ).enqueue( new Callback< List< BranchResponseModel > >() {
				@Override
				public void onResponse( Call< List< BranchResponseModel > > call, Response< List< BranchResponseModel > > response ) {
					CommonUtils.closeProgressDialog();
					if ( response != null ) {
						BranchResponseModel branchResponseModel = new BranchResponseModel();
						branchResponseModel.setTerrCaption( caption );
						branchResponseModel.setTerrTerritoryid( "-1" );
						response.body().add( 0, branchResponseModel );
						spnBranch.setAdapter( new BranchListAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getTerrCaption() ) ) {
								spnBranch.setSelection( i );
							}
						}
					}


				}

				@Override
				public void onFailure( Call< List< BranchResponseModel > > call, Throwable t ) {
					Log.e( "ErrorResponse", t.getMessage() + "" );
					CommonUtils.closeProgressDialog();
				}
			} );
		}
		else {
			CommonUtils.showAlert1( context, "", "No Internet Connection", false );
			CommonUtils.closeProgressDialog();
		}
	}

	public static void getStateBranchList( final Context context, final Spinner spnBranch, final InputBranchState inputBranchstate, final String caption ) {

		if ( CommonUtils.isNetworkAvailable( context ) ) {
			tmflApi = ApiService.getInstance().call();
			Log.e( "Request", new Gson().toJson( inputBranchstate ) );
			tmflApi.getStateBranches( inputBranchstate ).enqueue( new Callback< List< BranchResponseModel > >() {
				@Override
				public void onResponse( Call< List< BranchResponseModel > > call, Response< List< BranchResponseModel > > response ) {
					CommonUtils.closeProgressDialog();
					Log.e( "getStateBranches", response.body().toString() );
					if ( response != null ) {
						BranchResponseModel branchResponseModel = new BranchResponseModel();
						branchResponseModel.setTerrCaption( caption );
						branchResponseModel.setTerrTerritoryid( "-1" );
						response.body().add( 0, branchResponseModel );
						spnBranch.setAdapter( new BranchListAdapter( context, response.body() ) );
						for ( int i = 0; i < response.body().size(); i++ ) {
							if ( response.equals( response.body().get( i ).getTerrCaption() ) ) {
								spnBranch.setSelection( i );
							}
						}
					}
				}

				@Override
				public void onFailure( Call< List< BranchResponseModel > > call, Throwable t ) {
					Log.e( "ErrorResponse", t.getMessage() + "" );
					CommonUtils.closeProgressDialog();
				}
			} );
		}
	}


}

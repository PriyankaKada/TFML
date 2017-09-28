package com.tmfl.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.michael.easydialog.EasyDialog;
import com.tmfl.R;
import com.tmfl.adapter.BannerAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SocialUtil;
import com.tmfl.fragment.BannerFragment;
import com.tmfl.fragment.ComplaintsFragment;
import com.tmfl.model.LoanStatusResponseModel.LoanStatusInputModel;
import com.tmfl.model.LoanStatusResponseModel.LoanStatusResponse;
import com.tmfl.model.QuickcallResponseModel.QuickCallInputModel;
import com.tmfl.model.QuickcallResponseModel.QuickCallResponse;
import com.tmfl.model.bannerResponseModel.BannerlistResponse;
import com.tmfl.model.bannerResponseModel.Datum;
import com.tmfl.util.SetFonts;
import com.tmfl.util.ViewPagerCustomDuration;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.tfml.R.id.imageView1;
public class BannerActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
	//String emailId,whatsAppNo,phoneNo;
	final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
	public ViewPagerCustomDuration recentViewpager;
	Toolbar             mToolbar;
	CirclePageIndicator circlePageIndicator;
	TmflApi             tmflApi;
	BannerAdapter       bannerAdapter;
	LinearLayout        linSchemes, linApplyLoan, linReferFriend, linLoanStaus, linLogin, linQuickCall;
	String errormsg;
	View   view1, view2, view3, view4, view5;
	String strQuickCall, strOtpNo, strMobileNo;
	TmflApi    tmflApiOtpSubmit;
	ImageView  imgRefreshOtp;
	EasyDialog dialog;
	EditText   edtQuickCall, edtOtpNo;
	CheckBox            checkBox;
	QuickCallInputModel quickCallInputModel;
	int count = 0;
	ComplaintsFragment complaintsFragment;
	FrameLayout        containerFrameLayout;
	private BannerFragment bannerFragment;
	private ImageView      imgQuickCall,imgSocial;
	private TextView       txtTitle;
	private ImageView[]    dots;
	private int            dotsCount;
	private TextView       txtSchemes, txtApplyLoan, txtReferFriend, txtLoanStatus, txtLogin;
	private ImageView imgSchemes, imgApplyLoan, imgReferFriend, imgLoanStatus, imgLogin;
	private Timer timer;

	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_banner );
		init();
	}

	public void init() {

		mToolbar = ( Toolbar ) findViewById( R.id.toolbar );
		imgQuickCall = ( ImageView ) findViewById( R.id.imgQuickCall );
		imgSocial = ( ImageView ) findViewById( R.id.img_social );
		txtTitle = ( TextView ) findViewById( R.id.txtTitle );
		txtSchemes = ( TextView ) findViewById( R.id.txtSchemes );
		txtApplyLoan = ( TextView ) findViewById( R.id.txtApplyLoan );
		txtReferFriend = ( TextView ) findViewById( R.id.txtReferFriend );
		txtLoanStatus = ( TextView ) findViewById( R.id.txtLoanStatus );
		txtLogin = ( TextView ) findViewById( R.id.txtQuicCall );
		imgSchemes = ( ImageView ) findViewById( R.id.imageSchemes );
		imgApplyLoan = ( ImageView ) findViewById( R.id.imgApplyLoan );
		imgReferFriend = ( ImageView ) findViewById( R.id.imgReferFriend );
		imgLoanStatus = ( ImageView ) findViewById( R.id.imgLoanStatus );
		imgLogin = ( ImageView ) findViewById( R.id.imgLogin );
		recentViewpager = ( ViewPagerCustomDuration ) findViewById( R.id.recentViewpager );
		circlePageIndicator = ( CirclePageIndicator ) findViewById( R.id.titles );
		linSchemes = ( LinearLayout ) findViewById( R.id.llSchemes );
		linApplyLoan = ( LinearLayout ) findViewById( R.id.llApplyLoan );
		linReferFriend = ( LinearLayout ) findViewById( R.id.linReferFriend );
		linLoanStaus = ( LinearLayout ) findViewById( R.id.linLoanStaus );
		linLogin = ( LinearLayout ) findViewById( R.id.linLogin );
		linQuickCall = ( LinearLayout ) findViewById( R.id.linQuickCall );

		containerFrameLayout = ( FrameLayout ) findViewById( R.id.frame_complaint_container );
		complaintsFragment = new ComplaintsFragment();

		view1 = findViewById( R.id.viewId1 );
		view2 = findViewById( R.id.viewId2 );
		view3 = findViewById( R.id.viewId3 );
		view4 = findViewById( R.id.viewId4 );
		view5 = findViewById( R.id.viewId5 );

		circlePageIndicator.setRadius( 8.0f );
//		txtTitle.setText( "Welcome to TMFL" );
		SetFonts.setFonts( this, txtTitle, 2 );
		SetFonts.setFonts( this, txtSchemes, 2 );
		SetFonts.setFonts( this, txtApplyLoan, 2 );
		SetFonts.setFonts( this, txtReferFriend, 2 );
		SetFonts.setFonts( this, txtLoanStatus, 2 );
		SetFonts.setFonts( this, txtLogin, 2 );
		loadBannerData();
		imgQuickCall.setOnClickListener( this );
		imgSocial.setOnClickListener( this );
		imgSocial.setVisibility( View.VISIBLE );
		linSchemes.setOnClickListener( this );
		linApplyLoan.setOnClickListener( this );
		linReferFriend.setOnClickListener( this );
		linLoanStaus.setOnClickListener( this );
		linLogin.setOnClickListener( this );
		linQuickCall.setOnClickListener( this );
	}

	public void loadBannerData() {
		tmflApi = ApiService.getInstance().call();
		if ( CommonUtils.isNetworkAvailable( BannerActivity.this ) ) {
			CommonUtils.showProgressDialog( BannerActivity.this, "Getting Your Information" );
			callBannerList();
		}
		else {
			Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
	}

	public void callBannerList() {
		tmflApi.getBannerResponse().enqueue( new Callback< BannerlistResponse >() {
			@Override
			public void onResponse( Call< BannerlistResponse > call, Response< BannerlistResponse > response ) {
				BannerlistResponse bannerlistResponse = response.body();
				CommonUtils.closeProgressDialog();
				if ( response.body() != null ) {
					if ( response.body().getStatus() != null && response.body().getStatus().equals( "success" ) ) {
						// Logger.e(BannerActivity.class,new Gson().toJson(response.body().getStatus()));
						Log.e( "BannerlistResponse", new Gson().toJson( response.body().getStatus() ) );
						// Log.e("CallbannerListResponse", "" + bannerlistResponse.getBanners().getData().get(0).getImage());
						bannerAdapter = new BannerAdapter( BannerActivity.this, ( ArrayList< Datum > ) bannerlistResponse.getBanners().getData() );
						recentViewpager.setAdapter( bannerAdapter );
						recentViewpager.setOffscreenPageLimit( 1 );
						recentViewpager.animate();
						recentViewpager.setScrollDurationFactor( 8 );
						setUiPageViewController();

						recentViewpager.setCurrentItem( 0, true );
						circlePageIndicator.setViewPager( recentViewpager );
					}
					else {
						Log.e( "ErrorResponse", response.errorBody().toString() );
					}
				}
				else {
					Toast.makeText( getBaseContext(), "Server Error........", Toast.LENGTH_LONG ).show();
				}
			}

			@Override
			public void onFailure( Call< BannerlistResponse > call, Throwable t ) {
				Log.e( "Resp", "Error" );
				CommonUtils.closeProgressDialog();
			}
		} );
	}

	private void setUiPageViewController() {

		dotsCount = bannerAdapter.getCount();
		Log.e( "Logcount", "" + dotsCount );
		dots = new ImageView[dotsCount];

		for ( int i = 0; i < dotsCount; i++ ) {
			dots[i] = new ImageView( BannerActivity.this );
			dots[i].setImageDrawable( getResources().getDrawable( R.drawable.nonselecteditem_dot ) );

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
			                                                                  LinearLayout.LayoutParams.WRAP_CONTENT );
			params.setMargins( 4, 0, 4, 0 );
			recentViewpager.addView( dots[i], params );
		}

		dots[0].setImageDrawable( getResources().getDrawable( R.drawable.selecteditem_dot ) );

		timer = new Timer();
		timer.schedule( new TimerTask() {
			@Override
			public void run() {
				runOnUiThread( new Runnable() {
					@Override
					public void run() {
						if ( count <= dotsCount ) {
							recentViewpager.setCurrentItem( count, true );
							count++;
						}
						else {
							count = 0;
							recentViewpager.setCurrentItem( count, true );
						}
					}
				} );
			}
		}, 1500, 2000 );
	}

	@Override
	public void onClick( View v ) {
		switch ( v.getId() ) {
			case R.id.linQuickCall:

				view5.setVisibility( View.VISIBLE );
				view1.setVisibility( View.GONE );
				view2.setVisibility( View.GONE );
				view3.setVisibility( View.GONE );
				view4.setVisibility( View.GONE );

				quickCallDialog();
				break;

			case R.id.img_social:
				socialDialog();
				break;
			case R.id.llSchemes:

				view1.setVisibility( View.GONE );
				view5.setVisibility( View.GONE );
				view2.setVisibility( View.GONE );
				view3.setVisibility( View.GONE );
				view4.setVisibility( View.GONE );

				Intent intentSchema = new Intent( this, SchemesActivity.class );
				intentSchema.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
				intentSchema.putExtra( "TAB_SELECTED", Constant.ISSCHEMASTABSELECT ).putExtra( "LOGGED_IN", "false" );
				startActivity( intentSchema );
				//
				break;
			case R.id.llApplyLoan:

				view2.setVisibility( View.GONE );
				view1.setVisibility( View.GONE );
				view5.setVisibility( View.GONE );
				view3.setVisibility( View.GONE );
				view4.setVisibility( View.GONE );

				Intent intentApplyLoan = new Intent( this, SchemesActivity.class );
				intentApplyLoan.putExtra( "TAB_SELECTED", Constant.ISAPPLYLOANSELECT ).putExtra( "LOGGED_IN", "false" );
				startActivity( intentApplyLoan );
				break;

			case R.id.linReferFriend:
				Intent intentReferFriend = new Intent( this, SchemesActivity.class );
				intentReferFriend.putExtra( "TAB_SELECTED", Constant.ISREFERFREINDSELECT ).putExtra( "LOGGED_IN", "false" );
				startActivity( intentReferFriend );
				break;

			case R.id.linLoanStaus:

				view4.setVisibility( View.GONE );
				view1.setVisibility( View.GONE );
				view2.setVisibility( View.GONE );
				view3.setVisibility( View.GONE );
				view5.setVisibility( View.GONE );

				if ( complaintsFragment.isAdded() ) {
					getSupportFragmentManager().beginTransaction().replace( R.id.frame_banner, complaintsFragment ).commit();
				}
				else {
					getSupportFragmentManager().beginTransaction().add( R.id.frame_banner, complaintsFragment ).commit();
				}

				break;
			case R.id.linLogin:
				startActivity( new Intent( BannerActivity.this, LoginActivity.class ) );
				finish();
				break;

			case R.id.img_Refresh_token:
				if ( edtQuickCall.getText().toString().length() == 10 || edtQuickCall.getText().toString().length() == 12 ) {
					quickCallInputModel = new QuickCallInputModel();
					quickCallInputModel.setMobileNumber( edtQuickCall.getText().toString() );
					CommonUtils.showProgressDialog( BannerActivity.this, "Please wait..." );
					callResponseModel( quickCallInputModel );
				}

				break;

			case R.id.imgQuickCall:

				view5.setVisibility( View.VISIBLE );
				view1.setVisibility( View.GONE );
				view2.setVisibility( View.GONE );
				view3.setVisibility( View.GONE );
				view4.setVisibility( View.GONE );
				quickCallDialog();
				break;
		}
	}

	public void linLoanStausClick() {
		int width = linLoanStaus.getWidth();
		Log.e( "Widthoflin", "" + width );
		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) {
			linLoanStaus.setBackgroundColor( Color.parseColor( "#003668" ) );
			view1.setVisibility( View.VISIBLE );
		}
	}

	public void quickCallDialog() {

		View view = LayoutInflater.from( this ).inflate( R.layout.dialog_quick_calling, null );
		dialog = new EasyDialog( this )
				.setLayout( view )
				.setBackgroundColor( Color.parseColor( "#FFFFFF" ) )
				.setLocationByAttachedView( imgQuickCall )
				.setGravity( EasyDialog.GRAVITY_TOP )
				.setAnimationTranslationShow( EasyDialog.DIRECTION_Y, 500, 800, 0 )
				.setAnimationAlphaShow( 500, 0, 0.5f, 1 )
				.setAnimationTranslationDismiss( EasyDialog.DIRECTION_Y, 500, -50, 800 )
				.setAnimationAlphaDismiss( 150, 1, 0 )
				.setTouchOutsideDismiss( true )
				.setMatchParent( true )
				.setMarginLeftAndRight( 25, 25 )
				.setOutsideColor( ContextCompat.getColor( this, R.color.background_color_black ) )
				.show();

		edtQuickCall = ( EditText ) view.findViewById( R.id.edt_mobile_no );
		edtOtpNo = ( EditText ) view.findViewById( R.id.edt_otp_no );
		imgRefreshOtp = ( ImageView ) view.findViewById( R.id.img_Refresh_token );
		imgRefreshOtp.setOnClickListener( this );
		TextView txtSubmit = ( TextView ) view.findViewById( R.id.txt_submit );
		checkBox = ( CheckBox ) view.findViewById( R.id.chkTermsCond );

		edtQuickCall.addTextChangedListener( new TextWatcher() {
			@Override
			public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

			}

			@Override
			public void onTextChanged( CharSequence s, int start, int before, int count ) {

			}

			@Override
			public void afterTextChanged( Editable s ) {

				if ( s.length() == 10 || s.length() == 12 ) {
					quickCallInputModel = new QuickCallInputModel();
					quickCallInputModel.setMobileNumber( edtQuickCall.getText().toString() );
					CommonUtils.showProgressDialog( BannerActivity.this, "Please wait..." );
					callResponseModel( quickCallInputModel );
					imgRefreshOtp.postDelayed( new Runnable() {
						public void run() {
							imgRefreshOtp.setVisibility( View.VISIBLE );
						}
					}, 10000 );
				}
			}
		} );

		txtSubmit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {

				if ( TextUtils.isEmpty( edtQuickCall.getText().toString() ) ) {
					Toast.makeText( BannerActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT ).show();
				}

				if ( TextUtils.isEmpty( edtOtpNo.getText().toString() ) ) {
					Toast.makeText( BannerActivity.this, "Please Enter OTP Number", Toast.LENGTH_SHORT ).show();
				}

				if ( !checkBox.isChecked() ) {
					Toast.makeText( BannerActivity.this, "You must agree to the terms first!", Toast.LENGTH_SHORT ).show();

				}
				if ( !TextUtils.isEmpty( edtQuickCall.getText().toString() ) && !TextUtils.isEmpty( edtOtpNo.getText().toString() ) ) {
					LoanStatusInputModel loanStatusInputModel = new LoanStatusInputModel();
					loanStatusInputModel.setOtpNumber( edtOtpNo.getText().toString() );
					loanStatusInputModel.setMobileNumber( edtQuickCall.getText().toString() );
					if ( edtQuickCall.getText().toString().length() == 10 || edtQuickCall.getText().toString().length() == 12 ) {
						CallLoanStatusModel( loanStatusInputModel );
					}
					else {
						Toast.makeText( BannerActivity.this, "Mobile Number must between 10 or 15 digit", Toast.LENGTH_SHORT ).show();
					}
				}
			}
		} );
	}

	public void callResponseModel( QuickCallInputModel quickCallInputModel ) {
		Log.e( "callResponseModel", "" + quickCallInputModel.getMobileNumber() );
		tmflApi.getQuickCallResponse( quickCallInputModel ).enqueue( new Callback< QuickCallResponse >() {
			@Override
			public void onResponse( Call< QuickCallResponse > call, Response< QuickCallResponse > response ) {
				Log.e( "getQuickCallResponse", new Gson().toJson( response.body() ) );

				CommonUtils.closeProgressDialog();

				if ( response != null && response.body().getStatus().equalsIgnoreCase( "success" ) ) {

					strOtpNo = response.body().getData().getOtp();

				}
				else {
					if ( response != null && response.body().getStatus().contains( "error" ) ) {
						errormsg = response.body().getError().getMobileNo().get( 0 );
					}
					// Toast.makeText(getBaseContext(),errormsg,Toast.LENGTH_SHORT).show();
					Log.e( "getcallErrorResponse", errormsg );
				}
			}

			@Override
			public void onFailure( Call< QuickCallResponse > call, Throwable t ) {
				Log.d( "error response", t.getMessage() );

				CommonUtils.closeProgressDialog();

			}
		} );
	}

	public void CallLoanStatusModel( LoanStatusInputModel loanStatusInputModel ) {
		tmflApiOtpSubmit = ApiService.getInstance().call();
		tmflApiOtpSubmit.getOtpResponse( loanStatusInputModel ).enqueue( new Callback< LoanStatusResponse >() {
			@Override
			public void onResponse( Call< LoanStatusResponse > call, Response< LoanStatusResponse > response ) {
				if ( response.body().getStatus().contains( "success" ) ) {
					//  Log.e("CallLoanStatusModel", response.body().getStatus());
					Toast.makeText( BannerActivity.this, "Our Call Centre will reach you within 2 working days.", Toast.LENGTH_SHORT ).show();
					view5.setVisibility( View.GONE );
					edtQuickCall.setText( "" );
					edtOtpNo.setText( "" );
					dialog.dismiss();

				}
				if ( response.body().getStatus().contains( "error" ) ) {
					//  Log.e("CallLoanStatusModel", response.body().getError());
					Toast.makeText( BannerActivity.this, response.body().getError(), Toast.LENGTH_SHORT ).show();
				}
			}

			@Override
			public void onFailure( Call< LoanStatusResponse > call, Throwable t ) {

			}
		} );
	}

	public void socialDialog() {
		imgSocial.setVisibility( View.VISIBLE );
		final Dialog socialdialog = new Dialog( BannerActivity.this, android.R.style.Theme_Holo_Dialog_NoActionBar );
		socialdialog.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
		socialdialog.setContentView( R.layout.dialog_social );

		WindowManager.LayoutParams params = socialdialog.getWindow().getAttributes();
		params.y = 5;
		params.x = 5;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		socialdialog.getWindow().setAttributes( params );
		socialdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
		socialdialog.setCancelable( true );
		final ImageView imgMessage   = ( ImageView ) socialdialog.findViewById( R.id.imgmsg );
		final ImageView imgMap       = ( ImageView ) socialdialog.findViewById( R.id.imgmap );
		final ImageView imgWhatsApp  = ( ImageView ) socialdialog.findViewById( R.id.imgwhatsapp );
		final ImageView imgPhoneCall = ( ImageView ) socialdialog.findViewById( R.id.imgcall );
		final ImageView imgcancel    = ( ImageView ) socialdialog.findViewById( R.id.imgcancel );
		if ( CommonUtils.isNetworkAvailable( BannerActivity.this ) ) {
			SocialUtil.getContactList();
		}
		else {
			Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}

		imgcancel.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				socialdialog.dismiss();
				imgSocial.setVisibility( View.VISIBLE );
			}
		} );
	/*	imgMessage.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				SocialUtil.sendMail( BannerActivity.this, SocialUtil.email );
			}
		} );
		imgPhoneCall.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				SocialUtil.dialPhoneCall( BannerActivity.this, SocialUtil.phoneNo );
			}
		} );
		imgWhatsApp.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				SocialUtil.sendWhatsAppMsg( BannerActivity.this, SocialUtil.whatsAppNo );
			}
		} );*/
		imgMap.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				Intent mapIntent=  new Intent( BannerActivity.this, LocateUsActivity.class );
				mapIntent.putExtra( "LOGGED_IN","false" );
				startActivity(mapIntent );
				socialdialog.dismiss();
				imgSocial.setVisibility( View.VISIBLE );
			}
		} );

		socialdialog.show();
		socialdialog.setOnCancelListener( new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel( DialogInterface dialog ) {
				imgSocial.setVisibility( View.VISIBLE );
			}
		} );

	}

	@Override
	public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

	}

	@Override
	public void onPageSelected( int position ) {
		for ( int i = 0; i < dotsCount; i++ ) {

			dots[i].setImageDrawable( getResources().getDrawable( R.drawable.nonselecteditem_dot ) );
		}
		dots[position].setImageDrawable( getResources().getDrawable( R.drawable.selecteditem_dot ) );
	}

	@Override
	public void onPageScrollStateChanged( int state ) {

	}
}

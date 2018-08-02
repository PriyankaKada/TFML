package com.tmfl.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tmfl.R;
import com.tmfl.adapter.DrawerAdapter;
import com.tmfl.auth.Constant;
import com.tmfl.auth.TmflApi;
import com.tmfl.common.ApiService;
import com.tmfl.common.CommonUtils;
import com.tmfl.common.SocialUtil;
import com.tmfl.model.logoutResponseModel.LogoutInputModel;
import com.tmfl.model.logoutResponseModel.LogoutResponseModel;
import com.tmfl.util.PreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DrawerBaseActivity extends BaseActivity {

	protected FrameLayout frameLayout;
	DrawerLayout          drawerLayout;
	ActionBarDrawerToggle drawerToggle;
	Toolbar               toolbar;
	NavigationView        navigation;
	ListView              lsvNavList;
	ImageView             imgCancel;
	TextView              txtUserName;
	LogoutInputModel      logoutInputModel;
	LogoutResponseModel   logoutResponseModel;
	TmflApi               tmflApi;
	String TITLES[] = { "New Offers", "Apply Loan", "Refer Friend", "Downloads",
			"Change Password", "Logout", "Contact Us", "Toll-free Number", "WhatsApp", "Mail Us", "Locate us" };
	int    ICONS[]  = { R.drawable.ic_scheme_selected, R.drawable.ic_apply_loan_selected, R.drawable.ic_refer_friends_selected,
			R.drawable.ic_download, R.drawable.ic_change_pass_selected, R.drawable.ic_logout,
			R.drawable.ic_checked, R.drawable.ic_call_non_selected, R.drawable.icon_whatsapp,
			R.drawable.ic_email, R.drawable.ic_locate_us, };

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_drawer_base );
		frameLayout = ( FrameLayout ) findViewById( R.id.framelayout_base_container );
		lsvNavList = ( ListView ) findViewById( R.id.lst_navigation_menu );
		txtUserName = ( TextView ) findViewById( R.id.txt_user_name );
		imgCancel = ( ImageView ) findViewById( R.id.img_cancel );
		imgCancel.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( View v ) {
				drawerLayout.closeDrawer( GravityCompat.END );
			}
		} );
		if ( PreferenceHelper.getString( PreferenceHelper.USER_ID ) != null ) {
			txtUserName.setText( PreferenceHelper.getString( PreferenceHelper.USER_FIRT_NAME ) );
		}

		if ( CommonUtils.isNetworkAvailable( this ) ) {
			SocialUtil.getContactList();
		}
		else {
			Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
		}
		tmflApi = ApiService.getInstance().call();
		initInstances();
	}

	public void initInstances() {

		drawerLayout = ( DrawerLayout ) findViewById( R.id.drawerLayout );
		drawerToggle = new ActionBarDrawerToggle( DrawerBaseActivity.this, drawerLayout, R.string.refer_friend, R.string.refer_friend );
		drawerLayout.setDrawerListener( drawerToggle );
		lsvNavList.setAdapter( new DrawerAdapter( this, TITLES, ICONS ) );
		lsvNavList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
				switch ( position ) {
					case 0://New Schemes
						Intent intentSchema = new Intent( DrawerBaseActivity.this, SchemesActivity.class );
						intentSchema.putExtra( "TAB_SELECTED", Constant.ISSCHEMASTABSELECT ).putExtra( "LOGGED_IN", "true" );
						startActivity( intentSchema );

						drawerLayout.closeDrawers();
						/*getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE );

						getSupportFragmentManager().beginTransaction()
								.addToBackStack( getClass().getName() )
								.replace( R.id.frame_container_contract, new NewSchemeFragment() )
								.commit();*/

						break;

					case 1://Apply Loan
						Intent intentApplyLoan = new Intent( DrawerBaseActivity.this, SchemesActivity.class ).putExtra( "LOGGED_IN", "true" );
						intentApplyLoan.putExtra( "TAB_SELECTED", Constant.ISAPPLYLOANSELECT );
						startActivity( intentApplyLoan );
						drawerLayout.closeDrawers();

					/*	getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE );

						getSupportFragmentManager().beginTransaction()
								.addToBackStack( getClass().getName() )
								.replace( R.id.frame_container_contract, new ApplyLoanFragment() )
								.commit();*/

						break;
					case 2://Refer Friends
						Intent intentReferFriend = new Intent( DrawerBaseActivity.this, SchemesActivity.class );
						intentReferFriend.putExtra( "TAB_SELECTED", Constant.ISREFERFREINDSELECT ).putExtra( "LOGGED_IN", "true" );
						startActivity( intentReferFriend );
						drawerLayout.closeDrawers();

						/*getSupportFragmentManager().popBackStack( null, FragmentManager.POP_BACK_STACK_INCLUSIVE );

						getSupportFragmentManager().beginTransaction()
								.addToBackStack( getClass().getName() )
								.replace( R.id.frame_container_contract, new ReferFriendFragment() )
								.commit();*/

						break;
					case 3://Download
						startActivity( new Intent( DrawerBaseActivity.this, DownloadDataActivity.class ) );
						drawerLayout.closeDrawers();
						break;
					case 4://Change Password
						startActivity( new Intent( DrawerBaseActivity.this, ChangePasswordActivity.class ) );
						drawerLayout.closeDrawers();
						break;
					case 5://Logout
						if ( CommonUtils.isNetworkAvailable( DrawerBaseActivity.this ) ) {
							logoutInputModel = new LogoutInputModel();
							logoutResponseModel = new LogoutResponseModel();
							logoutInputModel.setUser_id( PreferenceHelper.getString( PreferenceHelper.USER_ID ) );
							logoutInputModel.setApi_token( PreferenceHelper.getString( PreferenceHelper.API_TOKEN ) );
							logout( logoutInputModel );
							drawerLayout.closeDrawers();
						}
						else {
							Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
						}
						break;
					case 6://Contact
						//Nothing do Here
						drawerLayout.closeDrawers();
						break;
					case 7://Phone Call
						if ( CommonUtils.isNetworkAvailable( DrawerBaseActivity.this ) ) {
							SocialUtil.getContactList();
							SocialUtil.dialPhoneCall( DrawerBaseActivity.this, "18002090188" );
							drawerLayout.closeDrawers();
						}
						else {
							Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
						}
						break;
					case 8://WhatsApp Call
						if ( CommonUtils.isNetworkAvailable( DrawerBaseActivity.this ) ) {
							SocialUtil.getContactList();
							try {
								SocialUtil.sendWhatsAppMsg( DrawerBaseActivity.this, SocialUtil.whatsAppNo );
							}
							catch ( Exception e ) {
								e.printStackTrace();
							}
							drawerLayout.closeDrawers();

						}
						else {
							Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
						}
						break;
					case 9://Send Mail
						if ( CommonUtils.isNetworkAvailable( DrawerBaseActivity.this ) ) {
							SocialUtil.getContactList();
//							Log.d( "to emailid", SocialUtil.emailId );

//							Log.d( "email", PreferenceHelper.getString( Constant.EMAIL ) );
							SocialUtil.sendMail( DrawerBaseActivity.this, PreferenceHelper.getString( Constant.EMAIL ) );
							drawerLayout.closeDrawers();
						}
						else {
							Toast.makeText( getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT ).show();
						}
						break;
					case 10://Locate Map
						Intent mapIntent=  new Intent( DrawerBaseActivity.this, LocateUsActivity.class );
						mapIntent.putExtra( "LOGGED_IN","true" );
						startActivity(mapIntent );
						drawerLayout.closeDrawers();
						break;
					default:
				}
			}
		} );

	}

	public void openDrawer() {
		drawerLayout.openDrawer( GravityCompat.END );
	}

	@Override
	public void onPostCreate( Bundle savedInstanceState ) {
		super.onPostCreate( savedInstanceState );
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged( Configuration newConfig ) {
		super.onConfigurationChanged( newConfig );
		drawerToggle.onConfigurationChanged( newConfig );
	}


	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {

		int id = item.getItemId();
		if ( id == android.R.id.home ) {
			drawerLayout.openDrawer( GravityCompat.END );
		}

		return super.onOptionsItemSelected( item );
	}


	private void logout( LogoutInputModel logoutInputModel ) {

		tmflApi.getLogoutResponse( logoutInputModel ).enqueue( new Callback< LogoutResponseModel >() {
			@Override
			public void onResponse( Call< LogoutResponseModel > call, Response< LogoutResponseModel > response ) {
				if ( response.body().getStatus().equals( "Success" ) ) {
					PreferenceHelper.remove( PreferenceHelper.USER_ID );
					PreferenceHelper.remove( PreferenceHelper.API_TOKEN );
					PreferenceHelper.insertBoolean( "SaveLogin", false );
					Intent intent = new Intent( DrawerBaseActivity.this, BannerActivity.class );
					intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
					intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
					intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
					startActivity( intent );
					finish();
				}
				else {
					Intent intent = new Intent( DrawerBaseActivity.this, BannerActivity.class );
					startActivity( intent );
					finish();
				}

			}

			@Override
			public void onFailure( Call< LogoutResponseModel > call, Throwable t ) {
				//  Log.e("onFailure",t.getMessage().toString());
			}
		} );


	}

}

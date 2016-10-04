package com.tfml.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.adapter.DrawerAdapter;
import com.tfml.auth.Constant;
import com.tfml.common.CommonUtils;
import com.tfml.common.SocialUtil;
import com.tfml.util.PreferenceHelper;


public class DrawerBaseActivity extends BaseActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    NavigationView navigation;
    ListView lsvNavList;
    ImageView imgCancel;
    protected FrameLayout frameLayout;
    String TITLES[] = {"New Schemes", "Apply Loan", "Refer Friend", "Downloads",
            "Change Password", "Logout", "Contact Us", "Phone Call", "WhatsApp Call", "Mail Us", "Locate us"};
    int ICONS[] = {R.drawable.ic_scheme_selected, R.drawable.ic_apply_loan_selected, R.drawable.ic_refer_friends_selected,
            R.drawable.ic_download, R.drawable.ic_change_pass_selected, R.drawable.ic_logout,
            R.drawable.ic_checked, R.drawable.ic_call_non_selected, R.drawable.icon_whatsapp,
            R.drawable.ic_email, R.drawable.ic_locate_us,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_base);
        frameLayout = (FrameLayout) findViewById(R.id.framelayout_base_container);
        lsvNavList = (ListView) findViewById(R.id.lst_navigation_menu);
        imgCancel = (ImageView) findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        initInstances();
    }

    public void initInstances() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(DrawerBaseActivity.this, drawerLayout, R.string.refer_friend, R.string.refer_friend);
        drawerLayout.setDrawerListener(drawerToggle);
        lsvNavList.setAdapter(new DrawerAdapter(this, TITLES, ICONS));
        lsvNavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://New Schemes
                        Intent intentSchema = new Intent(DrawerBaseActivity.this, SchemesActivity.class);
                        intentSchema.putExtra("TAB_SELECTED", Constant.ISSCHEMASTABSELECT);
                        startActivity(intentSchema);
                        break;

                    case 1://Apply Loan
                        Intent intentApplyLoan = new Intent(DrawerBaseActivity.this, SchemesActivity.class);
                        intentApplyLoan.putExtra("TAB_SELECTED", Constant.ISAPPLYLOANSELECT);
                        startActivity(intentApplyLoan);
                        break;
                    case 2://Refer Friends
                        Intent intentReferFriend = new Intent(DrawerBaseActivity.this, SchemesActivity.class);
                        intentReferFriend.putExtra("TAB_SELECTED", Constant.ISREFERFREINDSELECT);
                        startActivity(intentReferFriend);
                        break;
                    case 3://Download
                        startActivity(new Intent(DrawerBaseActivity.this, DownloadDataActivity.class));
                        break;
                    case 4://Change Password
                        startActivity(new Intent(DrawerBaseActivity.this, ChangePasswordActivity.class));
                        break;
                    case 5://Logout
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            logout();
                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 6://Contact
                        //Nothing do Here
                        break;
                    case 7://Phone Call
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            SocialUtil.getContactList();
                            SocialUtil.dialPhoneCall(DrawerBaseActivity.this, SocialUtil.phoneNo);
                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 8://WhatsApp Call
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            SocialUtil.getContactList();
                            try {
                                SocialUtil.sendWhatsAppMsg(DrawerBaseActivity.this, SocialUtil.whatsAppNo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 9://Send Mail
                        if (CommonUtils.isNetworkAvailable(DrawerBaseActivity.this)) {
                            SocialUtil.getContactList();
                            SocialUtil.sendMail(DrawerBaseActivity.this, SocialUtil.email);
                        } else {
                            Toast.makeText(getBaseContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 10://Locate Map
                        startActivity(new Intent(DrawerBaseActivity.this, LocateUsActivity.class));
                        break;
                    default:
                }
            }
        });

    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.END);
        }

        return super.onOptionsItemSelected(item);
    }


    private void logout() {
        PreferenceHelper.remove(PreferenceHelper.USER_ID);
        PreferenceHelper.remove(PreferenceHelper.API_TOKEN);
        PreferenceHelper.insertBoolean("SaveLogin", false);
        PreferenceHelper.insertBoolean(PreferenceHelper.ISLOGIN, false);
        PreferenceHelper.insertBoolean(PreferenceHelper.FLAG_LOGGED_OUT, true);
        Intent intent = new Intent(this,
                BannerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

}

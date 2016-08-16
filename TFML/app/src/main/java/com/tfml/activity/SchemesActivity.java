package com.tfml.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.adapter.SchemesPagerAdapter;
import com.tfml.fragment.ApplyLoanFragment;
import com.tfml.fragment.NewSchemeFragment;
import com.tfml.fragment.ReferFriendFragment;

public class SchemesActivity extends BaseActivity implements View.OnClickListener{
    Toolbar toolbarschemes;
    private TextView txtschemestitle;
    TabLayout tabLayout;
    private ImageView imgtoolbarhome,imgSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);

        toolbarschemes = (Toolbar) findViewById(R.id.toolbar_schemes);
        txtschemestitle = (TextView) findViewById(R.id.txt_schemes_title);
        imgtoolbarhome=(ImageView)findViewById(R.id.img_toolbar_home);
        imgSocial=(ImageView)findViewById(R.id.img_social);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        imgtoolbarhome.setOnClickListener(this);
        imgSocial.setOnClickListener(this);

    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Schemes");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_scheme_non_selected, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Apply Loan");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_apply_loan_non_selected, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Refer Friend");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_refer_friends_non_selected, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        SchemesPagerAdapter adapter = new SchemesPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new NewSchemeFragment(), "Schemes");
        adapter.addFrag(new ApplyLoanFragment(), "Apply Loan");
        adapter.addFrag(new ReferFriendFragment(), "Refer Friends");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTitle(String name) {
        txtschemestitle.setText(name);
    }

    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "New Schema";
            case 1:
                return "Apply Loans";
            case 2:
                return "Refer Friends";
            default:
                return "";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_toolbar_home:
                startActivity(new Intent(SchemesActivity.this,BannerActivity.class));
                finish();
                break;
            case R.id.img_social:
                socialDialog();
                break;
        }

    }

    public void socialDialog()
    {
        imgSocial.setVisibility(View.INVISIBLE);
        final Dialog socialdialog = new Dialog(SchemesActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        socialdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        socialdialog.setContentView(R.layout.dialog_social);
        WindowManager.LayoutParams params = socialdialog.getWindow().getAttributes();
        params.y = 5; params.x = 5;
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        socialdialog.getWindow().setAttributes(params);
        socialdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        socialdialog.setCancelable(true);
        final ImageView imgMessage = (ImageView) socialdialog.findViewById(R.id.imgmsg);
        final ImageView imgMap=(ImageView)socialdialog.findViewById(R.id.imgmap);
        final ImageView imgWhatsApp=(ImageView)socialdialog.findViewById(R.id.imgwhatsapp);
        final ImageView imgPhoneCall=(ImageView)socialdialog.findViewById(R.id.imgcall);
        final ImageView imgcancel=(ImageView)socialdialog.findViewById(R.id.imgcancel);
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialdialog.dismiss();
                imgSocial.setVisibility(View.VISIBLE);
            }
        });
        imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        imgPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialPhoneCall();
            }
        });
        imgWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendWhatsAppMsg();
            }
        });


        socialdialog.show();

    }

    public void sendWhatsAppMsg()
    {
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "Welcome to TFML";
            waIntent.setPackage("com.whatsapp");
            if (waIntent != null) {
                waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                startActivity(Intent.createChooser(waIntent, "Share with"));
            } else {
                Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);

        }

    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void dialPhoneCall()
    {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:18002090188"));
        startActivity(callIntent);
    }

    public void  sendMail()
    {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finish sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SchemesActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}

package com.tfml.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tfml.R;
import com.tfml.adapter.SchemesPagerAdapter;
import com.tfml.fragment.ApplyLoanFragment;
import com.tfml.fragment.NewSchemeFragment;
import com.tfml.fragment.ReferFriendFragment;

public class SchemesActivity extends BaseActivity implements View.OnClickListener{
    Toolbar toolbarschemes;
    TextView txtschemestitle;
    TabLayout tabLayout;
    ImageView imgtoolbarhome,imgsocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);

        toolbarschemes = (Toolbar) findViewById(R.id.toolbar_schemes);
        txtschemestitle = (TextView) findViewById(R.id.txt_schemes_title);
        imgtoolbarhome=(ImageView)findViewById(R.id.img_toolbar_home);
        imgsocial=(ImageView)findViewById(R.id.img_social);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        imgtoolbarhome.setOnClickListener(this);
        imgsocial.setOnClickListener(this);

    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Schemes");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Apply Loan");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_message, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Refer Friends");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_whatsapp, 0, 0);
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
        imgsocial.setVisibility(View.INVISIBLE);
        final Dialog socialdialog = new Dialog(SchemesActivity.this,android.R.style.Theme_Holo_Dialog_NoActionBar);
        socialdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        socialdialog.setContentView(R.layout.dialog_social);
        WindowManager.LayoutParams params = socialdialog.getWindow().getAttributes();
        params.y = 5; params.x = 5;
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        socialdialog.getWindow().setAttributes(params);
        socialdialog.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
        socialdialog.setCancelable(false);
        final ImageView imgmessage = (ImageView) socialdialog.findViewById(R.id.imgmsg);
        final ImageView imgmap=(ImageView)socialdialog.findViewById(R.id.imgmap);
        final ImageView imgwhatsapp=(ImageView)socialdialog.findViewById(R.id.imgwhatsapp);
        final ImageView imgphonecall=(ImageView)socialdialog.findViewById(R.id.imgcall);
        final ImageView imgcancel=(ImageView)socialdialog.findViewById(R.id.imgcancel);
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialdialog.dismiss();
                imgsocial.setVisibility(View.VISIBLE);
            }
        });
        socialdialog.show();

    }

}

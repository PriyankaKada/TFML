package com.tfml.activity;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tfml.R;
import com.tfml.adapter.EmiPagerAdapter;
import com.tfml.adapter.SchemesPagerAdapter;
import com.tfml.fragment.EmiPatternFragment;
import com.tfml.fragment.PreClosureFragment;
import com.tfml.fragment.RcUpdateFragment;
import com.tfml.fragment.StatementOfAccountFragment;

public class EmiActivity extends DrawerBaseActivity implements View.OnClickListener
{

    Toolbar toolbarEmi;
    ImageView imgEmiBack,imgDrawer;
    TextView txtEmiName;
    TabLayout emiTabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi);
        toolbarEmi=(Toolbar)findViewById(R.id.toolbar_emi);
        setSupportActionBar(toolbarEmi);
        getSupportActionBar().setTitle("");
        init();
    }

    public void init()
    {
         imgEmiBack=(ImageView)findViewById(R.id.img_emi_back);
        txtEmiName=(TextView)findViewById(R.id.txt_titel_emi);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);
        emiTabLayout= (TabLayout) findViewById(R.id.tab_layout_emi);
        emiTabLayout.setupWithViewPager(viewPager);
        imgEmiBack=(ImageView)findViewById(R.id.img_emi_back);
        imgDrawer=(ImageView)findViewById(R.id.img_drawer_emi);
        setupTabIcon();
        imgEmiBack.setOnClickListener(this);
        imgDrawer.setOnClickListener(this);

    }


    private void setupViewPager(ViewPager viewPager) {
        EmiPagerAdapter adapter = new EmiPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new EmiPatternFragment(), "EMI pattern");
        adapter.addFrag(new StatementOfAccountFragment(), "Statment of account");
        adapter.addFrag(new RcUpdateFragment(), "RC update");
      adapter.addFrag(new PreClosureFragment(),"Pre-closure statement");
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
    public String getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TATA Tiago";
            case 1:
                return "TATA Tiago";
            case 2:
                return "TATA Tiago";
            case 3:
                return "TATA Tiago";
            default:
                return "TATA Tiago";
        }
    }

    public void setupTabIcon()
    {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("EMI pattern");
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_scheme_non_selected, 0, 0);
        emiTabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Statment of account");
       // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_apply_loan_non_selected, 0, 0);
        emiTabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("RC update");
       // tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_refer_friends_non_selected, 0, 0);
        emiTabLayout.getTabAt(2).setCustomView(tabThree);
       TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Pre-closure statement");
       // tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_refer_friends_non_selected, 0, 0);
        emiTabLayout.getTabAt(3).setCustomView(tabFour);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_emi_back:
                startActivity(new Intent(EmiActivity.this,ContractActivity.class));
                break;
            case R.id.img_drawer_emi:
                Toast.makeText(getBaseContext(),"I am in Nav Drawer",Toast.LENGTH_SHORT).show();
                openDrawer();
                break;

        }

    }
}

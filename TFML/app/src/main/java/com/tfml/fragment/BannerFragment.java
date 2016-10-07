package com.tfml.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tfml.R;
import com.tfml.adapter.BannerAdapter;
import com.tfml.auth.TmflApi;
import com.tfml.common.ApiService;
import com.tfml.common.CommonUtils;
import com.tfml.model.bannerResponseModel.BannerlistResponse;
import com.tfml.model.bannerResponseModel.Datum;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BannerFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public ViewPager recentViewpager;
    CirclePageIndicator circlePageIndicator;
    TmflApi tmflApi;
    BannerAdapter bannerAdapter;
    private ImageView[] dots;
    private int dotsCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_banner, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recentViewpager = (ViewPager) view.findViewById(R.id.recentViewpager);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.titles);
        circlePageIndicator.setRadius(10.0f);
        tmflApi = ApiService.getInstance().call();
        if(CommonUtils.isNetworkAvailable(getActivity()))
        {
            CommonUtils.showProgressDialog(getActivity(),"Getting Your Information");
            callBannerList();
        }
        else
        {
            Toast.makeText(getActivity(),"Please Check Network Connection",Toast.LENGTH_SHORT).show();
        }

    }
        public void callBannerList()
        {
            tmflApi.getBannerResponse().enqueue(new Callback<BannerlistResponse>() {
                @Override
                public void onResponse(Call<BannerlistResponse> call, Response<BannerlistResponse> response) {
                    BannerlistResponse bannerlistResponse = response.body();
                    CommonUtils.closeProgressDialog();
                    if(response.body().getStatus()!=null && response.body().getStatus().equals("success"))
                    {
                        Log.e("BannerlistResponse",new Gson().toJson(response.body().getStatus()));
                        Log.e("CallbannerListResponse",""+bannerlistResponse.getBanners().getData().get(0).getImage());
                        bannerAdapter=new BannerAdapter(getActivity(), (ArrayList<Datum>) bannerlistResponse.getBanners().getData());
                        recentViewpager.setAdapter(bannerAdapter);
                        setUiPageViewController();
                        recentViewpager.setCurrentItem(0);
                        circlePageIndicator.setViewPager(recentViewpager);

                    }
                    else
                    {
                        Log.e("ErrorResponse",response.errorBody().toString());
                    }

                }

                @Override
                public void onFailure(Call<BannerlistResponse> call, Throwable t) {
                    Log.e("Resp", "Error");
                    CommonUtils.closeProgressDialog();
                }
            });

        }

    private void setUiPageViewController() {

        dotsCount = bannerAdapter.getCount();
        Log.e("Logcount",""+dotsCount);
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            recentViewpager.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

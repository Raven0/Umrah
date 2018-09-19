package com.birutekno.aiwa.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;

import com.birutekno.aiwa.GalleryActivity;
import com.birutekno.aiwa.ItineraryActivity;
import com.birutekno.aiwa.JadwalActivity;
import com.birutekno.aiwa.JamaahActivity;
import com.birutekno.aiwa.KalkulasiActivity;
import com.birutekno.aiwa.LokasiActivity;
import com.birutekno.aiwa.R;
import com.birutekno.aiwa.adapter.BannerPagerAdapter;
import com.birutekno.aiwa.helper.AutoScrollViewPager;
import com.birutekno.aiwa.helper.CirclePageIndicator;
import com.birutekno.aiwa.helper.GalleryResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataGallery;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class HomeFragment extends BaseFragment{

    private ArrayList<DataGallery> data;

    @OnClick(R.id.jamaah)
    void jamaahClicked() {
        Intent intent = JamaahActivity.createIntent(getActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("pos", 1);
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.kalkulasi)
    void kalkulasiClicked() {
        Intent intent = KalkulasiActivity.createIntent(getActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("pos", 1);
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.jadwal)
    void jadwalClicked() {
        Intent intent = JadwalActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.itinerary)
    void itineraryClicked() {
        Intent intent = ItineraryActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.lokasi)
    void lokasiClicked() {
        Intent intent = LokasiActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.galeri)
    void galeriClicked() {
        Intent intent = GalleryActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @Bind(R.id.pager)
    protected AutoScrollViewPager mPager;

    @Bind(R.id.circle_indicator)
    protected CirclePageIndicator mIndicator;

    private ProgressDialog pDialog;
    private BannerPagerAdapter mBannerAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initBanner();
    }

    private void initBanner(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(true);
        pDialog.show();
        Call<GalleryResponse> call = WebApi.getAPIService().getFotoDashboard();
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                if(response.isSuccessful()){
                    GalleryResponse jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mBannerAdapter = new BannerPagerAdapter(getChildFragmentManager(), data);

                    int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
                    mPager.setPageMargin(gap);

                    mBannerAdapter.setData(data);
                    mPager.setClipToPadding(false);
                    mPager.setAdapter(mBannerAdapter);
                    mIndicator.setViewPager(mPager);

                    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    if(data.size() > 1){
                        mPager.setInterval(3000);
                        mPager.setAutoScrollDurationFactor(10);
                        mPager.startAutoScroll();
                    }else if(data.size() == 1){
                        mPager.stopAutoScroll();
                    }
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
                initBanner();
            }
        });
    }
}

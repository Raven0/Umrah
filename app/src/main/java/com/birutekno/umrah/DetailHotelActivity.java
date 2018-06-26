package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.umrah.adapter.BannerPagerAdapter;
import com.birutekno.umrah.adapter.HotelPagerAdapter;
import com.birutekno.umrah.helper.AutoScrollViewPager;
import com.birutekno.umrah.helper.CirclePageIndicator;
import com.birutekno.umrah.model.Banner;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailHotelActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.infoText)
    TextView mInfoText;

    @Bind(R.id.infoTab)
    LinearLayout mInfoTab;

    @Bind(R.id.infoIndicator)
    LinearLayout mInfoIndicator;

    @Bind(R.id.fasilitasText)
    TextView mFasilitasText;

    @Bind(R.id.fasilitasTab)
    LinearLayout mFasilitasTab;

    @Bind(R.id.fasilitasIndicator)
    LinearLayout mFasilitasIndicator;

    @Bind(R.id.lokasiText)
    TextView mLokasiText;

    @Bind(R.id.lokasiTab)
    LinearLayout mLokasiTab;

    @Bind(R.id.lokasiIndicator)
    LinearLayout mLokasiIndicator;

    @Bind(R.id.videoText)
    TextView mVideoText;

    @Bind(R.id.videoTab)
    LinearLayout mVideoTab;

    @Bind(R.id.videoIndicator)
    LinearLayout mVideocator;

    @Bind(R.id.pagerAnj)
    ViewPager mPager;

    @Bind(R.id.pager)
    protected AutoScrollViewPager bPager;

    @Bind(R.id.circle_indicator)
    protected CirclePageIndicator mIndicator;

    @OnClick(R.id.infoTab)
    void fotoClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.fasilitasTab)
    void fasilitasClicked(){
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.lokasiTab)
    void lokasiClicked(){
        mPager.setCurrentItem(2);
    }

    @OnClick(R.id.videoTab)
    void videoClicked(){
        mPager.setCurrentItem(3);
    }

    private HotelPagerAdapter mAdapter;
    private BannerPagerAdapter mBannerAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, DetailHotelActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_detail_hotel;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");
        initBanner();
        setupPager();
    }

    private void setupPager() {
        mAdapter = new HotelPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(2);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources()
                .getDisplayMetrics());
        mPager.setPageMargin(pageMargin);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mInfoIndicator.setVisibility(View.VISIBLE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mFasilitasIndicator.setVisibility(View.GONE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mLokasiIndicator.setVisibility(View.GONE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mVideocator.setVisibility(View.GONE);
                        break;
                    case 1:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mInfoIndicator.setVisibility(View.GONE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mFasilitasIndicator.setVisibility(View.VISIBLE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mLokasiIndicator.setVisibility(View.GONE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mVideocator.setVisibility(View.GONE);
                        break;
                    case 2:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mInfoIndicator.setVisibility(View.GONE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mFasilitasIndicator.setVisibility(View.GONE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mLokasiIndicator.setVisibility(View.VISIBLE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mVideocator.setVisibility(View.GONE);
                        break;
                    case 3:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mInfoIndicator.setVisibility(View.GONE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mFasilitasIndicator.setVisibility(View.GONE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mLokasiIndicator.setVisibility(View.GONE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mVideocator.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initBanner(){
        final List<Banner> data = new ArrayList<>();

        data.add(new Banner(1, R.drawable.image));
        data.add(new Banner(2, R.drawable.image1));
        data.add(new Banner(3, R.drawable.image2));

        mBannerAdapter = new BannerPagerAdapter(getSupportFragmentManager(), data);

        int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        bPager.setPageMargin(gap);

        mBannerAdapter.setData(data);
        bPager.setClipToPadding(false);
        bPager.setAdapter(mBannerAdapter);
        mIndicator.setViewPager(bPager);

        bPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                int pos = mPager.getCurrentItem();
//                int size = data.size();
//
//                if(pos == size){
//                    mPager.setCurrentItem(1);
//                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Toast.makeText(mContext, state , Toast.LENGTH_SHORT).show();

            }
        });

        if(data.size() > 1){
            bPager.setInterval(3000);
            bPager.setAutoScrollDurationFactor(10);
            bPager.startAutoScroll();
        }else if(data.size() == 1){
            bPager.stopAutoScroll();
        }
    }
}

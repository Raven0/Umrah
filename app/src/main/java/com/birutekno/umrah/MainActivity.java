package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.birutekno.umrah.adapter.MainPagerAdapter;
import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {

    @Bind(R.id.pager)
    protected ViewPager mPager;

    @Bind(R.id.notificationImage)
    protected ImageView mNImage;

    @Bind(R.id.notificationText)
    protected TextView mNtext;

    @Bind(R.id.homeImage)
    protected ImageView mHImage;

    @Bind(R.id.homeText)
    protected TextView mHtext;

    @Bind(R.id.profileImage)
    protected ImageView mPImage;

    @Bind(R.id.profileText)
    protected TextView mPtext;

    @Bind(R.id.dashboardImage)
    protected ImageView mDImage;

    @Bind(R.id.dashboardText)
    protected TextView mDtext;

    @OnClick(R.id.notificationTab)
    void notificationTabClicked() {
        mPager.setCurrentItem(2);
    }

    @OnClick(R.id.homeTab)
    void homeTabClicked() {
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.profileTab)
    void profileTabClicked() {
        mPager.setCurrentItem(3);
    }

    @OnClick(R.id.dashboardTab)
    void dashboardTabClicked() {
        mPager.setCurrentItem(0);
    }

    private MainPagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

        mNtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        mNImage.setBackgroundResource(R.drawable.ic_notif_white);

        mHtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        mHImage.setBackgroundResource(R.drawable.ic_home_white);

        mPtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
        mPImage.setBackgroundResource(R.drawable.ic_setting_white);

        mDtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
        mDImage.setBackgroundResource(R.drawable.ic_dashboard_yellow);

        initPager();
    }

    private void initPager(){
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(4);
        mPager.setCurrentItem(0);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        mPager.setPageMargin(pageMargin);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mDtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                        mDImage.setBackgroundResource(R.drawable.ic_dashboard_yellow);

                        mNtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mNImage.setBackgroundResource(R.drawable.ic_notif_white);

                        mHtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mHImage.setBackgroundResource(R.drawable.ic_home_white);

                        mPtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mPImage.setBackgroundResource(R.drawable.ic_setting_white);
                        break;
                    case 1:
                        mDtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mDImage.setBackgroundResource(R.drawable.ic_dashboard_white);

                        mNtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mNImage.setBackgroundResource(R.drawable.ic_notif_white);

                        mHtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                        mHImage.setBackgroundResource(R.drawable.ic_home_yellow);

                        mPtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mPImage.setBackgroundResource(R.drawable.ic_setting_white);
                        break;
                    case 2:
                        mDtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mDImage.setBackgroundResource(R.drawable.ic_dashboard_white);

                        mNtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                        mNImage.setBackgroundResource(R.drawable.ic_notif_yellow);

                        mHtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mHImage.setBackgroundResource(R.drawable.ic_home_white);

                        mPtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mPImage.setBackgroundResource(R.drawable.ic_setting_white);
                        break;
                    case 3:
                        mDtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mDImage.setBackgroundResource(R.drawable.ic_dashboard_white);

                        mNtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mNImage.setBackgroundResource(R.drawable.ic_notif_white);

                        mHtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                        mHImage.setBackgroundResource(R.drawable.ic_home_white);

                        mPtext.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.yellow));
                        mPImage.setBackgroundResource(R.drawable.ic_setting_yellow);
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
}

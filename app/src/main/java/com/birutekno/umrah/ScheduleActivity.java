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

import com.birutekno.umrah.adapter.SchedulePagerAdapter;
import com.birutekno.umrah.adapter.ScorePagerAdapter;
import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class ScheduleActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.mondayText)
    TextView mMondayText;

    @Bind(R.id.mondayTab)
    LinearLayout mMondayTab;

    @Bind(R.id.mondayIndicator)
    LinearLayout mMondayIndicator;

    @Bind(R.id.tuesdayText)
    TextView mTuesdayText;

    @Bind(R.id.tuesdayTab)
    LinearLayout mTuesdayTab;

    @Bind(R.id.tuesdayIndicator)
    LinearLayout mTuesdayIndicator;

    @Bind(R.id.wednesdayText)
    TextView mWednesdayText;

    @Bind(R.id.wednesdayTab)
    LinearLayout mWednesdayTab;

    @Bind(R.id.wednesdayIndicator)
    LinearLayout mWednesdayIndicator;

    @Bind(R.id.thursdayText)
    TextView mThursdayText;

    @Bind(R.id.thursdayTab)
    LinearLayout mThursdayTab;

    @Bind(R.id.thursdayIndicator)
    LinearLayout mThursdayIndicator;

    @Bind(R.id.fridayText)
    TextView mFridayText;

    @Bind(R.id.fridayTab)
    LinearLayout mFridayTab;

    @Bind(R.id.fridayIndicator)
    LinearLayout mFridayIndicator;

    @Bind(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.mondayTab)
    void mondayClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.tuesdayTab)
    void tuesdayClicked(){
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.wednesdayTab)
    void wednesdayClicked(){
        mPager.setCurrentItem(2);
    }

    @OnClick(R.id.thursdayTab)
    void thursdayClicked(){
        mPager.setCurrentItem(3);
    }

    @OnClick(R.id.fridayTab)
    void fridayClicked(){
        mPager.setCurrentItem(4);
    }


    private SchedulePagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ScheduleActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_schedule;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        setupPager();
    }

    private void setupPager() {
        mAdapter = new SchedulePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(3);
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
                        mMondayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.white));
                        mMondayIndicator.setVisibility(View.VISIBLE);

                        mTuesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mTuesdayIndicator.setVisibility(View.GONE);

                        mWednesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mWednesdayIndicator.setVisibility(View.GONE);

                        mThursdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mThursdayIndicator.setVisibility(View.GONE);

                        mFridayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mFridayIndicator.setVisibility(View.GONE);
                        break;
                    case 1:
                        mMondayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mMondayIndicator.setVisibility(View.GONE);

                        mTuesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.white));
                        mTuesdayIndicator.setVisibility(View.VISIBLE);

                        mWednesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mWednesdayIndicator.setVisibility(View.GONE);

                        mThursdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mThursdayIndicator.setVisibility(View.GONE);

                        mFridayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mFridayIndicator.setVisibility(View.GONE);
                        break;
                    case 2:
                        mMondayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mMondayIndicator.setVisibility(View.GONE);

                        mTuesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mTuesdayIndicator.setVisibility(View.GONE);

                        mWednesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.white));
                        mWednesdayIndicator.setVisibility(View.VISIBLE);

                        mThursdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mThursdayIndicator.setVisibility(View.GONE);

                        mFridayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mFridayIndicator.setVisibility(View.GONE);
                        break;
                    case 3:
                        mMondayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mMondayIndicator.setVisibility(View.GONE);

                        mTuesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mTuesdayIndicator.setVisibility(View.GONE);

                        mWednesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mWednesdayIndicator.setVisibility(View.GONE);

                        mThursdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.white));
                        mThursdayIndicator.setVisibility(View.VISIBLE);

                        mFridayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mFridayIndicator.setVisibility(View.GONE);
                        break;
                    case 4:
                        mMondayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mMondayIndicator.setVisibility(View.GONE);

                        mTuesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mTuesdayIndicator.setVisibility(View.GONE);

                        mWednesdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mWednesdayIndicator.setVisibility(View.GONE);

                        mThursdayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.grey));
                        mThursdayIndicator.setVisibility(View.GONE);

                        mFridayText.setTextColor(ContextCompat.getColor(ScheduleActivity.this, R.color.white));
                        mFridayIndicator.setVisibility(View.VISIBLE);
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

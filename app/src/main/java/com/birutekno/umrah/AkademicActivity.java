package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.umrah.adapter.AcademicPagerAdapter;
import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 7/31/2017.
 */

public class AkademicActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.materiText)
    TextView mMateriText;

    @Bind(R.id.soalText)
    TextView mSoalText;

    @Bind(R.id.materiTab)
    LinearLayout mMateriTab;

    @Bind(R.id.soalTab)
    LinearLayout mSoalTab;

    @Bind(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.materiTab)
    void materiClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.soalTab)
    void soalClicked(){
        mPager.setCurrentItem(1);
    }

    private AcademicPagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, AkademicActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_academic;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        setupPager();
    }

    private void setupPager() {
        mAdapter = new AcademicPagerAdapter(getSupportFragmentManager());
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
                        mMateriText.setTextColor(ContextCompat.getColor(AkademicActivity.this, R.color.white));
                        mMateriTab.setBackgroundResource(R.drawable.bg_rounded_left_blue);

                        mSoalText.setTextColor(ContextCompat.getColor(AkademicActivity.this, R.color.grey));
                        mSoalTab.setBackgroundResource(R.drawable.bg_rounded_right_white);
                        break;
                    case 1:
                        mMateriText.setTextColor(ContextCompat.getColor(AkademicActivity.this, R.color.grey));
                        mMateriTab.setBackgroundResource(R.drawable.bg_rounded_left_white);

                        mSoalText.setTextColor(ContextCompat.getColor(AkademicActivity.this, R.color.white));
                        mSoalTab.setBackgroundResource(R.drawable.bg_rounded_right_blue);
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

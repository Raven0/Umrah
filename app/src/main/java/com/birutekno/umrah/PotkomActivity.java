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

import com.birutekno.umrah.adapter.PotkomPagerAdapter;
import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PotkomActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.komisiText)
    TextView komisiText;

    @Bind(R.id.komisiTab)
    LinearLayout komisiTab;

    @Bind(R.id.komisiIndicator)
    LinearLayout komisiIndicator;

    @Bind(R.id.potensiText)
    TextView potensiText;

    @Bind(R.id.potensiTab)
    LinearLayout potensiTab;

    @Bind(R.id.potensiIndicator)
    LinearLayout potensiIndicator;

    @Bind(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.komisiTab)
    void komisiClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.potensiTab)
    void potensiClicked(){
        mPager.setCurrentItem(1);
    }

    private PotkomPagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, PotkomActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_komisi;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Komisi");

        setupPager();
    }

    private void setupPager() {
        mAdapter = new PotkomPagerAdapter(getSupportFragmentManager());
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
                        komisiText.setTextColor(ContextCompat.getColor(PotkomActivity.this, R.color.white));
                        komisiIndicator.setVisibility(View.VISIBLE);

                        potensiText.setTextColor(ContextCompat.getColor(PotkomActivity.this, R.color.grey));
                        potensiIndicator.setVisibility(View.GONE);
                        break;
                    case 1:
                        komisiText.setTextColor(ContextCompat.getColor(PotkomActivity.this, R.color.grey));
                        komisiIndicator.setVisibility(View.GONE);

                        potensiText.setTextColor(ContextCompat.getColor(PotkomActivity.this, R.color.white));
                        potensiIndicator.setVisibility(View.VISIBLE);
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

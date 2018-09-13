package com.birutekno.aiwa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.aiwa.adapter.GalleryPagerAdapter;
import com.birutekno.aiwa.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 8/7/2017.
 */

public class GalleryActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.fotoText)
    TextView mBillingText;

    @Bind(R.id.fotoTab)
    LinearLayout mBillingTab;

    @Bind(R.id.fotoIndicator)
    LinearLayout mBillingIndicator;

    @Bind(R.id.videoText)
    TextView mNotPaidText;

    @Bind(R.id.videoTab)
    LinearLayout mNotPaidTab;

    @Bind(R.id.videoIndicator)
    LinearLayout mNotPaidIndicator;

    @Bind(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.fotoTab)
    void fotoClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.videoTab)
    void videoClicked(){
        mPager.setCurrentItem(1);
    }

    private GalleryPagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Gallery");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupPager();
    }

    private void setupPager() {
        mAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
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
                        mBillingText.setTextColor(ContextCompat.getColor(GalleryActivity.this, R.color.white));
                        mBillingIndicator.setVisibility(View.VISIBLE);

                        mNotPaidText.setTextColor(ContextCompat.getColor(GalleryActivity.this, R.color.grey));
                        mNotPaidIndicator.setVisibility(View.GONE);
                        break;
                    case 1:
                        mBillingText.setTextColor(ContextCompat.getColor(GalleryActivity.this, R.color.grey));
                        mBillingIndicator.setVisibility(View.GONE);

                        mNotPaidText.setTextColor(ContextCompat.getColor(GalleryActivity.this, R.color.white));
                        mNotPaidIndicator.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
//            onBackPressed();
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GalleryActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("viewpager_position", 1);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

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

import com.birutekno.umrah.adapter.PaymentPagerAdapter;
import com.birutekno.umrah.adapter.SchedulePagerAdapter;
import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 8/7/2017.
 */

public class PaymentActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.billingText)
    TextView mBillingText;

    @Bind(R.id.billingTab)
    LinearLayout mBillingTab;

    @Bind(R.id.billingIndicator)
    LinearLayout mBillingIndicator;

    @Bind(R.id.notPaidText)
    TextView mNotPaidText;

    @Bind(R.id.notPaidTab)
    LinearLayout mNotPaidTab;

    @Bind(R.id.notPaidIndicator)
    LinearLayout mNotPaidIndicator;

    @Bind(R.id.paidText)
    TextView mPaidText;

    @Bind(R.id.paidTab)
    LinearLayout mPaidTab;

    @Bind(R.id.paidIndicator)
    LinearLayout mPaidIndicator;

    @Bind(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.billingTab)
    void billingClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.notPaidTab)
    void notPaidClicked(){
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.paidTab)
    void paidClicked(){
        mPager.setCurrentItem(2);
    }

    private PaymentPagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, PaymentActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        setupPager();
    }

    private void setupPager() {
        mAdapter = new PaymentPagerAdapter(getSupportFragmentManager());
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
                        mBillingText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.white));
                        mBillingIndicator.setVisibility(View.VISIBLE);

                        mNotPaidText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.grey));
                        mNotPaidIndicator.setVisibility(View.GONE);

                        mPaidText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.grey));
                        mPaidIndicator.setVisibility(View.GONE);
                        break;
                    case 1:
                        mBillingText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.grey));
                        mBillingIndicator.setVisibility(View.GONE);

                        mNotPaidText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.white));
                        mNotPaidIndicator.setVisibility(View.VISIBLE);

                        mPaidText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.grey));
                        mPaidIndicator.setVisibility(View.GONE);
                        break;
                    case 2:
                        mBillingText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.grey));
                        mBillingIndicator.setVisibility(View.GONE);

                        mNotPaidText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.grey));
                        mNotPaidIndicator.setVisibility(View.GONE);

                        mPaidText.setTextColor(ContextCompat.getColor(PaymentActivity.this, R.color.white));
                        mPaidIndicator.setVisibility(View.VISIBLE);
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

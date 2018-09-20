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

import com.birutekno.aiwa.adapter.PotkomKoordPagerAdapter;
import com.birutekno.aiwa.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PotkomKoordActivity extends BaseActivity {

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

    private PotkomKoordPagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, PotkomKoordActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_komisi;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Komisi Agen");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupPager();
    }

    private void setupPager() {
        mAdapter = new PotkomKoordPagerAdapter(getSupportFragmentManager());
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
                        komisiText.setTextColor(ContextCompat.getColor(PotkomKoordActivity.this, R.color.white));
                        komisiIndicator.setVisibility(View.VISIBLE);

                        potensiText.setTextColor(ContextCompat.getColor(PotkomKoordActivity.this, R.color.grey));
                        potensiIndicator.setVisibility(View.GONE);
                        break;
                    case 1:
                        komisiText.setTextColor(ContextCompat.getColor(PotkomKoordActivity.this, R.color.grey));
                        komisiIndicator.setVisibility(View.GONE);

                        potensiText.setTextColor(ContextCompat.getColor(PotkomKoordActivity.this, R.color.white));
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
//        Intent intent = new Intent(PotkomKoordActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("viewpager_position", 3);
//        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

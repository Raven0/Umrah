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

import com.birutekno.aiwa.adapter.PotkomPagerAdapter;
import com.birutekno.aiwa.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PotkomActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.komisiText)
    TextView komisiText;

    @BindView(R.id.komisiTab)
    LinearLayout komisiTab;

    @BindView(R.id.komisiIndicator)
    LinearLayout komisiIndicator;

    @BindView(R.id.potensiText)
    TextView potensiText;

    @BindView(R.id.potensiTab)
    LinearLayout potensiTab;

    @BindView(R.id.potensiIndicator)
    LinearLayout potensiIndicator;

    @BindView(R.id.pager)
    public ViewPager mPager;

    @OnClick(R.id.komisiTab)
    void komisiClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.potensiTab)
    void potensiClicked(){
        mPager.setCurrentItem(1);
    }

    private PotkomPagerAdapter mAdapter;

    int position,pos;

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
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setupPager();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            position = extras.getInt("viewpager_position");
            pos = extras.getInt("pos");
            mPager.setCurrentItem(Integer.parseInt(String.valueOf(position)));
        }else {
        }
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
//        Intent intent = new Intent(PotkomActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("viewpager_position", pos);
//        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

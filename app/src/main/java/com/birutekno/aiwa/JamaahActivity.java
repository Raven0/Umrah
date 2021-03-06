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

import com.birutekno.aiwa.adapter.JamaahPagerAdapter;
import com.birutekno.aiwa.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JamaahActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.allText)
    TextView mAllText;

    @BindView(R.id.allTab)
    LinearLayout mAllTab;

    @BindView(R.id.allIndicator)
    LinearLayout mAllIndicator;

    @BindView(R.id.berangkatText)
    TextView mGoText;

    @BindView(R.id.berangkatTab)
    LinearLayout mGoTab;

    @BindView(R.id.berangkatIndicator)
    LinearLayout mGoIndicator;

    @BindView(R.id.pulangText)
    TextView mBackText;

    @BindView(R.id.pulangTab)
    LinearLayout mBackTab;

    @BindView(R.id.pulangIndicator)
    LinearLayout mBackIndicator;

    @BindView(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.allTab)
    void semuaClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.berangkatTab)
    void berangkatClicked(){
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.pulangTab)
    void pulangClicked(){
        mPager.setCurrentItem(2);
    }

//    private JamaahAdapter mAdapter;
    private JamaahPagerAdapter mAdapter;
    private String mDate = "";

    int pos;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JamaahActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_jamaah;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Jamaah");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupPager();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pos = extras.getInt("pos");
        }
    }

    private void setupPager() {
        mAdapter = new JamaahPagerAdapter(getSupportFragmentManager());
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
                        mAllText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.white));
                        mAllIndicator.setVisibility(View.VISIBLE);

                        mGoText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.grey));
                        mGoIndicator.setVisibility(View.GONE);

                        mBackText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.grey));
                        mBackIndicator.setVisibility(View.GONE);
                        break;
                    case 1:
                        mAllText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.grey));
                        mAllIndicator.setVisibility(View.GONE);

                        mGoText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.white));
                        mGoIndicator.setVisibility(View.VISIBLE);

                        mBackText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.grey));
                        mBackIndicator.setVisibility(View.GONE);
                        break;
                    case 2:
                        mAllText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.grey));
                        mAllIndicator.setVisibility(View.GONE);

                        mGoText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.grey));
                        mGoIndicator.setVisibility(View.GONE);

                        mBackText.setTextColor(ContextCompat.getColor(JamaahActivity.this, R.color.white));
                        mBackIndicator.setVisibility(View.VISIBLE);
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
//        Intent intent = new Intent(JamaahActivity.this, MainActivity.class);
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

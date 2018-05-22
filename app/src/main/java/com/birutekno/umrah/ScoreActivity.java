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
import com.birutekno.umrah.adapter.ScorePagerAdapter;
import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 03/08/2017.
 */

public class ScoreActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.schoolTaskText)
    TextView mSchoolTaskText;

    @Bind(R.id.schoolTaskTab)
    LinearLayout mSchoolTaskTab;

    @Bind(R.id.schoolTaskIndicator)
    LinearLayout mSchoolTaskIndicator;

    @Bind(R.id.schoolTaskImage)
    ImageView mSchoolTaskImage;

    @Bind(R.id.homeTaskText)
    TextView mHomeTaskText;

    @Bind(R.id.homeTaskTab)
    LinearLayout mHomeTaskTab;

    @Bind(R.id.homeTaskIndicator)
    LinearLayout mHomeTaskIndicator;

    @Bind(R.id.homeTaskImage)
    ImageView mHomeTaskImage;

    @Bind(R.id.midTermExamText)
    TextView mMidTermExamText;

    @Bind(R.id.midTermExamTab)
    LinearLayout mMidTermExamTab;

    @Bind(R.id.midTermExamIndicator)
    LinearLayout mMidTermExamIndicator;

    @Bind(R.id.midTermExamImage)
    ImageView mMidTermExamImage;

    @Bind(R.id.endTermExamText)
    TextView mEndTermExamText;

    @Bind(R.id.endTermExamTab)
    LinearLayout mEndTermExamTab;

    @Bind(R.id.endTermExamIndicator)
    LinearLayout mEndTermExamIndicator;

    @Bind(R.id.endTermExamImage)
    ImageView mEndTermExamImage;

    @Bind(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.schoolTaskTab)
    void schoolTaskClicked() {
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.homeTaskTab)
    void homeTaskClicked() {
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.midTermExamTab)
    void midTermExamClicked() {
        mPager.setCurrentItem(2);
    }

    @OnClick(R.id.endTermExamTab)
    void endTermExamClicked() {
        mPager.setCurrentItem(3);
    }


    private ScorePagerAdapter mAdapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ScoreActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_score;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        setupPager();
    }

    private void setupPager() {
        mAdapter = new ScorePagerAdapter(getSupportFragmentManager());
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
                        mSchoolTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.white));
                        mSchoolTaskIndicator.setVisibility(View.VISIBLE);
                        mSchoolTaskImage.setImageResource(R.drawable.tugas_sekolah_putih);

                        mHomeTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mHomeTaskIndicator.setVisibility(View.GONE);
                        mHomeTaskImage.setImageResource(R.drawable.tugas_rumah_biru);

                        mMidTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mMidTermExamIndicator.setVisibility(View.GONE);
                        mMidTermExamImage.setImageResource(R.drawable.uts_biru);

                        mEndTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mEndTermExamIndicator.setVisibility(View.GONE);
                        mEndTermExamImage.setImageResource(R.drawable.uas_biru);
                        break;
                    case 1:
                        mSchoolTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mSchoolTaskIndicator.setVisibility(View.GONE);
                        mSchoolTaskImage.setImageResource(R.drawable.tugas_sekolah_biru);

                        mHomeTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.white));
                        mHomeTaskIndicator.setVisibility(View.VISIBLE);
                        mHomeTaskImage.setImageResource(R.drawable.tugas_rumah_putih);

                        mMidTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mMidTermExamIndicator.setVisibility(View.GONE);
                        mMidTermExamImage.setImageResource(R.drawable.uts_biru);

                        mEndTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mEndTermExamIndicator.setVisibility(View.GONE);
                        mEndTermExamImage.setImageResource(R.drawable.uas_biru);
                        break;
                    case 2:
                        mSchoolTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mSchoolTaskIndicator.setVisibility(View.GONE);
                        mSchoolTaskImage.setImageResource(R.drawable.tugas_sekolah_biru);

                        mHomeTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mHomeTaskIndicator.setVisibility(View.GONE);
                        mHomeTaskImage.setImageResource(R.drawable.tugas_rumah_biru);

                        mMidTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.white));
                        mMidTermExamIndicator.setVisibility(View.VISIBLE);
                        mMidTermExamImage.setImageResource(R.drawable.uts_putih);

                        mEndTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mEndTermExamIndicator.setVisibility(View.GONE);
                        mEndTermExamImage.setImageResource(R.drawable.uas_biru);
                        break;
                    case 3:
                        mSchoolTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mSchoolTaskIndicator.setVisibility(View.GONE);
                        mSchoolTaskImage.setImageResource(R.drawable.tugas_sekolah_biru);

                        mHomeTaskText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mHomeTaskIndicator.setVisibility(View.GONE);
                        mHomeTaskImage.setImageResource(R.drawable.tugas_rumah_biru);

                        mMidTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.grey));
                        mMidTermExamIndicator.setVisibility(View.GONE);
                        mMidTermExamImage.setImageResource(R.drawable.uts_biru);

                        mEndTermExamText.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.white));
                        mEndTermExamIndicator.setVisibility(View.VISIBLE);
                        mEndTermExamImage.setImageResource(R.drawable.uas_putih);
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

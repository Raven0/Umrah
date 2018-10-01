package com.birutekno.aiwa.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.adapter.AcademicPagerAdapter;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by No Name on 7/29/2017.
 */

public class DashboardFragment extends BaseFragment{

    @BindView(R.id.sayaText)
    TextView mMateriText;

    @BindView(R.id.agenText)
    TextView mSoalText;

    @BindView(R.id.sayaTab)
    LinearLayout mMateriTab;

    @BindView(R.id.agenTab)
    LinearLayout mSoalTab;

    @BindView(R.id.pager)
    ViewPager mPager;

    @OnClick(R.id.sayaTab)
    void materiClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.agenTab)
    void soalClicked(){
        mPager.setCurrentItem(1);
    }

    private AcademicPagerAdapter mAdapter;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupPager();
    }

    private void setupPager() {
        mAdapter = new AcademicPagerAdapter(getChildFragmentManager());
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
                        mMateriText.setTextColor(Color.WHITE);
                        mMateriTab.setBackgroundResource(R.drawable.bg);

                        mSoalText.setTextColor(Color.GRAY);
                        mSoalTab.setBackgroundColor(Color.WHITE);
                        break;
                    case 1:
                        mMateriText.setTextColor(Color.GRAY);
                        mMateriTab.setBackgroundColor(Color.WHITE);

                        mSoalText.setTextColor(Color.WHITE);
                        mSoalTab.setBackgroundResource(R.drawable.bg);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}

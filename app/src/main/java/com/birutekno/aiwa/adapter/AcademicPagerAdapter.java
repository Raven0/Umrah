package com.birutekno.aiwa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.aiwa.fragment.DashboardAgenFragment;
import com.birutekno.aiwa.fragment.DashboardUserFragment;

/**
 * Created by No Name on 7/31/2017.
 */

public class AcademicPagerAdapter extends FragmentStatePagerAdapter {

    public AcademicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DashboardUserFragment.newInstance(1);
            case 1:
                return DashboardAgenFragment.newInstance(2);
            default:
                return DashboardUserFragment.newInstance(1);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

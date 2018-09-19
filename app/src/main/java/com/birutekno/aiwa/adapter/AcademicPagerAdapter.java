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

        DashboardUserFragment userFragment = DashboardUserFragment.newInstance(1);
        userFragment.loadOnLoad();

        DashboardAgenFragment agenFragment = DashboardAgenFragment.newInstance(2);
        agenFragment.loadOnLoad();

        switch (position) {
            case 0:
                return userFragment;
            case 1:
                return agenFragment;
            default:
                return userFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

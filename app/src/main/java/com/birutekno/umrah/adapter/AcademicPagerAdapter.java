package com.birutekno.umrah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.DAgenFragment;
import com.birutekno.umrah.fragment.DUserFragment;

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
                return DUserFragment.newInstance(1);
            case 1:
                return DAgenFragment.newInstance(2);
            default:
                return DUserFragment.newInstance(1);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

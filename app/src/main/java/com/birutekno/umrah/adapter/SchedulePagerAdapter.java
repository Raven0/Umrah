package com.birutekno.umrah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.child.ScheduleFragment;
import com.birutekno.umrah.fragment.child.ScoreFragment;

/**
 * Created by No Name on 04/08/2017.
 */

public class SchedulePagerAdapter extends FragmentStatePagerAdapter {

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScheduleFragment.newInstance(1);
            case 1:
                return ScheduleFragment.newInstance(2);
            case 2:
                return ScheduleFragment.newInstance(3);
            case 3:
                return ScheduleFragment.newInstance(4);
            case 4:
                return ScheduleFragment.newInstance(5);
            default:
                return ScheduleFragment.newInstance(1);
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}

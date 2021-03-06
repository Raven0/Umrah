package com.birutekno.aiwa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.aiwa.fragment.DashboardFragment;
import com.birutekno.aiwa.fragment.HomeFragment;
import com.birutekno.aiwa.fragment.NotificationFragment;
import com.birutekno.aiwa.fragment.SettingFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DashboardFragment.newInstance();
            case 1:
                return HomeFragment.newInstance();
            case 2:
                return NotificationFragment.newInstance();
            case 3:
                return SettingFragment.newInstance();
            default:
                return HomeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
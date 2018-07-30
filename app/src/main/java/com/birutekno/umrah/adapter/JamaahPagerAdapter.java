package com.birutekno.umrah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.JBerangkatFragment;
import com.birutekno.umrah.fragment.JPulangFragment;
import com.birutekno.umrah.fragment.JSemuaFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class JamaahPagerAdapter extends FragmentStatePagerAdapter {

    public JamaahPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return JSemuaFragment.newInstance();
            case 1:
                return JBerangkatFragment.newInstance();
            case 2:
                return JPulangFragment.newInstance();
            default:
                return JSemuaFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
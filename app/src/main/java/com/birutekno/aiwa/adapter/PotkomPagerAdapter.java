package com.birutekno.aiwa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.aiwa.fragment.KomisiFragment;
import com.birutekno.aiwa.fragment.PotensiFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class PotkomPagerAdapter extends FragmentStatePagerAdapter {

    public PotkomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return KomisiFragment.newInstance();
            case 1:
                return PotensiFragment.newInstance();
            default:
                return KomisiFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
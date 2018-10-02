package com.birutekno.aiwa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.aiwa.fragment.KomisiKoordFragment;
import com.birutekno.aiwa.fragment.PotensiKoordFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class PotkomKoordPagerAdapter extends FragmentStatePagerAdapter {

    public PotkomKoordPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return KomisiKoordFragment.newInstance();
            case 1:
                return PotensiKoordFragment.newInstance();
            default:
                return KomisiKoordFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
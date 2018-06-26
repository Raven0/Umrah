package com.birutekno.umrah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.FasilitasFragment;
import com.birutekno.umrah.fragment.InfoFragment;
import com.birutekno.umrah.fragment.LokasiFragment;
import com.birutekno.umrah.fragment.VideoFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class HotelPagerAdapter extends FragmentStatePagerAdapter {

    public HotelPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.newInstance();
            case 1:
                return FasilitasFragment.newInstance();
            case 2:
                return LokasiFragment.newInstance();
            case 3:
                return VideoFragment.newInstance();
            default:
                return InfoFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
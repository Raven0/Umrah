package com.birutekno.aiwa.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.aiwa.fragment.FotoFragment;
import com.birutekno.aiwa.fragment.VideoFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    public GalleryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FotoFragment.newInstance();
            case 1:
                return VideoFragment.newInstance();
            default:
                return FotoFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
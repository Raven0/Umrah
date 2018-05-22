package com.birutekno.umrah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.child.MaterialFragment;
import com.birutekno.umrah.fragment.child.ScoreFragment;

/**
 * Created by No Name on 04/08/2017.
 */

public class ScorePagerAdapter extends FragmentStatePagerAdapter {

    public ScorePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScoreFragment.newInstance(1);
            case 1:
                return ScoreFragment.newInstance(2);
            case 2:
                return ScoreFragment.newInstance(3);
            case 3:
                return ScoreFragment.newInstance(4);
            default:
                return ScoreFragment.newInstance(1);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

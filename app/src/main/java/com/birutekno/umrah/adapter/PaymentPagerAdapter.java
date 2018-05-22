package com.birutekno.umrah.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.child.PaymentFragment;
import com.birutekno.umrah.fragment.child.ScheduleFragment;

/**
 * Created by No Name on 8/7/2017.
 */

public class PaymentPagerAdapter extends FragmentStatePagerAdapter {

    public PaymentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PaymentFragment.newInstance(1);
            case 1:
                return PaymentFragment.newInstance(2);
            case 2:
                return PaymentFragment.newInstance(3);
            default:
                return PaymentFragment.newInstance(1);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

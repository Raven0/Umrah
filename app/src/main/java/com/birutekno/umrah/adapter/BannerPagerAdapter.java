package com.birutekno.umrah.adapter;

import android.support.v4.app.FragmentManager;

import com.birutekno.umrah.fragment.child.BannerFragment;
import com.birutekno.umrah.model.Banner;
import com.birutekno.umrah.ui.adapter.BasePagerAdapter;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by No Name on 8/15/2017.
 */

public class BannerPagerAdapter extends BasePagerAdapter {

    private List<BannerFragment> mFragments;
    private List<Banner> imageData;

    public BannerPagerAdapter(FragmentManager fm, List<Banner> list) {
        super(fm, list);
        imageData = new ArrayList<>();
    }

    public void setData(List<Banner> data){
        this.imageData = data;
        notifyDataSetChanged();
    }

    public List<Banner> getImageData(){
        return imageData;
    }

    public List<BannerFragment> getFragment(){
        return mFragments;
    }

    @Override
    public float getPageWidth(int position) {
        return 1;
    }

    public BannerFragment getFragment(int position){
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            if(imageData.size() > 0){
                for(int i = 0; i < imageData.size(); i++){
                    mFragments.add((BannerFragment) BannerFragment.newInstance(imageData.get(i)));
                }
            }
        }

        return mFragments.get(position);
    }

    @Override
    public BaseFragment getItem(int position) {
        return getFragment(position);
    }

    @Override
    public int getCount() {
        return imageData.size();
    }
}

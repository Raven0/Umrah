package com.birutekno.umrah.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Banner;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import butterknife.Bind;

/**
 * Created by Suitmedia on 9/21/2016.
 */

public class BannerFragment extends BaseFragment {

    @Bind(R.id.image)
    ImageView mImage;

    private Banner mBanner = new Banner();

    public static Fragment newInstance(Banner Banner) {
        BannerFragment fragment = new BannerFragment();
        fragment.mBanner = Banner;
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_banner;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        if (mBanner != null) {
            mImage.setImageResource(mBanner.getImage());
            Log.e("BANNER", "not" + mBanner.getImage());
        }else{
            Log.e("BANNER", "null");
        }
    }
}

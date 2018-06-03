package com.birutekno.umrah.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * Created by No Name on 7/29/2017.
 */

public class VideoFragment extends BaseFragment{

    @OnClick(R.id.satu)
    void satuClicked() {
        Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show();
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {

    }
}

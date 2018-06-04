package com.birutekno.umrah.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.birutekno.umrah.ProfileActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * Created by No Name on 7/29/2017.
 */

public class SettingFragment extends BaseFragment{

    @OnClick(R.id.profilCard)
    void profilCardClicked() {
        Intent intent = ProfileActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {

    }
}

package com.birutekno.umrah.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.birutekno.umrah.FaqActivity;
import com.birutekno.umrah.LoginActivity;
import com.birutekno.umrah.PotkomActivity;
import com.birutekno.umrah.PotkomKoordActivity;
import com.birutekno.umrah.ProfileActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.SubAgenActivity;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * Created by No Name on 7/29/2017.
 */

public class SettingFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @OnClick(R.id.profilCard)
    void profilCardClicked() {
        Intent intent = ProfileActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.agenCard)
    void agenCardClicked() {
        Intent intent = SubAgenActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.komisiCard)
    void komisiCardClicked() {
        Intent intent = PotkomActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.komisiKoordCard)
    void komisiKoordCardClicked() {
        Intent intent = PotkomKoordActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.faqCard)
    void faqCardClicked() {
        Intent intent = FaqActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.btnLogout)
    void btnLogoutClicked() {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE).edit();
        editor.putString("iduser", "0");
        editor.putString("token", "null");
        editor.putString("status", "out");
        editor.putString("password", "null");
        editor.apply();

        Intent intent = LoginActivity.createIntent(getActivity());
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

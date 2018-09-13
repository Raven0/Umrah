package com.birutekno.aiwa.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.view.View;

import com.birutekno.aiwa.FaqActivity;
import com.birutekno.aiwa.LoginActivity;
import com.birutekno.aiwa.PotkomActivity;
import com.birutekno.aiwa.PotkomKoordActivity;
import com.birutekno.aiwa.ProfileActivity;
import com.birutekno.aiwa.R;
import com.birutekno.aiwa.SubAgenActivity;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by No Name on 7/29/2017.
 */

public class SettingFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @Bind(R.id.komisiKoordCard)
    CardView komisiKoord;

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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("pos", 3);
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
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String idmarketing = prefs.getString("iduser", "0");

        if (idmarketing.equals("SM140")){
            komisiKoord.setVisibility(View.VISIBLE);
        }else if(idmarketing.equals("BR001")){
            komisiKoord.setVisibility(View.VISIBLE);
        }else {
            komisiKoord.setVisibility(View.GONE);
        }
    }
}

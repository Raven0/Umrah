package com.birutekno.aiwa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birutekno.aiwa.LoginActivity;
import com.birutekno.aiwa.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class KalkulasiFragment extends Fragment implements BlockingStep {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.intro3, container, false);

        //initialize your UI

        return v;
    }

    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {

    }
    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //you can do anythings you want
                Intent intent = LoginActivity.createIntent(getActivity());
                ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
                ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
            }
        }, 0L);// delay open another fragment,
    }
    @Override
    public void onBackClicked(final StepperLayout.OnBackClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //you can do anythings you want
                callback.goToPrevStep();
            }
        }, 0L);// delay open another fragment,
    }
    @Override
    public VerificationError verifyStep() {
        return null;
    }
    @Override
    public void onSelected() {
    }
    @Override
    public void onError(@NonNull VerificationError error) {
    }

}
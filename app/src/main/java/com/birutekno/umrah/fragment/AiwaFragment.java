package com.birutekno.umrah.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class AiwaFragment extends Fragment implements BlockingStep {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.intro1, container, false);

        //initialize your UI

        return v;
    }
    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //you can do anythings you want
                callback.goToNextStep();
            }
        }, 0L);// delay open another fragment,
    }
    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
    }
    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
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
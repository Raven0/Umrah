package com.birutekno.umrah.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.birutekno.umrah.fragment.Steps1Fragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }

    @Override
    public Step createStep(int position) {
        final Steps1Fragment step = new Steps1Fragment();
        Bundle b = new Bundle();
        b.putInt("TEST", position);
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        return new StepViewModel.Builder(context).setTitle("Title") //can be a CharSequence instead
                .create();
    }
}

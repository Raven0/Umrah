package com.birutekno.umrah;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.birutekno.umrah.adapter.StepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class WizardActivity extends AppCompatActivity implements StepperLayout.StepperListener{
    private StepperLayout mStepperLayout;
    private StepperAdapter mStepperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_wizard);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperAdapter = new StepperAdapter(getSupportFragmentManager(), this);
        mStepperLayout.setAdapter(mStepperAdapter);
        mStepperLayout.setListener(this);
    }
    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStepSelected(int newStepPosition) {
    }
    @Override
    public void onReturn() {
        finish();
    }
}
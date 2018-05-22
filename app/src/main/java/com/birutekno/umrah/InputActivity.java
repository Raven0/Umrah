package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.birutekno.umrah.fragment.Step1Fragment;

public class InputActivity extends AppCompatActivity {

    public static int width = 0;
    public static int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        loadFragment();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, InputActivity.class);
        return intent;
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, new Step1Fragment())
                .commit();
    }

    public static void goToStepOrangTua() {
        position = 2;
    }

    public static void goToStepUlasan() {
        position = 3;
    }

    @Override
    public void onBackPressed() {
        position--;
        super.onBackPressed();
    }
}

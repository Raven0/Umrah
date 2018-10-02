package com.birutekno.aiwa;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.birutekno.aiwa.fragment.Step1Fragment;

public class InputActivity extends AppCompatActivity {

    public static int width = 0;
    public static int position = 0;
    public Toolbar mToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                InputActivity.super.onBackPressed();
            }
        });

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

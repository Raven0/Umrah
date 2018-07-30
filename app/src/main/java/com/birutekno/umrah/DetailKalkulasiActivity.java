package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.birutekno.umrah.fragment.FormKalkulasiFragment;

public class DetailKalkulasiActivity extends AppCompatActivity {

    public static int width = 0;
    public static int position = 0;
    public Toolbar mToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kalkulasi);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                DetailKalkulasiActivity.super.onBackPressed();
            }
        });

        loadFragment();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, DetailKalkulasiActivity.class);
        return intent;
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, new FormKalkulasiFragment())
                .commit();
    }

    public static void goToStepTotal() {
        position = 2;
    }

    @Override
    public void onBackPressed() {
        position--;
        super.onBackPressed();
    }
}

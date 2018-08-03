package com.birutekno.umrah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.birutekno.umrah.fragment.FormKalkulasiFragment;

public class InputKalkulasiActivity extends AppCompatActivity {

    public static int width = 0;
    public static int position = 0;
    public Toolbar mToolbar;
    public Boolean isYes = false;
    FormKalkulasiFragment formKalkulasiFragment = new FormKalkulasiFragment();

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
                InputKalkulasiActivity.super.onBackPressed();
            }
        });

        loadFragment();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, InputKalkulasiActivity.class);
        return intent;
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, formKalkulasiFragment)
                .commit();
    }

    public static void goToStepTotal() {
        position = 1;
    }

    @Override
    public void onBackPressed() {
//        position--;
        final String pos = String.valueOf(position);
        if (String.valueOf(position).equals("0")){
            AlertDialog.Builder adb = new AlertDialog.Builder(InputKalkulasiActivity.this);
            adb.setMessage("Apakah data ini akan disimpan?");
            adb.setTitle("Data Prospek");
            adb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    isYes = true;
                    View view = formKalkulasiFragment.buttonSimpan;
                    formKalkulasiFragment.onClick(view);
                }
            });
            adb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    position--;
                    InputKalkulasiActivity.super.onBackPressed();
                }
            });
            adb.show();
        }else {
            position--;
            super.onBackPressed();
        }
    }
}

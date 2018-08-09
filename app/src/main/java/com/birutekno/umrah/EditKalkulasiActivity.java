package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.birutekno.umrah.fragment.EditKalkulasiFragment;

public class EditKalkulasiActivity extends AppCompatActivity {

    public static int width = 0;
    public static int position = 0;
    public Toolbar mToolbar;
    String idProspek;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kalkulasi);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Kalkulasi");
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                EditKalkulasiActivity.super.onBackPressed();
            }
        });

        idProspek = getIntent().getStringExtra("id");
        loadFragment(idProspek);
//        Toast.makeText(this, idProspek, Toast.LENGTH_SHORT).show();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditKalkulasiActivity.class);
        return intent;
    }

    private void loadFragment(String args) {
        EditKalkulasiFragment fragment = new EditKalkulasiFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",args);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, fragment)
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

package com.birutekno.aiwa;

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

import com.birutekno.aiwa.fragment.EditKalkulasiFragment;

public class EditKalkulasiActivity extends AppCompatActivity {

    public static int width = 0;
    public static int position = 0;
    public Toolbar mToolbar;
    public Boolean isYes = false;
    EditKalkulasiFragment editKalkulasiFragment = new EditKalkulasiFragment();
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
                onBackPressed();
            }
        });

        idProspek = getIntent().getStringExtra("id");
        loadFragment(idProspek);
        position = 0;
//        Toast.makeText(this, idProspek, Toast.LENGTH_SHORT).show();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditKalkulasiActivity.class);
        return intent;
    }

    private void loadFragment(String args) {
        Bundle bundle = new Bundle();
        bundle.putString("id",args);
        editKalkulasiFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, editKalkulasiFragment)
                .commit();
    }

    public static void goToStepTotal() {
        position = 1;
    }

    @Override
    public void onBackPressed() {
//        position--;
//        super.onBackPressed();
        final Intent intent = new Intent(EditKalkulasiActivity.this, KalkulasiActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (String.valueOf(position).equals("0")){
            AlertDialog.Builder adb = new AlertDialog.Builder(EditKalkulasiActivity.this);
            adb.setMessage("Apakah data ini akan disimpan?");
            adb.setTitle("Data Prospek");
            adb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    isYes = true;
                    editKalkulasiFragment.saveData("Yes");
                }
            });
            adb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    position--;
                    startActivity(intent);
                }
            });
            adb.show();
        }else {
            position--;
            super.onBackPressed();
        }
    }
}

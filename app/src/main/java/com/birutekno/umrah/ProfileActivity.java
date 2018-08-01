package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.AgenObject;
import com.birutekno.umrah.model.DataAgen;
import com.birutekno.umrah.ui.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class ProfileActivity extends BaseActivity {

    public static final String PREFS_NAME = "AUTH";
    private ProgressDialog pDialog;

    public Toolbar mToolbar;
    public Button btnEdt;
    TextView ktp;
    TextView nama;
    TextView notelp;
    TextView email;
    TextView domisili;
    TextView jk;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btnEdt = (Button) findViewById(R.id.btnEdit);
        ktp = (TextView) findViewById(R.id.ktpAgen);
        nama = (TextView) findViewById(R.id.namaAgen);
        notelp = (TextView) findViewById(R.id.telpAgen);
        email = (TextView) findViewById(R.id.emailAgen);
        domisili = (TextView) findViewById(R.id.domisiliAgen);
        jk = (TextView) findViewById(R.id.jkAgen);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                ProfileActivity.super.onBackPressed();
            }
        });

//        btnEdt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int id = prefs.getInt("iduser", 0);
        loadData(String.valueOf(id));
    }

    private void loadData(final String id){

        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<AgenObject> call = WebApi.getAPIService().showAgen(id);
        call.enqueue(new Callback<AgenObject>() {
            @Override
            public void onResponse(Call<AgenObject> call, Response<AgenObject> response) {
                pDialog.dismiss();
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    DataAgen dataAgen = response.body().getData();
                    ktp.setText(dataAgen.getNo_ktp());
                    nama.setText(dataAgen.getNama());
                    notelp.setText(dataAgen.getNo_telp());
                    email.setText(dataAgen.getEmail());
                    domisili.setText(dataAgen.getAlamat());
                    jk.setText(dataAgen.getJenis_kelamin());

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<AgenObject> call, Throwable t) {
                loadData(id);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

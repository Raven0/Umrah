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
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.AgenObject;
import com.birutekno.umrah.model.DataAgen;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class EditProfileActivity extends BaseActivity implements View.OnClickListener{

    public static final String PREFS_NAME = "AUTH";
    private ProgressDialog pDialog;

    public Toolbar mToolbar;
    public Button btnEdt;
    EditText ktp;
    EditText nama;
    EditText notelp;
    String email;
    String username;
    String status;
    String koordinator;
    EditText domisili;
    EditText jk;
    EditText bank;
    EditText norek;
    EditText narek;
    EditText feereg;
    EditText feeprom;
    EditText web;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        btnEdt = (Button) findViewById(R.id.btnEdit);
        btnEdt.setOnClickListener(this);
        ktp = (EditText) findViewById(R.id.noktp);
        nama = (EditText) findViewById(R.id.nama);
        notelp = (EditText) findViewById(R.id.telp);
//        email = (EditText) findViewById(R.id.email);
        domisili = (EditText) findViewById(R.id.alamat);
        jk = (EditText) findViewById(R.id.jk);
        bank = (EditText) findViewById(R.id.bank);
        norek = (EditText) findViewById(R.id.norek);
        narek = (EditText) findViewById(R.id.narek);
        feereg = (EditText) findViewById(R.id.feereg);
        feeprom = (EditText) findViewById(R.id.feepromo);
        web = (EditText) findViewById(R.id.website);

        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                EditProfileActivity.super.onBackPressed();
            }
        });

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int PERMISSION_ALL = 1;
//                String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};
//
//                if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
//                    ActivityCompat.requestPermissions(EditProfileActivity.this, PERMISSIONS, PERMISSION_ALL);
//                }
//
//                else {
//                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                    StrictMode.setVmPolicy(builder.build());
//                    Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(Environment.getExternalStorageDirectory(), "IMAGE " + new Date().getTime() + ".jpg");
//                    chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                    if (chooserIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(chooserIntent, 1);
//                    }
////                startActivityForResult(chooserIntent, CAM_REQ_CODE);
//                }
//            }
//        });
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_edit_profile;
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
                    email = dataAgen.getEmail();
                    username = dataAgen.getUsername();
                    status = dataAgen.getStatus();
                    koordinator = dataAgen.getKoordinator();
                    domisili.setText(dataAgen.getAlamat());
                    jk.setText(dataAgen.getJenis_kelamin());
                    bank.setText(dataAgen.getBank());
                    norek.setText(dataAgen.getNo_rekening());
                    narek.setText(dataAgen.getNama_rek_beda());
                    feereg.setText(dataAgen.getFee_reguler());
                    feeprom.setText(dataAgen.getFee_promo());
                    web.setText(dataAgen.getWebsite());

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
    public void onClick(View view) {
        if (view == btnEdt){
            SharedPreferences prefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            int id_agen = prefs.getInt("iduser", 0);
            String password = prefs.getString("password", "123");

            HashMap<String, String> params = new HashMap<>();
            //anggota_id
            params.put("nama", nama.getText().toString());
            params.put("email", email);
            params.put("username", username);
            params.put("password", password);
            params.put("jenis_kelamin", jk.getText().toString());
            params.put("no_ktp", ktp.getText().toString());
            params.put("alamat", domisili.getText().toString());
            params.put("no_telp", notelp.getText().toString());
            params.put("bank", bank.getText().toString());
            params.put("status", status);
            params.put("koordinator", koordinator);
            params.put("no_rekening", norek.getText().toString());
            params.put("fee_reguler", feereg.getText().toString());
            params.put("fee_promo", feeprom.getText().toString());
            params.put("nama_rek_beda", narek.getText().toString());
            params.put("website", web.getText().toString());

            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Harap tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();

            Call<ResponseBody> result = WebApi.getAPIService().editAgen(String.valueOf(id_agen), params);
            result.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pDialog.dismiss();
                    try {
                        if(response.body()!=null){
                            Intent intent = new Intent(mContext, ProfileActivity.class);
                            startActivity(intent);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(mContext, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(mContext, "Error Response", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.dismiss();
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, "On Failure", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

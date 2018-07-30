package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.HashMap;

import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class RegistrationActivity extends BaseActivity {

    private String no_telp;
    private String username;
    private String koordinator;
    private String status;
    private String email;
    private String jenis_kelamin;
    private String nama;
    private String alamat;
    private String no_ktp;
    private String password;

    private ProgressDialog pDialog;

    EditText notelp;
    EditText usrnm;
    EditText koord;
    EditText emil;
    EditText jk;
    EditText nma;
    EditText almt;
    EditText nokt;
    EditText pasw;

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_registration;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        notelp = (EditText) findViewById(R.id.telp);
        usrnm = (EditText) findViewById(R.id.username);
        koord = (EditText) findViewById(R.id.koordinator);
        emil = (EditText) findViewById(R.id.email);
        jk = (EditText) findViewById(R.id.jk);
        nma = (EditText) findViewById(R.id.nama);
        almt = (EditText) findViewById(R.id.alamat);
        nokt = (EditText) findViewById(R.id.noktp);
        pasw = (EditText) findViewById(R.id.password);
    }

    @OnClick(R.id.kirim)
    void kirimClicked() {

        no_telp = notelp.getText().toString().trim();
        username = usrnm.getText().toString().trim();
        koordinator = koord.getText().toString().trim();
        status = "1";
        email = emil.getText().toString().trim();
        jenis_kelamin = jk.getText().toString().trim();
        nama = nma.getText().toString().trim();
        alamat = almt.getText().toString().trim();
        no_ktp = nokt.getText().toString().trim();
        password = pasw.getText().toString().trim();

        HashMap<String, String> params = new HashMap<>();
        params.put("no_telp", no_telp);
        params.put("username", username);
        params.put("koordinator", koordinator);
        params.put("status", status);
        params.put("email", email);
        params.put("jenis_kelamin", jenis_kelamin);
        params.put("nama", nama);
        params.put("alamat", alamat);
        params.put("no_ktp", no_ktp);
        params.put("password", password);

        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<ResponseBody> result = WebApi.getAPIService().insertAgen(params);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismiss();
                try {
                    if(response.body()!=null){
                        Toast.makeText(mContext, "Registrasi berhasil, Tunggu Approval", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.here)
    void hereClicked() {
        Intent intentReward = LoginActivity.createIntent(this);
        ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(this, intentReward, optionsReward.toBundle());
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, RegistrationActivity.class);
        return intent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

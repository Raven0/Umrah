package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.birutekno.umrah.helper.AgenResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataAgen;
import com.birutekno.umrah.ui.BaseActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    private List<DataAgen> alldata;
    List<String> idAgen = new ArrayList<String>();
    List<String> namaAgen = new ArrayList<String>();

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

    private Boolean jk;

    private ProgressDialog pDialog;
    private ProgressDialog loading;

    EditText notelp;
    EditText usrnm;
    SearchableSpinner koord;
    EditText emil;
    EditText nma;
    EditText almt;
    EditText nokt;
    EditText pasw;
    RadioButton rb1, rb2;

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_registration;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        notelp = (EditText) findViewById(R.id.telp);
        usrnm = (EditText) findViewById(R.id.username);
        koord = (SearchableSpinner) findViewById(R.id.koordinator);
        emil = (EditText) findViewById(R.id.email);
        nma = (EditText) findViewById(R.id.nama);
        almt = (EditText) findViewById(R.id.alamat);
        nokt = (EditText) findViewById(R.id.noktp);
        pasw = (EditText) findViewById(R.id.password);
        rb1 = (RadioButton) findViewById(R.id.pria);
        rb2 = (RadioButton) findViewById(R.id.wanita);

        initSpinnerAgen();
        koord.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                koordinator = idAgen.get(position);
                Toast.makeText(mContext, koordinator, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick(R.id.kirim)
    void kirimClicked() {

        no_telp = notelp.getText().toString().trim();
        username = usrnm.getText().toString().trim();
        status = "0";
        email = emil.getText().toString().trim();
        if(rb1.isChecked()){
            jenis_kelamin = "L";
            jk = true;
        }else if(rb2.isChecked()){
            jenis_kelamin = "P";
            jk = true;
        }else {
            jk = false;
        }
        nama = nma.getText().toString().trim();
        alamat = almt.getText().toString().trim();
        no_ktp = nokt.getText().toString().trim();
        password = pasw.getText().toString().trim();

        if (jk){
            HashMap<String, String> params = new HashMap<>();
            params.put("nama", nama);
            params.put("email", email);
            params.put("username", username);
            params.put("password", password);
            params.put("jenis_kelamin", jenis_kelamin);
            params.put("no_ktp", no_ktp);
            params.put("alamat", alamat);
            params.put("no_telp", no_telp);
            params.put("status", status);
            if (TextUtils.isEmpty(koordinator)){
                params.put("koordinator", "0");
            }else {
                params.put("koordinator", koordinator);
            }

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
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(mContext, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show();
        }
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

    public void initSpinnerAgen(){
        loading = ProgressDialog.show(mContext, null, "Harap tunggu...", true, false);

        Call<AgenResponse> call = WebApi.getAPIService().getAgenApproved();
        call.enqueue(new Callback<AgenResponse>() {
            @Override
            public void onResponse(Call<AgenResponse> call, Response<AgenResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    alldata = Arrays.asList(response.body().getAgen());
                    for (int i = 0; i < alldata.size(); i++){
                        int id = Integer.parseInt(alldata.get(i).getId());
                        String nama = alldata.get(i).getNama();

                        idAgen.add(String.valueOf(id));
                        namaAgen.add(nama);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, namaAgen);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    koord.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data Koordinator", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AgenResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Server bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

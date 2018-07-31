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
import com.birutekno.umrah.model.DataAgen;
import com.birutekno.umrah.ui.BaseActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

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
    private ProgressDialog loading;

    EditText notelp;
    EditText usrnm;
    SearchableSpinner koord;
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
        koord = (SearchableSpinner) findViewById(R.id.koordinator);
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
//        koordinator = koord.getText().toString().trim();
        status = "0";
        email = emil.getText().toString().trim();
        jenis_kelamin = jk.getText().toString().trim();
        nama = nma.getText().toString().trim();
        alamat = almt.getText().toString().trim();
        no_ktp = nokt.getText().toString().trim();
        password = pasw.getText().toString().trim();

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
        params.put("koordinator", "0");

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

//    public void initSpinnerAgen(){
//        loading = ProgressDialog.show(mContext, null, "Harap tunggu...", true, false);
//
//        Call<AgenResponse> call = WebApi.getAPIService().getAgen();
//        call.enqueue(new Callback<AgenResponse>() {
//            @Override
//            public void onResponse(Call<AgenResponse> call, Response<AgenResponse> response) {
//                if (response.isSuccessful()) {
//                    loading.dismiss();
//                    alldata = Arrays.asList(response.body().getAgen();
//                    for (int i = 0; i < alldata.size(); i++){
//                        List<Jadwal> jadwal = Arrays.asList(alldata.get(i).get);
//                        listJadwal.add(convertDate(jadwal.get(0).getTgl_berangkat()) + "\nRute : " + jadwal.get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + "\nPesawat : " + jadwal.get(0).getPesawat_berangkat());
//                        ketJadwal.add("Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
////                        ketJadwal.add(jadwal.get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + " Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
//                    }
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
//                            android.R.layout.simple_spinner_item, listJadwal);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    jadwal.setAdapter(adapter);
//
//                } else {
//                    loading.dismiss();
//                    Toast.makeText(getContext(), "Gagal mengambil data jadwal", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AgenResponse> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(getContext(), "Server Jadwal bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

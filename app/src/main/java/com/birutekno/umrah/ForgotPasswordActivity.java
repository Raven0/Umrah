package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class ForgotPasswordActivity extends BaseActivity {

    String emailForgot;
    private ProgressDialog pDialog;

    @Bind(R.id.email)
    EditText email;

    @OnClick(R.id.kirim)
    void forgotClicked() {
        emailForgot = email.getText().toString().trim();
        if (TextUtils.isEmpty(emailForgot)){
            Toast.makeText(mContext, "Mohon masukkan email", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> params = new HashMap<>();
            params.put("email", emailForgot);

            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Harap tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();

            Call<ResponseBody> result = WebApi.getAPIService().passwordAgen(params);
            result.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    pDialog.dismiss();
                    try {
                        if (response.isSuccessful()){
                            Toast.makeText(mContext, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intentReward = LoginActivity.createIntent(mContext);
                            ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
                            ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
                        }
//                        if(response.body()!=null) {
//                            Response success = response.body().getResponses();
//                            String status = success.getStatus();
//                            if (status.equals("success")) {
//                                Toast.makeText(mContext, "Berhasil", Toast.LENGTH_SHORT).show();
//                                Intent intentReward = LoginActivity.createIntent(mContext);
//                                ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
//                                ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
//                            }else if(status.equals("Anda belum terverifikasi oleh admin!")){
//                                Toast.makeText(mContext, "Akun Anda belum di Approve", Toast.LENGTH_SHORT).show();
//                            }else if(status.equals("failed")){
//                                Toast.makeText(mContext, "Username atau Password salah", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("CATCH", "onResponse: " + e.getMessage());
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.dismiss();
                    t.printStackTrace();
                    Log.d("ERROR MSG", "onFailure: " + t.getMessage());
                    if (t.getMessage().equals("timeout")){
                        Toast.makeText(mContext, "Database timeout, silahkan coba lagi!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @OnClick(R.id.here)
    void hereClicked() {
        finish();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

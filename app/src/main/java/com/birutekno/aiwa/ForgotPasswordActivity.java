package com.birutekno.aiwa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.AuthModel;
import com.birutekno.aiwa.model.Response;
import com.birutekno.aiwa.ui.BaseActivity;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;
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

            Call<AuthModel> result = WebApi.getAPIService().passwordAgen(params);
            result.enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, retrofit2.Response<AuthModel> response) {
                    pDialog.dismiss();
                    try {
                        if(response.body()!=null) {
                            Response success = response.body().getResponses();
                            String status = success.getStatus();
                            if (status.equals("success")) {
                                Toast.makeText(mContext, "Silahkan cek email anda!", Toast.LENGTH_SHORT).show();
                                Intent intentReward = LoginActivity.createIntent(mContext);
                                ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
                                ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
                            }else if(status.equals("email")){
                                Toast.makeText(mContext, "Email tidak terdaftar", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mContext, "Server AIWA sedang dalam pemeliharaan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(mContext, "Server AIWA sedang dalam pemeliharaan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {
                    pDialog.dismiss();
                    Toast.makeText(mContext, "Server AIWA sedang dalam pemeliharaan", Toast.LENGTH_SHORT).show();
                    Intent intentReward = LoginActivity.createIntent(mContext);
                    ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
                    ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
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

package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.AuthModel;
import com.birutekno.umrah.model.Response;
import com.birutekno.umrah.model.User;
import com.birutekno.umrah.ui.BaseActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 7/28/2017.
 */

public class LoginActivity extends BaseActivity {

    String user;
    String pass;

    EditText username;
    EditText password;

    public static final String PREFS_NAME = "AUTH";

    private ProgressDialog pDialog;
    private boolean doubleBackToExitPressedOnce;

    FirebaseInstanceIdService firebaseInstanceId = new FirebaseInstanceIdService();

    @OnClick(R.id.login)
    void loginClicked() {
        user = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Toast.makeText(mContext, "Mohon masukkan username dan password", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> params = new HashMap<>();
            params.put("username", user);
            params.put("password", pass);

            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Harap tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();

            Call<AuthModel> result = WebApi.getAPIService().loginAgen(params);
            result.enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, retrofit2.Response<AuthModel> response) {
                    pDialog.dismiss();
                    try {
                        if(response.body()!=null) {
                            Response success = response.body().getResponses();
                            String status = success.getStatus();
                            if (status.equals("success")) {
                                User user = response.body().getUser();
                                int id = Integer.parseInt(user.getId());
                                String token = success.getToken();
                                Toast.makeText(mContext, "Login Berhasil", Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putInt("iduser", id);
                                editor.putString("token", token);
                                editor.putString("password", pass);
                                editor.apply();

                                String tokenDevice = FirebaseInstanceId.getInstance().getToken();

                                Log.d("DEVICE TOKEN", "onResponse: " + tokenDevice);
                                Intent intentReward = MainActivity.createIntent(mContext);
                                ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
                                ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
                            }else if(status.equals("Anda belum terverifikasi oleh admin!")){
                                Toast.makeText(mContext, "Akun Anda belum di Approve", Toast.LENGTH_SHORT).show();
                            }else if(status.equals("failed")){
                                Toast.makeText(mContext, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {
                    pDialog.dismiss();
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }

    @OnClick(R.id.here)
    void hereClicked() {
        Intent intentReward = ForgotPasswordActivity.createIntent(this);
        ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(this, intentReward, optionsReward.toBundle());
    }

    @OnClick(R.id.create)
    void createClicked() {
        Intent intentReward = RegistrationActivity.createIntent(this);
        ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(this, intentReward, optionsReward.toBundle());
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

package com.birutekno.aiwa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.AuthModel;
import com.birutekno.aiwa.model.Response;
import com.birutekno.aiwa.model.User;
import com.birutekno.aiwa.ui.BaseActivity;
import com.google.firebase.iid.FirebaseInstanceId;

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
    CheckBox show;

    boolean isChecked;

    public static final String PREFS_NAME = "AUTH";

    private ProgressDialog pDialog;
    private boolean doubleBackToExitPressedOnce;

    @OnClick(R.id.login)
    void loginClicked() {
        String tokenDevice = FirebaseInstanceId.getInstance().getToken();
        Log.d("DEVICE TOKEN", "onResponse: " + tokenDevice);

        user = username.getText().toString().trim();
        pass = password.getText().toString().trim();
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
            Toast.makeText(mContext, "Mohon masukkan username dan password", Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String, String> params = new HashMap<>();
            params.put("username", user);
            params.put("password", pass);
            params.put("device_token", tokenDevice);

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
                                String id = user.getId();
                                String token = success.getToken();
                                Toast.makeText(mContext, "Login Berhasil", Toast.LENGTH_LONG).show();

                                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("iduser", id);
                                editor.putString("token", token);
                                editor.putString("status", "in");
                                editor.putString("password", pass);
                                editor.apply();

                                Intent intentReward = MainActivity.createIntent(mContext);
                                ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
                                ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
                            }else if(status.equals("unverified")){
                                Toast.makeText(mContext, "Akun Anda belum di Approve", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mContext, "Username atau Password salah", Toast.LENGTH_SHORT).show();
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
                    t.printStackTrace();
                    if (t.getMessage().equals("timeout")){
                        Toast.makeText(mContext, "Server AIWA sedang dalam pemeliharaan", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, "Server AIWA sedang dalam pemeliharaan", Toast.LENGTH_SHORT).show();
                    }
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
        show = (CheckBox) findViewById(R.id.show);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String status = prefs.getString("status", "out");
        if (status.equals("in")){
            Intent intentReward = MainActivity.createIntent(mContext);
            ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
            ActivityCompat.startActivity(mContext, intentReward, optionsReward.toBundle());
        }

        show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
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
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("iduser", "0");
            editor.putString("token", "null");
            editor.putString("status", "out");
            editor.putString("password", "null");
            editor.apply();
            moveTaskToBack(true);
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

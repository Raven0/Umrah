package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.birutekno.umrah.ui.BaseActivity;

import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 7/28/2017.
 */

public class LoginActivity extends BaseActivity {

    @OnClick(R.id.login)
    void loginClicked() {
        Intent intentReward = MainActivity.createIntent(this);
        ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(this, intentReward, optionsReward.toBundle());
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

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

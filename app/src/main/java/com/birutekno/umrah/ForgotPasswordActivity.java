package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.birutekno.umrah.ui.BaseActivity;

import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class ForgotPasswordActivity extends BaseActivity {

    @OnClick(R.id.kirim)
    void loginClicked() {
        finish();
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

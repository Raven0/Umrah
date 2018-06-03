package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.Toast;

import com.birutekno.umrah.ui.BaseActivity;

import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 04/08/2017.
 */

public class RegistrationActivity extends BaseActivity {

    @OnClick(R.id.kirim)
    void kirimClicked() {
        Toast.makeText(this, "Registrasi berhasil, Tunggu Approval", Toast.LENGTH_LONG).show();
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
    protected int getResourceLayout() {
        return R.layout.activity_registration;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

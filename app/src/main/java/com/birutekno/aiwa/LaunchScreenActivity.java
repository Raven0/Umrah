package com.birutekno.aiwa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.birutekno.aiwa.ui.BaseActivity;

import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LaunchScreenActivity extends BaseActivity {

//    private FirebaseRemoteConfig mRemoteConfig;
    private boolean isLocked = true;

    @Override
    protected void onResume() {
        super.onResume();
        setupRemoteConfig();
    }

    @OnClick(R.id.siswa)
    void siswaClicked() {
        Intent intentReward = LoginActivity.createIntent(this);
        ActivityOptionsCompat optionsReward = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(this, intentReward, optionsReward.toBundle());
    }

    @OnClick(R.id.orangTua)
    void orangTuaClicked() {

    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_launch_screen;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setupRemoteConfig() {
//        mRemoteConfig = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setDeveloperModeEnabled(BuildConfig.DEBUG)
//                .build();
//        mRemoteConfig.setConfigSettings(remoteConfigSettings);


        long cacheExpiration = 0;

        //expire the cache immediately for development mode.
//        if (mRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
//            cacheExpiration = 0;
//        }
//
//        // fetch
//        mRemoteConfig.fetch(cacheExpiration)
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // task successful. Activate the fetched data
//                            mRemoteConfig.activateFetched();
//
//                            //update views?
//                        } else {
//                            //task failed
//                        }
//
//                        getDisplayData();
//                    }
//                });
    }

    private void getDisplayData() {
        String lock = "no";
        if (lock.equals("yes")) {
            isLocked = true;
            new MaterialDialog.Builder(this)
                    .title("WARNING")
                    .content("This application has been locked by developer, please complete the payment for using this app.\nThank you!")
                    .positiveText("OK")
                    .theme(Theme.LIGHT)
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .show();
        }else{
            isLocked = false;
        }
    }
}

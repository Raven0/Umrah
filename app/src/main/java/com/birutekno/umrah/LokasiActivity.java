package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LokasiActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R.id.mekah)
    void mekahClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        startActivity(intent);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, LokasiActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_lokasi;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");
    }
}

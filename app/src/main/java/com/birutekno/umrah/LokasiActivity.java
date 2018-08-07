package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.birutekno.umrah.ui.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LokasiActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R.id.mekah)
    void mekahClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        intent.putExtra("kota", "Mekkah");
        startActivity(intent);
    }

    @OnClick(R.id.madinah)
    void madinahClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        intent.putExtra("kota", "Madinah");
        startActivity(intent);
    }

    @OnClick(R.id.palestina)
    void palestinaClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        intent.putExtra("kota", "Palestina");
        startActivity(intent);
    }

    @OnClick(R.id.turki)
    void turkiClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        intent.putExtra("kota", "Turki");
        startActivity(intent);
    }

    @OnClick(R.id.dubai)
    void dubaiClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        intent.putExtra("kota", "Dubai");
        startActivity(intent);
    }

    @OnClick(R.id.cairo)
    void cairoClicked() {
        Intent intent = new Intent(this, HotelActivity.class);
        intent.putExtra("kota", "Cairo");
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
        setTitle("Lokasi");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        Log.d("CDA", "onBackPressed Called");
        Intent intent = new Intent(LokasiActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

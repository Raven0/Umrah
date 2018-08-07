package com.birutekno.umrah.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import butterknife.Bind;

/**
 * Created by No Name on 7/29/2017.
 */

public class FasilitasFragment extends BaseFragment{

    @Bind(R.id.wifi)
    TextView wifi;

    @Bind(R.id.park)
    TextView park;

    @Bind(R.id.roko)
    TextView roko;

    @Bind(R.id.ac)
    TextView ac;

    @Bind(R.id.keluarga)
    TextView keluarga;

    @Bind(R.id.makanan)
    TextView makanan;
    public static FasilitasFragment newInstance() {
        FasilitasFragment fragment = new FasilitasFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_fasilitas;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        wifi.setText(bundle.getString("wifi"));
        park.setText(bundle.getString("park"));
        roko.setText(bundle.getString("roko"));
        ac.setText(bundle.getString("ac"));
        keluarga.setText(bundle.getString("keluarga"));
        makanan.setText(bundle.getString("makanan"));
    }
}

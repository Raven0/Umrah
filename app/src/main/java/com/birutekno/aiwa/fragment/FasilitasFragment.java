package com.birutekno.aiwa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

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
        wifi.setText(boolProcessor(bundle.getString("wifi")));
        park.setText(boolProcessor(bundle.getString("park")));
        roko.setText(boolProcessor(bundle.getString("roko")));
        ac.setText(boolProcessor(bundle.getString("ac")));
        keluarga.setText(boolProcessor(bundle.getString("keluarga")));
        makanan.setText(boolProcessor(bundle.getString("makanan")));
    }

    public String boolProcessor(String args){
        String val = "tidak";
        if (args.equals("true")){
            val = "ya";
        }else {
            val = "tidak";
        }

        return val;
    }
}

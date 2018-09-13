package com.birutekno.aiwa.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

/**
 * Created by No Name on 7/29/2017.
 */

public class LokasiFragment extends BaseFragment{

    public static LokasiFragment newInstance() {
        LokasiFragment fragment = new LokasiFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_lokasi;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {

    }
}

package com.birutekno.umrah.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.fragment.BaseFragment;

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

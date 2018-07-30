package com.birutekno.umrah.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import butterknife.Bind;

/**
 * Created by No Name on 7/29/2017.
 */

public class JBerangkatFragment extends BaseFragment{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    public static JBerangkatFragment newInstance() {
        JBerangkatFragment fragment = new JBerangkatFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_jberangkat;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {

    }
}

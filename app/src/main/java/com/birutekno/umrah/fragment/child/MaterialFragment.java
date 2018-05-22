package com.birutekno.umrah.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.AbsensiAdapter;
import com.birutekno.umrah.adapter.MaterialAdapter;
import com.birutekno.umrah.fragment.HomeFragment;
import com.birutekno.umrah.model.Absensi;
import com.birutekno.umrah.model.Material;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.birutekno.umrah.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by No Name on 7/31/2017.
 */

public class MaterialFragment extends BaseFragment {

    private static final String TYPE = "type";

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private MaterialAdapter mAdapter;

    public static MaterialFragment newInstance(int type) {
        MaterialFragment fragment = new MaterialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_material;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        setUpAdapter();
        setUpRecyclerView();
        setData();
    }

    private void setData() {
        List<Material> data = new ArrayList<>();

        data.add(new Material(1, "Matematika"));
        data.add(new Material(2, "B.Indonesia"));
        data.add(new Material(3, "B.Inggris"));
        data.add(new Material(4, "Biologi"));
        data.add(new Material(5, "Kimia"));
        data.add(new Material(6, "Fisika"));

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new MaterialAdapter(mContext);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setUpAsList();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                setData();
            }

            @Override
            public void onLoadMore() {
            }
        });
    }
}

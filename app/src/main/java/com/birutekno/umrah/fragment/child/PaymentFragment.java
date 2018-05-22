package com.birutekno.umrah.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.PaymentAdapter;
import com.birutekno.umrah.adapter.ScheduleAdapter;
import com.birutekno.umrah.model.Payment;
import com.birutekno.umrah.model.Schedule;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.birutekno.umrah.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by No Name on 8/7/2017.
 */

public class PaymentFragment extends BaseFragment {

    private static final String TYPE = "type";

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private PaymentAdapter mAdapter;
    private int mType;

    public static PaymentFragment newInstance(int type) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_payment;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        mType = bundle.getInt(TYPE, 1);

        setUpAdapter();
        setUpRecyclerView();
        setData();
    }

    private void setData() {
        List<Payment> data = new ArrayList<>();

        if (mType == 1) {
            data.add(new Payment(1, "SPP Bulan Juli 2017", "Rp. 300.000", "Jatuh Tempo"));
        }else if (mType == 2){
            data.add(new Payment(2, "SPP Bulan Juni 2017", "Rp. 300.000", "Belum Dibayar"));
            data.add(new Payment(3, "SPP Bulan Mei 2017", "Rp. 300.000", "Belum Dibayar"));
        } else {
            data.add(new Payment(4, "SPP Bulan April 2017", "Rp. 300.000", "Sudah Dibayar"));
            data.add(new Payment(5, "SPP Bulan Maret 2017", "Rp. 300.000", "Sudah Dibayar"));
        }

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new PaymentAdapter(mContext);
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

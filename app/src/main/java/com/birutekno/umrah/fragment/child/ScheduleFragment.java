package com.birutekno.umrah.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.ScheduleAdapter;
import com.birutekno.umrah.adapter.ScoreAdapter;
import com.birutekno.umrah.model.Schedule;
import com.birutekno.umrah.model.Score;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.birutekno.umrah.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by No Name on 04/08/2017.
 */

public class ScheduleFragment extends BaseFragment {

    private static final String TYPE = "type";

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private ScheduleAdapter mAdapter;

    public static ScheduleFragment newInstance(int type) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_schedule;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        setUpAdapter();
        setUpRecyclerView();
        setData();
    }


    private void setData() {
        List<Schedule> data = new ArrayList<>();

        data.add(new Schedule(1, "Matematika", "Ruang 12", "07.00 - 08.30"));
        data.add(new Schedule(2, "Indonesia", "Ruang 15", "08.30 - 10.00"));
        data.add(new Schedule(3, "Biologi", "Ruang 20", "10.45 - 11.45"));
        data.add(new Schedule(4, "Fisika", "Ruang 5", "11.45 - 12.30"));
        data.add(new Schedule(5, "Sejarah", "Ruang 9", "12.30 - 13.45"));

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new ScheduleAdapter(mContext);
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

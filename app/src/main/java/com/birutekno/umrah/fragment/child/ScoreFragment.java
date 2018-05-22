package com.birutekno.umrah.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.ScoreAdapter;
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

public class ScoreFragment extends BaseFragment {

    private static final String TYPE = "type";

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private ScoreAdapter mAdapter;
    private int mType;

    public static ScoreFragment newInstance(int type) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_score;
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
        List<Score> data = new ArrayList<>();

        data.add(new Score(1, "Matematika", "Score 100", "25 Jan - 13.00 ", "Lulus"));
        data.add(new Score(2, "B.Inggris", "Score 60", "25 Jan - 13.00 ", "Tidak Lulus"));
        data.add(new Score(3, "B.Indonesia", "Score 90", "25 Jan - 13.00 ", "Lulus"));
        data.add(new Score(4, "Biologi", "Score 60", "25 Jan - 13.00 ", "Tidak Lulus"));
        data.add(new Score(5, "Sejarah", "Score 80", "25 Jan - 13.00 ", "Lulus"));
        data.add(new Score(6, "Kimia", "Score 60", "25 Jan - 13.00 ", "Tidak Lulus"));

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        switch (mType){
            case 1:
                mAdapter = new ScoreAdapter(mContext, 1);
                break;
            case 2:
                mAdapter = new ScoreAdapter(mContext, 2);
                break;
            case 3:
                mAdapter = new ScoreAdapter(mContext, 3);
                break;
            case 4:
                mAdapter = new ScoreAdapter(mContext, 4);
                break;
        }
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

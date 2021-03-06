package com.birutekno.aiwa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.birutekno.aiwa.adapter.JadwalAdapter;
import com.birutekno.aiwa.model.Jadwal;
import com.birutekno.aiwa.ui.BaseActivity;
import com.birutekno.aiwa.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.aiwa.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JadwalActivityBACKUP extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private JadwalAdapter mAdapter;
    private String mDate = "";

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JadwalActivityBACKUP.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_jadwal;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        setUpAdapter();
        setUpRecyclerView();
        setData();

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ExpandableLayout expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
                if (expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                } else {
                    expandableLayout.expand();
                }
            }
        });
    }

    private void setData() {
        List<Jadwal> data = new ArrayList<>();

        for (int i = 0; i < 3; i++){
//            data.add(new Jadwal(i));
        }

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new JadwalAdapter(mContext);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

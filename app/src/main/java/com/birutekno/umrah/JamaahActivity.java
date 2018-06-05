package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.birutekno.umrah.adapter.JamaahAdapter;
import com.birutekno.umrah.model.Jamaah;
import com.birutekno.umrah.ui.BaseActivity;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JamaahActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private JamaahAdapter mAdapter;
    private String mDate = "";

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JamaahActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_jamaah;
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
                LinearLayout edit = (LinearLayout) view.findViewById(R.id.edit);
                LinearLayout detail = (LinearLayout) view.findViewById(R.id.view);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Edit", Toast.LENGTH_SHORT).show();
                    }
                });

                detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(JamaahActivity.this, InputActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void setData() {
        List<Jamaah> data = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            data.add(new Jamaah(i));
        }

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new JamaahAdapter(mContext);
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

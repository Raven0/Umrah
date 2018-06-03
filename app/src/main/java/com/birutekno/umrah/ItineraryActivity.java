package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.birutekno.umrah.adapter.ItineraryAdapter;
import com.birutekno.umrah.model.Itinerary;
import com.birutekno.umrah.ui.BaseActivity;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by No Name on 7/31/2017.
 */

public class ItineraryActivity extends BaseActivity{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private ItineraryAdapter mAdapter;
    private String mDate = "";

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ItineraryActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_itinerary;
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
                LinearLayout download = (LinearLayout) view.findViewById(R.id.download);
                LinearLayout share = (LinearLayout) view.findViewById(R.id.share);

                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "Downloading", Toast.LENGTH_SHORT).show();
                    }
                });

                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent = new Intent();
                        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Share");
                        startActivity(Intent.createChooser(shareIntent,"Share with"));
                    }
                });
            }
        });
    }

    private void setData() {
        List<Itinerary> data = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            data.add(new Itinerary(i));
        }

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new ItineraryAdapter(mContext);
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

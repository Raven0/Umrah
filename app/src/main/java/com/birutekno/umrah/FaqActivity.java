package com.birutekno.umrah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.birutekno.umrah.adapter.FaqAdapter;
import com.birutekno.umrah.model.Faq;
import com.birutekno.umrah.ui.BaseActivity;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.ui.view.BaseRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FaqActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private FaqAdapter mAdapter;
    private String mDate = "";

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, FaqActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_faq;
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
                ImageView img = (ImageView) view.findViewById(R.id.expand);
                if (expandableLayout.isExpanded()) {
                    expandableLayout.collapse();
                    img.setImageResource(R.drawable.icon_plus);
//                    img.setBackgroundResource(R.drawable.icon_plus);
                } else {
                    expandableLayout.expand();
                    img.setImageResource(R.drawable.icon_min);
//                    img.setBackgroundResource(R.drawable.icon_min);
                }
            }
        });
    }

    private void setData() {
        List<Faq> data = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            data.add(new Faq(i));
        }

        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.loadMoreComplete();
            mRecyclerView.refreshComplete();
        }
    }

    private void setUpAdapter() {
        mAdapter = new FaqAdapter(mContext);
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

package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Score;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.ScoreItemView;

/**
 * Created by No Name on 03/08/2017.
 */

public class ScoreAdapter extends BaseRecyclerAdapter<Score, ScoreItemView> {

    private int mType;

    public ScoreAdapter(Context context, int type) {
        super(context);
        this.mType = type;
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_score;
    }

    @Override
    public ScoreItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        ScoreItemView itemView = new ScoreItemView(mContext, mType, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Banner;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.BannerItemView;

/**
 * Created by No Name on 7/29/2017.
 */

public class BannerAdapter extends BaseRecyclerAdapter<Banner, BannerItemView> {

    private BannerItemView.OnActionListener mOnActionListener;

    public void setOnActionListener(BannerItemView.OnActionListener onActionListener) {
        mOnActionListener = onActionListener;
    }

    public BannerAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_banner;
    }

    @Override
    public BannerItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        BannerItemView itemView = new BannerItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        itemView.setOnActionListener(mOnActionListener);
        return itemView;
    }
}

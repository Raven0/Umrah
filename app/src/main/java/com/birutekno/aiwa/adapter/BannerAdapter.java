package com.birutekno.aiwa.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.Banner;
import com.birutekno.aiwa.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.aiwa.view.BannerItemView;

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

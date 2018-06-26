package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Faq;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.FaqItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class FaqAdapter extends BaseRecyclerAdapter<Faq, FaqItemView> {

    public FaqAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_faq;
    }

    @Override
    public FaqItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        FaqItemView itemView = new FaqItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Jamaah;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.JamaahItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class JamaahAdapter extends BaseRecyclerAdapter<Jamaah, JamaahItemView> {

    public JamaahAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_jamaah;
    }

    @Override
    public JamaahItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        JamaahItemView itemView = new JamaahItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

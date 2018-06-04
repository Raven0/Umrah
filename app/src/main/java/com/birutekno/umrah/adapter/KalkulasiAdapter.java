package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Kalkulasi;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.KalkulasiItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class KalkulasiAdapter extends BaseRecyclerAdapter<Kalkulasi, KalkulasiItemView> {

    public KalkulasiAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_kalkulasi;
    }

    @Override
    public KalkulasiItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        KalkulasiItemView itemView = new KalkulasiItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

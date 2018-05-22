package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Absensi;
import com.birutekno.umrah.model.Banner;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.AbsensiItemView;
import com.birutekno.umrah.view.BannerItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class AbsensiAdapter extends BaseRecyclerAdapter<Absensi, AbsensiItemView> {

    public AbsensiAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_absensi;
    }

    @Override
    public AbsensiItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        AbsensiItemView itemView = new AbsensiItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

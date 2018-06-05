package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.JadwalItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class JadwalAdapter extends BaseRecyclerAdapter<Jadwal, JadwalItemView> {

    public JadwalAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_jadwal;
    }

    @Override
    public JadwalItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        JadwalItemView itemView = new JadwalItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

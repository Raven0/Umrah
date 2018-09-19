package com.birutekno.aiwa.view;

import android.content.Context;
import android.view.View;

import com.birutekno.aiwa.model.Jadwal;
import com.birutekno.aiwa.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.aiwa.ui.adapter.viewholder.BaseItemViewHolder;

/**
 * Created by No Name on 7/31/2017.
 */

public class JadwalItemView extends BaseItemViewHolder<Jadwal> {

    public JadwalItemView(Context context, View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        this.mContext = context;
    }

    @Override
    public void bind(Jadwal jadwal) {

    }
}
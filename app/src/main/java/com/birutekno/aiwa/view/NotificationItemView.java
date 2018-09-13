package com.birutekno.aiwa.view;

import android.content.Context;
import android.view.View;

import com.birutekno.aiwa.model.Notification;
import com.birutekno.aiwa.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.aiwa.ui.adapter.viewholder.BaseItemViewHolder;

/**
 * Created by Aldio Firando on 8/15/2017.
 */

public class NotificationItemView extends BaseItemViewHolder<Notification> {

    public NotificationItemView(Context context, View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        this.mContext = context;
    }

    @Override
    public void bind(Notification notification) {

    }
}

package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Notification;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.NotificationItemView;

/**
 * Created by Aldio Firando on 8/15/2017.
 */

public class NotificationAdapter extends BaseRecyclerAdapter<Notification, NotificationItemView> {

    public NotificationAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_notification;
    }

    @Override
    public NotificationItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationItemView itemView = new NotificationItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

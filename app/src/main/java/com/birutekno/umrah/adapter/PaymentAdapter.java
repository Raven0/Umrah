package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Payment;
import com.birutekno.umrah.model.Schedule;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.PaymentItemView;
import com.birutekno.umrah.view.ScheduleItemView;

/**
 * Created by No Name on 8/7/2017.
 */

public class PaymentAdapter extends BaseRecyclerAdapter<Payment, PaymentItemView> {

    public PaymentAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_payment;
    }

    @Override
    public PaymentItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        PaymentItemView itemView = new PaymentItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

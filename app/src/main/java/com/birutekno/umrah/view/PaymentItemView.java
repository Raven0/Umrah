package com.birutekno.umrah.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Payment;
import com.birutekno.umrah.model.Schedule;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.ui.adapter.viewholder.BaseItemViewHolder;

import butterknife.Bind;

/**
 * Created by No Name on 8/7/2017.
 */

public class PaymentItemView extends BaseItemViewHolder<Payment> {

    @Bind(R.id.desc)
    TextView mDesc;

    @Bind(R.id.value)
    TextView mValue;

    @Bind(R.id.status)
    TextView mStatus;

    public PaymentItemView(Context context, View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        this.mContext = context;
    }

    @Override
    public void bind(Payment payment) {
        mDesc.setText(payment.getDesc());
        mValue.setText(payment.getValue());
        mStatus.setText(payment.getStatus());
        if (payment.getStatus().equals("Jatuh Tempo")){
            mStatus.setBackgroundColor(Color.parseColor("#FF0000"));
        }else if (payment.getStatus().equals("Belum Dibayar")){
            mStatus.setBackgroundColor(Color.parseColor("#FF9A1F"));
        }else{
            mStatus.setBackgroundColor(Color.parseColor("#33CC33"));
        }
    }
}

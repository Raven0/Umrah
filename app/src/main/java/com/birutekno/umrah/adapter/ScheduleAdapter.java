package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Schedule;
import com.birutekno.umrah.model.Score;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.ScheduleItemView;
import com.birutekno.umrah.view.ScoreItemView;

/**
 * Created by No Name on 04/08/2017.
 */

public class ScheduleAdapter extends BaseRecyclerAdapter<Schedule, ScheduleItemView> {

    public ScheduleAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_schedule;
    }

    @Override
    public ScheduleItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        ScheduleItemView itemView = new ScheduleItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

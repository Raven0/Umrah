package com.birutekno.umrah.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Schedule;
import com.birutekno.umrah.model.Score;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.ui.adapter.viewholder.BaseItemViewHolder;

import butterknife.Bind;

/**
 * Created by No Name on 04/08/2017.
 */

public class ScheduleItemView extends BaseItemViewHolder<Schedule> {

    @Bind(R.id.subject)
    TextView mSubject;

    @Bind(R.id.room)
    TextView mRoom;

    @Bind(R.id.time)
    TextView mTime;

    public ScheduleItemView(Context context, View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        this.mContext = context;
    }

    @Override
    public void bind(Schedule schedule) {
        mSubject.setText(schedule.getSubject());
        mRoom.setText(schedule.getRoom());
        mTime.setText(schedule.getTime());
    }
}

package com.birutekno.umrah.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Score;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.ui.adapter.viewholder.BaseItemViewHolder;

import butterknife.Bind;

/**
 * Created by No Name on 03/08/2017.
 */

public class ScoreItemView extends BaseItemViewHolder<Score> {

    @Bind(R.id.subject)
    TextView mSubject;

    @Bind(R.id.score)
    TextView mScore;

    @Bind(R.id.time)
    TextView mTime;

    @Bind(R.id.status)
    TextView mStatus;

    @Bind(R.id.scoreType)
    TextView mScoreType;

    private int mType;

    public ScoreItemView(Context context, int type, View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView, itemClickListener, longItemClickListener);
        this.mContext = context;
        this.mType = type;
    }

    @Override
    public void bind(Score material) {
        switch (mType){
            case 1:
                mScoreType.setText("Tugas Sekolah");
                break;
            case 2:
                mScoreType.setText("Tugas Rumah");
                break;
            case 3:
                mScoreType.setText("UTS");
                break;
            case 4:
                mScoreType.setText("UAS");
                break;

        }

        mSubject.setText(material.getSubject());
        mTime.setText(material.getTime());
        mScore.setText(material.getScore());
        mStatus.setText(material.getStatus());
        if (material.getStatus().equals("Lulus")){
            mStatus.setTextColor(Color.parseColor("#33CC33"));
        }else {
            mStatus.setTextColor(Color.parseColor("#FF0000"));
        }
    }
}

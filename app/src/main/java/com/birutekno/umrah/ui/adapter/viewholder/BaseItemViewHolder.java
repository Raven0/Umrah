package com.birutekno.umrah.ui.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;

import butterknife.ButterKnife;

/**
 * Created on : 05-03-2016
 * Author     : derohimat
 * Name       : Deni Rohimat
 * Email      : rohimatdeni@gmail.com
 * GitHub     : https://github.com/derohimat
 * LinkedIn   : https://www.linkedin.com/in/derohimat
 */
public abstract class BaseItemViewHolder<Data> extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnLongClickListener {
    protected Context mContext;
    private BaseRecyclerAdapter.OnItemClickListener mItemClickListener;
    private BaseRecyclerAdapter.OnLongItemClickListener mLongItemClickListener;
    private boolean mHasHeader = false;

    public BaseItemViewHolder(View itemView, BaseRecyclerAdapter.OnItemClickListener itemClickListener, BaseRecyclerAdapter.OnLongItemClickListener longItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mItemClickListener = itemClickListener;
        this.mLongItemClickListener = longItemClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public abstract void bind(Data data);

    public boolean isHasHeader() {
        return mHasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.mHasHeader = hasHeader;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, mHasHeader ? getAdapterPosition() - 1 : getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongItemClickListener != null) {
            mLongItemClickListener.onLongItemClick(v, mHasHeader ? getAdapterPosition() - 1 : getAdapterPosition());
            return true;
        }
        return false;
    }
}

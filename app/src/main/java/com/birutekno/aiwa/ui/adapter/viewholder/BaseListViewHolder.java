package com.birutekno.aiwa.ui.adapter.viewholder;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created on : 05-03-2016
 * Author     : derohimat
 * Name       : Deni Rohimat
 * Email      : rohimatdeni@gmail.com
 * GitHub     : https://github.com/derohimat
 * LinkedIn   : https://www.linkedin.com/in/derohimat
 */
public abstract class BaseListViewHolder<Data> {
    protected Context mContext;

    public BaseListViewHolder(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    public abstract void bind(Data data);
}

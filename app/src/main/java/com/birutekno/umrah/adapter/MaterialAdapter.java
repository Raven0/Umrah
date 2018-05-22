package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Material;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.MaterialItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class MaterialAdapter extends BaseRecyclerAdapter<Material, MaterialItemView> {

    public MaterialAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_material;
    }

    @Override
    public MaterialItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        MaterialItemView itemView = new MaterialItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

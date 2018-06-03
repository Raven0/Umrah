package com.birutekno.umrah.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Itinerary;
import com.birutekno.umrah.ui.adapter.BaseRecyclerAdapter;
import com.birutekno.umrah.view.ItineraryItemView;

/**
 * Created by No Name on 7/31/2017.
 */

public class ItineraryAdapter extends BaseRecyclerAdapter<Itinerary, ItineraryItemView> {

    public ItineraryAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.list_item_itinerary;
    }

    @Override
    public ItineraryItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        ItineraryItemView itemView = new ItineraryItemView(mContext, getView(parent, viewType), mItemClickListener, mLongItemClickListener);
        return itemView;
    }
}

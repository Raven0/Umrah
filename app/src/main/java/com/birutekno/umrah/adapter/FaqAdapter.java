package com.birutekno.umrah.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataFaq;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {

    public ArrayList<DataFaq> dataFaqs;
    public ArrayList<DataFaq> mFilterData;
    private ProgressDialog pDialog;
    Context context;

    public FaqAdapter(ArrayList<DataFaq> dataFaqs, Context context) {
        this.dataFaqs = dataFaqs;
        mFilterData = dataFaqs;
        this.context = context;
    }

    @Override
    public FaqAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_faq, viewGroup, false);
        return new FaqAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FaqAdapter.ViewHolder viewHolder, int i) {

        viewHolder.judul.setText(mFilterData.get(i).getJudul());
        viewHolder.jawaban.setText(mFilterData.get(i).getJawaban());

    }

    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView judul, jawaban;
        private ExpandableLayout expandableLayout;
        public ViewHolder(final View view) {
            super(view);

            judul = (TextView)view.findViewById(R.id.judul);
            jawaban = (TextView)view.findViewById(R.id.jawaban);
            expandableLayout = (ExpandableLayout)view.findViewById(R.id.expandable_layout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.isExpanded()) {
                        expandableLayout.collapse();
                    } else {
                        expandableLayout.expand();
                    }
                }
            });

        }
    }
}

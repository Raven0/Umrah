package com.birutekno.aiwa.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.DataFaq;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> implements Filterable {

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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {
                    mFilterData = dataFaqs;
                } else {

                    ArrayList<DataFaq> filterData = new ArrayList<>();

                    for (DataFaq data: dataFaqs) {

                        if (data.getJudul().toLowerCase().contains(charString)) {
                            filterData.add(data);
                        }
                    }

                    mFilterData = filterData;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterData = (ArrayList<DataFaq>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView judul, jawaban;
        String question, answer, whatsapp;
        private ExpandableLayout expandableLayout;
        private ImageView plus, minus;
        public ViewHolder(final View view) {
            super(view);

            judul = (TextView)view.findViewById(R.id.judul);
            jawaban = (TextView)view.findViewById(R.id.jawaban);
            plus = (ImageView)view.findViewById(R.id.plus);
            minus = (ImageView)view.findViewById(R.id.minus);
            expandableLayout = (ExpandableLayout)view.findViewById(R.id.expandable_layout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (expandableLayout.isExpanded()) {
                        expandableLayout.collapse();
                        plus.setVisibility(View.VISIBLE);
                        minus.setVisibility(View.GONE);
                    } else {
                        expandableLayout.expand();
                        plus.setVisibility(View.GONE);
                        minus.setVisibility(View.VISIBLE);
                    }
                }
            });

            expandableLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Memuat data...", Toast.LENGTH_SHORT).show();

                    question = String.valueOf(judul.getText());
                    answer = String.valueOf(jawaban.getText());

                    whatsapp = "*"+question+"*\n"+answer;
                    Intent shareIntent = new Intent();
                    shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, whatsapp);
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(shareIntent,"Share with"));
                }
            });

        }
    }
}

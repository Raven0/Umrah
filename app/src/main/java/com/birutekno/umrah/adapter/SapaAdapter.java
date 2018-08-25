package com.birutekno.umrah.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataSapa;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class SapaAdapter extends RecyclerView.Adapter<SapaAdapter.ViewHolder> implements Filterable {

    public ArrayList<DataSapa> dataSapas;
    public ArrayList<DataSapa> mFilterData;
    private ProgressDialog pDialog;
    Context context;

    public SapaAdapter(ArrayList<DataSapa> dataSapas, Context context) {
        this.dataSapas = dataSapas;
        mFilterData = dataSapas;
        this.context = context;
    }

    @Override
    public SapaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_sapa, viewGroup, false);
        return new SapaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SapaAdapter.ViewHolder viewHolder, int i) {
        viewHolder.jawaban.setText(mFilterData.get(i).getText_sapaan());
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
                    mFilterData = dataSapas;
                } else {

                    ArrayList<DataSapa> filterData = new ArrayList<>();

                    for (DataSapa data: dataSapas) {

                        if (data.getText_sapaan().toLowerCase().contains(charString)) {
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
                mFilterData = (ArrayList<DataSapa>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView jawaban;
        String whatsapp;
        public ViewHolder(final View view) {
            super(view);

            jawaban = (TextView)view.findViewById(R.id.jawaban);

            jawaban.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Memuat data...", Toast.LENGTH_SHORT).show();

                    whatsapp = String.valueOf(jawaban.getText());

                    Intent shareIntent = new Intent();
                    shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, whatsapp);
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(shareIntent,"Share with"));
                }
            });

        }
    }
}

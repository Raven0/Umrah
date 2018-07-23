package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.umrah.InputKalkulasiActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataProspek;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class KalkulasiAdapter extends RecyclerView.Adapter<KalkulasiAdapter.ViewHolder> implements Filterable {

    private ArrayList<DataProspek> data;
    private ArrayList<DataProspek> mFilterData;
    Context context;

    public KalkulasiAdapter(ArrayList<DataProspek> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public KalkulasiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_kalkulasi, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KalkulasiAdapter.ViewHolder viewHolder, int i) {

        viewHolder.nama.setText(mFilterData.get(i).getNama());
        viewHolder.telp.setText(mFilterData.get(i).getNo_telp());
        viewHolder.alamat.setText(mFilterData.get(i).getAlamat());
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

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mFilterData = data;
                } else {

                    ArrayList<DataProspek> filterData = new ArrayList<>();

                    for (DataProspek data: data) {

                        if (data.getNama().toLowerCase().contains(charString) || data.getAlamat().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString)) {
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
                mFilterData = (ArrayList<DataProspek>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama,telp,alamat;
        private LinearLayout detail, edit;
        public ViewHolder(View view) {
            super(view);

            nama = (TextView)view.findViewById(R.id.namaProspek);
            telp = (TextView)view.findViewById(R.id.noTelp);
            alamat = (TextView)view.findViewById(R.id.alamatProspek);

            detail = (LinearLayout)view.findViewById(R.id.view);
            edit = (LinearLayout)view.findViewById(R.id.edit);

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InputKalkulasiActivity.class);
                    context.startActivity(intent);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InputKalkulasiActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

}

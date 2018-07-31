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
import android.widget.Toast;

import com.birutekno.umrah.EditKalkulasiActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataProspek;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class KalkulasiAdapter extends RecyclerView.Adapter<KalkulasiAdapter.ViewHolder> implements Filterable {

    private ArrayList<DataProspek> dataProspeks;
    private ArrayList<DataProspek> mFilterData;
    Context context;

    public KalkulasiAdapter(ArrayList<DataProspek> dataProspeks, Context context) {
        this.dataProspeks = dataProspeks;
        mFilterData = dataProspeks;
        this.context = context;
    }

    @Override
    public KalkulasiAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_kalkulasi, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KalkulasiAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(mFilterData.get(i).getId());
        viewHolder.nama.setText(mFilterData.get(i).getPic());
        viewHolder.telp.setText(mFilterData.get(i).getNo_telp());

        int jmldewasa = Integer.parseInt(mFilterData.get(i).getJml_dewasa());
        int jmlinfant = Integer.parseInt(mFilterData.get(i).getJml_infant());
        int jmlbalita = Integer.parseInt(mFilterData.get(i).getJml_balita());
        int jmlbalitaKasur = Integer.parseInt(mFilterData.get(i).getJml_balita_kasur());
        int pax = jmldewasa + jmlinfant + jmlbalita + jmlbalitaKasur;
        viewHolder.alamat.setText(String.valueOf(pax)+ " PAX");

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
                    mFilterData = dataProspeks;
                } else {

                    ArrayList<DataProspek> filterData = new ArrayList<>();

                    for (DataProspek data: dataProspeks) {

                        int jmldewasa = Integer.parseInt(data.getJml_dewasa());
                        int jmlinfant = Integer.parseInt(data.getJml_infant());
                        int jmlbalita = Integer.parseInt(data.getJml_balita());
                        int jmlbalitaKasur = Integer.parseInt(data.getJml_balita_kasur());
                        int pax = jmldewasa + jmlinfant + jmlbalita + jmlbalitaKasur;
                        String strPax = pax + " PAX";

                        if (data.getPic().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString) || strPax.toLowerCase().contains(charString)) {
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
        private TextView id,nama,telp,alamat;
        private LinearLayout detail, edit;
        public ViewHolder(final View view) {
            super(view);

            id = (TextView)view.findViewById(R.id.idProspek);
            nama = (TextView)view.findViewById(R.id.namaProspek);
            telp = (TextView)view.findViewById(R.id.noTelp);
            alamat = (TextView)view.findViewById(R.id.alamatProspek);

            detail = (LinearLayout)view.findViewById(R.id.view);
            edit = (LinearLayout)view.findViewById(R.id.edit);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, id.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "pay", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, InputKalkulasiActivity.class);
//                    context.startActivity(intent);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditKalkulasiActivity.class);
                    String sessionId = id.getText().toString();
                    intent.putExtra("id", sessionId);
                    context.startActivity(intent);
                }
            });

        }
    }

}

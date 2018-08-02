package com.birutekno.umrah.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataPotkom;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class PotkomAdapter extends RecyclerView.Adapter<PotkomAdapter.ViewHolder> implements Filterable {

    private ArrayList<DataPotkom> data;
    private ArrayList<DataPotkom> mFilterData;
    Context context;

    public PotkomAdapter(ArrayList<DataPotkom> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public PotkomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_potkom, viewGroup, false);
        return new PotkomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PotkomAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(mFilterData.get(i).getId());
        viewHolder.nama.setText(mFilterData.get(i).getNama());
        viewHolder.telp.setText(mFilterData.get(i).getNo_telp());
        viewHolder.alamat.setText(mFilterData.get(i).getTgl_berangkat());
        viewHolder.fee.setText(mFilterData.get(i).getMarketing_fee());

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

                    ArrayList<DataPotkom> filterData = new ArrayList<>();

                    for (DataPotkom data: data) {

                        if (data.getNama().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString)) {
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
                mFilterData = (ArrayList<DataPotkom>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id,nama,telp,alamat,fee;
        public ViewHolder(final View view) {
            super(view);

            id = (TextView)view.findViewById(R.id.idProspek);
            nama = (TextView)view.findViewById(R.id.namaProspek);
            telp = (TextView)view.findViewById(R.id.noTelp);
            alamat = (TextView)view.findViewById(R.id.alamatProspek);
            fee = (TextView)view.findViewById(R.id.fee);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, id.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}

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
import com.birutekno.umrah.model.DataJamaah;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class JamaahAdapter extends RecyclerView.Adapter<JamaahAdapter.ViewHolder> implements Filterable {

    private ArrayList<DataJamaah> data;
    private ArrayList<DataJamaah> mFilterData;
    Context context;

    public JamaahAdapter(ArrayList<DataJamaah> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public JamaahAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_kalkulasi, viewGroup, false);
        return new JamaahAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JamaahAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(mFilterData.get(i).getId());
        viewHolder.nama.setText(mFilterData.get(i).getNama());
        viewHolder.telp.setText(mFilterData.get(i).getNo_telp());
        viewHolder.alamat.setText(mFilterData.get(i).getTgl_berangkat());

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

                    ArrayList<DataJamaah> filterData = new ArrayList<>();

                    for (DataJamaah data: data) {

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
                mFilterData = (ArrayList<DataJamaah>) filterResults.values;
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

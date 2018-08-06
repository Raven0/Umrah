package com.birutekno.umrah.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataAgen;

import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class SubagenAdapter extends RecyclerView.Adapter<SubagenAdapter.ViewHolder> implements Filterable {

    public static final String PREFS_NAME = "AUTH";
    public ArrayList<DataAgen> dataAgens;
    public ArrayList<DataAgen> mFilterData;
    private ProgressDialog pDialog;
    Context context;

    public SubagenAdapter(ArrayList<DataAgen> dataAgens, Context context) {
        this.dataAgens = dataAgens;
        mFilterData = dataAgens;
        this.context = context;
    }

    @Override
    public SubagenAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_subagen, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubagenAdapter.ViewHolder viewHolder, int i) {

        viewHolder.id.setText(mFilterData.get(i).getId());
        viewHolder.nama.setText(mFilterData.get(i).getNama());
        viewHolder.telp.setText(mFilterData.get(i).getNo_telp());

        viewHolder.no_telp = mFilterData.get(i).getNo_telp();
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
                    mFilterData = dataAgens;
                } else {

                    ArrayList<DataAgen> filterData = new ArrayList<>();

                    for (DataAgen data: dataAgens) {
                        if (data.getNama().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString)) {
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
                mFilterData = (ArrayList<DataAgen>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView id,nama,telp,alamat;
        String no_telp;
        public ViewHolder(final View view) {
            super(view);

            id = (TextView)view.findViewById(R.id.idProspek);
            nama = (TextView)view.findViewById(R.id.namaProspek);
            telp = (TextView)view.findViewById(R.id.noTelp);
            alamat = (TextView)view.findViewById(R.id.alamatProspek);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String smsNumber="919426640584@s.whatsapp.net";
                    Uri uri = Uri.parse("smsto:" + no_telp);
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.putExtra("sms_body", "Prakash Gajera");
                    i.setPackage("com.whatsapp");
                    context.startActivity(i);
                }
            });
        }
    }

}

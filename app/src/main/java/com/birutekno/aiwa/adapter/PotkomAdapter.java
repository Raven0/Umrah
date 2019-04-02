package com.birutekno.aiwa.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.DataPotkom;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by No Name on 7/31/2017.
 */

public class PotkomAdapter extends RecyclerView.Adapter<PotkomAdapter.ViewHolder> implements Filterable {

    private ArrayList<DataPotkom> data;
    private ArrayList<DataPotkom> mFilterData;
    Context context;
    String code;

    public PotkomAdapter(ArrayList<DataPotkom> data, Context context, String code) {
        this.data = data;
        mFilterData = data;
        this.context = context;
        this.code = code;
    }

    @Override
    public PotkomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_potkom, viewGroup, false);
        return new PotkomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PotkomAdapter.ViewHolder viewHolder, int i) {

        String marketing = mFilterData.get(i).getMarketing();
        String koordinator = mFilterData.get(i).getKoordinator();

        viewHolder.id.setText(mFilterData.get(i).getId());
        viewHolder.nama.setText(mFilterData.get(i).getNama()+ " || Dari Agen : "+mFilterData.get(i).getMarketing_name());
        viewHolder.telp.setText("Berangkat : " + mFilterData.get(i).getTgl_berangkat());
        if (code.equals("agen")){
            if (viewHolder.idmarketing.equals(marketing)){
                viewHolder.alamat.setText("Fee : " + numberFormat(mFilterData.get(i).getMarketing_fee()));
            }else if(viewHolder.idmarketing.equals(koordinator)){
                viewHolder.alamat.setText("Fee : " + numberFormat(mFilterData.get(i).getKoordinator_fee()));
            }
        }else {
            viewHolder.alamat.setText("Fee : " + numberFormat(mFilterData.get(i).getTop_fee()));
        }

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
                    mFilterData = data;
                } else {

                    ArrayList<DataPotkom> filterData = new ArrayList<>();

                    for (DataPotkom data: data) {

                        if (data.getNama().toLowerCase().contains(charString) || data.getMarketing().toLowerCase().contains(charString)) {
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
        public static final String PREFS_NAME = "AUTH";

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        private String idmarketing;
        private TextView id,nama,telp,alamat;
        //        private Spinner spinner;
        public ViewHolder(final View view) {
            super(view);

            id = (TextView)view.findViewById(R.id.idProspek);
            nama = (TextView)view.findViewById(R.id.namaProspek);
            telp = (TextView)view.findViewById(R.id.noTelp);
            alamat = (TextView)view.findViewById(R.id.fee);

            idmarketing = prefs.getString("iduser", "0");
//            spinner = (SearchableSpinner)view.findViewById(R.id.searchSapaan);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    spinner.performClick();
//                    Intent intent = new Intent(context, SapaanActivity.class);
//                    context.startActivity(intent);
//                }
//            });

        }
    }

    public String numberFormat(String args){
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = null;
        if(args != null){
            int myNumber = Integer.parseInt(args);
            formattedNumber = formatter.format(myNumber);
        }
        return formattedNumber;
    }

}

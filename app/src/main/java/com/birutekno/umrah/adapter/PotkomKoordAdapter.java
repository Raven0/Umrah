package com.birutekno.umrah.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataPotkom;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by No Name on 7/31/2017.
 */

public class PotkomKoordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<DataPotkom> dataPotkomList;
    private Context context;

    private boolean isLoadingAdded = false;

    public PotkomKoordAdapter(Context context) {
        this.context = context;
        dataPotkomList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new PotkomKoordAdapter.LoadingViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.list_item_potkom, parent, false);
        viewHolder = new PotkomViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DataPotkom result = dataPotkomList.get(position); // Movie

        switch (getItemViewType(position)) {
            case ITEM:
                final PotkomViewHolder potkomViewHolder = (PotkomViewHolder) holder;
                potkomViewHolder.namaPotkom.setText(result.getNama());
                potkomViewHolder.noTelp.setText(result.getNo_telp());
//                potkomViewHolder.alamatPotkom.setText(result.getTgl_daftar());
                potkomViewHolder.fee.setText("Fee : " + numberFormat(result.getKoordinator_fee()));
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return dataPotkomList == null ? 0 : dataPotkomList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataPotkomList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//                    mFilterData = data;
//                } else {
//
//                    ArrayList<DataPotkom> filterData = new ArrayList<>();
//
//                    for (DataPotkom data: data) {
//
//                        if (data.getNama().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString) || data.getNo_telp().toLowerCase().contains(charString)) {
//                            filterData.add(data);
//                        }
//                    }
//
//                    mFilterData = filterData;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilterData;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mFilterData = (ArrayList<DataPotkom>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public void add(DataPotkom r) {
        dataPotkomList.add(r);
        notifyItemInserted(dataPotkomList.size() - 1);
    }

    public void addAll(List<DataPotkom> moveResults) {
        for (DataPotkom result : moveResults) {
            add(result);
        }
    }

    public void remove(DataPotkom r) {
        int position = dataPotkomList.indexOf(r);
        if (position > -1) {
            dataPotkomList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new DataPotkom());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = dataPotkomList.size() - 1;
        DataPotkom result = getItem(position);

        if (result != null) {
            dataPotkomList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public DataPotkom getItem(int position) {
        return dataPotkomList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class PotkomViewHolder extends RecyclerView.ViewHolder {
        private TextView namaPotkom;
        private TextView noTelp;
        private TextView alamatPotkom;
        private TextView fee;

        public PotkomViewHolder(View itemView) {
            super(itemView);

            namaPotkom = (TextView) itemView.findViewById(R.id.namaProspek);
            noTelp = (TextView) itemView.findViewById(R.id.noTelp);
            alamatPotkom = (TextView) itemView.findViewById(R.id.alamat);
            fee = (TextView) itemView.findViewById(R.id.fee);
        }
    }


    protected class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public String numberFormat(String args){
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(args);
        String formattedNumber = formatter.format(myNumber);
        return formattedNumber;
    }

}

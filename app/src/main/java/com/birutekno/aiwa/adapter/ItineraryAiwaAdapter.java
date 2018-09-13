package com.birutekno.aiwa.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.DataJadwal;
import com.birutekno.aiwa.model.Jadwal;
import com.birutekno.aiwa.model.Paket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ItineraryAiwaAdapter extends RecyclerView.Adapter<ItineraryAiwaAdapter.ViewHolder>  implements Filterable {

    private final Context context;
    private ArrayList<DataJadwal> data;
    private ArrayList<DataJadwal> mFilterData;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    String berangkatDetail;
    String pulangDetail;
    String namaPaket;
    String kamar;
    String harga;
    String itinerary;

    LinearLayout download;
//    LinearLayout share;

    public ItineraryAiwaAdapter(ArrayList<DataJadwal> data, Context context) {
        this.data = data;
        mFilterData = data;
        this.context = context;
    }

    @Override
    public ItineraryAiwaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_itinerary, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItineraryAiwaAdapter.ViewHolder viewHolder, int i) {
        jadwal = new ArrayList<>(Arrays.asList(mFilterData.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(" + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(" + jadwal.get(0).getJam_pulang() + ")" ;
        viewHolder.itineraryVH = jadwal.get(0).getItinerary();

        viewHolder.berangkat.setText(convertDate(jadwal.get(0).getTgl_berangkat()));
        viewHolder.pulang.setText(convertDate(jadwal.get(0).getTgl_pulang()));
        viewHolder.detailBerangkat.setText(berangkatDetail);
        viewHolder.detailPulang.setText(pulangDetail);
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

                    ArrayList<DataJadwal> filterData = new ArrayList<>();

                    for (DataJadwal data: data) {
                        jadwal = new ArrayList<>(Arrays.asList(data.getJadwal()));

                        if (convertDate(jadwal.get(0).getTgl_berangkat()).toLowerCase().contains(charString) || jadwal.get(0).getRute_berangkat().toLowerCase().contains(charString) || (jadwal.get(0).getJml_hari() + " Hari.").toLowerCase().contains(charString)) {
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
                mFilterData = (ArrayList<DataJadwal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang;
        String itineraryVH;
        public ViewHolder(View view) {
            super(view);

            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            download = (LinearLayout)view.findViewById(R.id.download);
//            share = (LinearLayout)view.findViewById(R.id.share);

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Memuat data...", Toast.LENGTH_SHORT).show();

                    String url = itineraryVH;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

//            share.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                    shareIntent.setType("text/plain");
////                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Berikut Link download untuk itinerary keberangkatan " + berangkat + " " + detailBerangkat + " dan kepulangan " + pulang + " " + pulangDetail + "URL : " +itinerary);
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, itinerary);
//                    context.startActivity(Intent.createChooser(shareIntent,"Share with"));
//                }
//            });

        }
    }

    public String convertDate(String args) {
        String date = args;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM yyyy");
        String newDateString = spf.format(newDate);

        return newDateString ;
    }

}

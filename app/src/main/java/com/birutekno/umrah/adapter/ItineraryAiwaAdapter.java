package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Data;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;

import java.util.ArrayList;
import java.util.Arrays;

public class ItineraryAiwaAdapter extends RecyclerView.Adapter<ItineraryAiwaAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<Data> data;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    String berangkatDetail;
    String pulangDetail;
    String namaPaket;
    String kamar;
    String harga;
    String itinerary;

    LinearLayout download;
    LinearLayout share;

    public ItineraryAiwaAdapter(ArrayList<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ItineraryAiwaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_itinerary, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItineraryAiwaAdapter.ViewHolder viewHolder, int i) {
        jadwal = new ArrayList<>(Arrays.asList(data.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(" + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(" + jadwal.get(0).getJam_pulang() + ")" ;
        itinerary = jadwal.get(0).getItinerary();

        viewHolder.berangkat.setText(jadwal.get(0).getTgl_berangkat());
        viewHolder.pulang.setText(jadwal.get(0).getTgl_pulang());
        viewHolder.detailBerangkat.setText(berangkatDetail);
        viewHolder.detailPulang.setText(pulangDetail);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang;
        public ViewHolder(View view) {
            super(view);

            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            download = (LinearLayout)view.findViewById(R.id.download);
            share = (LinearLayout)view.findViewById(R.id.share);

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = itinerary;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
//                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Berikut Link download untuk itinerary keberangkatan " + berangkat + " " + detailBerangkat + " dan kepulangan " + pulang + " " + pulangDetail + "URL : " +itinerary);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, itinerary);
                    context.startActivity(Intent.createChooser(shareIntent,"Share with"));
                }
            });

        }
    }

}

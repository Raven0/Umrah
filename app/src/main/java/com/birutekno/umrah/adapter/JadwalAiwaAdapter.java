package com.birutekno.umrah.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Data;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class JadwalAiwaAdapter extends RecyclerView.Adapter<JadwalAiwaAdapter.ViewHolder> {
    private final Context context;

    private ArrayList<Data> data;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    private PaketAiwaAdapter adapter;

    String berangkatDetail;
    String pulangDetail;

    public JadwalAiwaAdapter(ArrayList<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public JadwalAiwaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_jadwal, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalAiwaAdapter.ViewHolder viewHolder, int i) {
        jadwal = new ArrayList<>(Arrays.asList(data.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(" + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(" + jadwal.get(0).getJam_pulang() + ")" ;

        viewHolder.berangkat.setText(jadwal.get(0).getTgl_berangkat());
        viewHolder.pulang.setText(jadwal.get(0).getTgl_pulang());
        viewHolder.detailBerangkat.setText(berangkatDetail);
        viewHolder.detailPulang.setText(pulangDetail);
        viewHolder.paketHari.setText( "Paket " + jadwal.get(0).getJml_hari() + " Hari.");

        viewHolder.paketList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        viewHolder.paketList.setLayoutManager(layoutManager);

        adapter = new PaketAiwaAdapter(paket, context);
        viewHolder.paketList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang,paketHari;
        private RecyclerView paketList;
        public ViewHolder(View view) {
            super(view);

            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            paketHari = (TextView)view.findViewById(R.id.paket);
            paketList = (RecyclerView)view.findViewById(R.id.listPaket);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpandableLayout expandableLayout = (ExpandableLayout) v.findViewById(R.id.expandable_layout);
                    if (expandableLayout.isExpanded()) {
                        expandableLayout.collapse();
                    } else {
                        expandableLayout.expand();
                    }
                }
            });

        }
    }

}

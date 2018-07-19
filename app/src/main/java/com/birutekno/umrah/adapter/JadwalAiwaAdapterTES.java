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

public class JadwalAiwaAdapterTES extends RecyclerView.Adapter<JadwalAiwaAdapterTES.ViewHolder> {
    private final Context context;

    private ArrayList<Data> data;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    private PaketAiwaAdapterBACKUP adapter;

    String berangkatDetail;
    String pulangDetail;
    String tglBerangkat;
    String tglPulang;
    String maskapai;
    String paketHari;
    String linkItinerary;

    public JadwalAiwaAdapterTES(ArrayList<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public JadwalAiwaAdapterTES.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_jadwalb, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalAiwaAdapterTES.ViewHolder viewHolder, int i) {
        //initialize Array List
        jadwal = new ArrayList<>(Arrays.asList(data.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        //initialize String to create a sentence from combined attribute
        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(Pukul " + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(Pukul" + jadwal.get(0).getJam_pulang() + ")" ;

        //assign attribute value into textView in recyclerview
        viewHolder.berangkat.setText(jadwal.get(0).getTgl_berangkat());
        viewHolder.pulang.setText(jadwal.get(0).getTgl_pulang());
        viewHolder.detailBerangkat.setText(jadwal.get(0).getRute_berangkat());
        viewHolder.detailPulang.setText(jadwal.get(0).getRute_pulang());
//        viewHolder.paketHari.setText( "Paket " + jadwal.get(0).getJml_hari() + " Hari.");
        viewHolder.paketHari.setText(jadwal.get(0).getId());

//        set adapter for paket list recyclerview
        adapter = new PaketAiwaAdapterBACKUP(jadwal, paket, context);
        if (adapter.getItemCount() != 0){
            viewHolder.paketList.setAdapter(adapter);
            viewHolder.emptyData.setVisibility(View.GONE);
        } else {
            viewHolder.emptyData.setVisibility(View.VISIBLE);
        }

        tglBerangkat = jadwal.get(0).getTgl_berangkat();
        tglPulang = jadwal.get(0).getTgl_pulang();
        maskapai = jadwal.get(0).getMaskapai();
        paketHari = jadwal.get(0).getJml_hari() + " Hari";
        linkItinerary = jadwal.get(0).getItinerary();
//        manasik = jadwal.get(0).getTgl_manasik() + ", Pukul " + jadwal.get(0).getJam_manasik();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang,paketHari,emptyData;
        private RecyclerView paketList;
        private ExpandableLayout expandableLayout;
        public ViewHolder(View view) {
            super(view);

            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            paketHari = (TextView)view.findViewById(R.id.paket);
            emptyData = (TextView)view.findViewById(R.id.emptyData);
            paketList = (RecyclerView)view.findViewById(R.id.listPaket);
            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);

            //initialize view for paket list recyclerview
            paketList.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            paketList.setLayoutManager(layoutManager);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

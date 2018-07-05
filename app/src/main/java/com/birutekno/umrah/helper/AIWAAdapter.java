package com.birutekno.umrah.helper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class AIWAAdapter extends RecyclerView.Adapter<AIWAAdapter.ViewHolder> {
    private ArrayList<Data> data;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    public AIWAAdapter(ArrayList<Data> data) {
        this.data = data;
    }

    @Override
    public AIWAAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_jadwal, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AIWAAdapter.ViewHolder viewHolder, int i) {
        jadwal = new ArrayList<>(Arrays.asList(data.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        int paketSize = paket.size();

        String berangkatDetail;
        String pulangDetail;
        String namaPaket;
        String kamar;
        String harga;

        for (int a = 0; a < paketSize; a++){
            namaPaket = paket.get(a).getNama_paket();
            kamar = paket.get(a).getKamar();
            harga = paket.get(a).getHarga();

//            viewHolder.nama.setText(namaPaket);
//            viewHolder.kamar.setText(kamar);
//            viewHolder.harga.setText(harga);
        }

        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(" + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(" + jadwal.get(0).getJam_pulang() + ")" ;

        viewHolder.berangkat.setText(jadwal.get(0).getTgl_berangkat());
        viewHolder.pulang.setText(jadwal.get(0).getTgl_pulang());
        viewHolder.detailBerangkat.setText(berangkatDetail);
        viewHolder.detailPulang.setText(pulangDetail);
        viewHolder.paket.setText( "Paket " + jadwal.get(0).getJml_hari() + " Hari.");

//        for (int a = 0; a < data.size(); a++){
//            viewHolder.id.setText(jadwal.get(a).getId());
//        }
//        viewHolder.id.setText(data.get(i).getId());
//        viewHolder.berangkat.setText(data.get(i).getJam_berangkat());
//        viewHolder.pulang.setText(data.get(i).getTgl_pulang());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang,paket;
        public ViewHolder(View view) {
            super(view);

            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            paket = (TextView)view.findViewById(R.id.paket);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("ASD", String.valueOf(v.getId()));
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

package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        //initialize Array List
        jadwal = new ArrayList<>(Arrays.asList(data.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        //initialize String to create a sentence from combined attribute
//        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(" + jadwal.get(0).getJam_berangkat() + ")" ;
//        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(" + jadwal.get(0).getJam_pulang() + ")" ;

        //assign attribute value into textView in recyclerview
        viewHolder.berangkat.setText(jadwal.get(0).getTgl_berangkat());
        viewHolder.pulang.setText(jadwal.get(0).getTgl_pulang());
        viewHolder.detailBerangkat.setText(jadwal.get(0).getRute_berangkat());
        viewHolder.detailPulang.setText(jadwal.get(0).getRute_pulang());
        viewHolder.paketHari.setText( "Paket " + jadwal.get(0).getJml_hari() + " Hari.");

//        set adapter for paket list recyclerview
        adapter = new PaketAiwaAdapter(jadwal, paket, context);
        viewHolder.paketList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView berangkat,pulang,detailBerangkat,detailPulang,paketHari;
        private RecyclerView paketList;
        private Button shareBtn;
        public ViewHolder(View view) {
            super(view);

            berangkat = (TextView)view.findViewById(R.id.berangkat);
            pulang = (TextView)view.findViewById(R.id.pulang);
            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
            paketHari = (TextView)view.findViewById(R.id.paket);
            paketList = (RecyclerView)view.findViewById(R.id.listPaket);
            shareBtn = (Button)view.findViewById(R.id.shareDetail);

            //initialize view for paket list recyclerview
            paketList.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            paketList.setLayoutManager(layoutManager);

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

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String whatsAppMessage = "Keberangkatan = 2018-10-20 CGKJED, SV 817(Pukul 20.00 - 02.00)\n" +
                            "Kepulangan = 2018-10-28 JEDCGK, SV 818(Pukul 04.25 - 18.00)\n" +
                            "Maskapai = SAUDIA\n" +
                            "Paket = 9HARI\n" +
                            "Manasik = 2018-10-13, Pukul 08.30\n" +
                            "Link Itinerary = http://115.124.86.218/aiw/up/itinerary/20180418100330_ITINERARY_20_OKTOBER_2018_(JED_MEK).pdf\n" +
                            "\n" +
                            "=============================\n" +
                            "Harga : \n" +
                            "-Jenis Rahmah\n" +
                            "\tKamar Double = 31900000\n" +
                            "\tKamar Triple = 30600000\n" +
                            "\tKamar Quard = 29300000\n" +
                            "\tHotel Madinah = NOKHBA ROYAL INN\n" +
                            "\tHotel Mekkah = ZAM-ZAM TOWER\n" +
                            "-Jenis NUR\n" +
                            "\tKamar Double = 30300000\n" +
                            "\tKamar Triple = 29100000\n" +
                            "\tKamar Quard = 27900000\n" +
                            "\tHotel Madinah = NEW SURFAH\n" +
                            "\tHotel Mekkah = ZAM-ZAM TOWER\n" +
                            "-Jenis UHUD\n" +
                            "\tKamar Double = 24600000\n" +
                            "\tKamar Triple = 23500000\n" +
                            "\tKamar Quard = 22600000\n" +
                            "\tHotel Madinah = NEW SURFAH\n" +
                            "\tHotel Mekkah = ROYAL MAJESTIC\n" +
                            "\n" +
                            "*Harga diatas hanya simulasi";

                    Intent shareIntent = new Intent();
                    shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                    context.startActivity(Intent.createChooser(shareIntent,"Share with"));
                }
            });

        }
    }

}

package com.birutekno.umrah.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Data;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JadwalAiwaAdapterBACKUP extends RecyclerView.Adapter<JadwalAiwaAdapterBACKUP.ViewHolder> {
    private final Context context;

    private ArrayList<Data> data;
    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    String berangkatDetail;
    String pulangDetail;
    String tglBerangkat;
    String tglPulang;
    String maskapai;
    String paketHari;
    String linkItinerary;
    String manasik;

    HashMap<String, String> uhud = new HashMap<String, String>();
    HashMap<String, String> nur = new HashMap<String, String>();
    HashMap<String, String> rahmah = new HashMap<String, String>();

    public JadwalAiwaAdapterBACKUP(ArrayList<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public JadwalAiwaAdapterBACKUP.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_jadwalb, viewGroup, false);
        return new JadwalAiwaAdapterBACKUP.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalAiwaAdapterBACKUP.ViewHolder viewHolder, int i) {
        //initialize Array List
        jadwal = new ArrayList<>(Arrays.asList(data.get(i).getJadwal()));
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        //initialize String to create a sentence from combined attribute
        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(Pukul " + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(Pukul" + jadwal.get(0).getJam_pulang() + ")" ;

        tglBerangkat = jadwal.get(0).getTgl_berangkat();
        tglPulang = jadwal.get(0).getTgl_pulang();
        maskapai = jadwal.get(0).getMaskapai();
        paketHari = jadwal.get(0).getJml_hari() + " Hari";
        linkItinerary = jadwal.get(0).getItinerary();
        manasik = jadwal.get(0).getTgl_manasik() + ", Pukul " + jadwal.get(0).getJam_manasik();

        //assign attribute value into textView in recyclerview
//        viewHolder.berangkat.setText(tglBerangkat);
//        viewHolder.pulang.setText(tglPulang);
//        viewHolder.detailBerangkat.setText(jadwal.get(0).getRute_berangkat());
//        viewHolder.detailPulang.setText(jadwal.get(0).getRute_pulang());
//        viewHolder.paketHari.setText("Paket " + jadwal.get(0).getJml_hari() + " Hari.");
//        viewHolder.paketHari.setText(jadwal.get(0).getId());

//        String uhdMekah = "0", uhdMadinah = "0", uhdDobel = "0", uhdTripel = "0", uhdQuard = "0";
//        String nurMekah = "0", nurMadinah = "0", nurDobel = "0", nurTripel = "0", nurQuard = "0";
//        String rhmMekah = "0", rhmMadinah = "0", rhmDobel = "0", rhmTripel = "0", rhmQuard = "0";

        if (paket.isEmpty()){
//            viewHolder.uhdRow.setVisibility(View.GONE);
//            viewHolder.nurRow.setVisibility(View.GONE);
//            viewHolder.rhmRow.setVisibility(View.GONE);
//            viewHolder.emptyData.setVisibility(View.VISIBLE);
        }else {
            int paketSize = paket.size();
            for (int a = 0; a < paketSize; a++){

                String Jenis = paket.get(a).getNama_paket();
                String Kamar = paket.get(a).getKamar();
                String harga = paket.get(a).getHarga();
                String hotelMadinah = paket.get(a).getHotel_madinah();
                String hotelMekah = paket.get(a).getHotel_mekkah();

                if (Jenis.equals("UHUD")){
                    uhud.put("key",Jenis);
                    uhud.put("hotel_madinah", hotelMadinah);
                    uhud.put("hotel_mekkah", hotelMekah);
                    if (Kamar.equals("Double")){
                        uhud.put("kamar_double", Kamar);
                        uhud.put("harga_double", harga);
                    }else if(Kamar.equals("Triple")){
                        uhud.put("kamar_triple", Kamar);
                        uhud.put("harga_triple", harga);
                    }else {
                        uhud.put("kamar_quard", Kamar);
                        uhud.put("harga_quard", harga);
                    }
                }else if(Jenis.equals("NUR ")){
                    nur.put("key",Jenis);
                    nur.put("hotel_madinah", hotelMadinah);
                    nur.put("hotel_mekkah", hotelMekah);
                    if (Kamar.equals("Double")){
                        nur.put("kamar_double", Kamar);
                        nur.put("harga_double", harga);
                    }else if(Kamar.equals("Triple")){
                        nur.put("kamar_triple", Kamar);
                        nur.put("harga_triple", harga);
                    }else {
                        nur.put("kamar_quard", Kamar);
                        nur.put("harga_quard", harga);
                    }
                }else if (Jenis.equals("Rahmah")){
                    rahmah.put("key",Jenis);
                    rahmah.put("hotel_madinah", hotelMadinah);
                    rahmah.put("hotel_mekkah", hotelMekah);
                    if (Kamar.equals("Double")){
                        rahmah.put("kamar_double", Kamar);
                        rahmah.put("harga_double", harga);
                    }else if(Kamar.equals("Triple")){
                        rahmah.put("kamar_triple", Kamar);
                        rahmah.put("harga_triple", harga);
                    }else {
                        rahmah.put("kamar_quard", Kamar);
                        rahmah.put("harga_quard", harga);
                    }
                }
            }
        }

//        viewHolder.rhmDouble.setText(rahmah.get("harga_double"));
//        viewHolder.rhmTriple.setText(rahmah.get("harga_triple"));
//        viewHolder.rhmQuard.setText(rahmah.get("harga_quard"));
//        viewHolder.rhmMed.setText(rahmah.get("hotel_madinah"));
//        viewHolder.rhmMek.setText(rahmah.get("hotel_mekkah"));
//        viewHolder.rhmMan.setText(manasik);
//
//        viewHolder.nurDouble.setText(nur.get("harga_double"));
//        viewHolder.nurTriple.setText(nur.get("harga_triple"));
//        viewHolder.nurQuard.setText(nur.get("harga_quard"));
//        viewHolder.nurMed.setText(nur.get("hotel_madinah"));
//        viewHolder.nurMek.setText(nur.get("hotel_mekkah"));
//        viewHolder.nurMan.setText(manasik);
//
//        viewHolder.uhdDouble.setText(uhud.get("harga_double"));
//        viewHolder.uhdTriple.setText(uhud.get("harga_triple"));
//        viewHolder.uhdQuard.setText(uhud.get("harga_quard"));
//        viewHolder.uhdMed.setText(uhud.get("hotel_madinah"));
//        viewHolder.uhdMek.setText(uhud.get("hotel_mekkah"));
//        viewHolder.uhdMan.setText(manasik);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        private TextView berangkat,pulang,detailBerangkat,detailPulang,paketHari,emptyData;
//        private TextView nurDouble, nurTriple, nurQuard, nurMed, nurMek, nurMan, nur;
//        private TextView uhdDouble, uhdTriple, uhdQuard, uhdMed, uhdMek, uhdMan, uhd;
//        private TextView rhmDouble, rhmTriple, rhmQuard, rhmMed, rhmMek, rhmMan, rhm;
//        private TableRow nurRow, uhdRow, rhmRow;
        private Button shareBtn;
        private ExpandableLayout expandableLayout;
        public ViewHolder(View view) {
            super(view);

            shareBtn = (Button)view.findViewById(R.id.shareDetail);
            expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);

//            berangkat = (TextView)view.findViewById(R.id.berangkat);
//            pulang = (TextView)view.findViewById(R.id.pulang);
//            detailBerangkat = (TextView)view.findViewById(R.id.detailBerangkat);
//            detailPulang = (TextView)view.findViewById(R.id.detailPulang);
//            paketHari = (TextView)view.findViewById(R.id.paket);
//            emptyData = (TextView)view.findViewById(R.id.emptyData);
//
//            uhdRow = (TableRow) view.findViewById(R.id.uhudRow);
//            uhd = (TextView)view.findViewById(R.id.uhud);
//            uhdDouble = (TextView)view.findViewById(R.id.uhdDouble);
//            uhdTriple = (TextView)view.findViewById(R.id.uhdTriple);
//            uhdQuard = (TextView)view.findViewById(R.id.uhdQuard);
//            uhdMed = (TextView)view.findViewById(R.id.uhdMadinah);
//            uhdMek = (TextView)view.findViewById(R.id.uhdMekah);
//            uhdMan = (TextView)view.findViewById(R.id.uhdManasik);
//
//            nurRow = (TableRow) view.findViewById(R.id.nurRow);
//            nur = (TextView)view.findViewById(R.id.nur);
//            nurDouble = (TextView)view.findViewById(R.id.nurDouble);
//            nurTriple = (TextView)view.findViewById(R.id.nurTriple);
//            nurQuard = (TextView)view.findViewById(R.id.nurQuard);
//            nurMed = (TextView)view.findViewById(R.id.nurMadinah);
//            nurMek = (TextView)view.findViewById(R.id.nurMekah);
//            nurMan = (TextView)view.findViewById(R.id.nurManasik);
//
//            rhmRow = (TableRow) view.findViewById(R.id.rahmahRow);
//            rhm = (TextView)view.findViewById(R.id.rahmah);
//            rhmDouble = (TextView)view.findViewById(R.id.rhmDouble);
//            rhmTriple = (TextView)view.findViewById(R.id.rhmTriple);
//            rhmQuard = (TextView)view.findViewById(R.id.rhmQuard);
//            rhmMed = (TextView)view.findViewById(R.id.rhmMadinah);
//            rhmMek = (TextView)view.findViewById(R.id.rhmMekah);
//            rhmMan = (TextView)view.findViewById(R.id.rhmManasik);

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

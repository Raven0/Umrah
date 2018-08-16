package com.birutekno.umrah.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PaketAiwaAdapter extends RecyclerView.Adapter<PaketAiwaAdapter.ViewHolder> {
    private final Context context;

    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    HashMap<String, String> uhud = new HashMap<String, String>();
    HashMap<String, String> nur = new HashMap<String, String>();
    HashMap<String, String> rahmah = new HashMap<String, String>();
    HashMap<String, String> standar = new HashMap<String, String>();
    HashMap<String, String> map = new HashMap<String, String>();

    public PaketAiwaAdapter(ArrayList<Jadwal> jadwal, ArrayList<Paket> paket, Context context) {
        this.jadwal = jadwal;
        this.paket = paket;
        this.context = context;
    }

    @Override
    public PaketAiwaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_paketb, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaketAiwaAdapter.ViewHolder viewHolder, int i) {
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));
        int paketSize = paket.size();
        final String manasik = jadwal.get(0).getTgl_manasik();
        for (int a = 0; a < paketSize; a++){

            String Jenis = paket.get(a).getNama_paket();
            String Kamar = paket.get(a).getKamar();
            String harga = paket.get(a).getHarga();
            String hotelMadinah = paket.get(a).getHotel_madinah();
            String hotelMekah = paket.get(a).getHotel_mekkah();

            if (Jenis.equals("Standard")){
                standar.put("key",Jenis);
                map.put("standard",Jenis);
                standar.put("hotel_madinah", hotelMadinah);
                map.put("hotel_madinah_standard", hotelMadinah);
                standar.put("hotel_mekkah", hotelMekah);
                map.put("hotel_mekkah_standard", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_standard", harga);
                    standar.put("harga_double", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_standard", harga);
                    standar.put("harga_triple", harga);
                }else {
                    map.put("harga_quard_standard", harga);
                    standar.put("harga_quard", harga);
                }
            }else if (Jenis.equals("UHUD")){
                uhud.put("key",Jenis);
                map.put("uhud",Jenis);
                uhud.put("hotel_madinah", hotelMadinah);
                map.put("hotel_madinah_uhud", hotelMadinah);
                uhud.put("hotel_mekkah", hotelMekah);
                map.put("hotel_mekkah_uhud", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_uhud", harga);
                    uhud.put("harga_double", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_uhud", harga);
                    uhud.put("harga_triple", harga);
                }else {
                    map.put("harga_quard_uhud", harga);
                    uhud.put("harga_quard", harga);
                }
            }else if(Jenis.equals("NUR ")){
                nur.put("key",Jenis);
                map.put("nur",Jenis);
                nur.put("hotel_madinah", hotelMadinah);
                map.put("hotel_madinah_nur", hotelMadinah);
                nur.put("hotel_mekkah", hotelMekah);
                map.put("hotel_mekkah_nur", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_nur", harga);
                    nur.put("harga_double", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_nur", harga);
                    nur.put("harga_triple", harga);
                }else {
                    map.put("harga_quard_nur", harga);
                    nur.put("harga_quard", harga);
                }
            }else if (Jenis.equals("NUR")){
                nur.put("key",Jenis);
                map.put("nur",Jenis);
                nur.put("hotel_madinah", hotelMadinah);
                map.put("hotel_madinah_nur", hotelMadinah);
                nur.put("hotel_mekkah", hotelMekah);
                map.put("hotel_mekkah_nur", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_nur", harga);
                    nur.put("harga_double", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_nur", harga);
                    nur.put("harga_triple", harga);
                }else {
                    map.put("harga_quard_nur", harga);
                    nur.put("harga_quard", harga);
                }
            }else if (Jenis.equals("Rahmah")){
                rahmah.put("key",Jenis);
                map.put("rhm",Jenis);
                rahmah.put("hotel_madinah", hotelMadinah);
                map.put("hotel_madinah_rhm", hotelMadinah);
                rahmah.put("hotel_mekkah", hotelMekah);
                map.put("hotel_mekkah_rhm", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_rhm", harga);
                    rahmah.put("harga_double", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_rhm", harga);
                    rahmah.put("harga_triple", harga);
                }else {
                    map.put("harga_quard_rhm", harga);
                    rahmah.put("harga_quard", harga);
                }
            }else if (Jenis.equals("RAHMAH")){
                rahmah.put("key",Jenis);
                map.put("rhm",Jenis);
                rahmah.put("hotel_madinah", hotelMadinah);
                map.put("hotel_madinah_rhm", hotelMadinah);
                rahmah.put("hotel_mekkah", hotelMekah);
                map.put("hotel_mekkah_rhm", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_rhm", harga);
                    rahmah.put("harga_double", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_rhm", harga);
                    rahmah.put("harga_triple", harga);
                }else {
                    map.put("harga_quard_rhm", harga);
                    rahmah.put("harga_quard", harga);
                }
            }
        }

        viewHolder.rhmDouble.setText(map.get("harga_double_rhm"));
        viewHolder.rhmTriple.setText(map.get("harga_triple_rhm"));
        viewHolder.rhmQuard.setText(map.get("harga_quard_rhm"));
        viewHolder.rhmMed.setText(map.get("hotel_madinah_rhm"));
        viewHolder.rhmMek.setText(map.get("hotel_mekkah_rhm"));

        viewHolder.nurDouble.setText(map.get("harga_double_nur"));
        viewHolder.nurTriple.setText(map.get("harga_triple_nur"));
        viewHolder.nurQuard.setText(map.get("harga_quard_nur"));
        viewHolder.nurMed.setText(map.get("hotel_madinah_nur"));
        viewHolder.nurMek.setText(map.get("hotel_mekkah_nur"));
        viewHolder.nurMan.setText(manasik);

        viewHolder.uhdDouble.setText(map.get("harga_double_uhud"));
        viewHolder.uhdTriple.setText(map.get("harga_triple_uhud"));
        viewHolder.uhdQuard.setText(map.get("harga_quard_uhud"));
        viewHolder.uhdMed.setText(map.get("hotel_madinah_uhud"));
        viewHolder.uhdMek.setText(map.get("hotel_mekkah_uhud"));

        viewHolder.stdDouble.setText(map.get("harga_double_standard"));
        viewHolder.stdTriple.setText(map.get("harga_triple_standard"));
        viewHolder.stdQuard.setText(map.get("harga_quard_standard"));
        viewHolder.stdMed.setText(map.get("hotel_madinah_standard"));
        viewHolder.stdMek.setText(map.get("hotel_mekkah_standard"));

        final String berangkatDetail;
        final String pulangDetail;
        final String tglBerangkat;
        final String tglPulang;
        final String maskapai;
        final String paketHari;
        final String manasikShare;
        final String linkItinerary;

        berangkatDetail = jadwal.get(0).getRute_berangkat() + " , " + jadwal.get(0).getPesawat_berangkat() + "(Pukul " + jadwal.get(0).getJam_berangkat() + ")" ;
        pulangDetail = jadwal.get(0).getRute_pulang() + " , " + jadwal.get(0).getPesawat_pulang() + "(Pukul" + jadwal.get(0).getJam_pulang() + ")" ;
        tglBerangkat = jadwal.get(0).getTgl_berangkat();
        tglPulang = jadwal.get(0).getTgl_pulang();
        maskapai = jadwal.get(0).getMaskapai();
        paketHari = jadwal.get(0).getJml_hari() + " Hari";
        linkItinerary = jadwal.get(0).getItinerary();
        manasikShare = jadwal.get(0).getTgl_manasik() + ", Pukul " + jadwal.get(0).getJam_manasik();

        viewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Memuat data...", Toast.LENGTH_SHORT).show();

                    String whatsAppMessage = "Keberangkatan = "+tglBerangkat+" "+ berangkatDetail + "\n" +
                            "Kepulangan = "+tglPulang+" "+ pulangDetail + "\n" +
                            "Maskapai = "+maskapai+"\n" +
                            "Paket = "+paketHari+"\n" +
                            "Manasik = "+manasikShare+"\n" +
                            "Link Itinerary = "+ Uri.parse(linkItinerary) +"\n" +
                            "\n" +
                            "=============================\n" +
                            "Harga : \n" +
                            "-Jenis Rahmah\n" +
                            "\tKamar Double = "+map.get("harga_double_rhm")+"\n" +
                            "\tKamar Triple = "+map.get("harga_triple_rhm")+"\n" +
                            "\tKamar Quard = "+map.get("harga_quard_rhm")+"\n" +
                            "\tHotel Madinah = "+map.get("hotel_madinah_rhm")+"\n" +
                            "\tHotel Mekkah = "+map.get("hotel_mekkah_rhm")+"\n" +
                            "-Jenis NUR\n" +
                            "\tKamar Double = "+map.get("harga_double_nur")+"\n" +
                            "\tKamar Triple = "+map.get("harga_triple_nur")+"\n" +
                            "\tKamar Quard = "+map.get("harga_quard_nur")+"\n" +
                            "\tHotel Madinah = "+map.get("hotel_madinah_nur")+"\n" +
                            "\tHotel Mekkah = "+map.get("hotel_mekkah_nur")+"\n" +
                            "-Jenis UHUD\n" +
                            "\tKamar Double = "+map.get("harga_double_uhud")+"\n" +
                            "\tKamar Triple = "+map.get("harga_triple_uhud")+"\n" +
                            "\tKamar Quard = "+map.get("harga_quard_uhud")+"\n" +
                            "\tHotel Madinah = "+map.get("hotel_madinah_uhud")+"\n" +
                            "\tHotel Mekkah = "+map.get("hotel_mekkah_uhud")+"\n" +
                            "-Jenis STANDARD\n" +
                            "\tKamar Double = "+map.get("harga_double_standard")+"\n" +
                            "\tKamar Triple = "+map.get("harga_triple_standard")+"\n" +
                            "\tKamar Quard = "+map.get("harga_quard_standard")+"\n" +
                            "\tHotel Madinah = "+map.get("hotel_madinah_standard")+"\n" +
                            "\tHotel Mekkah = "+map.get("hotel_mekkah_standard")+"\n" +
                            "\n" +
                            "*Harga diatas bisa berubah sewaktu-waktu";

                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                    context.startActivity(Intent.createChooser(shareIntent,"Share with").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
        });

        viewHolder.shareItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linkItinerary;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView rhmDouble, rhmTriple, rhmQuard, rhmMed, rhmMek;
        private TextView nurDouble, nurTriple, nurQuard, nurMed, nurMek, nurMan;
        private TextView uhdDouble, uhdTriple, uhdQuard, uhdMed, uhdMek;
        private TextView stdDouble, stdTriple, stdQuard, stdMed, stdMek;
        private Button shareBtn, shareItinerary;
        public ViewHolder(View view) {
            super(view);

            rhmDouble = (TextView)view.findViewById(R.id.rhmDouble);
            rhmTriple = (TextView)view.findViewById(R.id.rhmTriple);
            rhmQuard = (TextView)view.findViewById(R.id.rhmQuard);
            rhmMed = (TextView)view.findViewById(R.id.rhmMadinah);
            rhmMek = (TextView)view.findViewById(R.id.rhmMekah);

            nurDouble = (TextView)view.findViewById(R.id.nurDouble);
            nurTriple = (TextView)view.findViewById(R.id.nurTriple);
            nurQuard = (TextView)view.findViewById(R.id.nurQuard);
            nurMed = (TextView)view.findViewById(R.id.nurMadinah);
            nurMek = (TextView)view.findViewById(R.id.nurMekah);
            nurMan = (TextView)view.findViewById(R.id.nurManasik);

            uhdDouble = (TextView)view.findViewById(R.id.uhdDouble);
            uhdTriple = (TextView)view.findViewById(R.id.uhdTriple);
            uhdQuard = (TextView)view.findViewById(R.id.uhdQuard);
            uhdMed = (TextView)view.findViewById(R.id.uhdMadinah);
            uhdMek = (TextView)view.findViewById(R.id.uhdMekah);

            stdDouble = (TextView)view.findViewById(R.id.stdDouble);
            stdTriple = (TextView)view.findViewById(R.id.stdTriple);
            stdQuard = (TextView)view.findViewById(R.id.stdQuard);
            stdMed = (TextView)view.findViewById(R.id.stdMadinah);
            stdMek = (TextView)view.findViewById(R.id.stdMekah);

            shareBtn = (Button)view.findViewById(R.id.shareDetail);
            shareItinerary = (Button)view.findViewById(R.id.shareItinerary);

        }
    }
}

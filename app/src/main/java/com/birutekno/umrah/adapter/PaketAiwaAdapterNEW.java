package com.birutekno.umrah.adapter;

import android.content.Context;
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

public class PaketAiwaAdapterNEW extends RecyclerView.Adapter<PaketAiwaAdapterNEW.ViewHolder> {
    private final Context context;

    private ArrayList<Jadwal> jadwal;
    private ArrayList<Paket> paket;

    String Jenis = "KOSONG";
    String Kamar = "KOSONG";
    String harga = "KOSONG";
    String hotelMadinah = "KOSONG";
    String hotelMekah = "KOSONG";
    String manasik = "KOSONG";
    Integer sizeReturn;

    public PaketAiwaAdapterNEW(ArrayList<Jadwal> jadwal, ArrayList<Paket> paket, Context context) {
        this.jadwal = jadwal;
        this.paket = paket;
        this.context = context;
    }

    @Override
    public PaketAiwaAdapterNEW.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_paket, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaketAiwaAdapterNEW.ViewHolder viewHolder, int i) {
        paket = new ArrayList<>(Arrays.asList(jadwal.get(0).getPaket()));

        manasik = jadwal.get(0).getTgl_manasik();

        Jenis = paket.get(i).getNama_paket();
        Kamar = paket.get(i).getKamar();
        harga = paket.get(i).getHarga();
        hotelMadinah = paket.get(i).getHotel_madinah();
        hotelMekah = paket.get(i).getHotel_mekkah();
        sizeReturn = paket.size();
//        for (int a = 0; a < paket.size(); a++){
//            Jenis = paket.get(a).getNama_paket();
//            Kamar = paket.get(a).getKamar();
//            harga = paket.get(a).getHarga();
//            hotelMadinah = paket.get(a).getHotel_madinah();
//            hotelMekah = paket.get(a).getHotel_mekkah();
//            sizeReturn = paket.size();
//        }

        viewHolder.jenis.setText(Jenis);
        viewHolder.kamar.setText(Kamar);
        viewHolder.harga.setText(harga);
        viewHolder.hotelMadinah.setText(hotelMadinah);
        viewHolder.hotelMekah.setText(hotelMekah);
        viewHolder.manasik.setText(manasik);
    }

    @Override
    public int getItemCount() {
        return paket.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView jenis,harga,hotelMadinah,hotelMekah,manasik,kamar;
        private Button btnShare;
        public ViewHolder(View view) {
            super(view);

            jenis = (TextView)view.findViewById(R.id.jenis);
            kamar = (TextView)view.findViewById(R.id.kamar);
            harga = (TextView)view.findViewById(R.id.hargaAja);
            hotelMadinah = (TextView)view.findViewById(R.id.hotelMadinah);
            hotelMekah = (TextView)view.findViewById(R.id.hotelMekah);
            manasik = (TextView)view.findViewById(R.id.manasik);
            btnShare = (Button)view.findViewById(R.id.shareDetail);

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "asd", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}

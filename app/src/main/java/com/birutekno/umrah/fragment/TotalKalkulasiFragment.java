package com.birutekno.umrah.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalKalkulasiFragment extends Fragment {

    private View view;
    private Button shareBtn;

//    DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");

    private LinearLayout quardView;
    private LinearLayout tripleView;
    private LinearLayout doubleView;

    private Boolean quardBool;
    private Boolean tripleBool;
    private Boolean doubleBool;

    private TextView maskapai;
    private TextView landing;
    private TextView nopesawat;
    private TextView pukul;
    private TextView mekah;
    private TextView madinah;

    //    QUARD
    private TextView pakethotel;
    private TextView hargaKamar;
    private TextView totalHitungan;
    private TextView jumlahOrang;
    private TextView hasilAkhir;

    //    TRIPLE
    private TextView pakethotelTriple;
    private TextView hargaKamarTriple;
    private TextView totalHitunganTriple;
    private TextView jumlahOrangTriple;
    private TextView hasilAkhirTriple;

    //    DOUBLE
    private TextView pakethotelDouble;
    private TextView hargaKamarDouble;
    private TextView totalHitunganDouble;
    private TextView jumlahOrangDouble;
    private TextView hasilAkhirDouble;

    private TextView diskon;
    private TextView visa;
    private TextView total;

    private String jenisPaket;

    private int hargaDouble;
    private int hargaTriple;
    private int hargaQuard;

    private int jmlDobel = 0;
    private int jmlTripel = 0;
    private int jmlQuard = 0;
    private int jmlProgresif = 0;
    private int jmlDiskon = 0;

    private int hitunganDouble = 0;
    private int hitunganTriple = 0;
    private int hitunganQuard = 0;

    private int hitunganKotor = 0;
    private int hitunganFinal = 0;

    String head = "";
    String contentQuard = "";
    String contentTriple = "";
    String contentDouble = "";
    String footer = "";
    String whatsapp = "";

    String passport = "";
    String meningitis = "";
    String foto = "";
    String nikah = "";
    String akta = "";

    public TotalKalkulasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_total_kalkulasi, container, false);
        loadComponent();

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, whatsapp);
                startActivity(Intent.createChooser(shareIntent,"Share with"));
            }
        });

        return view;
    }

    private void loadComponent() {
        quardView = (LinearLayout) view.findViewById(R.id.quard);
        tripleView = (LinearLayout) view.findViewById(R.id.triple);
        doubleView = (LinearLayout) view.findViewById(R.id.dobel);

        maskapai = (TextView) view.findViewById(R.id.maskapai);
        landing = (TextView) view.findViewById(R.id.landing);
        nopesawat = (TextView) view.findViewById(R.id.pesawatBerangkat);
        pukul = (TextView) view.findViewById(R.id.jamBerangkat);
        mekah = (TextView) view.findViewById(R.id.hotelMekah);
        madinah = (TextView) view.findViewById(R.id.hotelMadinah);

        pakethotel = (TextView) view.findViewById(R.id.paketHotel);
        hargaKamar = (TextView) view.findViewById(R.id.hargaKamar);
        totalHitungan = (TextView) view.findViewById(R.id.totalHitungan);
        jumlahOrang = (TextView) view.findViewById(R.id.jumlahOrang);
        hasilAkhir = (TextView) view.findViewById(R.id.hasilAkhir);

        pakethotelTriple = (TextView) view.findViewById(R.id.paketHotelTriple);
        hargaKamarTriple = (TextView) view.findViewById(R.id.hargaKamarTriple);
        totalHitunganTriple = (TextView) view.findViewById(R.id.totalHitunganTriple);
        jumlahOrangTriple = (TextView) view.findViewById(R.id.jumlahOrangTriple);
        hasilAkhirTriple = (TextView) view.findViewById(R.id.hasilAkhirTriple);

        pakethotelDouble = (TextView) view.findViewById(R.id.paketHotelDouble);
        hargaKamarDouble = (TextView) view.findViewById(R.id.hargaKamarDouble);
        totalHitunganDouble = (TextView) view.findViewById(R.id.totalHitunganDouble);
        jumlahOrangDouble = (TextView) view.findViewById(R.id.jumlahOrangDouble);
        hasilAkhirDouble = (TextView) view.findViewById(R.id.hasilAkhirDouble);

        diskon = (TextView) view.findViewById(R.id.nominalDiskon);
        visa = (TextView) view.findViewById(R.id.nominalVisa);
        total = (TextView) view.findViewById(R.id.nominalTotal);
        shareBtn = (Button) view.findViewById(R.id.shareBtn);

        Bundle bundle = getArguments();
        maskapai.setText(bundle.getString("maskapai"));
        landing.setText(bundle.getString("landing"));
        nopesawat.setText(bundle.getString("pesawat"));
        pukul.setText(bundle.getString("pukul"));
        mekah.setText(bundle.getString("mekah"));
        madinah.setText(bundle.getString("madinah"));

        doubleBool = bundle.getBoolean("boolDouble");
        tripleBool = bundle.getBoolean("boolTriple");
        quardBool = bundle.getBoolean("boolQuard");
        jenisPaket = bundle.getString("jenisPaket");

        hargaDouble = bundle.getInt("hargaDouble");
        hargaTriple = bundle.getInt("hargaTriple");
        hargaQuard = bundle.getInt("hargaQuard");

        jmlDobel = bundle.getInt("jmlDobel");
        jmlTripel = bundle.getInt("jmlTripel");
        jmlQuard = bundle.getInt("jmlQuard");
        jmlProgresif = bundle.getInt("visa");
        jmlDiskon = bundle.getInt("diskon");

        if (bundle.getBoolean("passport")){
            passport = "passport";
            Toast.makeText(getContext(), passport, Toast.LENGTH_SHORT).show();
        }

        if (bundle.getBoolean("meningitis")){
            meningitis = "meningitis";
        }

        if (bundle.getBoolean("foto")){
            foto = "foto";
        }

        if (bundle.getBoolean("nikah")){
            nikah = "buku nikah";
        }

        if (bundle.getBoolean("akta")){
            akta = "akta";
        }

        visa.setText(String.valueOf(jmlProgresif));
        diskon.setText(String.valueOf(jmlDiskon));

        Toast.makeText(getContext(), String.valueOf(jmlDiskon), Toast.LENGTH_SHORT).show();

        if (doubleBool.equals(true)){
            doubleView.setVisibility(View.VISIBLE);
            pakethotelDouble.setText(jenisPaket + " DOUBLE");
            hargaKamarDouble.setText(String.valueOf(hargaDouble));
            totalHitunganDouble.setText(String.valueOf(hargaDouble + 1200000));
            jumlahOrangDouble.setText(String.valueOf(jmlDobel));

            hitunganDouble = (hargaDouble + 1200000) * jmlDobel;

            hasilAkhirDouble.setText(String.valueOf(hitunganDouble));

            contentDouble = jenisPaket + " DOUBLE\n" +
                    "Rp. "+ hargaDouble +" (Hrg Paket / orang)\n" +
                    "Rp.   1.200.000 (Perlengkapan & Asuransi)\n" +
                    "---------- +\n" +
                    "Rp."+ String.valueOf(hargaDouble + 1200000 ) +"\n" +
                    "---------- × "+ jmlDobel +" orang\n" +
                    "Rp. "+ hitunganDouble +"\n" +
                    "\n";
        }else {
            doubleView.setVisibility(View.GONE);
        }

        if (tripleBool.equals(true)){
            tripleView.setVisibility(View.VISIBLE);
            pakethotelTriple.setText(jenisPaket + " TRIPLE");
            hargaKamarTriple.setText(String.valueOf(hargaTriple));
            totalHitunganTriple.setText(String.valueOf(hargaTriple + 1200000));
            jumlahOrangTriple.setText(String.valueOf(jmlTripel));

            hitunganTriple = (hargaTriple + 1200000) * jmlTripel;

            hasilAkhirTriple.setText(String.valueOf(hitunganTriple));

            contentTriple = jenisPaket + " TRIPLE\n" +
                    "Rp. "+ hargaTriple +" (Hrg Paket / orang)\n" +
                    "Rp.   1.200.000 (Perlengkapan & Asuransi)\n" +
                    "---------- +\n" +
                    "Rp. "+ String.valueOf(hargaTriple + 1200000 ) +"\n" +
                    "---------- × "+ jmlTripel +" orang\n" +
                    "Rp. "+ hitunganTriple +"\n" +
                    "\n";
        }else {
            tripleView.setVisibility(View.GONE);
        }

        if (quardBool.equals(true)){
            quardView.setVisibility(View.VISIBLE);
            pakethotel.setText(jenisPaket + " QUARD");
            hargaKamar.setText(String.valueOf(hargaQuard));
            totalHitungan.setText(String.valueOf(hargaQuard + 1200000));
            jumlahOrang.setText(String.valueOf(jmlQuard));

            hitunganQuard = (hargaQuard + 1200000) * jmlQuard;

            hasilAkhir.setText(String.valueOf(hitunganQuard));

            contentQuard = jenisPaket + " QUARD\n" +
                    "Rp. "+ hargaQuard +"  (Hrg Paket / orang)\n" +
                    "Rp. 1.200.000  (Biaya Perlengkapan & Asuransi)\n" +
                    "---------- +\n" +
                    "Rp. "+ String.valueOf(hargaQuard + 1200000 )+" \n" +
                    "---------- × "+ jmlQuard +" orang\n" +
                    "Rp."+ hitunganQuard +"\n" +
                    "\n";
        }else {
            quardView.setVisibility(View.GONE);
        }

        hitunganKotor = hitunganDouble + hitunganTriple + hitunganQuard;
        hitunganFinal = hitunganKotor + jmlProgresif - jmlDiskon;

        total.setText(String.valueOf(hitunganFinal));

        head = "Pesawat " + bundle.getString("maskapai") + "\n" +
                "Landing "+ bundle.getString("landing") +"\n" +
                "No Pesawat : "+ bundle.getString("pesawat") +"\n" +
                "Pukul : "+ bundle.getString("pukul") +"\n" +
                "Hotel di Mekah : "+ bundle.getString("mekah") +"\n" +
                "Hotel di Madinah : "+ bundle.getString("madinah") +"\n" +
                "\n";

        footer = "Total = Rp. "+ hitunganKotor +" (sebelum diskon)\n" +
                "\n" +
                "DISKON = "+ jmlDiskon +"\n" +
                "\n" +
                "VISA = "+ jmlProgresif +"\n" +
                "\n" +
                "Mohon Untuk Melengkapi Dokumen Ini \n"+ passport +" \n "+ meningitis +" \n "+ foto +" \n "+ nikah +" \n "+ akta +"\n" +
                "\n" +
                "TOTAL BIAYA UMROH = Rp. "+ hitunganFinal +"\n";

        whatsapp = head + contentQuard + contentTriple + contentDouble + footer;
    }

}

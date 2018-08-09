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

import com.birutekno.umrah.R;
import com.birutekno.umrah.model.DataKalkulasi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalKalkulasiFragment extends Fragment {

    ArrayList<DataKalkulasi> pojo;

    private View view;
    private Button shareBtn;

    private LinearLayout quardView;
    private LinearLayout tripleView;
    private LinearLayout doubleView;
    private LinearLayout infantView;
    private LinearLayout visaView;
    private LinearLayout diskonView;

    private Boolean quardBool;
    private Boolean tripleBool;
    private Boolean doubleBool;

    private TextView berangkatTanggal;
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
    private TextView perlengkapanQuard;

    //    TRIPLE
    private TextView pakethotelTriple;
    private TextView hargaKamarTriple;
    private TextView totalHitunganTriple;
    private TextView jumlahOrangTriple;
    private TextView hasilAkhirTriple;
    private TextView perlengkapanTriple;

    //    DOUBLE
    private TextView pakethotelDouble;
    private TextView hargaKamarDouble;
    private TextView totalHitunganDouble;
    private TextView jumlahOrangDouble;
    private TextView hasilAkhirDouble;
    private TextView perlengkapanDouble;

    //    BALITA
    private LinearLayout balitaView;
    private TextView hargaNormal;
    private TextView pakethotelBalita;
    private TextView perlengkapanBalita;
    private TextView penambahanBalita;
    private TextView diskonBalita;
    private TextView penguranganBalita;
    private TextView jmlBalitatext;
    private TextView hasilAkhirBalita;

    private TextView diskon;
    private TextView visa;
    private TextView jmlOrangVisa;
    private TextView totalVisa;
    private TextView infant;
    private TextView jmlInfants;
    private TextView totalInfant;
    private TextView total;
    private TextView textKet;

    private String jenisPaket;
//    private String jenisPaketBalita;
    private String jenisPerlengkapan;
    private String jenisPerlengkapanDewasa;

    String jml = "null";

    private int hargaDouble;
    private int hargaTriple;
    private int hargaQuard;
    private int hargaDefault = 0;
    private int hargaPromo = 0;
    private int hargaInfant = 0;
    private int hargaFull = 0;
    private int hargaLite = 0;
    private int diskonBalitaUhud = 0;
    private int diskonBalitaNur = 0;
    private int diskonBalitaRahmah = 0;
    private int diskonBalitaStandar = 0;

    private int jmlDobel = 0;
    private int jmlTripel = 0;
    private int jmlQuard = 0;
    private int jmlProgresif = 0;
    private int jmlProgresifOrang = 0;
    private int jmlDiskon = 0;
    private int jmlInfant = 0;
    private int jmlBalita = 0;
    private int jmlBalitaKasur = 0;

    private int hitunganDouble = 0;
    private int hitunganTriple = 0;
    private int hitunganQuard = 0;
    private int hitunganVisa = 0;
    private int hitunganInfant = 0;
    private int hitunganBalita = 0;

    private int hitunganKotor = 0;
    private int hitunganFinal = 0;

    String head = "";
    String contentQuard = "";
    String contentTriple = "";
    String contentDouble = "";
    String contentBalita = "";
    String contentInfant = "";
    String contentVisa = "";
    String footer = "";
    String keterangan = "";
    String whatsapp = "";

    String passport = null;
    String meningitis = null;
    String foto = null;
    String nikah = null;
    String akta = null;

    public TotalKalkulasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_total_kalkulasi, container, false);
        loadComponent();
        loadBundle();
        calculate();

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, whatsapp);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(shareIntent,"Share with"));
            }
        });

        return view;
    }

    private void loadComponent() {
        quardView = (LinearLayout) view.findViewById(R.id.quard);
        tripleView = (LinearLayout) view.findViewById(R.id.triple);
        doubleView = (LinearLayout) view.findViewById(R.id.dobel);
        infantView = (LinearLayout) view.findViewById(R.id.infantView);
        visaView = (LinearLayout) view.findViewById(R.id.viewVisa);
        balitaView = (LinearLayout) view.findViewById(R.id.viewBalita);
        diskonView = (LinearLayout) view.findViewById(R.id.viewDiskon);

        berangkatTanggal = (TextView) view.findViewById(R.id.berangkatTanggal);
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
        perlengkapanQuard = (TextView) view.findViewById(R.id.perlengkapanQuard);

        pakethotelTriple = (TextView) view.findViewById(R.id.paketHotelTriple);
        hargaKamarTriple = (TextView) view.findViewById(R.id.hargaKamarTriple);
        totalHitunganTriple = (TextView) view.findViewById(R.id.totalHitunganTriple);
        jumlahOrangTriple = (TextView) view.findViewById(R.id.jumlahOrangTriple);
        hasilAkhirTriple = (TextView) view.findViewById(R.id.hasilAkhirTriple);
        perlengkapanTriple = (TextView) view.findViewById(R.id.perlengkapanTriple);

        pakethotelDouble = (TextView) view.findViewById(R.id.paketHotelDouble);
        hargaKamarDouble = (TextView) view.findViewById(R.id.hargaKamarDouble);
        totalHitunganDouble = (TextView) view.findViewById(R.id.totalHitunganDouble);
        jumlahOrangDouble = (TextView) view.findViewById(R.id.jumlahOrangDouble);
        hasilAkhirDouble = (TextView) view.findViewById(R.id.hasilAkhirDouble);
        perlengkapanDouble = (TextView) view.findViewById(R.id.perlengkapanDouble);

        hargaNormal = (TextView) view.findViewById(R.id.hargaNormal);
        pakethotelBalita = (TextView) view.findViewById(R.id.paketHotelBalita);
        perlengkapanBalita = (TextView) view.findViewById(R.id.perlengkapanBalita);
        penambahanBalita = (TextView) view.findViewById(R.id.penambahanBalita);
        diskonBalita = (TextView) view.findViewById(R.id.diskonBalita);
        penguranganBalita = (TextView) view.findViewById(R.id.penguranganBalita);
        jmlBalitatext = (TextView) view.findViewById(R.id.jumlahOrangBalita);
        hasilAkhirBalita = (TextView) view.findViewById(R.id.hasilAkhirBalita);

        diskon = (TextView) view.findViewById(R.id.nominalDiskon);
        visa = (TextView) view.findViewById(R.id.nominalVisa);
        jmlOrangVisa = (TextView) view.findViewById(R.id.jumlahOrangVisa);
        totalVisa = (TextView) view.findViewById(R.id.hasilVisa);
        infant = (TextView) view.findViewById(R.id.nominalInfant);
        jmlInfants = (TextView) view.findViewById(R.id.jumlahInfant);
        totalInfant = (TextView) view.findViewById(R.id.hasilInfant);
        total = (TextView) view.findViewById(R.id.nominalTotal);
        textKet = (TextView) view.findViewById(R.id.textKet);
        shareBtn = (Button) view.findViewById(R.id.shareBtn);
    }

    private void loadBundle(){
        //START BUNDLE
        Bundle bundle = getArguments();

        hargaDefault = bundle.getInt("hargaDefault");
        hargaPromo = bundle.getInt("hargaPromo");
        hargaInfant = bundle.getInt("hargaInfant");
        hargaFull = bundle.getInt("hargaFull");
        hargaLite = bundle.getInt("hargaLite");
        diskonBalitaUhud = bundle.getInt("diskonBalitaUhud");
        diskonBalitaNur = bundle.getInt("diskonBalitaNur");
        diskonBalitaRahmah = bundle.getInt("diskonBalitaRahmah");
        diskonBalitaStandar = bundle.getInt("diskonBalitaStandar");

        berangkatTanggal.setText(bundle.getString("berangkatTgl"));
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
        jenisPerlengkapan = bundle.getString("perlengkapan");
        jenisPerlengkapanDewasa = bundle.getString("perlengkapanDewasa");

        hargaDouble = bundle.getInt("hargaDouble");
        hargaTriple = bundle.getInt("hargaTriple");
        hargaQuard = bundle.getInt("hargaQuard");

        jmlDobel = bundle.getInt("jmlDobel");
        jmlTripel = bundle.getInt("jmlTripel");
        jmlQuard = bundle.getInt("jmlQuard");
        jmlProgresif = bundle.getInt("visa");
        jmlProgresifOrang = bundle.getInt("visa_jml");
        jmlDiskon = bundle.getInt("diskon");
        jmlInfant = bundle.getInt("jmlInfant");
        jmlBalita = bundle.getInt("jmlBalita");
        jmlBalitaKasur = bundle.getInt("jmlBalitaKasur");
        keterangan = bundle.getString("keterangan");
        textKet.setText(keterangan);

        if (bundle.getBoolean("passport")){
            passport = "ya";
        }else {
            passport = "tidak";
        }

        if (bundle.getBoolean("meningitis")){
            meningitis = "ya";
        }else {
            meningitis = "tidak";
        }

        if (bundle.getBoolean("foto")){
            foto = "ya";
        }else {
            foto = "tidak";
        }

        if (bundle.getBoolean("nikah")){
            nikah = "ya";
        }else {
            nikah = "tidak";
        }

        if (bundle.getBoolean("akta")){
            akta = "ya";
        }else {
            akta = "tidak";
        }
    }

    private void calculate(){
        //CALCULATE
        //VISA
        if (jmlProgresifOrang == 0){
            visaView.setVisibility(View.GONE);
            contentVisa = "";
        }else {
            visaView.setVisibility(View.VISIBLE);
            visa.setText("Rp. "+ numberFormat(String.valueOf(jmlProgresif)));
            jmlOrangVisa.setText("x "+String.valueOf(jmlProgresifOrang));
            hitunganVisa = jmlProgresif * jmlProgresifOrang;
            totalVisa.setText("Rp. "+numberFormat(String.valueOf(hitunganVisa)));

            contentVisa = "*PERHITUNGAN VISA*\n" +
                    "Rp. "+ numberFormat(String.valueOf(jmlProgresif)) +"  (Hrg Visa Progresif / Orang)\n" +
                    "______\n× "+ jmlProgresifOrang +" PAX\n" +
                    "Rp."+ numberFormat(String.valueOf(hitunganVisa)) +"\n" +
                    "\n\n";
        }

        //DOUBLE
        if (doubleBool.equals(true)){
            doubleView.setVisibility(View.VISIBLE);
            pakethotelDouble.setText(jenisPaket + " DOUBLE");
            hargaKamarDouble.setText("Rp. "+ numberFormat(String.valueOf(hargaDouble)));
            if (jenisPerlengkapanDewasa.equals("DEFAULT")){
                perlengkapanDouble.setText("Rp. "+numberFormat(String.valueOf(hargaDefault))+" (Perlengkapan & Asuransi)");
                totalHitunganDouble.setText("Rp. "+numberFormat(String.valueOf(hargaDouble + hargaDefault)));
                jumlahOrangDouble.setText("x "+String.valueOf(jmlDobel)+" PAX");

                hitunganDouble = (hargaDouble + hargaDefault) * jmlDobel;

                hasilAkhirDouble.setText("Rp. "+numberFormat(String.valueOf(hitunganDouble)));

                contentDouble ="*PERHITUNGAN JENIS DOUBLE*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaDouble)) + " " +jenisPaket + " DOUBLE\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaDefault))+" (Perlengkapan & Asuransi)\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(hargaDouble + hargaDefault )) +"\n" +
                        "______\n×"+ jmlDobel +" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganDouble)) +"\n" +
                        "\n\n";
            }else if (jenisPerlengkapanDewasa.equals("PROMO")){
                perlengkapanDouble.setText("Rp. "+numberFormat(String.valueOf(hargaPromo))+" (PROMO)");
                totalHitunganDouble.setText("Rp. "+numberFormat(String.valueOf(hargaDouble + hargaPromo)));
                jumlahOrangDouble.setText("x "+String.valueOf(jmlDobel)+" PAX");

                hitunganDouble = (hargaDouble + hargaPromo) * jmlDobel;

                hasilAkhirDouble.setText("Rp. "+numberFormat(String.valueOf(hitunganDouble)));

                contentDouble ="*PERHITUNGAN JENIS DOUBLE*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaDouble)) + " " +jenisPaket + " DOUBLE\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaPromo))+" (PROMO)\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(hargaDouble + hargaPromo )) +"\n" +
                        "______\n×"+ jmlDobel +" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganDouble)) +"\n" +
                        "\n\n";
            }
        }else {
            doubleView.setVisibility(View.GONE);
        }

        //TRIPLE
        if (tripleBool.equals(true)){
            tripleView.setVisibility(View.VISIBLE);
            pakethotelTriple.setText(jenisPaket + " TRIPLE");
            hargaKamarTriple.setText("Rp. "+numberFormat(String.valueOf(hargaTriple)));
            if (jenisPerlengkapanDewasa.equals("DEFAULT")){
                perlengkapanTriple.setText("Rp. "+numberFormat(String.valueOf(hargaDefault))+" (Perlengkapan & Asuransi)");
                totalHitunganTriple.setText("Rp. "+numberFormat(String.valueOf(hargaTriple + hargaDefault)));
                jumlahOrangTriple.setText("x "+String.valueOf(jmlTripel)+" PAX");

                hitunganTriple = (hargaTriple + hargaDefault) * jmlTripel;

                hasilAkhirTriple.setText("Rp. "+numberFormat(String.valueOf(hitunganTriple)));

                contentTriple ="*PERHITUNGAN JENIS TRIPLE*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaTriple)) + " " +jenisPaket + " TRIPLE\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaDefault))+" (Perlengkapan & Asuransi)\n" +
                        "______+\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaTriple + hargaDefault)) +"\n" +
                        "______\n× "+ jmlTripel +" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganTriple)) +"\n" +
                        "\n\n";
            }else if (jenisPerlengkapanDewasa.equals("PROMO")){
                perlengkapanTriple.setText("Rp. "+numberFormat(String.valueOf(hargaPromo))+" (PROMO)");
                totalHitunganTriple.setText("Rp. "+numberFormat(String.valueOf(hargaTriple + hargaPromo)));
                jumlahOrangTriple.setText("x "+String.valueOf(jmlTripel)+" PAX");

                hitunganTriple = (hargaTriple + hargaPromo) * jmlTripel;

                hasilAkhirTriple.setText("Rp. "+numberFormat(String.valueOf(hitunganTriple)));

                contentTriple ="*PERHITUNGAN JENIS TRIPLE*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaTriple)) + " " +jenisPaket + " TRIPLE\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaPromo))+" (PROMO)\n" +
                        "______+\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaTriple + hargaPromo)) +"\n" +
                        "______\n× "+ jmlTripel +" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganTriple)) +"\n" +
                        "\n\n";
            }
        }else {
            tripleView.setVisibility(View.GONE);
        }

        //QUARD
        if (quardBool.equals(true)){
            quardView.setVisibility(View.VISIBLE);
            pakethotel.setText(jenisPaket + " QUARD");
            hargaKamar.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            if (jenisPerlengkapanDewasa.equals("DEFAULT")){
                perlengkapanQuard.setText("Rp. "+numberFormat(String.valueOf(hargaDefault))+" (Perlengkapan & Asuransi)");
                totalHitungan.setText("Rp. "+numberFormat(String.valueOf(hargaQuard + hargaDefault)));
                jumlahOrang.setText("x "+String.valueOf(jmlQuard)+" PAX");

                hitunganQuard = (hargaQuard + hargaDefault) * jmlQuard;

                hasilAkhir.setText("Rp. "+numberFormat(String.valueOf(hitunganQuard)));

                contentQuard ="*PERHITUNGAN JENIS QUARD*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard ))+ " " +jenisPaket + " QUAD\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaDefault))+"  (Perlengkapan & Asuransi)\n" +
                        "______+\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard + hargaDefault ))+" \n" +
                        "______\n× "+ jmlQuard +" PAX\n" +
                        "Rp."+ numberFormat(String.valueOf(hitunganQuard)) +"\n" +
                        "\n\n";
            }else if (jenisPerlengkapanDewasa.equals("PROMO")){
                perlengkapanQuard.setText("Rp. "+numberFormat(String.valueOf(hargaPromo))+" (PROMO)");
                totalHitungan.setText("Rp. "+numberFormat(String.valueOf(hargaQuard + hargaPromo)));
                jumlahOrang.setText("x "+String.valueOf(jmlQuard)+" PAX");

                hitunganQuard = (hargaQuard + hargaPromo) * jmlQuard;

                hasilAkhir.setText("Rp. "+numberFormat(String.valueOf(hitunganQuard)));

                contentQuard ="*PERHITUNGAN JENIS QUARD*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard ))+ " " +jenisPaket + " QUAD\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaPromo))+"  (PROMO)\n" +
                        "______+\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard + hargaPromo ))+" \n" +
                        "______\n× "+ jmlQuard +" PAX\n" +
                        "Rp."+ numberFormat(String.valueOf(hitunganQuard)) +"\n" +
                        "\n\n";
            }
        }else {
            quardView.setVisibility(View.GONE);
        }

        //BALITA
        if (jenisPaket.equals("RAHMAH") && jmlBalita != 0){
            balitaView.setVisibility(View.VISIBLE);
            hargaNormal.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            pakethotelBalita.setText(String.valueOf(jenisPaket));
            if (jenisPerlengkapan.equals("FULL")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaFull))+ " Dengan Perlengkapan");
                int h = hargaQuard + hargaFull;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaRahmah)));
                int d = h - diskonBalitaRahmah;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA RAHMAH*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaFull))+" Dengan Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaRahmah)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";

            }else if(jenisPerlengkapan.equals("LITE")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaLite))+ " Tanpa Perlengkapan");
                int h = hargaQuard + hargaLite;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaRahmah)));
                int d = h - diskonBalitaRahmah;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+numberFormat(String.valueOf(jmlBalita)));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA RAHMAH*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaLite))+" Tanpa Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h))+"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaRahmah)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";
            }
        }else if (jenisPaket.equals("Rahmah") && jmlBalita != 0){
            balitaView.setVisibility(View.VISIBLE);
            hargaNormal.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            pakethotelBalita.setText(String.valueOf(jenisPaket));
            if (jenisPerlengkapan.equals("FULL")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaFull))+ " Dengan Perlengkapan");
                int h = hargaQuard + hargaFull;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaRahmah)));
                int d = h - diskonBalitaRahmah;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA RAHMAH*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+numberFormat(String.valueOf(hargaFull))+" Dengan Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaRahmah)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";

            }else if(jenisPerlengkapan.equals("LITE")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaLite))+ " Tanpa Perlengkapan");
                int h = hargaQuard + hargaLite;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaRahmah)));
                int d = h - diskonBalitaRahmah;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+numberFormat(String.valueOf(jmlBalita)));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA RAHMAH*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaLite))+" Tanpa Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h))+"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaRahmah)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";
            }
        }else if(jenisPaket.equals("NUR") && jmlBalita != 0) {
            balitaView.setVisibility(View.VISIBLE);
            hargaNormal.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            pakethotelBalita.setText(String.valueOf(jenisPaket));
            if (jenisPerlengkapan.equals("FULL")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaFull))+ " Dengan Perlengkapan");
                int h = hargaQuard + hargaFull;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaNur)));
                int d = h - diskonBalitaNur;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA NUR*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaFull))+" Dengan Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaNur)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";

            }else if(jenisPerlengkapan.equals("LITE")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaLite))+ " Tanpa Perlengkapan");
                int h = hargaQuard + hargaLite;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaNur)));
                int d = h - diskonBalitaNur;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA NUR*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaLite))+" Tanpa Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaNur)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";
            }
        }else if(jenisPaket.equals("NUR ") && jmlBalita != 0) {
            balitaView.setVisibility(View.VISIBLE);
            hargaNormal.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            pakethotelBalita.setText(String.valueOf(jenisPaket));
            if (jenisPerlengkapan.equals("FULL")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaFull))+ " Dengan Perlengkapan");
                int h = hargaQuard + hargaFull;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaNur)));
                int d = h - diskonBalitaNur;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA NUR*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaFull))+" Dengan Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaNur)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";

            }else if(jenisPerlengkapan.equals("LITE")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaLite))+ " Tanpa Perlengkapan");
                int h = hargaQuard + hargaLite;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaNur)));
                int d = h - diskonBalitaNur;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA NUR*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaLite))+" Tanpa Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaNur)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";
            }
        }else if(jenisPaket.equals("UHUD") && jmlBalita != 0) {
            balitaView.setVisibility(View.VISIBLE);
            hargaNormal.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            pakethotelBalita.setText(String.valueOf(jenisPaket));
            if (jenisPerlengkapan.equals("FULL")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaFull))+ " Dengan Perlengkapan");
                int h = hargaQuard + hargaFull;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaUhud)));
                int d = h - diskonBalitaUhud;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA UHUD*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaFull))+" Dengan Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaUhud)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";

            }else if(jenisPerlengkapan.equals("LITE")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaLite))+ " Tanpa Perlengkapan");
                int h = hargaQuard + hargaLite;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaUhud)));
                int d = h - diskonBalitaUhud;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA UHUD*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaLite))+" Tanpa Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaUhud)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";
            }
        }else if(jenisPaket.equals("Standard") && jmlBalita != 0) {
            balitaView.setVisibility(View.VISIBLE);
            hargaNormal.setText("Rp. "+numberFormat(String.valueOf(hargaQuard)));
            pakethotelBalita.setText(String.valueOf(jenisPaket));
            if (jenisPerlengkapan.equals("FULL")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaFull))+ " Dengan Perlengkapan");
                int h = hargaQuard + hargaFull;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaStandar)));
                int d = h - diskonBalitaStandar;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA STANDAR*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaFull))+" Dengan Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaStandar)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";

            }else if(jenisPerlengkapan.equals("LITE")){
                perlengkapanBalita.setText("Rp. "+numberFormat(String.valueOf(hargaLite))+ " Tanpa Perlengkapan");
                int h = hargaQuard + hargaLite;
                penambahanBalita.setText("Rp. "+numberFormat(String.valueOf(h)));
                diskonBalita.setText("Rp. "+numberFormat(String.valueOf(diskonBalitaStandar)));
                int d = h - diskonBalitaStandar;
                penguranganBalita.setText("Rp. "+numberFormat(String.valueOf(d)));
                jmlBalitatext.setText("x "+String.valueOf(jmlBalita));
                hitunganBalita = d * jmlBalita;
                hasilAkhirBalita.setText("Rp. "+numberFormat(String.valueOf(hitunganBalita)));

                contentBalita ="*PERHITUNGAN BALITA STANDAR*\n"+
                        "Rp. "+ numberFormat(String.valueOf(hargaQuard)) + " " +jenisPaket+ " QUAD\n" +
                        "Rp. "+ numberFormat(String.valueOf(hargaLite))+" Tanpa Perlengkapan\n" +
                        "______+\n" +
                        "Rp."+ numberFormat(String.valueOf(h)) +"\n" +
                        "Rp."+ numberFormat(String.valueOf(diskonBalitaStandar)) +"\n" +
                        "______-\n" +
                        "Rp."+ numberFormat(String.valueOf(d)) +"\n" +
                        "______\n×"+ jmlBalita+" PAX\n" +
                        "Rp. "+ numberFormat(String.valueOf(hitunganBalita)) +"\n" +
                        "\n\n";
            }
        }else {
            balitaView.setVisibility(View.GONE);
        }

        //INFANT
        if(String.valueOf(jmlInfant).equals("0")){
            infantView.setVisibility(View.GONE);
        }else {
            infantView.setVisibility(View.VISIBLE);
            infant.setText("Rp. "+numberFormat(String.valueOf(hargaInfant)));
            jmlInfants.setText("x "+String.valueOf(jmlInfant));
            hitunganInfant = hargaInfant * jmlInfant;
            totalInfant.setText("Rp. "+numberFormat(String.valueOf(hitunganInfant)));

            contentInfant = "*PERHITUNGAN INFANT*\n" +
                    "Rp. "+ numberFormat(String.valueOf(hargaInfant)) +"  (Hrg Paket / infant)\n" +
                    "______× "+ jmlInfant +" infant\n" +
                    "Rp."+ numberFormat(String.valueOf(hitunganInfant)) +"\n" +
                    "\n\n";
        }

        //CALCULATION
        hitunganKotor = hitunganDouble + hitunganTriple + hitunganQuard + hitunganVisa + hitunganInfant + hitunganBalita;
        hitunganFinal = hitunganKotor - jmlDiskon;

        total.setText(numberFormat(String.valueOf(hitunganFinal)));

        //SHARE
//        head =  "Tanggal Keberangkatan " + berangkatTanggal.getText().toString() + "\n" +
//                "Pesawat " + maskapai.getText().toString() + "\n" +
//                "Landing "+ landing.getText().toString() +"\n" +
//                "No Pesawat : "+ nopesawat.getText().toString()+"\n" +
//                "Pukul : "+ pukul.getText().toString()+"\n" +
//                "Hotel di Mekah : "+ mekah.getText().toString() +"\n" +
//                "Hotel di Madinah : "+ madinah.getText().toString() +"\n" +
//                "\n\n";

        head =  "*Rincian Kalkulasi Simulasi untuk tanggal "+ berangkatTanggal.getText().toString() +" Paket "+ jenisPaket +"*\n\n";

        if(jmlDiskon == 0){
            diskonView.setVisibility(View.GONE);
            footer ="Total Visa Progresif = Rp. "+ numberFormat(String.valueOf(hitunganVisa)) +"\n" +
                    "\n" +
                    "*TOTAL BIAYA UMROH  = Rp. "+ numberFormat(String.valueOf(hitunganFinal))+"*\n"+
                    "Mohon Untuk Melengkapi Dokumen Ini : \n"+
                    "Passport       : " + passport + ".\n"+
                    "Meningitis     : " + meningitis + ".\n"+
                    "Pas Foto       : " + foto + ".\n"+
                    "Buku Nikah     : " + nikah + ".\n"+
                    "FC Akta Lahir  : " + akta + ".\n"+
                    "\n\n";
        }else {
            diskonView.setVisibility(View.VISIBLE);
            diskon.setText("Rp. "+ numberFormat(String.valueOf(jmlDiskon)));

            footer ="Total Visa Progresif = Rp. "+ numberFormat(String.valueOf(hitunganVisa)) +"\n" +
                    "Total = Rp. "+ numberFormat(String.valueOf(hitunganKotor)) +"(sebelum diskon)\n" +
                    "Diskon = Rp. "+ numberFormat(String.valueOf(jmlDiskon)) +"\n" +
                    "\n" +
                    "*TOTAL BIAYA UMROH  = Rp. "+ numberFormat(String.valueOf(hitunganFinal))+"*\n"+
                    "Mohon Untuk Melengkapi Dokumen Ini : \n"+
                    "Passport       : " + passport + ".\n"+
                    "Meningitis     : " + meningitis + ".\n"+
                    "Pas Foto       : " + foto + ".\n"+
                    "Buku Nikah     : " + nikah + ".\n"+
                    "FC Akta Lahir  : " + akta + ".\n"+
                    "\n\n";
        }

        keterangan = "Keterangan Tambahan: \n"+ keterangan;

        whatsapp = head + contentQuard + contentTriple + contentDouble + contentBalita + contentInfant + contentVisa + footer + keterangan;
    }

    public String numberFormat(String args){
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(args);
        String formattedNumber = formatter.format(myNumber);
        return formattedNumber;
    }

}

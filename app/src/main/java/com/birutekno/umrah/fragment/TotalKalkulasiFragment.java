package com.birutekno.umrah.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.birutekno.umrah.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TotalKalkulasiFragment extends Fragment {

    private View view;
    private TextView textViewNamaLengkap;
    private TextView textViewAlamat;
    private TextView textViewNamaIbu;
    private TextView textViewNamaAyah;
    private Button shareBtn;
    private ProgressBar progressBar;

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
                String whatsAppMessage = "Pesawat Saudi Arabia\n" +
                        "Landing Madinah\n" +
                        "No Pesawat : SV825\n" +
                        "Pukul : 11:00 – 17:10\n" +
                        "Hotel di Mekah : SAFWA ROYAL ORCHID\n" +
                        "Hotel di Madinah : RAWDA ROYAL INN\n" +
                        "\n" +
                        "RAHMAH QUAD\n" +
                        "Rp.28.700.000  (Hrg Paket / orang)\n" +
                        "Rp. 1.200.000  (Biaya Perlengkapan & Asuransi)\n" +
                        "---------------------- +\n" +
                        "Rp.29.900.000 \n" +
                        "--------------------- × 4 orang\n" +
                        "Rp.119.600.000\n" +
                        "\n" +
                        "RAHMAH TRIPLE\n" +
                        "Rp. 30.000.000 (Hrg Paket / orang)\n" +
                        "Rp.   1.200.000 (Biaya Perlengkapan & Asuransi)\n" +
                        "----------------------- +\n" +
                        "Rp.31.200.000\n" +
                        "---------------------- × 3 orang\n" +
                        "Rp. 93.600.000\n" +
                        "\n" +
                        "Total = Rp.119.600.000 + Rp.93.600.000\n" +
                        "= Rp.213.200.000 (sebelum diskon)\n" +
                        "\n" +
                        "DISKON = 7 Juta \n" +
                        "\n" +
                        "TOTAL BIAYA UMROH = Rp. 206.200.000\n";

                Intent shareIntent = new Intent();
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                startActivity(Intent.createChooser(shareIntent,"Share with"));
            }
        });

        return view;
    }

    private void loadComponent() {
//        textViewNamaLengkap = (TextView) view.findViewById(R.id.text_view_nama_lengkap);
//        textViewAlamat = (TextView) view.findViewById(R.id.text_view_alamat);
//        textViewNamaIbu = (TextView) view.findViewById(R.id.text_view_nama_ibu);
//        textViewNamaAyah = (TextView) view.findViewById(R.id.text_view_nama_ayah);
//
//        Bundle bundle = getArguments();
//        textViewNamaLengkap.setText(bundle.getString("namaLengkap"));
//        textViewAlamat.setText(bundle.getString("alamat"));
//        textViewNamaIbu.setText(bundle.getString("namaIbu"));
//        textViewNamaAyah.setText(bundle.getString("namaAyah"));

        shareBtn = (Button) view.findViewById(R.id.shareBtn);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_loader);
//        progressBar.setVisibility(View.GONE);
    }

}

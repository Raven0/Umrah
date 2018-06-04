package com.birutekno.umrah.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.birutekno.umrah.InputKalkulasiActivity;
import com.birutekno.umrah.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormKalkulasiFragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText pic;
    private EditText maskapai;
    private EditText hari;
    private EditText sisa_seat;
    private EditText etJumlah;
    private TextView depart_date;
    private TextView arrive_date;
    private Button buttonNext;
    private int cjumlah = 1;
    private int jumlah;


    public FormKalkulasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_form_kalkulasi, container, false);
        loadComponent();
        return view;
    }

    private void loadComponent() {
        etJumlah = (EditText) view.findViewById(R.id.jumlah);
        pic = (EditText) view.findViewById(R.id.picName);
        depart_date= (TextView) view.findViewById(R.id.dateDeparture);

        buttonNext = (Button) view.findViewById(R.id.btnNext);
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonNext) {

            InputKalkulasiActivity.goToStepTotal();
//            String namaIbu = editTextNamaIbu.getText().toString().trim();
//            String namaAyah = editTextNamaAyah.getText().toString().trim();
            TotalKalkulasiFragment step3Fragment = new TotalKalkulasiFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("jumlah", jumlah);
//            bundle.putString("namaIbu", namaIbu.isEmpty() ? "-" : namaIbu);
//            bundle.putString("namaAyah", namaAyah.isEmpty() ? "-" : namaAyah);
            step3Fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                    .replace(R.id.frame_layout, step3Fragment)
                    .addToBackStack(null)
                    .commit();

//            if(cjumlah == jumlah){
//                InputActivity.goToStepUlasan();
////            String namaIbu = editTextNamaIbu.getText().toString().trim();
////            String namaAyah = editTextNamaAyah.getText().toString().trim();
//                Step3Fragment step3Fragment = new Step3Fragment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("jumlah", jumlah);
////            bundle.putString("namaIbu", namaIbu.isEmpty() ? "-" : namaIbu);
////            bundle.putString("namaAyah", namaAyah.isEmpty() ? "-" : namaAyah);
//                step3Fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
//                        .replace(R.id.frame_layout, step3Fragment)
//                        .addToBackStack(null)
//                        .commit();
//            }else {
////            String alamat = editTextAlamat.getText().toString().trim();
//                InputActivity.goToStepOrangTua();
//                Step2Fragment step2Fragment = new Step2Fragment();
//                Bundle bundle = new Bundle();
////            bundle.putString("jumlah", namaLengkap.isEmpty() ? "-" : namaLengkap);
//                bundle.putInt("jumlah", jumlah);
//                cjumlah++;
//                bundle.putInt("cjumlah", cjumlah);
////            bundle.putString("alamat", alamat.isEmpty() ? "-" : alamat);
//                step2Fragment.setArguments(bundle);
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
//                        .replace(R.id.frame_layout, step2Fragment)
//                        .addToBackStack(null)
//                        .commit();
//
//            }
        }
    }
}

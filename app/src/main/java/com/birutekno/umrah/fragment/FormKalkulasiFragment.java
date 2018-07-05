package com.birutekno.umrah.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.InputKalkulasiActivity;
import com.birutekno.umrah.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormKalkulasiFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    ArrayList<String> items =new ArrayList<>();

    private View view;
    private EditText pic;
    private EditText maskapai;
    private EditText hari;
    private EditText sisa_seat;
    private EditText etJumlah;
    private TextView depart_date;
    private TextView arrive_date;
    private Button buttonNext;
    private Button depart;
    private Button follow;
    private Spinner hotel, paket;
    private int departStatus = 0;
    private int followStatus = 0;
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

        depart = (Button) view.findViewById(R.id.dateDeparture);
        depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) FormKalkulasiFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih Periode");
                datePickerDialog.show(fm,"Date");
                departStatus = 1;
            }
        });

        follow = (Button) view.findViewById(R.id.dateFollow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) FormKalkulasiFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih Periode");
                datePickerDialog.show(fm,"Date");
                followStatus = 1;
            }
        });

        items.add("Pilih Hotel");
        items.add("RAHMAH");
        items.add("NUR");
        items.add("UHUD");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        hotel = (Spinner) view.findViewById(R.id.searchJenis);
        hotel.setAdapter(adapter);
        hotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case "Pilih Hotel":
                        break;
                    case "RAHMAH":
                        Toast.makeText(getContext(), "Anda memilih jenis RAHMAH", Toast.LENGTH_SHORT).show();
                        break;
                    case "NUR":
                        Toast.makeText(getContext(), "Anda memilih jenis NUR", Toast.LENGTH_SHORT).show();
                        break;
                    case "UHUD":
                        Toast.makeText(getContext(), "Anda memilih jenis UHUD", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(getContext(), String.format("You Selected : %d/%d/%d", dayOfMonth,monthOfYear,year), Toast.LENGTH_LONG).show();
        if(departStatus == 1){
            depart.setText(String.format("%d/%d/%d", dayOfMonth,monthOfYear,year));
            departStatus = 0;
        }else if(followStatus == 1){
            follow.setText(String.format("%d/%d/%d", dayOfMonth,monthOfYear,year));
            followStatus = 0;
        }
    }
}

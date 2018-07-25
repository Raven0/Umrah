package com.birutekno.umrah.fragment;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.InputKalkulasiActivity;
import com.birutekno.umrah.KalkulasiActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.helper.AIWAInterface;
import com.birutekno.umrah.helper.AIWAResponse;
import com.birutekno.umrah.helper.UtilsApi;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataJadwal;
import com.birutekno.umrah.model.DataProspek;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;
import com.birutekno.umrah.model.ProspekObject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditKalkulasiFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    List<Jadwal> objJadwal;
    List<Paket> objPaket;
    List<DataJadwal> alldata;
    List<DataProspek> prospeks;

    List<String> listJadwal = new ArrayList<String>();
    List<String> ketJadwal = new ArrayList<String>();
    List<String> listPaket = new ArrayList<String>();
    List<String> listPembayaran = new ArrayList<String>();

    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, String> insertVal = new HashMap<String, String>();

    ArrayAdapter<String> adapterJadwal;
    ArrayAdapter<String> adapterHotel;
    ArrayAdapter<String> adapterPembayaran;

    private AIWAInterface apiservice;
    ProgressDialog loading;

    private View view;
    private TextView namaJadwal, totalIndicator;

    //Bundle
    private String id;

    // Input
    private EditText pic;
    private EditText telp;
    private EditText dewasa;
    private EditText infant;
    private EditText balita;
    private EditText dobel;
    private EditText tripel;
    private EditText quard;
    private EditText progresif;
    private EditText diskonboy;
    private EditText keterangan;
    private CheckBox cbPassport;
    private Boolean passport = false;
    private String passportString = "Tidak";
    private CheckBox cbFoto;
    private Boolean foto = false;
    private String fotoString = "Tidak";
    private CheckBox cbAkta;
    private Boolean akta = false;
    private String aktaString = "Tidak";
    private CheckBox cbMeningitis;
    private Boolean meningitis = false;
    private String meningitisString = "Tidak";
    private CheckBox cbBukuNikah;
    private Boolean bukuNikah = false;
    private String bukuNikahString = "Tidak";
    private Button buttonNext;
    private Button buttonSimpan;
    private Button followUp;
    private Spinner hotel, jadwal, pembayaran;

    // Calculation
    private int jmlDewasa = 0;
    private int jmlInfant = 0;
    private int jmlBalita = 0;
    private int jmlTotal = 0;

    // Actual Calculation
    private int jmlDobel = 0;
    private int jmlTripel = 0;
    private int jmlQuard = 0;
    private int jmlKamar = 0;
    private int jmlProgresif = 0;
    private int jmlDiskon = 0;

    private Boolean quardBool = false;
    private Boolean tripleBool = false;
    private Boolean doubleBool = false;

    // Reference Calculation
    private int hargaDouble;
    private int hargaTriple;
    private int hargaQuard;

    private String picName;
    private String no_telp;
    private String tgl_berangkat;
    private String jenisPaket;
    private String maskapai;
    private String landing;
    private String pesawat;
    private String pukul;
    private String mekahSend;
    private String mekah;
    private String madinahSend;
    private String madinah;
    private String statPembayaran;
    private String tglFollowup;

    private ProgressDialog pDialog;
    private ProgressDialog nDialog;

    public EditKalkulasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_form_kalkulasi, container, false);
        id = getArguments().getString("id");
        loadComponent();
        setupAdapter();
        loadData(id);
        return view;
    }

    private void loadComponent() {
        pic = (EditText) view.findViewById(R.id.picName);
        telp = (EditText) view.findViewById(R.id.telp);
        dewasa = (EditText) view.findViewById(R.id.etDewasa);
        infant = (EditText) view.findViewById(R.id.etInfant);
        balita = (EditText) view.findViewById(R.id.etBalita);
        buttonNext = (Button) view.findViewById(R.id.btnNext);
        buttonSimpan = (Button) view.findViewById(R.id.btnSimpan);
        buttonNext.setOnClickListener(this);
        buttonSimpan.setOnClickListener(this);
        jadwal = (Spinner) view.findViewById(R.id.searchBerangkat);
        namaJadwal = (TextView) view.findViewById(R.id.namaJadwal);
        totalIndicator = (TextView) view.findViewById(R.id.totalIndicator);
        hotel = (Spinner) view.findViewById(R.id.searchJenis);
        pembayaran = (Spinner) view.findViewById(R.id.spinnerPembayaran);
        dobel = (EditText) view.findViewById(R.id.etDouble);
        tripel = (EditText) view.findViewById(R.id.etTriple);
        quard = (EditText) view.findViewById(R.id.etQuard);
        progresif = (EditText) view.findViewById(R.id.etVisa);
        diskonboy = (EditText) view.findViewById(R.id.etDiskon);
        keterangan = (EditText) view.findViewById(R.id.etKeterangan);
        followUp = (Button) view.findViewById(R.id.dateFollow);
        cbPassport = (CheckBox) view.findViewById(R.id.passport);
        cbMeningitis = (CheckBox) view.findViewById(R.id.meningitis);
        cbFoto = (CheckBox) view.findViewById(R.id.foto);
        cbBukuNikah = (CheckBox) view.findViewById(R.id.nikah);
        cbAkta = (CheckBox) view.findViewById(R.id.akta);

        dewasa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(dewasa.getText())){
                    dewasa.setText("0");
                }else {
                    jmlDewasa = Integer.parseInt(dewasa.getText().toString().trim());
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal));
                }
            }
        });

        infant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(infant.getText())){
                    infant.setText("0");
                }else {
                    jmlInfant = Integer.parseInt(infant.getText().toString().trim());
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal));
                }
            }
        });

        balita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(balita.getText())){
                    balita.setText("0");
                }else {
                    jmlBalita = Integer.parseInt(balita.getText().toString().trim());
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal));
                }
            }
        });

        dobel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(dobel.getText())){
                    dobel.setText("0");
                }else {
                    jmlDobel = Integer.parseInt(dobel.getText().toString().trim());
                    jmlKamar = jmlQuard + jmlTripel + jmlDobel;
                    if(jmlDobel == 0){
                        doubleBool = false;
                    }else {
                        doubleBool = true;
                    }
                }
            }
        });

        tripel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(tripel.getText())){
                    tripel.setText("0");
                }else {
                    jmlTripel = Integer.parseInt(tripel.getText().toString().trim());
                    jmlKamar = jmlQuard + jmlTripel + jmlDobel;
                    if(jmlTripel == 0){
                        tripleBool = false;
                    }else {
                        tripleBool = true;
                    }
                }
            }
        });

        quard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(quard.getText())){
                    quard.setText("0");
                }else {
                    jmlQuard = Integer.parseInt(quard.getText().toString().trim());
                    jmlKamar = jmlQuard + jmlTripel + jmlDobel;
                    if(jmlQuard == 0){
                        quardBool = false;
                    }else {
                        quardBool = true;
                    }
                }
            }
        });

        diskonboy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(diskonboy.getText())){
                    diskonboy.setText("0");
                }
            }
        });

        progresif.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(progresif.getText())){
                    progresif.setText("0");
                }
            }
        });

        jadwal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tgl_berangkat = parent.getItemAtPosition(position).toString();

                listPaket.clear();
                map.clear();
                String detailJadwal = ketJadwal.get(position);
                objJadwal = Arrays.asList(alldata.get(position).getJadwal());
                objPaket = Arrays.asList(objJadwal.get(0).getPaket());
                initSpinnerPaket(objPaket);
                namaJadwal.setText(detailJadwal);

                maskapai = objJadwal.get(0).getMaskapai();
                landing = objJadwal.get(0).getRute_berangkat();
                pesawat = objJadwal.get(0).getPesawat_berangkat();
                pukul = objJadwal.get(0).getJam_berangkat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        hotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                jenisPaket = selectedItem;
                if (selectedItem.equals("UHUD")){
                    dobel.setHint(map.get("harga_double_uhud"));
                    tripel.setHint(map.get("harga_triple_uhud"));
                    quard.setHint(map.get("harga_quard_uhud"));

                    hargaDouble = Integer.parseInt(map.get("harga_double_uhud"));
                    hargaTriple = Integer.parseInt(map.get("harga_triple_uhud"));
                    hargaQuard = Integer.parseInt(map.get("harga_quard_uhud"));

                    mekahSend = map.get("hotel_mekkah_uhud");
                    madinahSend = map.get("hotel_madinah_uhud");
                }else if(selectedItem.equals("NUR ")){
                    dobel.setHint(map.get("harga_double_nur"));
                    tripel.setHint(map.get("harga_triple_nur"));
                    quard.setHint(map.get("harga_quard_nur"));

                    hargaDouble = Integer.parseInt(map.get("harga_double_nur"));
                    hargaTriple = Integer.parseInt(map.get("harga_triple_nur"));
                    hargaQuard = Integer.parseInt(map.get("harga_quard_nur"));

                    mekahSend = map.get("hotel_mekkah_nur");
                    madinahSend = map.get("hotel_madinah_nur");
                }else if (selectedItem.equals("NUR")){
                    dobel.setHint(map.get("harga_double_nur"));
                    tripel.setHint(map.get("harga_triple_nur"));
                    quard.setHint(map.get("harga_quard_nur"));

                    hargaDouble = Integer.parseInt(map.get("harga_double_nur"));
                    hargaTriple = Integer.parseInt(map.get("harga_triple_nur"));
                    hargaQuard = Integer.parseInt(map.get("harga_quard_nur"));

                    mekahSend = map.get("hotel_mekkah_nur");
                    madinahSend = map.get("hotel_madinah_nur");
                }else if (selectedItem.equals("Rahmah")){
                    dobel.setHint(map.get("harga_double_rhm"));
                    tripel.setHint(map.get("harga_triple_rhm"));
                    quard.setHint(map.get("harga_quard_rhm"));

                    hargaDouble = Integer.parseInt(map.get("harga_double_rhm"));
                    hargaTriple = Integer.parseInt(map.get("harga_triple_rhm"));
                    hargaQuard = Integer.parseInt(map.get("harga_quard_rhm"));

                    mekahSend = map.get("hotel_mekkah_rhm");
                    madinahSend = map.get("hotel_madinah_rhm");
                }else if (selectedItem.equals("RAHMAH")){
                    dobel.setHint(map.get("harga_double_rhm"));
                    tripel.setHint(map.get("harga_triple_rhm"));
                    quard.setHint(map.get("harga_quard_rhm"));

                    hargaDouble = Integer.parseInt(map.get("harga_double_rhm"));
                    hargaTriple = Integer.parseInt(map.get("harga_triple_rhm"));
                    hargaQuard = Integer.parseInt(map.get("harga_quard_rhm"));

                    mekahSend = map.get("hotel_mekkah_rhm");
                    madinahSend = map.get("hotel_madinah_rhm");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pembayaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statPembayaran = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        followUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) EditKalkulasiFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih Periode");
                datePickerDialog.show(fm,"Date");

                Toast.makeText(getContext(), String.valueOf(jmlDiskon), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(String id){
//        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
        nDialog = ProgressDialog.show(getContext(), null, "Memuat Data...", true, false);
        Call<ProspekObject> call = WebApi.getAPIService().showProspek(id);
        call.enqueue(new Callback<ProspekObject>() {
            @Override
            public void onResponse(Call<ProspekObject> call, Response<ProspekObject> response) {
                try{
                    DataProspek dataProspek = response.body().getData();
                    pic.setText(dataProspek.getPic());
                    telp.setText(dataProspek.getNo_telp());
                    dewasa.setText(dataProspek.getJml_dewasa());
                    infant.setText(dataProspek.getJml_infant());
                    balita.setText(dataProspek.getJml_balita());

                    int posJadwal = adapterJadwal.getPosition(dataProspek.getTgl_keberangkatan());
                    jadwal.setSelection(posJadwal);
                    if(adapterHotel != null){
                        int posHotel = adapterHotel.getPosition(dataProspek.getJenis());
                        hotel.setSelection(posHotel);
                    }

                    dobel.setText(dataProspek.getDobel());
                    tripel.setText(dataProspek.getTriple());
                    quard.setText(dataProspek.getQuard());
//                cbPassport.setChecked(dataProspek.getPassport());
//                cbMeningitis.setChecked(dataProspek.getMeningitis());
//                cbFoto.setChecked(dataProspek.getPas_foto());
//                cbBukuNikah.setChecked(dataProspek.getBuku_nikah());
//                cbAkta.setChecked(dataProspek.getFc_akta());
                    progresif.setText(dataProspek.getVisa_progresif());
                    diskonboy.setText(dataProspek.getDiskon());
                    keterangan.setText(dataProspek.getKeterangan());
                    followUp.setText(dataProspek.getTanggal_followup());

                    int posPembayaran = adapterPembayaran.getPosition(dataProspek.getPembayaran());
                    pembayaran.setSelection(posPembayaran);
                }catch (Exception ex){
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                nDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProspekObject> call, Throwable t) {
                nDialog.dismiss();
            }
        });
    }

    // NEXT PAGE
    @Override
    public void onClick(View view) {
        if (view == buttonNext) {
            jmlDewasa = Integer.parseInt(dewasa.getText().toString().trim());
            jmlInfant = Integer.parseInt(infant.getText().toString().trim());
            jmlBalita = Integer.parseInt(balita.getText().toString().trim());
            jmlDobel = Integer.parseInt(dobel.getText().toString().trim());
            jmlTripel = Integer.parseInt(tripel.getText().toString().trim());
            jmlQuard = Integer.parseInt(quard.getText().toString().trim());
            jmlDiskon = Integer.parseInt(diskonboy.getText().toString().trim());
            jmlProgresif = Integer.parseInt(progresif.getText().toString().trim());

            if(cbPassport.isChecked()){
                passport = true;
            }else {
                passport = false;
            }

            if(cbMeningitis.isChecked()){
                meningitis = true;
            }else {
                meningitis = false;
            }

            if(cbFoto.isChecked()){
                foto = true;
            }else {
                foto = false;
            }

            if(cbBukuNikah.isChecked()){
                bukuNikah = true;
            }else {
                bukuNikah = false;
            }

            if(cbAkta.isChecked()){
                akta = true;
            }else {
                akta = false;
            }

            if(jmlDewasa == jmlKamar){
                InputKalkulasiActivity.goToStepTotal();
                TotalKalkulasiFragment step3Fragment = new TotalKalkulasiFragment();
                Bundle bundle = new Bundle();
                bundle.putString("maskapai", maskapai);
                bundle.putString("landing", landing);
                bundle.putString("pesawat", pesawat);
                bundle.putString("pukul", pukul);
                bundle.putString("mekah", mekahSend);
                bundle.putString("madinah", madinahSend);

                bundle.putString("jenisPaket", jenisPaket);
                bundle.putInt("hargaDouble", hargaDouble);
                bundle.putInt("hargaTriple", hargaTriple);
                bundle.putInt("hargaQuard", hargaQuard);

                bundle.putBoolean("boolDouble", doubleBool);
                bundle.putBoolean("boolTriple", tripleBool);
                bundle.putBoolean("boolQuard", quardBool);
                bundle.putBoolean("passport", passport);
                bundle.putBoolean("meningitis", meningitis);
                bundle.putBoolean("foto", foto);
                bundle.putBoolean("nikah", bukuNikah);
                bundle.putBoolean("akta", akta);
                bundle.putInt("jmlDobel", jmlDobel);
                bundle.putInt("jmlTripel", jmlTripel);
                bundle.putInt("jmlQuard", jmlQuard);
                bundle.putInt("visa", jmlProgresif);
                bundle.putInt("diskon", jmlDiskon);

                step3Fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                        .replace(R.id.frame_layout, step3Fragment)
                        .addToBackStack(null)
                        .commit();
            }else {
                Toast.makeText(getContext(), "Pastikan Jumlah Total", Toast.LENGTH_SHORT).show();
            }
        } else if (view == buttonSimpan){

            Toast.makeText(getContext(), "AHAAHHA", Toast.LENGTH_SHORT).show();
            picName = pic.getText().toString().trim();
            no_telp = telp.getText().toString().trim();
            jmlDewasa = Integer.parseInt(dewasa.getText().toString().trim());
            jmlInfant = Integer.parseInt(infant.getText().toString().trim());
            jmlBalita = Integer.parseInt(balita.getText().toString().trim());
            jmlDobel = Integer.parseInt(dobel.getText().toString().trim());
            jmlTripel = Integer.parseInt(tripel.getText().toString().trim());
            jmlQuard = Integer.parseInt(quard.getText().toString().trim());
            jmlDiskon = Integer.parseInt(diskonboy.getText().toString().trim());
            jmlProgresif = Integer.parseInt(progresif.getText().toString().trim());

            if(cbPassport.isChecked()){
                passportString = "Ya";
            }else {
                passportString = "Tidak";
            }

            if(cbMeningitis.isChecked()){
                meningitisString = "Ya";
            }else {
                meningitisString = "Tidak";
            }

            if(cbFoto.isChecked()){
                fotoString = "Ya";
            }else {
                fotoString = "Tidak";
            }

            if(cbBukuNikah.isChecked()){
                bukuNikahString = "Ya";
            }else {
                bukuNikahString = "Tidak";
            }

            if(cbAkta.isChecked()){
                aktaString = "Ya";
            }else {
                aktaString = "Tidak";
            }

            HashMap<String, String> params = new HashMap<>();
            params.put("no_telp", no_telp);
            params.put("tgl_keberangkatan", tgl_berangkat);
            params.put("pas_foto", fotoString);
            params.put("anggota_id", "45");
            params.put("visa_progresif", String.valueOf(jmlProgresif));
            params.put("jml_infant", String.valueOf(jmlInfant));
            params.put("pembayaran", statPembayaran);
            params.put("pic", picName);
            params.put("dobel", String.valueOf(jmlDobel));
            params.put("fc_akta", aktaString);
            params.put("meningitis", meningitisString);
            params.put("quard", String.valueOf(jmlQuard));
            params.put("keterangan", keterangan.getText().toString().trim());
            params.put("diskon", String.valueOf(jmlDiskon));
            params.put("triple", String.valueOf(jmlTripel));
            params.put("jenis", jenisPaket);
            params.put("tanggal_followup", tglFollowup);
            params.put("jml_balita", String.valueOf(jmlBalita));
            params.put("passport", passportString);
            params.put("buku_nikah", bukuNikahString);
            params.put("jml_dewasa", String.valueOf(jmlDewasa));

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Harap tunggu...");
            pDialog.setCancelable(false);
            pDialog.show();

            Call<ResponseBody> result = WebApi.getAPIService().editProspek(id, params);
            result.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pDialog.dismiss();
                    try {
                        if(response.body()!=null){
//                            Toast.makeText(getContext()," response message "+response.body().string(),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(), KalkulasiActivity.class);
                            startActivity(intent);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.dismiss();
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(getContext(), String.format("You Selected : %d/%d/%d", dayOfMonth,monthOfYear,year), Toast.LENGTH_LONG).show();
        followUp.setText(String.format("%d/%d/%d", dayOfMonth,monthOfYear,year));
        tglFollowup = String.format("%d/%d/%d", dayOfMonth,monthOfYear,year);
    }

    public void setupAdapter(){

        apiservice = UtilsApi.getAPIService();

        initSpinnerJadwal();
        initSpinnerPembayaran();
    }

    public void initSpinnerJadwal(){
        loading = ProgressDialog.show(getContext(), null, "Harap tunggu...", true, false);

        apiservice.getJSON("1440").enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    alldata = Arrays.asList(response.body().getData());
                    for (int i = 0; i < alldata.size(); i++){
                        List <Jadwal> jadwal = Arrays.asList(alldata.get(i).getJadwal());
                        listJadwal.add(jadwal .get(0).getTgl_berangkat() + "\nBerangkat : " + jadwal .get(0).getRute_berangkat() + "\nMaskapai : " + jadwal.get(0).getMaskapai());
                        ketJadwal.add(jadwal .get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + " Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
                    }

                    adapterJadwal = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, listJadwal);
                    adapterJadwal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    jadwal.setAdapter(adapterJadwal);

                } else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Gagal mengambil data jadwal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AIWAResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initSpinnerPaket(List<Paket> objPaket) {
        int paketSize = objPaket.size();
        for (int i = 0; i < paketSize; i++){

            String Jenis = objPaket.get(i).getNama_paket();
            String Kamar = objPaket.get(i).getKamar();
            String harga = objPaket.get(i).getHarga();
            String hotelMadinah = objPaket.get(i).getHotel_madinah();
            String hotelMekah = objPaket.get(i).getHotel_mekkah();

            if (Jenis.equals("UHUD")){
                map.put("uhud",Jenis);
                map.put("hotel_madinah_uhud", hotelMadinah);
                map.put("hotel_mekkah_uhud", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_uhud", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_uhud", harga);
                }else {
                    map.put("harga_quard_uhud", harga);
                }
            }else if(Jenis.equals("NUR ")){
                map.put("nur",Jenis);
                map.put("hotel_madinah_nur", hotelMadinah);
                map.put("hotel_mekkah_nur", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_nur", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_nur", harga);
                }else {
                    map.put("harga_quard_nur", harga);
                }
            }else if (Jenis.equals("NUR")){
                map.put("nur",Jenis);
                map.put("hotel_madinah_nur", hotelMadinah);
                map.put("hotel_mekkah_nur", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_nur", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_nur", harga);
                }else {
                    map.put("harga_quard_nur", harga);
                }
            }else if (Jenis.equals("Rahmah")){
                map.put("rhm",Jenis);
                map.put("hotel_madinah_rhm", hotelMadinah);
                map.put("hotel_mekkah_rhm", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_rhm", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_rhm", harga);
                }else {
                    map.put("harga_quard_rhm", harga);
                }
            }else if (Jenis.equals("RAHMAH")){
                map.put("rhm",Jenis);
                map.put("hotel_madinah_rhm", hotelMadinah);
                map.put("hotel_mekkah_rhm", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_rhm", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_rhm", harga);
                }else {
                    map.put("harga_quard_rhm", harga);
                }
            }
        }

        if (!TextUtils.isEmpty(map.get("rhm"))) {
            listPaket.add(map.get("rhm"));
            mekah = map.get("hotel_mekkah_rhm");
            madinah = map.get("hotel_madinah_rhm");
        }
        if (!TextUtils.isEmpty(map.get("nur"))) {
            listPaket.add(map.get("nur"));
            mekah = map.get("hotel_mekkah_nur");
            madinah = map.get("hotel_madinah_nur");
        }
        if (!TextUtils.isEmpty(map.get("uhud"))){
            listPaket.add(map.get("uhud"));
            mekah = map.get("hotel_mekkah_uhud");
            madinah = map.get("hotel_madinah_uhud");
        }

        adapterHotel = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, listPaket);
        adapterHotel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hotel.setAdapter(adapterHotel);
    }

    public void initSpinnerPembayaran() {
        listPembayaran.add("PROSPEK");
        listPembayaran.add("DP");

        adapterPembayaran = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, listPembayaran);
        adapterPembayaran.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pembayaran.setAdapter(adapterPembayaran);
    }
}

package com.birutekno.umrah.fragment;


import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FormKalkulasiFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    public static final String PREFS_NAME = "AUTH";
    List<Jadwal> objJadwal;
    List<Paket> objPaket;
    List<DataJadwal> alldata;

    List<String> listJadwal = new ArrayList<String>();
    List<String> ketJadwal = new ArrayList<String>();
    List<String> listPaket = new ArrayList<String>();

    HashMap<String, String> map = new HashMap<String, String>();

    private AIWAInterface apiservice;
    ProgressDialog loading;

    private View view;
    private TextView namaJadwal, totalIndicator;

    // Input
    private RadioGroup radioGroup,radioGroup1;
    private RadioButton rb1, rbDefault;
    private RadioButton rb2, rbPromo;
    private LinearLayout viewPerlengkapan, viewPerlengkapanDewasa, viewVisa, viewDiskon;

    private EditText pic;
    private EditText telp;
    private EditText dewasa;
    private EditText infant;
    private EditText balitaKasur;
    private EditText balita;
    private EditText dobel;
    private EditText tripel;
    private EditText quard;
    private TextView progresif;
    private EditText progresifJml;
    private CurrencyEditText diskonboy;
    private EditText keterangan;

    private CheckBox cbVisa, cbDiskon;
    private CheckBox cbPassport;
    private Boolean passport = false;
    private String passportString = "false";
    private CheckBox cbFoto;
    private Boolean foto = false;
    private String fotoString = "false";
    private CheckBox cbAkta;
    private Boolean akta = false;
    private String aktaString = "false";
    private CheckBox cbMeningitis;
    private Boolean meningitis = false;
    private String meningitisString = "false";
    private CheckBox cbBukuNikah;
    private Boolean bukuNikah = false;
    private String bukuNikahString = "false";

    public Button buttonNext;
    public Button buttonSimpan;
    private Button followUp;
    private Spinner hotel, jadwal;
//    pembayaran

    // Calculation
    private int jmlDewasa = 0;
    private int jmlInfant = 0;
    private int jmlBalitaKasur = 0;
    private int jmlBalita = 0;
    private int jmlTotal = 0;

    // Actual Calculation
    private int jmlDobel = 0;
    private int jmlTripel = 0;
    private int jmlQuard = 0;
    private int jmlKamar = 0;
    private int jmlProgresif = 0;
    private int jmlvisa = 0;
    private int jmlDiskon = 0;

    private Boolean quardBool = false;
    private Boolean tripleBool = false;
    private Boolean doubleBool = false;
    private Boolean perlengkapanFull = false;
    private Boolean perlengkapanDefault = false;
    private Boolean perlengkapanLite = false;
    private Boolean perlengkapanPromo = false;

    // Reference Calculation
    private int hargaDouble;
    private int hargaTriple;
    private int hargaQuard;

    private String picName;
    private String no_telp;
    private String tgl_berangkat;
    private String jenisPaket;
    private String sendTgl;
    private String maskapai;
    private String landing;
    private String pesawat;
    private String pukul;
    private String mekahSend;
    private String mekah;
    private String madinahSend;
    private String madinah;
    private String tglFollowup;
    private String jenisPerlengkapan;
    private String jenisPerlengkapanDewasa;

    private ProgressDialog pDialog;

    public FormKalkulasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_form_kalkulasi, container, false);
        loadComponent();
        setupAdapter();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadComponent() {
        pic = (EditText) view.findViewById(R.id.picName);
        telp = (EditText) view.findViewById(R.id.telp);
        dewasa = (EditText) view.findViewById(R.id.etDewasa);
        infant = (EditText) view.findViewById(R.id.etInfant);
        balita = (EditText) view.findViewById(R.id.etBalita);
        balitaKasur = (EditText) view.findViewById(R.id.etBalitaKasur);
        buttonNext = (Button) view.findViewById(R.id.btnNext);
        buttonNext.setOnClickListener(this);
        buttonSimpan = (Button) view.findViewById(R.id.btnSimpan);
        buttonSimpan.setOnClickListener(this);
        jadwal = (Spinner) view.findViewById(R.id.searchBerangkat);
        namaJadwal = (TextView) view.findViewById(R.id.namaJadwal);
        totalIndicator = (TextView) view.findViewById(R.id.totalIndicator);
        hotel = (Spinner) view.findViewById(R.id.searchJenis);
        dobel = (EditText) view.findViewById(R.id.etDouble);
        tripel = (EditText) view.findViewById(R.id.etTriple);
        quard = (EditText) view.findViewById(R.id.etQuard);
        progresif = (TextView) view.findViewById(R.id.etVisa);
        progresifJml = (EditText) view.findViewById(R.id.etVisaJml);
        diskonboy = (CurrencyEditText ) view.findViewById(R.id.etDiskon);
        keterangan = (EditText) view.findViewById(R.id.etKeterangan);
        followUp = (Button) view.findViewById(R.id.dateFollow);
        viewPerlengkapan = (LinearLayout) view.findViewById(R.id.viewPerlengkapan);
        viewPerlengkapanDewasa = (LinearLayout) view.findViewById(R.id.viewPerlengkapanDewasa);
        viewVisa = (LinearLayout) view.findViewById(R.id.viewVisavisa);
        viewDiskon = (LinearLayout) view.findViewById(R.id.viewDiskondiskon);

        cbVisa = (CheckBox) view.findViewById(R.id.cbvisa);
        cbDiskon = (CheckBox) view.findViewById(R.id.cbdiskon);
        cbPassport = (CheckBox) view.findViewById(R.id.passport);
        cbMeningitis = (CheckBox) view.findViewById(R.id.meningitis);
        cbFoto = (CheckBox) view.findViewById(R.id.foto);
        cbBukuNikah = (CheckBox) view.findViewById(R.id.nikah);
        cbAkta = (CheckBox) view.findViewById(R.id.akta);

        radioGroup = (RadioGroup) view.findViewById(R.id.groupPerlengkapan);
        radioGroup1 = (RadioGroup) view.findViewById(R.id.groupPerlengkapanDewasa);
        rb1 = (RadioButton) view.findViewById(R.id.perlengkapanFull);
        rbDefault = (RadioButton) view.findViewById(R.id.perlengkapanDefault);
        rb2 = (RadioButton) view.findViewById(R.id.perlengkapanLite);
        rbPromo = (RadioButton) view.findViewById(R.id.perlengkapanPromo);
        radioGroup.clearCheck();
        radioGroup1.clearCheck();

        cbVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbVisa.isChecked()){
                    viewVisa.setVisibility(View.VISIBLE);
                }else {
                    viewVisa.setVisibility(View.GONE);
                    jmlvisa = 0;
                }
            }
        });

        cbDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbDiskon.isChecked()){
                    viewDiskon.setVisibility(View.VISIBLE);
                }else {
                    viewDiskon.setVisibility(View.GONE);
                    jmlDiskon = 0;
                }
            }
        });

        if (jmlvisa > 0){
            cbVisa.setChecked(true);
            viewVisa.setVisibility(View.VISIBLE);
        }else {
            cbVisa.setChecked(false);
            viewVisa.setVisibility(View.GONE);
        }
        if (jmlDiskon > 0){
            cbDiskon.setChecked(true);
            viewDiskon.setVisibility(View.VISIBLE);
        }else {
            cbDiskon.setChecked(false);
            viewDiskon.setVisibility(View.GONE);
        }

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
//                    dewasa.setText("0");
                    jmlDewasa = 0;
                    viewPerlengkapanDewasa.setVisibility(View.GONE);
                }else {
                    try {
                        jmlDewasa = Integer.parseInt(dewasa.getText().toString().trim());
                        jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                        totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
                        if (jmlDewasa == 0){
                            viewPerlengkapanDewasa.setVisibility(View.GONE);
                        }else {
                            viewPerlengkapanDewasa.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception ex){
                        dewasa.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }

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
                    jmlInfant = 0;
                }else {
                    try {
                        jmlInfant = Integer.parseInt(infant.getText().toString().trim());
                        jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                        totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
                    }catch (Exception ex){
                        infant.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
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
//                    balita.setText("0");
                    jmlBalita = 0;
                    viewPerlengkapan.setVisibility(View.GONE);
                }else {
                    try{
                        jmlBalita = Integer.parseInt(balita.getText().toString().trim());
                        jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                        totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
                        if (jmlBalita == 0){
                            viewPerlengkapan.setVisibility(View.GONE);
                        }else {
                            viewPerlengkapan.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception ex){
                        balita.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        balitaKasur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(balitaKasur.getText())){
//                    balitaKasur.setText("0");
                    jmlBalitaKasur = 0;
                }else {
                    try {
                        jmlBalitaKasur = Integer.parseInt(balitaKasur.getText().toString().trim());
                        jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                        totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
                    }catch (Exception ex){
                        balitaKasur.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
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
                    jmlDobel = 0;
                    doubleBool = false;
                }else {
                    try {
                        jmlDobel = Integer.parseInt(dobel.getText().toString().trim());
                        jmlKamar = jmlQuard + jmlTripel + jmlDobel;
                        if(jmlDobel == 0){
                            doubleBool = false;
                        }else {
                            doubleBool = true;
                        }

                        if (jmlDobel == 2 || jmlDobel == 02){
                            Toast.makeText(getContext(), "Sudah 2", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        dobel.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
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
                    jmlTripel = 0;
                    tripleBool = false;
                }else {
                    try {
                        jmlTripel = Integer.parseInt(tripel.getText().toString().trim());
                        jmlKamar = jmlQuard + jmlTripel + jmlDobel;
                        if(jmlTripel == 0){
                            tripleBool = false;
                        }else {
                            tripleBool = true;
                        }

                        if (jmlTripel == 3 || jmlTripel == 03){
                            Toast.makeText(getContext(), "Sudah 3", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        tripel.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
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
//                    quard.setText("0");
                    jmlQuard = 0;
                    quardBool = false;
                }else {
                    try {
                        jmlQuard = Integer.parseInt(quard.getText().toString().trim());
                        jmlKamar = jmlQuard + jmlTripel + jmlDobel;
                        if(jmlQuard == 0){
                            quardBool = false;
                        }else {
                            quardBool = true;
                        }

                        if (jmlQuard == 4 || jmlQuard == 04){
                            Toast.makeText(getContext(), "Sudah 4", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        quard.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        diskonboy.setLocale(new Locale("in","ID"));
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
                    jmlDiskon = 0;
                }else {
                    try {
                        jmlDiskon = (int) (long) diskonboy.getRawValue();
                    }catch (Exception ex){
                        diskonboy.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara : "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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
                    jmlProgresif = 0;
                }else {
                    try {
                        jmlProgresif = Integer.parseInt(progresif.getText().toString().trim());
                    }catch (Exception ex){
                        progresif.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        progresifJml.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(progresifJml.getText())){
                    jmlvisa = 0;
                }else {
                    try {
                        jmlvisa = Integer.parseInt(progresifJml.getText().toString().trim());
                    }catch (Exception ex){
                        progresifJml.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
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

                sendTgl = objJadwal.get(0).getTgl_berangkat();

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

        followUp.setOnClickListener(new View.OnClickListener() {
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

//                Toast.makeText(getContext(), String.valueOf(jmlDiskon), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    NEXT PAGE
    @Override
    public void onClick(View view) {
        try {
            jmlProgresif = Integer.parseInt(progresif.getText().toString().trim());
            jmlvisa = Integer.parseInt(progresifJml.getText().toString().trim());
            jmlDiskon = (int) (long) diskonboy.getRawValue();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if (view == buttonNext) {

            if(rb1.isChecked()){
                perlengkapanFull = true;
                jenisPerlengkapan = "FULL";
            }else if(rb2.isChecked()){
                perlengkapanLite = true;
                jenisPerlengkapan = "LITE";
            }else {
                perlengkapanFull = false;
                perlengkapanLite = false;
                jenisPerlengkapan = "NULL";
            }

            if(rbDefault.isChecked()){
                perlengkapanDefault = true;
                jenisPerlengkapanDewasa = "DEFAULT";
            }else if(rbPromo.isChecked()){
                perlengkapanPromo = true;
                jenisPerlengkapanDewasa = "PROMO";
            }else {
                perlengkapanDefault = false;
                perlengkapanPromo = false;
                jenisPerlengkapanDewasa = "NULL";
            }

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

            if(jmlDewasa + jmlBalitaKasur == jmlKamar){
                if(jmlBalita != 0){
                    if (perlengkapanFull || perlengkapanLite){
                        InputKalkulasiActivity.goToStepTotal();
                        TotalKalkulasiFragment step3Fragment = new TotalKalkulasiFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("berangkatTgl", convertDate(sendTgl));
                        bundle.putString("maskapai", maskapai);
                        bundle.putString("landing", landing);
                        bundle.putString("pesawat", pesawat);
                        bundle.putString("pukul", pukul);
                        bundle.putString("mekah", mekahSend);
                        bundle.putString("madinah", madinahSend);
                        bundle.putString("keterangan", keterangan.getText().toString());
                        bundle.putString("perlengkapan", jenisPerlengkapan);
                        bundle.putString("perlengkapanDewasa", jenisPerlengkapanDewasa);

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
                        bundle.putInt("jmlInfant", jmlInfant);
                        bundle.putInt("jmlBalita", jmlBalita);
                        bundle.putInt("jmlBalitaKasur", jmlBalitaKasur);
                        bundle.putInt("jmlDobel", jmlDobel);
                        bundle.putInt("jmlTripel", jmlTripel);
                        bundle.putInt("jmlQuard", jmlQuard);
                        bundle.putInt("visa", jmlProgresif);
                        bundle.putInt("visa_jml", jmlvisa);
                        bundle.putInt("diskon", jmlDiskon);

                        step3Fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                                .replace(R.id.frame_layout, step3Fragment)
                                .addToBackStack(null)
                                .commit();
                    }else {
                        Toast.makeText(getContext(), "Pilih Salahsatu perlengkapan untuk balita", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (perlengkapanDefault || perlengkapanPromo){
                        InputKalkulasiActivity.goToStepTotal();
                        TotalKalkulasiFragment step3Fragment = new TotalKalkulasiFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("berangkatTgl", convertDate(sendTgl));
                        bundle.putString("maskapai", maskapai);
                        bundle.putString("landing", landing);
                        bundle.putString("pesawat", pesawat);
                        bundle.putString("pukul", pukul);
                        bundle.putString("mekah", mekahSend);
                        bundle.putString("madinah", madinahSend);
                        bundle.putString("keterangan", keterangan.getText().toString());
                        bundle.putString("perlengkapan", jenisPerlengkapan);
                        bundle.putString("perlengkapanDewasa", jenisPerlengkapanDewasa);

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
                        bundle.putInt("jmlInfant", jmlInfant);
                        bundle.putInt("jmlBalita", jmlBalita);
                        bundle.putInt("jmlBalitaKasur", jmlBalitaKasur);
                        bundle.putInt("jmlDobel", jmlDobel);
                        bundle.putInt("jmlTripel", jmlTripel);
                        bundle.putInt("jmlQuard", jmlQuard);
                        bundle.putInt("visa", jmlProgresif);
                        bundle.putInt("visa_jml", jmlvisa);
                        bundle.putInt("diskon", jmlDiskon);

                        step3Fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                                .replace(R.id.frame_layout, step3Fragment)
                                .addToBackStack(null)
                                .commit();
                    }else {
                        Toast.makeText(getContext(), "Pilih Salahsatu perlengkapan", Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                Toast.makeText(getContext(), "Pastikan Jumlah Dewasa dan Balita (Dengan Kasur) terisi sesuai dengan Jumlah Kamar", Toast.LENGTH_SHORT).show();
            }
        } else if (view == buttonSimpan){
            if(TextUtils.isEmpty(pic.getText().toString().trim())|| TextUtils.isEmpty(telp.getText().toString().trim())){
                Toast.makeText(getContext(), "Pastikan nama PIC dan Nomor Telepon Terisi", Toast.LENGTH_SHORT).show();
            }else {
                picName = pic.getText().toString().trim();
                no_telp = telp.getText().toString().trim();

                if(rb1.isChecked()){
                    perlengkapanFull = true;
                    jenisPerlengkapan = "FULL";
                }else if(rb2.isChecked()){
                    perlengkapanLite = true;
                    jenisPerlengkapan = "LITE";
                }else {
                    perlengkapanLite = false;
                    perlengkapanFull = false;
                    jenisPerlengkapan = "NULL";
                }

                if(rbDefault.isChecked()){
                    perlengkapanDefault = true;
                    jenisPerlengkapanDewasa = "DEFAULT";
                }else if(rbPromo.isChecked()){
                    perlengkapanPromo = true;
                    jenisPerlengkapanDewasa = "PROMO";
                }else {
                    perlengkapanDefault = false;
                    perlengkapanPromo = false;
                    jenisPerlengkapanDewasa = "NULL";
                }

                if(cbPassport.isChecked()){
                    passportString = "true";
                }else {
                    passportString = "false";
                }

                if(cbMeningitis.isChecked()){
                    meningitisString = "true";
                }else {
                    meningitisString = "false";
                }

                if(cbFoto.isChecked()){
                    fotoString = "true";
                }else {
                    fotoString = "false";
                }

                if(cbBukuNikah.isChecked()){
                    bukuNikahString = "true";
                }else {
                    bukuNikahString = "false";
                }

                if(cbAkta.isChecked()){
                    aktaString = "true";
                }else {
                    aktaString = "false";
                }

                SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                int id_agen = prefs.getInt("iduser", 0);
                HashMap<String, String> params = new HashMap<>();
                //anggota_id
                params.put("anggota_id", String.valueOf(id_agen));
                params.put("pic", picName);
                params.put("no_telp", no_telp);
                params.put("jml_dewasa", String.valueOf(jmlDewasa));
                params.put("jml_infant", String.valueOf(jmlInfant));
                params.put("jml_balita", String.valueOf(jmlBalita));
                params.put("jml_visa", String.valueOf(jmlvisa));
                params.put("jml_balita_kasur", String.valueOf(jmlBalitaKasur));
                params.put("tgl_keberangkatan", tgl_berangkat);
                params.put("jenis", jenisPaket);
                params.put("dobel", String.valueOf(jmlDobel));
                params.put("triple", String.valueOf(jmlTripel));
                params.put("quard", String.valueOf(jmlQuard));
                params.put("passport", passportString);
                params.put("meningitis", meningitisString);
                params.put("pas_foto", fotoString);
                params.put("buku_nikah", bukuNikahString);
                params.put("fc_akta", aktaString);
                params.put("visa_progresif", String.valueOf(jmlProgresif));
                params.put("diskon", String.valueOf(jmlDiskon));
                params.put("keterangan", keterangan.getText().toString().trim());
                params.put("tanggal_followup", tglFollowup);
                params.put("pembayaran", "BELUM");
                params.put("perlengkapan_balita", jenisPerlengkapan);
                params.put("perlengkapan_dewasa", jenisPerlengkapanDewasa);

                pDialog = new ProgressDialog(getContext());
                pDialog.setMessage("Harap tunggu...");
                pDialog.setCancelable(false);
                pDialog.show();

                Call<ResponseBody> result = WebApi.getAPIService().insertProspek(params);
                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        pDialog.dismiss();
                        try {
                            if(response.body()!=null){
                                Intent intent = new Intent(getContext(), KalkulasiActivity.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Error Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        pDialog.dismiss();
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "On Failure", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        Toast.makeText(getContext(), String.format("You Selected : %d/%d/%d", dayOfMonth,monthOfYear+1,year), Toast.LENGTH_LONG).show();
        followUp.setText(String.format("%d/%d/%d", dayOfMonth,monthOfYear+1,year));
        tglFollowup = String.format("%d/%d/%d", dayOfMonth,monthOfYear+1,year);
    }

    public void setupAdapter(){

        apiservice = UtilsApi.getAPIService();

        initSpinnerJadwal();
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
                        listJadwal.add(convertDate(jadwal.get(0).getTgl_berangkat()) + "\nRute : " + jadwal.get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + "\nPesawat : " + jadwal.get(0).getPesawat_berangkat());
                        ketJadwal.add("Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
//                        ketJadwal.add(jadwal.get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + " Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, listJadwal);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    jadwal.setAdapter(adapter);

                } else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Gagal mengambil data jadwal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AIWAResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Server Jadwal bermasalah", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter<String> adapterB = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, listPaket);
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hotel.setAdapter(adapterB);
    }

    public String convertDate(String args) {
        String date = args;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd MMM yyyy");
        String newDateString = spf.format(newDate);

        return newDateString ;
    }

    public String getLastThree(String args){
        String temp = args;
        String output = temp.substring(temp.length()-3);
        return output;
    }
}

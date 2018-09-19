package com.birutekno.aiwa.fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import com.birutekno.aiwa.EditKalkulasiActivity;
import com.birutekno.aiwa.KalkulasiActivity;
import com.birutekno.aiwa.R;
import com.birutekno.aiwa.helper.AIWAInterface;
import com.birutekno.aiwa.helper.AIWAResponse;
import com.birutekno.aiwa.helper.KalkulasiResponse;
import com.birutekno.aiwa.helper.UtilsApi;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataJadwal;
import com.birutekno.aiwa.model.DataKalkulasi;
import com.birutekno.aiwa.model.DataProspek;
import com.birutekno.aiwa.model.Jadwal;
import com.birutekno.aiwa.model.Paket;
import com.birutekno.aiwa.model.ProspekObject;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Type;
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
public class EditKalkulasiFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";
    private List<Jadwal> objJadwal;
    private List<Paket> objPaket;
    private List<DataJadwal> alldata;
    private ArrayList<DataKalkulasi> pojo;

    private String indexJadwal;

    private List<String> listJadwal = new ArrayList<String>();
    private List<String> ketJadwal = new ArrayList<String>();
    private List<String> tglJadwal = new ArrayList<String>();
    private List<String> idJadwal = new ArrayList<String>();
    private List<String> paketJadwal = new ArrayList<String>();
    private List<String> listPaket = new ArrayList<String>();
    private List<String> hotelPaket = new ArrayList<String>();

    HashMap<String, String> map = new HashMap<String, String>();

    ArrayAdapter<String> adapterJadwal;
    ArrayAdapter<String> adapterHotel;

    private AIWAInterface apiservice;
    ProgressDialog loading;

    private View view;
    private TextView namaJadwal, totalIndicator;

    //Bundle
    private String id;

    // Input
    private RadioGroup radioGroup,radioGroup1;
    private RadioButton rb1, rb2, rb3, rbDefault, rbPromo;
    private LinearLayout viewPerlengkapan, viewPerlengkapanDewasa, viewVisa, viewDiskon;

    private TextView orangDua;
    private TextView orangTiga;
    private TextView orangEmpat;
    private EditText pic;
    private EditText telp;
    private Button telpBtn;
    private EditText dewasa;
    private EditText infant;
    private EditText balita;
    private EditText balitaKasur;
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
    public Button buttonSimpan;
    private Button followUp;
    private Spinner hotel, jadwal;
//    pembayaran

    // Calculation
    private int jmlDewasa = 0;
    private int jmlInfant = 0;
    private int jmlBalita = 0;
    private int jmlBalitaKasur = 0;
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
    private Boolean perlengkapanNol = false;
    private Boolean perlengkapanPromo = false;

    // Reference Calculation
    private int hargaDouble;
    private int hargaTriple;
    private int hargaQuard;

    private String picName;
    private String no_telp;
    private String tgl_berangkat;
    private String harijadwal;
    private String jenisPaket;
    private int selectedPaket = 0;
    private String sendTgl;
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
    private String jenisPerlengkapan;
    private String jenisPerlengkapanDewasa;

//    private ProgressDialog pDialog;
    private ProgressDialog nDialog;

    //LOAD MASTER KALKULASI
    private int hargaDefault = 0;
    private int hargaPromo = 0;
    private int hargaInfant = 0;
    private int hargaFull = 0;
    private int hargaLite = 0;
    private int diskonBalitaUhud = 0;
    private int diskonBalitaNur = 0;
    private int diskonBalitaRahmah = 0;
    private int diskonBalitaStandar = 0;

    private Boolean jadwalLoaded = false;
    private Boolean kalkulasiLoaded = false;
    private Boolean jadwalSelected = false;

    String id_agen,token;

    public EditKalkulasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        id_agen = prefs.getString("iduser", "0");
        token = prefs.getString("token", "0");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_form_kalkulasi, container, false);
        id = getArguments().getString("id");

        if(!kalkulasiLoaded){
            loadKalkulasi();
        }else {
            String jml = pojo.get(0).getHarga_visa();
            progresif.setText(jml);

            hargaDefault = Integer.parseInt(pojo.get(0).getHarga_default());
            hargaPromo = Integer.parseInt(pojo.get(0).getHarga_promo());
            hargaInfant = Integer.parseInt(pojo.get(0).getHarga_infant());
            hargaFull = Integer.parseInt(pojo.get(0).getHarga_full());
            hargaLite = Integer.parseInt(pojo.get(0).getHarga_lite());
            diskonBalitaUhud = Integer.parseInt(pojo.get(0).getDiskon_balita_uhud());
            diskonBalitaNur = Integer.parseInt(pojo.get(0).getDiskon_balita_nur());
            diskonBalitaRahmah = Integer.parseInt(pojo.get(0).getDiskon_balita_rhm());
            diskonBalitaStandar = Integer.parseInt(pojo.get(0).getDiskon_balita_standar());
        }
        loadComponent();
        if(!jadwalLoaded){
            nDialog = ProgressDialog.show(getContext(), null, "Memuat Data...", true, false);
            nDialog.show();
            setupAdapter();
            nDialog.dismiss();
        }else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, listJadwal);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            jadwal.setAdapter(adapter);
        }

//        if (!dataLoaded){
//            for (int i = 1 ; i < 4 ; i++){
//                loadData(id);
//                loadData(id);
//                loadData(id);
////                Toast.makeText(getContext(), "Load data tahap : " + i + "/3", Toast.LENGTH_SHORT).show();
//                if (i == 3){
//                    nDialog.dismiss();
////                    Toast.makeText(getContext(), "Jika +", Toast.LENGTH_SHORT).show();
//                    dataLoaded = true;
//                }
//            }
//        }

        telpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    private void loadComponent() {
        orangDua = (TextView) view.findViewById(R.id.orangDua);
        orangTiga = (TextView) view.findViewById(R.id.orangTiga);
        orangEmpat = (TextView) view.findViewById(R.id.orangEmpat);
        pic = (EditText) view.findViewById(R.id.picName);
        telp = (EditText) view.findViewById(R.id.telp);
        telpBtn = (Button) view.findViewById(R.id.telpBtn);
        dewasa = (EditText) view.findViewById(R.id.etDewasa);
        infant = (EditText) view.findViewById(R.id.etInfant);
        balita = (EditText) view.findViewById(R.id.etBalita);
        balitaKasur = (EditText) view.findViewById(R.id.etBalitaKasur);
        buttonNext = (Button) view.findViewById(R.id.btnNext);
        buttonSimpan = (Button) view.findViewById(R.id.btnSimpan);
        buttonNext.setOnClickListener(this);
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
        diskonboy = (CurrencyEditText) view.findViewById(R.id.etDiskon);
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
        rb3 = (RadioButton) view.findViewById(R.id.perlengkapanNol);
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

        dewasa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (dewasa.getText().toString().equals("0")){
                    dewasa.setText("");
                }
            }
        });
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
                    jmlDewasa = 0;
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
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

        infant.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (infant.getText().toString().equals("0")){
                    infant.setText("");
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
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
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

        balita.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (balita.getText().toString().equals("0")){
                    balita.setText("");
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
                    jmlBalita = 0;
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
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

        balitaKasur.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (balitaKasur.getText().toString().equals("0")){
                    balitaKasur.setText("");
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
                    jmlBalitaKasur = 0;
                    jmlTotal = jmlDewasa + jmlInfant + jmlBalita + jmlBalitaKasur;
                    totalIndicator.setText("Total : " + String.valueOf(jmlTotal)+ " PAX");
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

        dobel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (dobel.getText().toString().equals("0")){
                    dobel.setText("");
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
                    orangDua.setText("Orang");
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
                            orangDua.setText("Orang (OK)");
                        }else {
                            orangDua.setText("Orang");
                        }
                    }catch (Exception ex){
                        dobel.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tripel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (tripel.getText().toString().equals("0")){
                    tripel.setText("");
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
                    orangTiga.setText("Orang");
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
                            orangTiga.setText("Orang (OK)");
                        }else {
                            orangTiga.setText("Orang");
                        }
                    }catch (Exception ex){
                        tripel.setText("0");
                        Toast.makeText(getContext(), "Batas Maximal sementara", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        quard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (quard.getText().toString().equals("0")){
                    quard.setText("");
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
                    jmlQuard = 0;
                    quardBool = false;
                    orangEmpat.setText("Orang");
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
                            orangEmpat.setText("Orang (OK)");
                        }else {
                            orangEmpat.setText("Orang");
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

        diskonboy.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    String leng = String.valueOf((int) (long)diskonboy.getRawValue());
                    if (leng.length() == 1){
                        diskonboy.setText("0");
                    }
                }
                return false;
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

        progresifJml.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (progresifJml.getText().toString().equals("0")){
                    progresifJml.setText("");
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

                listPaket.clear();
                map.clear();
                String detailJadwal = ketJadwal.get(position);
                tgl_berangkat = tglJadwal.get(position);
                indexJadwal = idJadwal.get(position);
                harijadwal = paketJadwal.get(position);
                objJadwal = Arrays.asList(alldata.get(position).getJadwal());
                objPaket = Arrays.asList(objJadwal.get(0).getPaket());
                try{
                    initSpinnerPaket(objPaket);
                    hotel.setSelection(selectedPaket);
                }catch (Exception ex){
                    initSpinnerPaket(objPaket);
                    hotel.setSelection(selectedPaket);
                }
                namaJadwal.setText(detailJadwal);

                sendTgl = objJadwal.get(0).getTgl_berangkat();

                maskapai = objJadwal.get(0).getMaskapai();
                landing = objJadwal.get(0).getRute_berangkat();
                pesawat = objJadwal.get(0).getPesawat_berangkat();
                pukul = objJadwal.get(0).getJam_berangkat();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hotel.setSelection(selectedPaket);
            }
        });

        hotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                jenisPaket = selectedItem;
                selectedPaket = position;
                if (selectedItem.equals("Standard")){
                    dobel.setHint(map.get("harga_double_std"));
                    tripel.setHint(map.get("harga_triple_std"));
                    quard.setHint(map.get("harga_quard_std"));

                    hargaDouble = Integer.parseInt(map.get("harga_double_std"));
                    hargaTriple = Integer.parseInt(map.get("harga_triple_std"));
                    hargaQuard = Integer.parseInt(map.get("harga_quard_std"));

                    mekahSend = map.get("hotel_mekkah_std");
                    madinahSend = map.get("hotel_madinah_std");
                }else if (selectedItem.equals("UHUD")){
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
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) EditKalkulasiFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih Periode");
                datePickerDialog.show(fm,"Date");

//                Toast.makeText(getContext(), String.valueOf(jmlDiskon), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(final String id){
        Call<ProspekObject> call = WebApi.getAPIService().showProspek(id);
        call.enqueue(new Callback<ProspekObject>() {
            @Override
            public void onResponse(Call<ProspekObject> call, Response<ProspekObject> response) {
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    DataProspek dataProspek = response.body().getData();
                    pic.setText(dataProspek.getPic());
                    telp.setText(dataProspek.getNo_telp());
                    dewasa.setText(dataProspek.getJml_dewasa());
                    infant.setText(dataProspek.getJml_infant());
                    balita.setText(dataProspek.getJml_balita());
                    balitaKasur.setText(dataProspek.getJml_balita_kasur());
                    dobel.setText(dataProspek.getDobel());
                    tripel.setText(dataProspek.getTriple());
                    quard.setText(dataProspek.getQuard());
                    cbPassport.setChecked(Boolean.parseBoolean(dataProspek.getPassport()));
                    cbMeningitis.setChecked(Boolean.parseBoolean(dataProspek.getMeningitis()));
                    cbFoto.setChecked(Boolean.parseBoolean(dataProspek.getPas_foto()));
                    cbBukuNikah.setChecked(Boolean.parseBoolean(dataProspek.getBuku_nikah()));
                    cbAkta.setChecked(Boolean.parseBoolean(dataProspek.getFc_akta()));
                    progresif.setText(dataProspek.getVisa_progresif());
                    diskonboy.setText(dataProspek.getDiskon());
                    keterangan.setText(dataProspek.getKeterangan());
                    followUp.setText(dataProspek.getTanggal_followup());
                    tglFollowup = dataProspek.getTanggal_followup();
                    balitaKasur.setText(dataProspek.getJml_balita_kasur());
                    progresifJml.setText(dataProspek.getJml_visa());
                    int jmlVisas = Integer.parseInt(dataProspek.getJml_visa());
                    int jmlDiskons = Integer.parseInt(dataProspek.getDiskon());
                    int jmlBalita = Integer.parseInt(dataProspek.getJml_balita());
                    int jmlDewasa = Integer.parseInt(dataProspek.getJml_dewasa());
                    selectedPaket = Integer.parseInt(dataProspek.getIndex_paket());

                    if (jadwalLoaded){
                        int posjadwal = idJadwal.indexOf(dataProspek.getIndex_jadwal());
                        jadwal.setSelection(posjadwal);
                    }

                    String perlengkapan = dataProspek.getPerlengkapan_balita();
                    String perlengkapanDewasa = dataProspek.getPerlengkapan_dewasa();
                    if (jmlBalita > 0){
                        if(perlengkapan.equals("FULL")){
                            rb1.setChecked(true);
                            rb2.setChecked(false);
                            rb3.setChecked(false);
                        }else if (perlengkapan.equals("LITE")){
                            rb1.setChecked(false);
                            rb2.setChecked(true);
                            rb3.setChecked(false);
                        }else if (perlengkapan.equals("NOL")){
                            rb1.setChecked(false);
                            rb2.setChecked(false);
                            rb3.setChecked(true);
                        }
                    }

                    if (jmlDewasa > 0){
                        if(perlengkapanDewasa.equals("DEFAULT")){
                            rbDefault.setChecked(true);
                            rbPromo.setChecked(false);
                        }else if (perlengkapanDewasa.equals("PROMO")){
                            rbDefault.setChecked(false);
                            rbPromo.setChecked(true);
                        }
                    }

                    if (jmlVisas > 0){
                        cbVisa.setChecked(true);
                        viewVisa.setVisibility(View.VISIBLE);
                    }else {
                        cbVisa.setChecked(false);
                        viewVisa.setVisibility(View.GONE);
                    }
                    if (jmlDiskons > 0){
                        cbDiskon.setChecked(true);
                        viewDiskon.setVisibility(View.VISIBLE);
                    }else {
                        cbDiskon.setChecked(false);
                        viewDiskon.setVisibility(View.GONE);
                    }

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ProspekObject> call, Throwable t) {
                loadData(id);
            }
        });
    }

    // NEXT PAGE
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
            }else if(rb3.isChecked()){
                perlengkapanNol = true;
                jenisPerlengkapan = "NOL";
            }else {
                perlengkapanFull = false;
                perlengkapanLite = false;
                perlengkapanNol= false;
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
                    if (perlengkapanFull || perlengkapanLite || perlengkapanNol){
                        EditKalkulasiActivity.goToStepTotal();
                        TotalKalkulasiFragment step3Fragment = new TotalKalkulasiFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("berangkatTgl", convertDate(sendTgl));
                        bundle.putString("jml_hari", harijadwal);
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

                        bundle.putInt("hargaDefault", hargaDefault);
                        bundle.putInt("hargaPromo", hargaPromo);
                        bundle.putInt("hargaInfant", hargaInfant);
                        bundle.putInt("hargaFull", hargaFull);
                        bundle.putInt("hargaLite", hargaLite);
                        bundle.putInt("diskonBalitaUhud", diskonBalitaUhud);
                        bundle.putInt("diskonBalitaNur", diskonBalitaNur);
                        bundle.putInt("diskonBalitaRahmah", diskonBalitaRahmah);
                        bundle.putInt("diskonBalitaStandar", diskonBalitaStandar);

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
                        EditKalkulasiActivity.goToStepTotal();
                        TotalKalkulasiFragment step3Fragment = new TotalKalkulasiFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("berangkatTgl", convertDate(sendTgl));
                        bundle.putString("jml_hari", harijadwal);
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

                        bundle.putInt("hargaDefault", hargaDefault);
                        bundle.putInt("hargaPromo", hargaPromo);
                        bundle.putInt("hargaInfant", hargaInfant);
                        bundle.putInt("hargaFull", hargaFull);
                        bundle.putInt("hargaLite", hargaLite);
                        bundle.putInt("diskonBalitaUhud", diskonBalitaUhud);
                        bundle.putInt("diskonBalitaNur", diskonBalitaNur);
                        bundle.putInt("diskonBalitaRahmah", diskonBalitaRahmah);
                        bundle.putInt("diskonBalitaStandar", diskonBalitaStandar);

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
            saveData("No");
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        Toast.makeText(getContext(), String.format("You Selected : %d/%d/%d", dayOfMonth,monthOfYear+1,year), Toast.LENGTH_LONG).show();
        followUp.setText(String.format("%d/%d/%d", dayOfMonth,monthOfYear+1,year));
        tglFollowup = String.format("%d/%d/%d", dayOfMonth,monthOfYear+1,year);
    }

    public void saveData(final String command){
        if(TextUtils.isEmpty(pic.getText().toString().trim())|| TextUtils.isEmpty(telp.getText().toString().trim()) || TextUtils.isEmpty(tglFollowup)){
            Toast.makeText(getContext(), "Pastikan nama PIC, Nomor Telepon, dan Tanggal FollowUp Terisi", Toast.LENGTH_SHORT).show();
        }else {
            if (jmlDewasa + jmlBalitaKasur == jmlKamar){
                Toast.makeText(getContext(), "Menyimpan data...", Toast.LENGTH_SHORT).show();

                picName = pic.getText().toString().trim();
                no_telp = telp.getText().toString().trim();

                if(rb1.isChecked()){
                    perlengkapanFull = true;
                    jenisPerlengkapan = "FULL";
                }else if(rb2.isChecked()){
                    perlengkapanLite = true;
                    jenisPerlengkapan = "LITE";
                }else if(rb3.isChecked()){
                    perlengkapanNol = true;
                    jenisPerlengkapan = "NOL";
                }else {
                    perlengkapanFull = false;
                    perlengkapanLite = false;
                    perlengkapanNol= false;
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
                params.put("index_jadwal", indexJadwal);
                params.put("index_paket", String.valueOf(selectedPaket));
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

//                pDialog = new ProgressDialog(getContext());
//                pDialog.setMessage("Harap tunggu...");
//                pDialog.setCancelable(false);
//                pDialog.show();

                Call<ResponseBody> result = WebApi.getAPIService().editProspek(id, params);
                result.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(), "Berhasil di simpan", Toast.LENGTH_SHORT).show();
//                        pDialog.dismiss();
                        try {
                            if(response.body()!=null){
                                if (command.equals("Yes")){
                                    Intent intent = new Intent(getContext(), KalkulasiActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Error Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "On Failure", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            }else {
                Toast.makeText(getContext(), "Pastikan Jumlah Dewasa dan Balita (Dengan Kasur) terisi sesuai dengan Jumlah Kamar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setupAdapter(){
        apiservice = UtilsApi.getAPIService();

        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("pojo_jadwal", "");
        Type type = new TypeToken<ArrayList<DataJadwal>>(){}.getType();
        List<DataJadwal> dataJadwals = gson.fromJson(json, type);

        try {
            initSpinnerJadwalCache(dataJadwals);
        }catch (Exception ex){
            initSpinnerJadwal();
        }
    }

    public void initSpinnerJadwal(){
        loading = ProgressDialog.show(getContext(), null, "Harap tunggu...", true, true);

        apiservice.getJSON(token).enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    alldata = Arrays.asList(response.body().getData());
                    for (int i = 0; i < alldata.size(); i++){
                        List <Jadwal> jadwal = Arrays.asList(alldata.get(i).getJadwal());
                        listJadwal.add(convertDate(jadwal.get(0).getTgl_berangkat()) + "\nRute : " + jadwal.get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + "\nPesawat : " + jadwal.get(0).getPesawat_berangkat() + "\nSisa Seat: " + jadwal.get(0).getSisa() + "\nHari :" + jadwal.get(0).getJml_hari() + "\nPromo :" + isPromo(jadwal.get(0).getPromo()));
                        ketJadwal.add("Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
                        tglJadwal.add(jadwal.get(0).getTgl_berangkat());
                        idJadwal.add(jadwal.get(0).getId());
                        paketJadwal.add(jadwal.get(0).getJml_hari());
                    }

                    adapterJadwal = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, listJadwal);
                    adapterJadwal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    jadwal.setAdapter(adapterJadwal);
                    jadwalLoaded = true;
                    loadData(id);
                } else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Server kantor pusat sedang dalam pemeliharaan, hubungi koordinator anda!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), KalkulasiActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<AIWAResponse> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Server kantor pusat sedang dalam pemeliharaan, hubungi koordinator anda!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), KalkulasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void initSpinnerJadwalCache(List<DataJadwal> cache){
        alldata = cache;
        for (int i = 0; i < alldata.size(); i++){
            List <Jadwal> jadwal = Arrays.asList(alldata.get(i).getJadwal());
            listJadwal.add(convertDate(jadwal.get(0).getTgl_berangkat()) + "\nRute : " + jadwal.get(0).getRute_berangkat() + " => " + jadwal .get(0).getRute_pulang() + "\nPesawat : " + jadwal.get(0).getPesawat_berangkat() + "\nSisa Seat: " + jadwal.get(0).getSisa() + "\nHari :" + jadwal.get(0).getJml_hari() + "\nPromo :" + isPromo(jadwal.get(0).getPromo()));
            ketJadwal.add("Maskapai : " + jadwal.get(0).getMaskapai() + " Hari : " + jadwal.get(0).getJml_hari());
            tglJadwal.add(jadwal.get(0).getTgl_berangkat());
            idJadwal.add(jadwal.get(0).getId());
            paketJadwal.add(jadwal.get(0).getJml_hari());
        }

        adapterJadwal = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, listJadwal);
        adapterJadwal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jadwal.setAdapter(adapterJadwal);
        jadwalLoaded = true;
        loadData(id);
    }

    String isPromo(int a){
        String promo;
        if (a == 1){
            promo = "Ya";
        }else {
            promo = "Tidak";
        }

        return promo;
    }

    public void initSpinnerPaket(List<Paket> objPaket) {
        int paketSize = objPaket.size();
        for (int i = 0; i < paketSize; i++){

            String Jenis = objPaket.get(i).getNama_paket();
            String Kamar = objPaket.get(i).getKamar();
            String harga = objPaket.get(i).getHarga();
            String hotelMadinah = objPaket.get(i).getHotel_madinah();
            String hotelMekah = objPaket.get(i).getHotel_mekkah();
            hotelPaket.add(objPaket.get(i).getNama_paket());

            if (Jenis.equals("Standard")){
                map.put("std",Jenis);
                map.put("hotel_madinah_std", hotelMadinah);
                map.put("hotel_mekkah_std", hotelMekah);
                if (Kamar.equals("Double")){
                    map.put("harga_double_std", harga);
                }else if(Kamar.equals("Triple")){
                    map.put("harga_triple_std", harga);
                }else {
                    map.put("harga_quard_std", harga);
                }
            }else if (Jenis.equals("UHUD")){
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
        if (!TextUtils.isEmpty(map.get("std"))){
            listPaket.add(map.get("std"));
            mekah = map.get("hotel_mekkah_std");
            madinah = map.get("hotel_madinah_std");
        }

        adapterHotel = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, listPaket);
        adapterHotel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hotel.setAdapter(adapterHotel);
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

    private void loadKalkulasi(){
        Call<KalkulasiResponse> call = WebApi.getAPIService().getKalkulasi();
        call.enqueue(new Callback<KalkulasiResponse>() {
            @Override
            public void onResponse(Call<KalkulasiResponse> call, Response<KalkulasiResponse> response) {
                try{
                    if (response.isSuccessful()){
                        Log.d("MSGASD", "SUCCESS");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }else {
                        Log.d("MSGASD", "FAIL");
                        Log.d("RESP", "onResponse: " +response.message());
                        Log.d("RESP", "onBody: " +response.body());
                    }
                    KalkulasiResponse kalkulasiResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(kalkulasiResponse.getKalkulasi()));
                    String jml = pojo.get(0).getHarga_visa();
                    progresif.setText(jml);

                    hargaDefault = Integer.parseInt(pojo.get(0).getHarga_default());
                    hargaPromo = Integer.parseInt(pojo.get(0).getHarga_promo());
                    hargaInfant = Integer.parseInt(pojo.get(0).getHarga_infant());
                    hargaFull = Integer.parseInt(pojo.get(0).getHarga_full());
                    hargaLite = Integer.parseInt(pojo.get(0).getHarga_lite());
                    diskonBalitaUhud = Integer.parseInt(pojo.get(0).getDiskon_balita_uhud());
                    diskonBalitaNur = Integer.parseInt(pojo.get(0).getDiskon_balita_nur());
                    diskonBalitaRahmah = Integer.parseInt(pojo.get(0).getDiskon_balita_rhm());
                    diskonBalitaStandar = Integer.parseInt(pojo.get(0).getDiskon_balita_standar());
                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<KalkulasiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), KalkulasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        kalkulasiLoaded = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c =  getContext().getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {

                String phoneNumber="",emailAddress="";
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if ( hasPhone.equalsIgnoreCase("1"))
                    hasPhone = "true";
                else
                    hasPhone = "false" ;

                if (Boolean.parseBoolean(hasPhone))
                {
                    Cursor phones = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
                    while (phones.moveToNext())
                    {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }

                // Find Email Addresses
                Cursor emails = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId,null, null);
                while (emails.moveToNext())
                {
                    emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                emails.close();

                //mainActivity.onBackPressed();
                // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();

//                tvname.setText("Name: "+name);
                pic.setText(name);
                telp.setText(phoneNumber);
//                tvmail.setText("Email: "+emailAddress);
//                Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
            }
            c.close();
        }
    }
}

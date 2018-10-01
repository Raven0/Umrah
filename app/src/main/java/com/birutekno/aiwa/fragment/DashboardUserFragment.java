package com.birutekno.aiwa.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.aiwa.JamaahActivity;
import com.birutekno.aiwa.KalkulasiActivity;
import com.birutekno.aiwa.PotkomActivity;
import com.birutekno.aiwa.R;
import com.birutekno.aiwa.helper.ChartResponse;
import com.birutekno.aiwa.helper.PeriodeResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DashboardModel;
import com.birutekno.aiwa.model.DataPeriode;
import com.birutekno.aiwa.ui.chart.LineView;
import com.birutekno.aiwa.ui.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by No Name on 7/29/2017.
 */

public class DashboardUserFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";
    private static final String TYPE = "type";

    private ProgressDialog nDialog;
    private ProgressDialog aDialog;
    private ProgressDialog bDialog;

    String id_agen;
    String token;

    int randomint = 12;

    @BindView(R.id.line_view)
    LineView line;

    @BindView(R.id.line_view_two)
    LineView line_two;

    @BindView(R.id.periode)
    Spinner periode;

    @BindView(R.id.potensiCard)
    CardView potensiCard;

    @BindView(R.id.potensi)
    TextView potensi;

    @BindView(R.id.progressPotensi)
    ProgressBar progressPotensi;

    @BindView(R.id.komisiCard)
    CardView komisiCard;

    @BindView(R.id.komisi)
    TextView komisi;

    @BindView(R.id.progressKomisi)
    ProgressBar progressKomisi;

    @BindView(R.id.prospekCard)
    CardView prospekCard;

    @BindView(R.id.prospek)
    TextView prospek;

    @BindView(R.id.progressProspek)
    ProgressBar progressProspek;

    @BindView(R.id.jamaahCard)
    CardView jamaahCard;

    @BindView(R.id.jamaah)
    TextView jamaah;

    @BindView(R.id.progressJamaah)
    ProgressBar progressJamaah;

    @BindView(R.id.cardJamaah)
    CardView cardjam;

    @BindView(R.id.cardKomisi)
    CardView cardkom;

    @BindView(R.id.judulCardJamaah)
    TextView jcardjam;

    @BindView(R.id.judulCardKomisi)
    TextView jcardkom;

    private ArrayList<DataPeriode> pojd;
    List<String> listPeriode = new ArrayList<String>();

    String selectedPeriode;

    public static DashboardUserFragment newInstance(int type) {
        DashboardUserFragment fragment = new DashboardUserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static DashboardUserFragment newInstance() {
        DashboardUserFragment fragment = new DashboardUserFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_dashboard_user;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        id_agen = prefs.getString("iduser", "0");
        token = prefs.getString("token", "0");

        initLineView(line);
        initLineView(line_two);
        loadDataProspek(String.valueOf(id_agen));

        loadOnLoad();

        periode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedPeriode = item;
                nDialog = ProgressDialog.show(getContext(), null, "Memuat Data...", true, true);
                nDialog.show();
                try {
                    loadDataPotensi(String.valueOf(id_agen), item);
                    loadDataKomisi(String.valueOf(id_agen), item);
                    loadDataJamaah(String.valueOf(id_agen), item);
//                    setDataTotalJamaahView(id_agen, item);
//                    setDataTotalPotkomView(id_agen, item);
                    nDialog.dismiss();
                }catch (Exception ex){
                    nDialog.dismiss();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPeriode = token;
                try {
                    loadDataPotensi(String.valueOf(id_agen), token);
                    loadDataKomisi(String.valueOf(id_agen), token);
                    loadDataJamaah(String.valueOf(id_agen), token);
//                    setDataTotalJamaahView(id_agen, token);
//                    setDataTotalPotkomView(id_agen, token);
                    nDialog.dismiss();
                }catch (Exception ex){
                    nDialog.dismiss();
                }
            }
        });

        jcardjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataTotalJamaahView(id_agen, selectedPeriode);
            }
        });

        jcardkom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataTotalPotkomView(id_agen, selectedPeriode);
            }
        });

        prospekCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, KalkulasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });

        jamaahCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, JamaahActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });

        potensiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PotkomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("viewpager_position", 1);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });

        komisiCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PotkomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("pos", 0);
                startActivity(intent);
            }
        });
    }

    private void initLineView(LineView lineView) {
        ArrayList<String> test = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < randomint; i++) {
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            calendar.set(Calendar.MONTH, i);
            String month_name = month_date.format(calendar.getTime());
            test.add(month_name);
        }
        lineView.setBottomTextList(test);
        lineView.setColorArray(new int[]{Color.BLUE,Color.RED});
        lineView.setDrawDotLine(true);
        lineView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void setDataTotalJamaahView(final String id, final String tahun){
        aDialog = ProgressDialog.show(getContext(), null, "Memuat Data Grafik...", true, true);
        aDialog.show();
        Call<ChartResponse> call = WebApi.getAPIService().getJamaahPerbulan(id, tahun);
        call.enqueue(new Callback<ChartResponse>() {
            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
                try{
                    //Grafik Total Jamaah
                    //Value
                    ArrayList<Integer> dataList = new ArrayList<>();
                    dataList.add(Integer.parseInt(response.body().getResponse().getJanuary()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getFebruary()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getMarch()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getApril()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getMei()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getJune()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getJuly()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getAugust()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getSeptember()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getOctober()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getNovember()));
                    dataList.add(Integer.parseInt(response.body().getResponse().getDecember()));

                    //Assign to Array
                    ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
                    dataLists.add(dataList);

                    //Set data to graph
                    line.setDataList(dataLists);

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
                aDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
//                setDataTotalJamaahView(id, tahun);
                Log.d("GRAFIK JAMAAH", "onFailure: ");
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDataTotalPotkomView(final String id, final String tahun){
        bDialog = ProgressDialog.show(getContext(), null, "Memuat Data Grafik...", true, true);
        bDialog.show();
        Call<ChartResponse> call = WebApi.getAPIService().getKomisiPerbulan(id, tahun);
        call.enqueue(new Callback<ChartResponse>() {
            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {
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
                    //Grafik Total Jamaah
                    //Value
                    ArrayList<Integer> dataList = new ArrayList<>();
                    dataList.add(removeLastThree(response.body().getResponse().getJanuary()));
                    dataList.add(removeLastThree(response.body().getResponse().getFebruary()));
                    dataList.add(removeLastThree(response.body().getResponse().getMarch()));
                    dataList.add(removeLastThree(response.body().getResponse().getApril()));
                    dataList.add(removeLastThree(response.body().getResponse().getMei()));
                    dataList.add(removeLastThree(response.body().getResponse().getJune()));
                    dataList.add(removeLastThree(response.body().getResponse().getJuly()));
                    dataList.add(removeLastThree(response.body().getResponse().getAugust()));
                    dataList.add(removeLastThree(response.body().getResponse().getSeptember()));
                    dataList.add(removeLastThree(response.body().getResponse().getOctober()));
                    dataList.add(removeLastThree(response.body().getResponse().getNovember()));
                    dataList.add(removeLastThree(response.body().getResponse().getDecember()));

                    //Assign to Array
                    ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
                    dataLists.add(dataList);

                    //Set data to graph
                    try {
                        line_two.setDataList(dataLists);
                    }catch (Exception ex){
                        Log.d("LIMITER", "onResponse: " + ex.getMessage());
                    }

                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
                bDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {
                Log.d("POTKOM", "onFailure: ");
//                setDataTotalJamaahView(id, tahun);
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataPotensi(final String id, final String tahun){
        progressPotensi.setVisibility(View.VISIBLE);
        potensi.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getUangPotensi(id, tahun);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
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
                    progressPotensi.setVisibility(View.GONE);
                    potensi.setVisibility(View.VISIBLE);
                    potensi.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    potensi.setText("Rp. " + numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                Log.d("POTENSI", "onFailure: ");
//                loadDataPotensi(id, tahun);
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataKomisi(final String id, final String tahun){
        progressKomisi.setVisibility(View.VISIBLE);
        komisi.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getUangKomisi(id, tahun);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
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
                    progressKomisi.setVisibility(View.GONE);
                    komisi.setVisibility(View.VISIBLE);
                    komisi.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    komisi.setText(numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                Log.d("KOMISI", "onFailure: ");
//                loadDataKomisi(id, tahun);
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataJamaah(final String id, final String tahun){
        progressJamaah.setVisibility(View.VISIBLE);
        jamaah.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getTotalJamaah(id, tahun);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
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
                    progressJamaah.setVisibility(View.GONE);
                    jamaah.setVisibility(View.VISIBLE);
                    jamaah.setText(response.body().getResponse().getTotal());

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
//                loadDataJamaah(id, tahun);
                Log.d("JAMAAH", "onFailure: ");
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDataProspek(final String id){
        progressProspek.setVisibility(View.VISIBLE);
        prospek.setVisibility(View.GONE);
        Call<DashboardModel> call = WebApi.getAPIService().getTotalProspek(id);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
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
                    progressProspek.setVisibility(View.GONE);
                    prospek.setVisibility(View.VISIBLE);
                    prospek.setText(response.body().getResponse().getTotal());

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
//                loadDataProspek(id);
                Log.d("PROSPEK", "onFailure: ");
                Toast.makeText(getContext(), "Server AIWA sedang dalam pemeliharaan, hubungi koordinator anda atau coba lagi", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String numberFormat(String args){
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(args);
        String formattedNumber = formatter.format(myNumber);
        return formattedNumber;
    }

    private void loadPeriode(final ArrayList<DataPeriode> cache){
        Call<PeriodeResponse> call = WebApi.getAPIService().getPeriode();
        call.enqueue(new Callback<PeriodeResponse>() {
            @Override
            public void onResponse(Call<PeriodeResponse> call, Response<PeriodeResponse> response) {
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
                    PeriodeResponse PeriodeResponse = response.body();

                    pojd = new ArrayList<>(Arrays.asList(PeriodeResponse.getData()));

                    SharedPreferences.Editor editor = getContext().getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(pojd);
                    editor.putString("pojo_periode",json);
                    editor.apply();

                    for (int i = 0; i < pojd.size() ; i++ ){
                        listPeriode.add(pojd.get(i).getJudul());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, listPeriode);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    periode.setAdapter(adapter);
                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<PeriodeResponse> call, Throwable t) {
                loadPeriodeCache(cache);
            }
        });
    }

    private void loadPeriodeCache(ArrayList<DataPeriode> cache){
        try {
            for (int i = 0; i < cache.size() ; i++ ){
                listPeriode.add(cache.get(i).getJudul());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, listPeriode);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            periode.setAdapter(adapter);
        }catch (Exception ex){
            loadPeriode(cache);
            Log.d("FAILCACHE", "onItemSelected: " + ex.getMessage());
        }
    }

    public void loadOnLoad(){
        SharedPreferences prefs = DashboardUserFragment.this.getContext().getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("pojo_periode", "");
        Type type = new TypeToken<ArrayList<DataPeriode>>(){}.getType();
        ArrayList<DataPeriode> dataPeriodes = gson.fromJson(json, type);
        loadPeriode(dataPeriodes);
    }

    public int removeLastThree(String args) {
        int output = 0;
        if (args.length() > 3){
            output = Integer.parseInt(args.substring(0, args.length() - 3));
        }else {
            output = Integer.parseInt(args);
        }
        return output;
    }
}
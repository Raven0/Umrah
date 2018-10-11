package com.birutekno.aiwa.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.adapter.PotkomAdapter;
import com.birutekno.aiwa.helper.PeriodeResponse;
import com.birutekno.aiwa.helper.PotkomResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DashboardModel;
import com.birutekno.aiwa.model.DataPeriode;
import com.birutekno.aiwa.model.DataPotkom;
import com.birutekno.aiwa.ui.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by No Name on 7/29/2017.
 */

public class KomisiKoordFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.judul)
    TextView judul;

    @BindView(R.id.nominal)
    TextView nominal;

    @BindView(R.id.searchField)
    android.support.v7.widget.SearchView searchView;

    @BindView(R.id.spinnerFilter)
    Spinner periode;

    List<String> listPeriode = new ArrayList<String>();
    String idAgen, token;

    private ArrayList<DataPotkom> pojo;
    private ArrayList<DataPeriode> pojd;
    private PotkomAdapter mAdapter;
    private ProgressDialog pDialog;

    public static KomisiKoordFragment newInstance() {
        KomisiKoordFragment fragment = new KomisiKoordFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_komisi;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        idAgen = prefs.getString("iduser", "0");
        token = prefs.getString("token", "0");

        initViews();

        SharedPreferences sp = getContext().getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("pojo_periode", "");
        Type type = new TypeToken<ArrayList<DataPeriode>>(){}.getType();
        ArrayList<DataPeriode> dataPeriodes = gson.fromJson(json, type);
        loadPeriode(dataPeriodes);

        periode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                loadJSON(item);
                loadDataKomisi(String.valueOf(idAgen),item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadJSON(token);
                loadDataKomisi(String.valueOf(idAgen),token);
            }
        });

        try {
            search(searchView);
        }catch (Exception ex){
            searchView.setQuery("",false);
            Toast.makeText(mContext, "Mohon tunggu, data sedang dimuat...", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
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

    private void loadJSON(String periode){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<PotkomResponse> call = WebApi.getAPIService().getDataKomisiKoord(String.valueOf(idAgen), periode);
        call.enqueue(new Callback<PotkomResponse>() {
            @Override
            public void onResponse(Call<PotkomResponse> call, Response<PotkomResponse> response) {
                if(response.isSuccessful()){
                    PotkomResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new PotkomAdapter(pojo, getContext(),"koord");
                    recyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<PotkomResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    private void loadDataKomisi(final String id, final String tahunSelected){
        Call<DashboardModel> call = WebApi.getAPIService().getUangKoordKomisi(id,tahunSelected);
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
                    judul.setText("Komisi Anda Saat ini");
                    nominal.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    Komisi.setText("Rp. " + numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataKomisi(id, tahunSelected);
            }
        });
    }

    private void search(android.support.v7.widget.SearchView searchView) {

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public String numberFormat(String args){
        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber = Double.parseDouble(args);
        String formattedNumber = formatter.format(myNumber);
        return formattedNumber;
    }
}

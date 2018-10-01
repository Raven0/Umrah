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
import android.widget.Toast;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.adapter.JamaahAdapter;
import com.birutekno.aiwa.helper.JamaahResponse;
import com.birutekno.aiwa.helper.PeriodeResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataJamaah;
import com.birutekno.aiwa.model.DataPeriode;
import com.birutekno.aiwa.ui.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

public class JPulangFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.searchField)
    android.support.v7.widget.SearchView sae;

    @BindView(R.id.spinnerFilter)
    Spinner periode;

    private ArrayList<DataJamaah> pojo;
    private ArrayList<DataPeriode> pojd;
    private JamaahAdapter mAdapter;
    private ProgressDialog pDialog;

    List<String> listPeriode = new ArrayList<String>();
    String id, token;

    public static JPulangFragment newInstance() {
        JPulangFragment fragment = new JPulangFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_jsemua;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        id = prefs.getString("iduser", "0");
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadJSON(token);
            }
        });

        try {
            search(sae);
        }catch (Exception ex){
            sae.setQuery("",false);
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

        Call<JamaahResponse> call = WebApi.getAPIService().getJamaahPulangAgen(String.valueOf(id),periode);
        call.enqueue(new Callback<JamaahResponse>() {
            @Override
            public void onResponse(Call<JamaahResponse> call, Response<JamaahResponse> response) {
                if(response.isSuccessful()){
                    JamaahResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getJamaah()));
                    mAdapter = new JamaahAdapter(pojo, getContext());
                    recyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<JamaahResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
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
}

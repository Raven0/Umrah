package com.birutekno.umrah.fragment;

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

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.JamaahAdapter;
import com.birutekno.umrah.helper.JamaahResponse;
import com.birutekno.umrah.helper.PeriodeResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataJamaah;
import com.birutekno.umrah.model.DataPeriode;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class JSemuaFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.searchField)
    android.support.v7.widget.SearchView sae;

    @Bind(R.id.spinnerFilter)
    Spinner periode;

    private ArrayList<DataJamaah> pojo;
    private ArrayList<DataPeriode> pojd;
    private JamaahAdapter mAdapter;
    private ProgressDialog pDialog;

    List<String> listPeriode = new ArrayList<String>();
    String id, token;

    public static JSemuaFragment newInstance() {
        JSemuaFragment fragment = new JSemuaFragment();
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
        loadPeriode();

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

    private void loadPeriode(){
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
                loadPeriode();
            }
        });
    }

    private void loadJSON(String periode){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<JamaahResponse> call = WebApi.getAPIService().getJamaahAgen(String.valueOf(id), periode);
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

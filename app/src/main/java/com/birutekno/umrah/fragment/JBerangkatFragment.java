package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.JamaahAdapter;
import com.birutekno.umrah.helper.JamaahResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataJamaah;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class JBerangkatFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.searchField)
    android.support.v7.widget.SearchView sae;

    private ArrayList<DataJamaah> pojo;
    private JamaahAdapter mAdapter;
    private ProgressDialog pDialog;

    public static JBerangkatFragment newInstance() {
        JBerangkatFragment fragment = new JBerangkatFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_jsemua;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initViews();
        loadJSON();

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

    private void loadJSON(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");

        Call<JamaahResponse> call = WebApi.getAPIService().getJamaahBerangkatAgen(String.valueOf(id));
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

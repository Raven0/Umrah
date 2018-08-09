package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class JPulangFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<DataJamaah> pojo;
    private JamaahAdapter mAdapter;
    private ProgressDialog pDialog;

    public static JPulangFragment newInstance() {
        JPulangFragment fragment = new JPulangFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_jpulang;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initViews();
        loadJSON();
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
        int id = prefs.getInt("iduser", 0);

        Call<JamaahResponse> call = WebApi.getAPIService().getJamaahAgen(String.valueOf(id));
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
}

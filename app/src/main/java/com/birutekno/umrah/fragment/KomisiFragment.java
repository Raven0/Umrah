package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.KomisiAdapter;
import com.birutekno.umrah.helper.KomisiResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataKomisi;
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

public class KomisiFragment extends BaseFragment{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<DataKomisi> pojo;
    private KomisiAdapter mAdapter;
    private ProgressDialog pDialog;

    public static KomisiFragment newInstance() {
        KomisiFragment fragment = new KomisiFragment();
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
        Call<KomisiResponse> call = WebApi.getAPIService().getKomisi();
        call.enqueue(new Callback<KomisiResponse>() {
            @Override
            public void onResponse(Call<KomisiResponse> call, Response<KomisiResponse> response) {
                if(response.isSuccessful()){
                    KomisiResponse jsonResponse = response.body();
                    Toast.makeText(getContext(), jsonResponse.getKomisi().toString(), Toast.LENGTH_SHORT).show();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getKomisi()));
                    mAdapter = new KomisiAdapter(pojo, getContext());
                    recyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<KomisiResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
            }
        });
    }
}

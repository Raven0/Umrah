package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.FotoAdapter;
import com.birutekno.umrah.helper.GalleryResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataGallery;
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

public class FotoFragment extends BaseFragment{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<DataGallery> pojo;
    private FotoAdapter mAdapter;
    private ProgressDialog pDialog;

    public static FotoFragment newInstance() {
        FotoFragment fragment = new FotoFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_foto;
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
        Call<GalleryResponse> call = WebApi.getAPIService().getGalleryFoto();
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                if(response.isSuccessful()){
                    GalleryResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new FotoAdapter(pojo, getContext());
                    recyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
            }
        });
    }
}

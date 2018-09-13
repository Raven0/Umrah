package com.birutekno.aiwa.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.adapter.VideoAdapter;
import com.birutekno.aiwa.helper.GalleryResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataGallery;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class VideoHotelFragment extends BaseFragment{

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<DataGallery> pojo;
    private VideoAdapter mAdapter;
    private ProgressDialog pDialog;

    public static VideoHotelFragment newInstance() {
        VideoHotelFragment fragment = new VideoHotelFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initViews();
        Bundle bundle = getArguments();
        loadJSON(bundle.getInt("id"));
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(final int id){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<GalleryResponse> call = WebApi.getAPIService().getHotelVideo(id);
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                if(response.isSuccessful()){
                    GalleryResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new VideoAdapter(pojo, getContext());
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
                loadJSON(id);
            }
        });
    }
}

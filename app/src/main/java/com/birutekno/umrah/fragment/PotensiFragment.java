package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.PotkomAdapter;
import com.birutekno.umrah.helper.PotkomResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.model.DataPotkom;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class PotensiFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.judul)
    TextView judul;

    @Bind(R.id.nominal)
    TextView nominal;

    private ArrayList<DataPotkom> pojo;
    private PotkomAdapter mAdapter;
    private ProgressDialog pDialog;

    public static PotensiFragment newInstance() {
        PotensiFragment fragment = new PotensiFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_komisi;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initViews();
        loadJSON();

        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        int id = prefs.getInt("iduser", 0);

        loadDataPotensi(String.valueOf(id));
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

        Call<PotkomResponse> call = WebApi.getAPIService().getDataPotensi(String.valueOf(id));
        call.enqueue(new Callback<PotkomResponse>() {
            @Override
            public void onResponse(Call<PotkomResponse> call, Response<PotkomResponse> response) {
                if(response.isSuccessful()){
                    PotkomResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getKomisi()));
                    mAdapter = new PotkomAdapter(pojo, getContext());
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

    private void loadDataPotensi(final String id){
        Call<DashboardModel> call = WebApi.getAPIService().getUangPotensi(id);
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
                    judul.setText("Potensi Anda Saat ini");
                    nominal.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    potensi.setText("Rp. " + numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataPotensi(id);
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

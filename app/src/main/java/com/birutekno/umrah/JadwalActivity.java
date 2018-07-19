package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.birutekno.umrah.adapter.JadwalAiwaAdapter;
import com.birutekno.umrah.adapter.JadwalAiwaAdapterTES;
import com.birutekno.umrah.helper.AIWAResponse;
import com.birutekno.umrah.helper.UtilsApi;
import com.birutekno.umrah.model.Data;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JadwalActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.btnSearch)
    Button btnSearch;

    @Bind(R.id.btnFilter)
    Button btnFilter;

    private String mDate = "";

    private ArrayList<Data> pojo;
    private JadwalAiwaAdapter adapter;
    private JadwalAiwaAdapterTES adapterB;

    private ProgressDialog pDialog;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, JadwalActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_jadwal;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("");

        initViews();

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJSON();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJSON2();
            }
        });
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(){
        pDialog = new ProgressDialog(JadwalActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        Call<AIWAResponse> call = UtilsApi.getAPIService().getJSON();
        call.enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {

                AIWAResponse jsonResponse = response.body();
                pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                adapter = new JadwalAiwaAdapter(pojo, getBaseContext());
                recyclerView.setAdapter(adapter);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AIWAResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    private void loadJSON2(){
        pDialog = new ProgressDialog(JadwalActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        Call<AIWAResponse> call = UtilsApi.getAPIService().getJSON();
        call.enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {

                AIWAResponse jsonResponse = response.body();
                pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                adapterB = new JadwalAiwaAdapterTES(pojo, getBaseContext());
                recyclerView.setAdapter(adapterB);
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AIWAResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.birutekno.umrah.helper.AIWAAdapter;
import com.birutekno.umrah.helper.AIWAInterface;
import com.birutekno.umrah.helper.AIWAResponse;
import com.birutekno.umrah.model.Data;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JadwalActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private String mDate = "";

    private ArrayList<Data> pojo;
    private ArrayList<Jadwal> jadwal;
    private AIWAAdapter adapter;

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
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON(){
        pDialog = new ProgressDialog(JadwalActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.124.86.218")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AIWAInterface request = retrofit.create(AIWAInterface.class);
        Call<AIWAResponse> call = request.getJSON();
        call.enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {

                AIWAResponse jsonResponse = response.body();
                pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                Toast.makeText(JadwalActivity.this, String.valueOf(pojo.size()), Toast.LENGTH_SHORT).show();
                adapter = new AIWAAdapter(pojo);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.birutekno.umrah.adapter.KalkulasiAdapter;
import com.birutekno.umrah.helper.WEBResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataProspek;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KalkulasiActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tambah)
    Button btnTambah;

    private ArrayList<DataProspek> pojo;
    private KalkulasiAdapter mAdapter;
    private String mDate = "";

    private ProgressDialog pDialog;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, KalkulasiActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_kalkulasi;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Kalkulasi");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalkulasiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initViews();
        loadJSON();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalkulasiActivity.this, InputKalkulasiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(){
        pDialog = new ProgressDialog(KalkulasiActivity.this);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<WEBResponse> call = WebApi.getAPIService().getProspek();
        call.enqueue(new Callback<WEBResponse>() {
            @Override
            public void onResponse(Call<WEBResponse> call, Response<WEBResponse> response) {
                if(response.isSuccessful()){
                    WEBResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new KalkulasiAdapter(pojo, getBaseContext());
                    mRecyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<WEBResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        title.setVisibility(View.GONE);
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                title.setVisibility(View.VISIBLE);
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

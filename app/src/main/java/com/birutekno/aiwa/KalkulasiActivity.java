package com.birutekno.aiwa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.birutekno.aiwa.adapter.KalkulasiAdapter;
import com.birutekno.aiwa.helper.ProspekResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataProspek;
import com.birutekno.aiwa.ui.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KalkulasiActivity extends BaseActivity {

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.tambah)
    Button btnTambah;

    @Bind(R.id.loadBtn)
    Button loadBtn;

    @Bind(R.id.loadView)
    LinearLayout loadview;

    private ArrayList<DataProspek> pojo;
    private KalkulasiAdapter mAdapter;

    private ProgressDialog pDialog;

    int pos;

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
                onBackPressed();
            }
        });

        initViews();

        SharedPreferences prefs = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);
        int cache = prefs.getInt("kalkulasi", 0);
        if (cache == 0){
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
            editor.putInt("kalkulasi", 1);
            editor.apply();
            loadJSON();
        }else if (cache == 1){
            SharedPreferences.Editor editor = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
            editor.putInt("kalkulasi", 0);
            editor.apply();

            Gson gson = new Gson();
            String json = prefs.getString("pojo_kalkulasi", "");
            Type type = new TypeToken<ArrayList<DataProspek>>(){}.getType();
            ArrayList<DataProspek> dataProspeks = gson.fromJson(json, type);

            loadJSONCache(dataProspeks);
        }

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalkulasiActivity.this, InputKalkulasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJSON();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            pos = extras.getInt("pos");
        }
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

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");
        Call<ProspekResponse> call = WebApi.getAPIService().getProspekAgen(String.valueOf(id));
        call.enqueue(new Callback<ProspekResponse>() {
            @Override
            public void onResponse(Call<ProspekResponse> call, Response<ProspekResponse> response) {
                if(response.isSuccessful()){
                    ProspekResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));

                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(pojo);
                    editor.putString("pojo_kalkulasi",json);
                    editor.apply();

                    mAdapter = new KalkulasiAdapter(pojo, getBaseContext());
                    mRecyclerView.setAdapter(mAdapter);
                    if (mAdapter.getItemCount() == 0){
                        loadview.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    }else {
                        loadview.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();
                    loadJSON();
                }
            }

            @Override
            public void onFailure(Call<ProspekResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(KalkulasiActivity.this, "Server Error, Coba Lagi : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }

    private void loadJSONCache(ArrayList<DataProspek> cache){
        mAdapter = new KalkulasiAdapter(cache, getBaseContext());
        mRecyclerView.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0){
            loadview.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }else {
            loadview.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    //SEARCH
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
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
//            onBackPressed();
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KalkulasiActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("viewpager_position", pos);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

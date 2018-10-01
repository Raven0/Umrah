package com.birutekno.aiwa;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.birutekno.aiwa.adapter.ItineraryAiwaAdapter;
import com.birutekno.aiwa.helper.AIWAResponse;
import com.birutekno.aiwa.helper.UtilsApi;
import com.birutekno.aiwa.model.DataJadwal;
import com.birutekno.aiwa.ui.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ItineraryActivity extends BaseActivity{

    public static final String PREFS_NAME = "AUTH";
    public static final String PREFS_CACHE = "CACHE_LOAD";
    String id_agen,token,selectedItem;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.spinnerFilter)
    Spinner spinnerFilter;

    @BindView(R.id.btn_update)
    Button buttonUpdate;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    List<String> listPeriode = new ArrayList<String>();
    private ArrayList<DataJadwal> pojo;
    private ItineraryAiwaAdapter adapter;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ItineraryActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_itinerary;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        id_agen = prefs.getString("iduser", "0");
        token = prefs.getString("token", "0");

        setupToolbar(mToolbar, true);
        setTitle("Itinerary");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initViews();

        int pos = listPeriode.indexOf(token);
        spinnerFilter.setSelection(pos);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                SharedPreferences prefs = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);
                String cache = prefs.getString("itinerary", "");
                try {
                    if (cache.equals(selectedItem)){
                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
                        editor.putString("itinerary", selectedItem);
                        editor.apply();

                        Gson gson = new Gson();
                        String json = prefs.getString("pojo_jadwal", "");
                        Type type = new TypeToken<ArrayList<DataJadwal>>(){}.getType();
                        ArrayList<DataJadwal> dataJadwals = gson.fromJson(json, type);

                        try {
                            loadJSON(selectedItem, dataJadwals);
                        }catch (Exception ex){
                            loadJSONCache(dataJadwals);
                        }

                    }else{
                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
                        editor.putString("itinerary", selectedItem);
                        editor.apply();

                        Gson gson = new Gson();
                        String json = prefs.getString("pojo_jadwal", "");
                        Type type = new TypeToken<ArrayList<DataJadwal>>(){}.getType();
                        ArrayList<DataJadwal> dataJadwals = gson.fromJson(json, type);

                        try {
                            loadJSON(selectedItem, dataJadwals);
                        }catch (Exception ex){
                            loadJSONCache(dataJadwals);
                        }
                    }
                }catch (Exception ex){
                    Gson gson = new Gson();
                    String json = prefs.getString("pojo_jadwal", "");
                    Type type = new TypeToken<ArrayList<DataJadwal>>(){}.getType();
                    ArrayList<DataJadwal> dataJadwals = gson.fromJson(json, type);

                    loadJSONCache(dataJadwals);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE);

                Gson gson = new Gson();
                String json = prefs.getString("pojo_jadwal", "");
                Type type = new TypeToken<ArrayList<DataJadwal>>(){}.getType();
                ArrayList<DataJadwal> dataJadwals = gson.fromJson(json, type);

                loadJSON(selectedItem, dataJadwals);
            }
        });
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        initSpinnerPeriode();
    }

    private void loadJSON(final String periode, final ArrayList<DataJadwal> cache){
        Call<AIWAResponse> call = UtilsApi.getAPIService().getJSON(periode);
        call.enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    try{
                        AIWAResponse jsonResponse = response.body();
                        pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));

                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_CACHE, MODE_PRIVATE).edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(pojo);
                        editor.putString("pojo_jadwal",json);
                        editor.apply();

                        adapter = new ItineraryAiwaAdapter(pojo, getBaseContext());
                        recyclerView.setAdapter(adapter);
                        Log.d("SUKSES", "onResponse: SUKSES AHAHAHAHAHAHAHAHAHAH");
                    }catch (Exception ex){
                        if (ex.getMessage() == null){
                            Toast.makeText(ItineraryActivity.this, "Data jadwal masih kosong, silahkan hubungi koordinator anda!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AIWAResponse> call, Throwable t) {
                loadJSONCache(cache);
            }
        });
    }

    private void loadJSONCache(ArrayList<DataJadwal> cache){
        try {
            cache.size();
            adapter = new ItineraryAiwaAdapter(cache, getBaseContext());
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }catch (Exception ex){
            loadJSON(token, cache);
        }
    }

    public void initSpinnerPeriode() {
        listPeriode.add("1450");
        listPeriode.add("1449");
        listPeriode.add("1448");
        listPeriode.add("1447");
        listPeriode.add("1446");
        listPeriode.add("1445");
        listPeriode.add("1444");
        listPeriode.add("1443");
        listPeriode.add("1442");
        listPeriode.add("1441");
        listPeriode.add("1440");
        listPeriode.add("1439");
        listPeriode.add("1438");
        listPeriode.add("1437");
        listPeriode.add("1436");
        listPeriode.add("1435");
        listPeriode.add("1434");
        listPeriode.add("1433");
        listPeriode.add("1432");
        listPeriode.add("1431");
        listPeriode.add("1430");

        ArrayAdapter<String> adapterC= new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_spinner_item, listPeriode);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(adapterC);
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
                adapter.getFilter().filter(newText);
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
        super.onBackPressed();
//        Intent intent = new Intent(ItineraryActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("viewpager_position", 1);
//        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

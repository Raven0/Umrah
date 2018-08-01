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

import com.birutekno.umrah.adapter.ItineraryAiwaAdapter;
import com.birutekno.umrah.helper.AIWAResponse;
import com.birutekno.umrah.helper.UtilsApi;
import com.birutekno.umrah.model.DataJadwal;
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

/**
 * Created by No Name on 7/31/2017.
 */

public class ItineraryActivity extends BaseActivity{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<DataJadwal> pojo;
    private ItineraryAiwaAdapter adapter;

    private ProgressDialog pDialog;

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
        setupToolbar(mToolbar, true);
        setTitle("Itinerary");

        initViews();
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON("1440");
    }

    private void loadJSON(String periode){
        pDialog = new ProgressDialog(ItineraryActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://115.124.86.218")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<AIWAResponse> call = UtilsApi.getAPIService().getJSON(periode);
        call.enqueue(new Callback<AIWAResponse>() {
            @Override
            public void onResponse(Call<AIWAResponse> call, Response<AIWAResponse> response) {

                AIWAResponse jsonResponse = response.body();
                pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
//                Toast.makeText(ItineraryActivity.this, String.valueOf(pojo.size()), Toast.LENGTH_SHORT).show();
                adapter = new ItineraryAiwaAdapter(pojo, getBaseContext());
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

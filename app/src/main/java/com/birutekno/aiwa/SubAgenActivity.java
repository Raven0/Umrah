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

import com.birutekno.aiwa.adapter.SubagenAdapter;
import com.birutekno.aiwa.helper.AgenResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataAgen;
import com.birutekno.aiwa.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubAgenActivity extends BaseActivity {

    public static final String PREFS_NAME = "AUTH";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.loadBtn)
    Button loadBtn;

    @BindView(R.id.loadView)
    LinearLayout loadview;

    private ArrayList<DataAgen> pojo;
    private SubagenAdapter mAdapter;

    private ProgressDialog pDialog;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SubAgenActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_agen;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Agen Saya");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initViews();
        loadJSON();

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJSON();
            }
        });
    }

    private void initViews(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(){
        pDialog = new ProgressDialog(SubAgenActivity.this);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");
        Call<AgenResponse> call = WebApi.getAPIService().getSubAgen(String.valueOf(id));
        call.enqueue(new Callback<AgenResponse>() {
            @Override
            public void onResponse(Call<AgenResponse> call, Response<AgenResponse> response) {
                if(response.isSuccessful()){
                    AgenResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getAgen()));
                    mAdapter = new SubagenAdapter(pojo, getBaseContext());
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

                }
            }

            @Override
            public void onFailure(Call<AgenResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(SubAgenActivity.this, "Server Error, Coba Lagi : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
                loadJSON();
            }
        });
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
//        Intent intent = new Intent(SubAgenActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("viewpager_position", 3);
//        startActivity(intent);
        super.onBackPressed();
    }
}

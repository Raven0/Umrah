package com.birutekno.aiwa;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.birutekno.aiwa.adapter.SapaAdapter;
import com.birutekno.aiwa.helper.SapaResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataSapa;
import com.birutekno.aiwa.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SapaanActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ArrayList<DataSapa> pojo;
    private SapaAdapter mAdapter;

    private ProgressDialog pDialog;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SapaanActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_sapa;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("Sapaan");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initViews();
        loadJSON();

//        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                ExpandableLayout expandableLayout = (ExpandableLayout) view.findViewById(R.id.expandable_layout);
//                ImageView img = (ImageView) view.findViewById(R.id.expand);
//                if (expandableLayout.isExpanded()) {
//                    expandableLayout.collapse();
//                    img.setImageResource(R.drawable.icon_plus);
////                    img.setBackgroundResource(R.drawable.icon_plus);
//                } else {
//                    expandableLayout.expand();
//                    img.setImageResource(R.drawable.icon_min);
////                    img.setBackgroundResource(R.drawable.icon_min);
//                }
//            }
//        });
    }

    private void initViews(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON(){
        pDialog = new ProgressDialog(SapaanActivity.this);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<SapaResponse> call = WebApi.getAPIService().getSapa();
        call.enqueue(new Callback<SapaResponse>() {
            @Override
            public void onResponse(Call<SapaResponse> call, Response<SapaResponse> response) {
                if(response.isSuccessful()){
                    SapaResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new SapaAdapter(pojo, getBaseContext());
                    mRecyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<SapaResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(SapaanActivity.this, "Server Error, Coba Lagi : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SapaanActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("viewpager_position", 1);
        startActivity(intent);

//        Log.d("CDA", "onBackPressed Called");
//        AlertDialog.Builder ask = new AlertDialog.Builder(FaqActivity.this);
//        ask.setTitle("Apakah Anda Akan Keluar?");
//        ask.setMessage("Tekan tombol Ya jika anda benar benar ingin keluar dari menu ini");
//        ask.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(FaqActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("viewpager_position", 2);
//                startActivity(intent);
//            }
//        });
//        ask.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        ask.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

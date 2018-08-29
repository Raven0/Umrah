package com.birutekno.umrah.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.PotkomAdapter;
import com.birutekno.umrah.helper.PeriodeResponse;
import com.birutekno.umrah.helper.PotkomResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.model.DataPeriode;
import com.birutekno.umrah.model.DataPotkom;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.birutekno.umrah.view.PaginationScrollListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class KomisiFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.main_progress)
    ProgressBar progressBar;

    @Bind(R.id.judul)
    TextView judul;

    @Bind(R.id.nominal)
    TextView nominal;

    @Bind(R.id.searchField)
    android.support.v7.widget.SearchView sae;

    @Bind(R.id.spinnerFilter)
    Spinner periode;

    List<String> listPeriode = new ArrayList<String>();
    String id, token;
    String idpot;

    private ArrayList<DataPeriode> pojd;

    PotkomAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

    String tahunSelected;
    boolean isInitialized = false;

    public static KomisiFragment newInstance() {
        KomisiFragment fragment = new KomisiFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_komisi;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        id = prefs.getString("iduser", "0");
        idpot = prefs.getString("iduser", "0");
        token = prefs.getString("token", "0");

        tahunSelected = token;

        loadPeriode();
        //init service and load data
        loadFirstPage(tahunSelected);
        //Load nominal diatas
        loadDataKomisi(String.valueOf(id), tahunSelected);

        periode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitialized){
                    adapter = new PotkomAdapter(getContext());
                    adapter.removeAll();
                    tahunSelected = parent.getItemAtPosition(position).toString();
                    loadFirstPage(tahunSelected);
                    loadDataKomisi(idpot, tahunSelected);
                    recyclerView.setAdapter(adapter);
                }else {
                    tahunSelected = parent.getItemAtPosition(position).toString();
                    loadFirstPage(tahunSelected);
                    //Load nominal diatas
                    loadDataKomisi(idpot, tahunSelected);
                    isInitialized = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search(sae);

        adapter = new PotkomAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage(tahunSelected);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadFirstPage(final String tahun) {
        firstCallTopRatedMoviesApi(tahun).enqueue(new Callback<PotkomResponse>() {
            @Override
            public void onResponse(Call<PotkomResponse> call, Response<PotkomResponse> response) {
                // Got data. Send it to adapter
                List<DataPotkom> results = fetchResults(response);
                TOTAL_PAGES = fetchTotal(response);
                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);
                if (currentPage <= TOTAL_PAGES){
                    adapter.addLoadingFooter();
                    isLoading = true;
                    currentPage += 1;

                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadNextPage(tahun);
                        }
                    }, 1000);
                }
                else{
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<PotkomResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Server bermasalah ,coba lagi" + t.getMessage(), Toast.LENGTH_SHORT).show();
                if (t.getMessage().equals("timeout")){
                    Toast.makeText(getContext(), "Server Timeout, mencoba lagi", Toast.LENGTH_SHORT).show();
                    loadFirstPage(tahun);
                }
                // TODO: 08/11/16 handle failure
            }
        });

    }

    /**
     * @param response extracts List<{@link DataPotkom>} from response
     * @return
     */
    private List<DataPotkom> fetchResults(Response<PotkomResponse> response) {
        PotkomResponse potkomResponse= response.body();
        return potkomResponse.getData();
    }

    private int fetchTotal(Response<PotkomResponse> response) {
        PotkomResponse totalPage = response.body();
        return Integer.parseInt(totalPage.getMeta().getLast_page());
    }

    private void loadNextPage(String tahun) {
        callTopRatedMoviesApi(tahun).enqueue(new Callback<PotkomResponse>() {
            @Override
            public void onResponse(Call<PotkomResponse> call, Response<PotkomResponse> response) {
                try {
                    adapter.removeLoadingFooter();
                }catch (Exception ex){
                    Log.d("TAG", "onResponse: " + ex.getMessage());
                }
                isLoading = false;
                List<DataPotkom> results = fetchResults(response);
                adapter.addAll(results);
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<PotkomResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
//                if (t.getMessage().equals("timeout")){
//                    Toast.makeText(MainActivity.this, "Server Timeout, mencoba lagi", Toast.LENGTH_SHORT).show();
//                    loadNextPage();
//                }
            }
        });
    }


    /**
     * Performs a Retrofit call to the top rated movies API.
     * Same API call for Pagination.
     * As {@link #currentPage} will be incremented automatically
     * by @{@link PaginationScrollListener} to load next page.
     */
    private Call<PotkomResponse> callTopRatedMoviesApi(String tahun) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");

        return WebApi.getAPIService().getDataKomisi(String.valueOf(id), tahun, currentPage);
    }

    private Call<PotkomResponse> firstCallTopRatedMoviesApi(String tahun) {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");

        currentPage = 1;
        return WebApi.getAPIService().getDataKomisi(String.valueOf(id), tahun, 1);
    }

    private void loadDataKomisi(final String id, final String tahunSelected){
        // TODO: Filter Periode
        Call<DashboardModel> call = WebApi.getAPIService().getUangKomisi(id, tahunSelected);
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
                    judul.setText("Komisi Anda Saat ini");
                    nominal.setText("Rp. " + numberFormat(response.body().getResponse().getTotal()));
//                    Komisi.setText("Rp. " + numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataKomisi(id,tahunSelected);
            }
        });
    }

    private void search(android.support.v7.widget.SearchView searchView) {

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void loadPeriode(){
        Call<PeriodeResponse> call = WebApi.getAPIService().getPeriode();
        call.enqueue(new Callback<PeriodeResponse>() {
            @Override
            public void onResponse(Call<PeriodeResponse> call, Response<PeriodeResponse> response) {
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
                    PeriodeResponse PeriodeResponse = response.body();
                    pojd = new ArrayList<>(Arrays.asList(PeriodeResponse.getData()));
                    for (int i = 0; i < pojd.size() ; i++ ){
                        listPeriode.add(pojd.get(i).getJudul());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.simple_spinner_item, listPeriode);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    periode.setAdapter(adapter);
                    Toast.makeText(mContext, "Silahkan pilih periode terlebih dahulu", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<PeriodeResponse> call, Throwable t) {
                loadPeriode();
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

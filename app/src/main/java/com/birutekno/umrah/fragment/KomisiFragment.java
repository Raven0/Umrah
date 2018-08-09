package com.birutekno.umrah.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.PotkomAdapter;
import com.birutekno.umrah.helper.PotkomResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.model.DataPotkom;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.birutekno.umrah.view.PaginationScrollListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    PotkomAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;

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
        adapter = new PotkomAdapter(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
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

        //init service and load data
        loadFirstPage();

        //Load nominal diatas
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        int id = prefs.getInt("iduser", 0);
        loadDataKomisi(String.valueOf(id));
    }

    private void loadFirstPage() {
        callTopRatedMoviesApi().enqueue(new Callback<PotkomResponse>() {
            @Override
            public void onResponse(Call<PotkomResponse> call, Response<PotkomResponse> response) {
                // Got data. Send it to adapter

                List<DataPotkom> results = fetchResults(response);
                TOTAL_PAGES = fetchTotal(response);

                progressBar.setVisibility(View.GONE);
                adapter.addAll(results);
                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<PotkomResponse> call, Throwable t) {
                t.printStackTrace();
                if (t.getMessage().equals("timeout")){
                    Toast.makeText(getContext(), "Server Timeout, mencoba lagi", Toast.LENGTH_SHORT).show();
                    loadFirstPage();
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

    private void loadNextPage() {
        callTopRatedMoviesApi().enqueue(new Callback<PotkomResponse>() {
            @Override
            public void onResponse(Call<PotkomResponse> call, Response<PotkomResponse> response) {
                adapter.removeLoadingFooter();
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
    private Call<PotkomResponse> callTopRatedMoviesApi() {
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        int id = prefs.getInt("iduser", 0);

        return WebApi.getAPIService().getDataKomisi(String.valueOf(id), currentPage);
    }

    private void loadDataKomisi(final String id){
        Call<DashboardModel> call = WebApi.getAPIService().getUangKomisi(id);
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
//                    komisi.setText(numberFormat(getFirstFour(response.body().getResponse().getTotal()))+" K");

                }catch (Exception ex){
//                    nDialog.dismiss();
//                    Toast.makeText(getContext(), "Exception " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception" , ex.getMessage());
                }
            }
            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                loadDataKomisi(id);
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

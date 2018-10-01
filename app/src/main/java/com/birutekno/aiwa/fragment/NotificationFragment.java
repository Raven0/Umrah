package com.birutekno.aiwa.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.adapter.NotificationAdapter;
import com.birutekno.aiwa.helper.NotifResponse;
import com.birutekno.aiwa.helper.WebApi;
import com.birutekno.aiwa.model.DataNotification;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class NotificationFragment extends BaseFragment{

    public static final String PREFS_NAME = "AUTH";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.queryButton)
    Button clearBtn;

    private ArrayList<DataNotification> pojo;
    private NotificationAdapter mAdapter;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        initViews();
        loadJSON();
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNotif();
            }
        });
    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void loadJSON(){
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");

        Call<NotifResponse> call = WebApi.getAPIService().getNotification(String.valueOf(id));
        call.enqueue(new Callback<NotifResponse>() {
            @Override
            public void onResponse(Call<NotifResponse> call, Response<NotifResponse> response) {
                if(response.isSuccessful()){
                    NotifResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new NotificationAdapter(pojo, getContext());
                    recyclerView.setAdapter(mAdapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<NotifResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    public void clearNotif(){
        SharedPreferences prefs = getContext().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        String id = prefs.getString("iduser", "0");

        Call<ResponseBody> call = WebApi.getAPIService().clearNotification(String.valueOf(id));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                   mAdapter.clearData();
                   recyclerView.setAdapter(mAdapter);
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}

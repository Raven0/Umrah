package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.birutekno.umrah.adapter.FaqAdapter;
import com.birutekno.umrah.helper.FaqResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataFaq;
import com.birutekno.umrah.ui.BaseActivity;
import com.birutekno.umrah.ui.view.BaseRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FaqActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recyclerView)
    BaseRecyclerView mRecyclerView;

    private ArrayList<DataFaq> pojo;
    private FaqAdapter mAdapter;

    private ProgressDialog pDialog;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, FaqActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_faq;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        setTitle("FAQ");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaqActivity.this, MainActivity.class);
                startActivity(intent);
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
        pDialog = new ProgressDialog(FaqActivity.this);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<FaqResponse> call = WebApi.getAPIService().getFaq();
        call.enqueue(new Callback<FaqResponse>() {
            @Override
            public void onResponse(Call<FaqResponse> call, Response<FaqResponse> response) {
                if(response.isSuccessful()){
                    FaqResponse jsonResponse = response.body();
                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mAdapter = new FaqAdapter(pojo, getBaseContext());
                    mRecyclerView.setAdapter(mAdapter);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<FaqResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(FaqActivity.this, "Server Error, Coba Lagi : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

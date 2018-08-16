package com.birutekno.umrah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birutekno.umrah.adapter.BannerPagerAdapter;
import com.birutekno.umrah.adapter.HotelPagerAdapter;
import com.birutekno.umrah.helper.AutoScrollViewPager;
import com.birutekno.umrah.helper.CirclePageIndicator;
import com.birutekno.umrah.helper.GalleryResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataGallery;
import com.birutekno.umrah.model.DataHotel;
import com.birutekno.umrah.model.HotelObject;
import com.birutekno.umrah.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailHotelActivity extends BaseActivity {

    private HotelPagerAdapter mAdapter;
    private BannerPagerAdapter mBannerAdapter;

    String id;
    String nama;

    private DataHotel pojo;
    private ArrayList<DataGallery> data;
    private ProgressDialog pDialog;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.infoText)
    TextView mInfoText;

    @Bind(R.id.infoTab)
    LinearLayout mInfoTab;

    @Bind(R.id.infoIndicator)
    LinearLayout mInfoIndicator;

    @Bind(R.id.fasilitasText)
    TextView mFasilitasText;

    @Bind(R.id.fasilitasTab)
    LinearLayout mFasilitasTab;

    @Bind(R.id.fasilitasIndicator)
    LinearLayout mFasilitasIndicator;

    @Bind(R.id.lokasiText)
    TextView mLokasiText;

    @Bind(R.id.lokasiTab)
    LinearLayout mLokasiTab;

    @Bind(R.id.lokasiIndicator)
    LinearLayout mLokasiIndicator;

    @Bind(R.id.videoText)
    TextView mVideoText;

    @Bind(R.id.videoTab)
    LinearLayout mVideoTab;

    @Bind(R.id.videoIndicator)
    LinearLayout mVideocator;

    @Bind(R.id.pagerAnj)
    ViewPager mPager;

    @Bind(R.id.fab)
    ImageView fab;

    @Bind(R.id.pager)
    protected AutoScrollViewPager bPager;

    @Bind(R.id.circle_indicator)
    protected CirclePageIndicator mIndicator;

    @OnClick(R.id.infoTab)
    void fotoClicked(){
        mPager.setCurrentItem(0);
    }

    @OnClick(R.id.fasilitasTab)
    void fasilitasClicked(){
        mPager.setCurrentItem(1);
    }

    @OnClick(R.id.lokasiTab)
    void lokasiClicked(){
        mPager.setCurrentItem(2);
    }

    @OnClick(R.id.videoTab)
    void videoClicked(){
        mPager.setCurrentItem(3);
    }

    @OnClick(R.id.fab)
    void fabClicked(){
        shareDataHotel(id);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, DetailHotelActivity.class);
        return intent;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_detail_hotel;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        setupToolbar(mToolbar, true);
        id= getIntent().getStringExtra("id");
        nama= getIntent().getStringExtra("nama");
        setTitle(nama);
        loadJSON(id);
        initBanner();
    }

    private void setupPager(DataHotel obj) {
        mAdapter = new HotelPagerAdapter(getSupportFragmentManager(), obj);
        mPager.setAdapter(mAdapter);
        mPager.setOffscreenPageLimit(2);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources()
                .getDisplayMetrics());
        mPager.setPageMargin(pageMargin);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mInfoIndicator.setVisibility(View.VISIBLE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mFasilitasIndicator.setVisibility(View.GONE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mLokasiIndicator.setVisibility(View.GONE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mVideocator.setVisibility(View.GONE);
                        break;
                    case 1:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mInfoIndicator.setVisibility(View.GONE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mFasilitasIndicator.setVisibility(View.VISIBLE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mLokasiIndicator.setVisibility(View.GONE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mVideocator.setVisibility(View.GONE);
                        break;
                    case 2:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mInfoIndicator.setVisibility(View.GONE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mFasilitasIndicator.setVisibility(View.GONE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mLokasiIndicator.setVisibility(View.VISIBLE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mVideocator.setVisibility(View.GONE);
                        break;
                    case 3:
                        mInfoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mInfoIndicator.setVisibility(View.GONE);

                        mFasilitasText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mFasilitasIndicator.setVisibility(View.GONE);

                        mLokasiText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.grey));
                        mLokasiIndicator.setVisibility(View.GONE);

                        mVideoText.setTextColor(ContextCompat.getColor(DetailHotelActivity.this, R.color.black));
                        mVideocator.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void loadJSON(final String id){
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<HotelObject> call = WebApi.getAPIService().getHotelDetail(Integer.valueOf(id));
        call.enqueue(new Callback<HotelObject>() {
            @Override
            public void onResponse(Call<HotelObject> call, Response<HotelObject> response) {
                if(response.isSuccessful()){
                    HotelObject jsonResponse = response.body();
                    pojo = jsonResponse.getDataHotel();
                    setupPager(pojo);
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<HotelObject> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
                loadJSON(id);
            }
        });
    }

    private void initBanner(){
        Call<GalleryResponse> call = WebApi.getAPIService().getGalleryFoto();
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                if(response.isSuccessful()){
                    GalleryResponse jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mBannerAdapter = new BannerPagerAdapter(getSupportFragmentManager(), data);

                    int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
                    mPager.setPageMargin(gap);

                    mBannerAdapter.setData(data);
                    bPager.setClipToPadding(false);
                    bPager.setAdapter(mBannerAdapter);
                    mIndicator.setViewPager(bPager);

                    if(data.size() > 1){
                        bPager.setInterval(3000);
                        bPager.setAutoScrollDurationFactor(10);
                        bPager.startAutoScroll();
                    }else if(data.size() == 1){
                        bPager.stopAutoScroll();
                    }
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                initBanner();
            }
        });
    }

    private void shareDataHotel(final String id){
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Mengambil data hotel...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<HotelObject> call = WebApi.getAPIService().getHotelDetail(Integer.valueOf(id));
        call.enqueue(new Callback<HotelObject>() {
            @Override
            public void onResponse(Call<HotelObject> call, Response<HotelObject> response) {
                pDialog.dismiss();
                if(response.isSuccessful()){
                    HotelObject jsonResponse = response.body();
                    pojo = jsonResponse.getDataHotel();

                    String whatsapp =
                            "Info Hotel :\n" + pojo.getInfo() +
                            "\n\nFasilitas Hotel :" +
                            "\nAkses Wifi : " + pojo.getWifi() +
                            "\nParkir Pribadi : " + pojo.getPark() +
                            "\nKamar Bebas Rokok : " + pojo.getKamar_rokok() +
                            "\nKamar ber-AC : " + pojo.getKamar_ac() +
                            "\nKamar Keluarga : " + pojo.getKamar_keluarga() +
                            "\nMakanan Enak : " + pojo.getMakanan_enak();

                    Intent shareIntent = new Intent();
                    shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, whatsapp);
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(shareIntent,"Share with"));
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<HotelObject> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
                loadJSON(id);
            }
        });
    }
}

package com.birutekno.umrah.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;

import com.birutekno.umrah.GalleryActivity;
import com.birutekno.umrah.ItineraryActivity;
import com.birutekno.umrah.JadwalActivity;
import com.birutekno.umrah.JamaahActivity;
import com.birutekno.umrah.KalkulasiActivity;
import com.birutekno.umrah.LokasiActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.adapter.BannerAdapter;
import com.birutekno.umrah.adapter.BannerPagerAdapter;
import com.birutekno.umrah.adapter.BrosurAdapter;
import com.birutekno.umrah.helper.AutoScrollViewPager;
import com.birutekno.umrah.helper.CirclePageIndicator;
import com.birutekno.umrah.helper.GalleryResponse;
import com.birutekno.umrah.helper.WebApi;
import com.birutekno.umrah.model.DataBrosur;
import com.birutekno.umrah.model.DataGallery;
import com.birutekno.umrah.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by No Name on 7/29/2017.
 */

public class HomeFragment extends BaseFragment{

//    implements BannerItemView.OnActionListener
    private ArrayList<DataGallery> data;

    @OnClick(R.id.jamaah)
    void jamaahClicked() {
        Intent intent = JamaahActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.kalkulasi)
    void kalkulasiClicked() {

        Intent intent = KalkulasiActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.jadwal)
    void jadwalClicked() {

        Intent intent = JadwalActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());

//        NotificationManager notif=(NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notify=new Notification.Builder
//                (getContext().getApplicationContext()).setContentTitle("Please check payment status").setContentText("The Payment Status of Jamaah ID 12 Is Almost Over").
//                setContentTitle("Payment Status").setSmallIcon(R.drawable.ic_notif_white).build();
//
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//        notif.notify(0, notify);
    }

    @OnClick(R.id.itinerary)
    void itineraryClicked() {
        Intent intent = ItineraryActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.lokasi)
    void lokasiClicked() {
        Intent intent = LokasiActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.galeri)
    void galeriClicked() {
        Intent intent = GalleryActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

//    @Bind(R.id.brosur)
//    RecyclerView recyclerViewBrosur;
//
//    @Bind(R.id.voucher)
//    RecyclerView recyclerViewVoucher;

    @Bind(R.id.pager)
    protected AutoScrollViewPager mPager;

    @Bind(R.id.circle_indicator)
    protected CirclePageIndicator mIndicator;

    private ArrayList<DataBrosur> pojo;
    private ProgressDialog pDialog;
    private BrosurAdapter brosurAdapter;
    private BannerAdapter mStationaryAdapter;
    private BannerPagerAdapter mBannerAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
//        setUpRecyclerView();
        initBanner();
//        loadJSON();
    }

//    private void setUpRecyclerView() {
//        recyclerViewBrosur.setHasFixedSize(true);
//        recyclerViewBrosur.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewBrosur.setAdapter(brosurAdapter);
//
//        recyclerViewVoucher.setHasFixedSize(true);
//        recyclerViewVoucher.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewVoucher.setAdapter(mStationaryAdapter);
//    }

//    @Override
//    public void onClick(BannerItemView view) {
////        String itemPos = String.valueOf(view.getAdapterPosition());
////        view.getBanner().getId()
////        view.getBanner().getImage();
////        String itemIds = String.valueOf(view.getBanner().getId());
////        String itemImgS = String.valueOf(view.getBanner().getImage());
////        int itemId = view.getBanner().getId();
//        int itemImg = view.getBanner().getImage();
//
////        Drawable image = getResources().getDrawable(itemImg);
////        Drawable image = getResources().getDrawable(itemId);
//
////        changeMe.setImageResource(itemImg);
//
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),itemImg);
//        String path = getContext().getExternalCacheDir()+"/shareimage.jpg";
//        java.io.OutputStream out = null;
//        java.io.File file=new java.io.File(path);
//        try { out = new java.io.FileOutputStream(file); bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); out.flush(); out.close(); } catch (Exception e) { e.printStackTrace(); } path=file.getPath();
//        Uri bmpUri = Uri.parse("file://"+path);
//
//        Intent shareIntent = new Intent();
//        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//        shareIntent.setType("*/*");
//
//        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
//        shareIntent.putExtra(Intent.EXTRA_TEXT, "Alhijaz Indowisata Agen");
//        startActivity(Intent.createChooser(shareIntent,"Share with"));
//
////        Toast.makeText(mContext, itemId + " and " + itemImg + " with " + image.toString(), Toast.LENGTH_SHORT).show();
//
//    }

    private void initBanner(){
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Harap tunggu...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<GalleryResponse> call = WebApi.getAPIService().getGalleryFoto();
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                if(response.isSuccessful()){
                    GalleryResponse jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    mBannerAdapter = new BannerPagerAdapter(getChildFragmentManager(), data);

                    int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
                    mPager.setPageMargin(gap);

                    mBannerAdapter.setData(data);
                    mPager.setClipToPadding(false);
                    mPager.setAdapter(mBannerAdapter);
                    mIndicator.setViewPager(mPager);

                    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                int pos = mPager.getCurrentItem();
//                int size = data.size();
//
//                if(pos == size){
//                    mPager.setCurrentItem(1);
//                }

                        }

                        @Override
                        public void onPageSelected(int position) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
//                Toast.makeText(mContext, state , Toast.LENGTH_SHORT).show();

                        }
                    });

                    if(data.size() > 1){
                        mPager.setInterval(3000);
                        mPager.setAutoScrollDurationFactor(10);
                        mPager.startAutoScroll();
                    }else if(data.size() == 1){
                        mPager.stopAutoScroll();
                    }
                    pDialog.dismiss();
                }else {
                    Log.d("ERROR CODE" , String.valueOf(response.code()));
                    Log.d("ERROR BODY" , response.errorBody().toString());
                    pDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                pDialog.dismiss();
                initBanner();
            }
        });
//        final List<Banner> data = new ArrayList<>();
//
//        data.add(new Banner(1, R.drawable.banners));
//        data.add(new Banner(2, R.drawable.image1));
//        data.add(new Banner(3, R.drawable.image2));
//
//        mBannerAdapter = new BannerPagerAdapter(getChildFragmentManager(), data);
//
//        int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
//        mPager.setPageMargin(gap);
//
//        mBannerAdapter.setData(data);
//        mPager.setClipToPadding(false);
//        mPager.setAdapter(mBannerAdapter);
//        mIndicator.setViewPager(mPager);
//
//        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                int pos = mPager.getCurrentItem();
////                int size = data.size();
////
////                if(pos == size){
////                    mPager.setCurrentItem(1);
////                }
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                Toast.makeText(mContext, state , Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        if(data.size() > 1){
//            mPager.setInterval(3000);
//            mPager.setAutoScrollDurationFactor(10);
//            mPager.startAutoScroll();
//        }else if(data.size() == 1){
//            mPager.stopAutoScroll();
//        }
    }

//    private void loadJSON(){
//        pDialog = new ProgressDialog(getContext());
//        pDialog.setMessage("Harap tunggu...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//        Call<BrosurResponse> call = WebApi.getAPIService().getBrosur();
//        call.enqueue(new Callback<BrosurResponse>() {
//            @Override
//            public void onResponse(Call<BrosurResponse> call, Response<BrosurResponse> response) {
//                if(response.isSuccessful()){
//                    BrosurResponse jsonResponse = response.body();
//                    pojo = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
//                    brosurAdapter = new BrosurAdapter(pojo, getContext());
//                    recyclerViewBrosur.setAdapter(brosurAdapter);
//                    pDialog.dismiss();
//                }else {
//                    Log.d("ERROR CODE" , String.valueOf(response.code()));
//                    Log.d("ERROR BODY" , response.errorBody().toString());
//                    pDialog.dismiss();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BrosurResponse> call, Throwable t) {
//                Log.d("Error",t.getMessage());
//                pDialog.dismiss();
//            }
//        });
//    }
}

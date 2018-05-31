package com.birutekno.umrah.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.birutekno.umrah.InputActivity;
import com.birutekno.umrah.R;
import com.birutekno.umrah.WhatsapActivity;
import com.birutekno.umrah.adapter.BannerAdapter;
import com.birutekno.umrah.adapter.BannerPagerAdapter;
import com.birutekno.umrah.helper.AutoScrollViewPager;
import com.birutekno.umrah.helper.CirclePageIndicator;
import com.birutekno.umrah.model.Banner;
import com.birutekno.umrah.ui.fragment.BaseFragment;
import com.birutekno.umrah.view.BannerItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by No Name on 7/29/2017.
 */

public class HomeFragment extends BaseFragment implements BannerItemView.OnActionListener {

    @OnClick(R.id.absensi)
    void absensiClicked() {
        Intent intent = InputActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.akademik)
    void akademikClicked() {

        Intent intent = WhatsapActivity.createIntent(getActivity());
        ActivityOptionsCompat optionsProfile = ActivityOptionsCompat.makeCustomAnimation(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        ActivityCompat.startActivity(getActivity(), intent, optionsProfile.toBundle());
    }

    @OnClick(R.id.jadwal)
    void jadwalClicked() {

        NotificationManager notif=(NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getContext().getApplicationContext()).setContentTitle("Please check payment status").setContentText("The Payment Status of Jamaah ID 12 Is Almost Over").
                setContentTitle("Payment Status").setSmallIcon(R.drawable.ic_notif_white).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }

    @OnClick(R.id.nilai)
    void nilaiClicked() {
    }

    @OnClick(R.id.pembayaran)
    void pembayaranClicked() {
    }

    @OnClick(R.id.anjas)
    void anjasClicked() {
    }

    @Bind(R.id.tutoringRecyclerView)
    RecyclerView mTutoringRecyclerView;

    @Bind(R.id.pager)
    protected AutoScrollViewPager mPager;

    @Bind(R.id.circle_indicator)
    protected CirclePageIndicator mIndicator;

    private BannerAdapter mTutoringAdapter;
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
        setUpAdapter();
        setUpRecyclerView();
        setData();
        initBanner();
    }

    private void setUpAdapter() {
        mTutoringAdapter = new BannerAdapter(mContext);
        mTutoringAdapter.setOnActionListener(this);

        mStationaryAdapter = new BannerAdapter(mContext);
        mStationaryAdapter.setOnActionListener(this);
    }

    private void setUpRecyclerView() {
        mTutoringRecyclerView.setHasFixedSize(true);
        mTutoringRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTutoringRecyclerView.setAdapter(mTutoringAdapter);

//        mStationaryRecyclerView.setHasFixedSize(true);
//        mStationaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        mStationaryRecyclerView.setAdapter(mStationaryAdapter);
    }

    private void setData() {
        List<Banner> data = new ArrayList<>();

        data.add(new Banner(4, R.drawable.brosur1));
        data.add(new Banner(5, R.drawable.brosur2));
        data.add(new Banner(6, R.drawable.brosur3));

        mTutoringAdapter.addAll(data);
        mTutoringAdapter.notifyDataSetChanged();

        mStationaryAdapter.addAll(data);
        mStationaryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(BannerItemView view) {
//        String itemPos = String.valueOf(view.getAdapterPosition());
//        view.getBanner().getId()
//        view.getBanner().getImage();
//        String itemIds = String.valueOf(view.getBanner().getId());
//        String itemImgS = String.valueOf(view.getBanner().getImage());
//        int itemId = view.getBanner().getId();
        int itemImg = view.getBanner().getImage();

//        Drawable image = getResources().getDrawable(itemImg);
//        Drawable image = getResources().getDrawable(itemId);

//        changeMe.setImageResource(itemImg);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),itemImg);
        String path = getContext().getExternalCacheDir()+"/shareimage.jpg";
        java.io.OutputStream out = null;
        java.io.File file=new java.io.File(path);
        try { out = new java.io.FileOutputStream(file); bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); out.flush(); out.close(); } catch (Exception e) { e.printStackTrace(); } path=file.getPath();
        Uri bmpUri = Uri.parse("file://"+path);

        Intent shareIntent = new Intent();
        shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("*/*");

        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Alhijaz Indowisata Agen");
        startActivity(Intent.createChooser(shareIntent,"Share with"));

//        Toast.makeText(mContext, itemId + " and " + itemImg + " with " + image.toString(), Toast.LENGTH_SHORT).show();

    }

    private void initBanner(){
        final List<Banner> data = new ArrayList<>();

        data.add(new Banner(1, R.drawable.banners));
        data.add(new Banner(2, R.drawable.image1));
        data.add(new Banner(3, R.drawable.image2));

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
    }
}

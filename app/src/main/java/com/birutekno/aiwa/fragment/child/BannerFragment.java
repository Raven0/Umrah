package com.birutekno.aiwa.fragment.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.model.DataGallery;
import com.birutekno.aiwa.ui.fragment.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by Suitmedia on 9/21/2016.
 */

public class BannerFragment extends BaseFragment {

    @BindView(R.id.image)
    ImageView mImage;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private DataGallery mBanner = new DataGallery();

    public static Fragment newInstance(DataGallery Banner) {
        BannerFragment fragment = new BannerFragment();
        fragment.mBanner = Banner;
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_banner;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        String link = "http://"+mBanner.getFile();
        if (mBanner != null) {
            mImage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            try {
                Picasso.get().load(link).fit().centerCrop().into(mImage);
            }catch (Exception ex){
                Log.d("ERROR_MSG", "onBindViewViewHolder: " + ex.getMessage());
            }
//            mImage.setImageResource(mBanner.getFile());
//            Log.e("BANNER", "not" + mBanner.getImage());
        }else{
            mImage.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            Log.e("BANNER", "null");
        }
    }
}

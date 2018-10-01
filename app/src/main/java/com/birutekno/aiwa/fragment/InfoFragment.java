package com.birutekno.aiwa.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.birutekno.aiwa.R;
import com.birutekno.aiwa.ui.fragment.BaseFragment;

import butterknife.BindView;

/**
 * Created by No Name on 7/29/2017.
 */

public class InfoFragment extends BaseFragment{

    @BindView(R.id.infotv)
    TextView info;

    @BindView(R.id.maps)
    LinearLayout mapsv;
//
//    @BindView(R.id.whatsapp)
//    LinearLayout whatsapp;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        return fragment;
    }

    @Override
    protected int getResourceLayout() {
        return R.layout.fragment_informasi;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String text = bundle.getString("info");
        final String maps = bundle.getString("maps");
        info.setText(String.valueOf(text));

        mapsv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(maps));
                try {
                    getContext().startActivity(i);
                }catch (Exception ex){
                    Toast.makeText(getContext(), "Link Maps tidak valid! silahkan hubungi koordinator anda!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//
//        whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent();
//                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, maps);
//                shareIntent.setPackage("com.whatsapp");
//                startActivity(shareIntent);
////                startActivity(Intent.createChooser(shareIntent,"Share with"));
//            }
//        });
    }
}

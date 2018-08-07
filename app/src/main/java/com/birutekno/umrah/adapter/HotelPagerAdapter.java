package com.birutekno.umrah.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.birutekno.umrah.fragment.FasilitasFragment;
import com.birutekno.umrah.fragment.FotoHotelFragment;
import com.birutekno.umrah.fragment.InfoFragment;
import com.birutekno.umrah.fragment.VideoHotelFragment;
import com.birutekno.umrah.model.DataHotel;

/**
 * Created by No Name on 7/29/2017.
 */

public class HotelPagerAdapter extends FragmentStatePagerAdapter {

    DataHotel dataHotel;

    String id;
    String link_map;
    String wifi;
    String kamar_rokok;
    String park;
    String kamar_ac;
    String kamar_keluarga;
    String makanan_enak;
    String info;

    public HotelPagerAdapter(FragmentManager fm, DataHotel dataHotel) {
        super(fm);
        this.dataHotel = dataHotel;
    }

    @Override
    public Fragment getItem(int position) {

        id = dataHotel.getId();
        link_map = dataHotel.getLink_map();
        wifi = dataHotel.getWifi();
        kamar_rokok = dataHotel.getKamar_rokok();
        park = dataHotel.getPark();
        kamar_ac = dataHotel.getKamar_ac();
        kamar_keluarga = dataHotel.getKamar_keluarga();
        makanan_enak = dataHotel.getMakanan_enak();
        info = dataHotel.getInfo();

        switch (position) {
            case 0:
                InfoFragment infoFragment = new InfoFragment();
                Bundle args = new Bundle();
                args.putString("info", info);
                args.putString("maps", link_map);
                infoFragment.setArguments(args);
                return infoFragment;
            case 1:
                FasilitasFragment fasilitasFragment = new FasilitasFragment();
                Bundle args1 = new Bundle();
                args1.putString("wifi", wifi);
                args1.putString("park", park);
                args1.putString("roko", kamar_rokok);
                args1.putString("ac", kamar_ac);
                args1.putString("keluarga", kamar_keluarga);
                args1.putString("makanan", makanan_enak);
                fasilitasFragment.setArguments(args1);
                return fasilitasFragment;
            case 2:
                FotoHotelFragment fotoFragment = new FotoHotelFragment();
                Bundle args2 = new Bundle();
                args2.putInt("id", Integer.parseInt(id));
                fotoFragment.setArguments(args2);
                return fotoFragment ;
            case 3:
                VideoHotelFragment videoFragment = new VideoHotelFragment();
                Bundle args3 = new Bundle();
                args3.putInt("id", Integer.parseInt(id));
                videoFragment.setArguments(args3);
                return videoFragment;
            default:
                return InfoFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
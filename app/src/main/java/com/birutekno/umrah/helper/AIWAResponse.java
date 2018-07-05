package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.Data;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;

public class AIWAResponse {
    private Data[] data;
    private Jadwal[] jadwal;
    private Paket[] paket;

    public Data[] getData() {
        return data;
    }
    public Jadwal[] getJadwal() {
        return  jadwal;
    }
    public Paket[] getPaket() {
        return paket;
    }
}

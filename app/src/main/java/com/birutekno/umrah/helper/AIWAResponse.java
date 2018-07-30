package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataJadwal;
import com.birutekno.umrah.model.Jadwal;
import com.birutekno.umrah.model.Paket;

public class AIWAResponse {
    private DataJadwal[] data;
    private Jadwal[] jadwal;
    private Paket[] paket;

    public DataJadwal[] getData() {
        return data;
    }
    public Jadwal[] getJadwal() {
        return  jadwal;
    }
    public Paket[] getPaket() {
        return paket;
    }
}

package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataJadwal;
import com.birutekno.aiwa.model.Jadwal;
import com.birutekno.aiwa.model.Paket;

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

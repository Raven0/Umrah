package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.Jadwal;

public class Data {
    private Jadwal[] jadwal;

    public Jadwal[] getJadwal ()
    {
        return jadwal;
    }

    public void setJadwal (Jadwal[] jadwal)
    {
        this.jadwal = jadwal;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [jadwal = "+jadwal+"]";
    }
}

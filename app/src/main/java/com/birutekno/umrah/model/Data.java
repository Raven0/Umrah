package com.birutekno.umrah.model;

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

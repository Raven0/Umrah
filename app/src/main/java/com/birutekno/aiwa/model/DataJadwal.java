package com.birutekno.aiwa.model;

public class DataJadwal {
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

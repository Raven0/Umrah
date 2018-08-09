package com.birutekno.umrah.model;

public class DataNotification {
    private String id;

    private String pesan;

    private String status;

    private String anggota_id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPesan ()
    {
        return pesan;
    }

    public void setPesan (String pesan)
    {
        this.pesan = pesan;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getAnggota_id ()
    {
        return anggota_id;
    }

    public void setAnggota_id (String anggota_id)
    {
        this.anggota_id = anggota_id;
    }
}

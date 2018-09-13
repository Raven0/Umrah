package com.birutekno.aiwa.model;

public class DataGallery {
    private String judul;

    private String id;

    private String file;

    private String tipe;

    private String tanggal;

    private String deskripsi;

    public String getJudul ()
    {
        return judul;
    }

    public void setJudul (String judul)
    {
        this.judul = judul;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFile ()
    {
        return file;
    }

    public void setFile (String file)
    {
        this.file = file;
    }

    public String getTipe ()
    {
        return tipe;
    }

    public void setTipe (String tipe)
    {
        this.tipe = tipe;
    }

    public String getTanggal ()
    {
        return tanggal;
    }

    public void setTanggal (String tanggal)
    {
        this.tanggal = tanggal;
    }

    public String getDeskripsi ()
    {
        return deskripsi;
    }

    public void setDeskripsi (String deskripsi)
    {
        this.deskripsi = deskripsi;
    }
}

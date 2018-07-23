package com.birutekno.umrah.model;

public class DataProspek {
    private String no_telp;

    private String status;

    private String anggota_id;

    private String jml_infant;

    private String keterangan;

    private String jenis_kelamin;

    private String jml_balita;

    private String pembayaran;

    private Anggota anggota;

    private String nama;

    private String alamat;

    private String jml_dewasa;

    public String getNo_telp ()
    {
        return no_telp;
    }

    public void setNo_telp (String no_telp)
    {
        this.no_telp = no_telp;
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

    public String getJml_infant ()
    {
        return jml_infant;
    }

    public void setJml_infant (String jml_infant)
    {
        this.jml_infant = jml_infant;
    }

    public String getKeterangan ()
    {
        return keterangan;
    }

    public void setKeterangan (String keterangan)
    {
        this.keterangan = keterangan;
    }

    public String getJenis_kelamin ()
    {
        return jenis_kelamin;
    }

    public void setJenis_kelamin (String jenis_kelamin)
    {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getJml_balita ()
    {
        return jml_balita;
    }

    public void setJml_balita (String jml_balita)
    {
        this.jml_balita = jml_balita;
    }

    public String getPembayaran ()
    {
        return pembayaran;
    }

    public void setPembayaran (String pembayaran)
    {
        this.pembayaran = pembayaran;
    }

    public Anggota getAnggota ()
    {
        return anggota;
    }

    public void setAnggota (Anggota anggota)
    {
        this.anggota = anggota;
    }

    public String getNama ()
    {
        return nama;
    }

    public void setNama (String nama)
    {
        this.nama = nama;
    }

    public String getAlamat ()
    {
        return alamat;
    }

    public void setAlamat (String alamat)
    {
        this.alamat = alamat;
    }

    public String getJml_dewasa ()
    {
        return jml_dewasa;
    }

    public void setJml_dewasa (String jml_dewasa)
    {
        this.jml_dewasa = jml_dewasa;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [no_telp = "+no_telp+", status = "+status+", anggota_id = "+anggota_id+", jml_infant = "+jml_infant+", keterangan = "+keterangan+", jenis_kelamin = "+jenis_kelamin+", jml_balita = "+jml_balita+", pembayaran = "+pembayaran+", anggota = "+anggota+", nama = "+nama+", alamat = "+alamat+", jml_dewasa = "+jml_dewasa+"]";
    }
}

package com.birutekno.umrah.model;

public class Paket {
    private String bintang_mekkah;

    private String kamar;

    private String hotel_madinah;

    private String nama_paket;

    private String bintang_madinah;

    private String hotel_mekkah;

    private String harga;

    public String getBintang_mekkah ()
    {
        return bintang_mekkah;
    }

    public void setBintang_mekkah (String bintang_mekkah)
    {
        this.bintang_mekkah = bintang_mekkah;
    }

    public String getKamar ()
    {
        return kamar;
    }

    public void setKamar (String kamar)
    {
        this.kamar = kamar;
    }

    public String getHotel_madinah ()
    {
        return hotel_madinah;
    }

    public void setHotel_madinah (String hotel_madinah)
    {
        this.hotel_madinah = hotel_madinah;
    }

    public String getNama_paket ()
    {
        return nama_paket;
    }

    public void setNama_paket (String nama_paket)
    {
        this.nama_paket = nama_paket;
    }

    public String getBintang_madinah ()
    {
        return bintang_madinah;
    }

    public void setBintang_madinah (String bintang_madinah)
    {
        this.bintang_madinah = bintang_madinah;
    }

    public String getHotel_mekkah ()
    {
        return hotel_mekkah;
    }

    public void setHotel_mekkah (String hotel_mekkah)
    {
        this.hotel_mekkah = hotel_mekkah;
    }

    public String getHarga ()
    {
        return harga;
    }

    public void setHarga (String harga)
    {
        this.harga = harga;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bintang_mekkah = "+bintang_mekkah+", kamar = "+kamar+", hotel_madinah = "+hotel_madinah+", nama_paket = "+nama_paket+", bintang_madinah = "+bintang_madinah+", hotel_mekkah = "+hotel_mekkah+", harga = "+harga+"]";
    }
}

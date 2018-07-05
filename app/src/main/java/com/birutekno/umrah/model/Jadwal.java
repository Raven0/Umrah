package com.birutekno.umrah.model;

public class Jadwal {
    private String jml_hari;

    private String jam_manasik;

    private String passpor;

    private String seat_total;

    private String status;

    private String sisa;

    private String pesawat_berangkat;

    private String maskapai;

    private String id;

    private String jam_berangkat;

    private String tgl_berangkat;

    private String pesawat_pulang;

    private String seat_terpakai;

    private String rute_berangkat;

    private String rute_pulang;

    private Paket[] paket;

    private String itinerary;

    private String tgl_manasik;

    private String jam_pulang;

    private String tgl_pulang;

    public String getJml_hari ()
    {
        return jml_hari;
    }

    public void setJml_hari (String jml_hari)
    {
        this.jml_hari = jml_hari;
    }

    public String getJam_manasik ()
    {
        return jam_manasik;
    }

    public void setJam_manasik (String jam_manasik)
    {
        this.jam_manasik = jam_manasik;
    }

    public String getPasspor ()
    {
        return passpor;
    }

    public void setPasspor (String passpor)
    {
        this.passpor = passpor;
    }

    public String getSeat_total ()
    {
        return seat_total;
    }

    public void setSeat_total (String seat_total)
    {
        this.seat_total = seat_total;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getSisa ()
    {
        return sisa;
    }

    public void setSisa (String sisa)
    {
        this.sisa = sisa;
    }

    public String getPesawat_berangkat ()
    {
        return pesawat_berangkat;
    }

    public void setPesawat_berangkat (String pesawat_berangkat)
    {
        this.pesawat_berangkat = pesawat_berangkat;
    }

    public String getMaskapai ()
    {
        return maskapai;
    }

    public void setMaskapai (String maskapai)
    {
        this.maskapai = maskapai;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getJam_berangkat ()
    {
        return jam_berangkat;
    }

    public void setJam_berangkat (String jam_berangkat)
    {
        this.jam_berangkat = jam_berangkat;
    }

    public String getTgl_berangkat ()
    {
        return tgl_berangkat;
    }

    public void setTgl_berangkat (String tgl_berangkat)
    {
        this.tgl_berangkat = tgl_berangkat;
    }

    public String getPesawat_pulang ()
    {
        return pesawat_pulang;
    }

    public void setPesawat_pulang (String pesawat_pulang)
    {
        this.pesawat_pulang = pesawat_pulang;
    }

    public String getSeat_terpakai ()
    {
        return seat_terpakai;
    }

    public void setSeat_terpakai (String seat_terpakai)
    {
        this.seat_terpakai = seat_terpakai;
    }

    public String getRute_berangkat ()
    {
        return rute_berangkat;
    }

    public void setRute_berangkat (String rute_berangkat)
    {
        this.rute_berangkat = rute_berangkat;
    }

    public String getRute_pulang ()
    {
        return rute_pulang;
    }

    public void setRute_pulang (String rute_pulang)
    {
        this.rute_pulang = rute_pulang;
    }

    public Paket[] getPaket ()
    {
        return paket;
    }

    public void setPaket (Paket[] paket)
    {
        this.paket = paket;
    }

    public String getItinerary ()
    {
        return itinerary;
    }

    public void setItinerary (String itinerary)
    {
        this.itinerary = itinerary;
    }

    public String getTgl_manasik ()
    {
        return tgl_manasik;
    }

    public void setTgl_manasik (String tgl_manasik)
    {
        this.tgl_manasik = tgl_manasik;
    }

    public String getJam_pulang ()
    {
        return jam_pulang;
    }

    public void setJam_pulang (String jam_pulang)
    {
        this.jam_pulang = jam_pulang;
    }

    public String getTgl_pulang ()
    {
        return tgl_pulang;
    }

    public void setTgl_pulang (String tgl_pulang)
    {
        this.tgl_pulang = tgl_pulang;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [jml_hari = "+jml_hari+", jam_manasik = "+jam_manasik+", passpor = "+passpor+", seat_total = "+seat_total+", status = "+status+", sisa = "+sisa+", pesawat_berangkat = "+pesawat_berangkat+", maskapai = "+maskapai+", id = "+id+", jam_berangkat = "+jam_berangkat+", tgl_berangkat = "+tgl_berangkat+", pesawat_pulang = "+pesawat_pulang+", seat_terpakai = "+seat_terpakai+", rute_berangkat = "+rute_berangkat+", rute_pulang = "+rute_pulang+", paket = "+paket+", itinerary = "+itinerary+", tgl_manasik = "+tgl_manasik+", jam_pulang = "+jam_pulang+", tgl_pulang = "+tgl_pulang+"]";
    }
}

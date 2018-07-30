package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.Anggota;
import com.birutekno.umrah.model.DataProspek;

public class ProspekResponse {
    private DataProspek[] data;
    private Anggota[] anggota;

    public DataProspek[] getProspek() {
        return data;
    }
    public Anggota[] getAnggota() {
        return  anggota;
    }
}

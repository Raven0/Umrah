package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.Anggota;
import com.birutekno.umrah.model.DataProspek;

public class WEBResponse {
    private DataProspek[] data;
    private Anggota[] anggota;

    public DataProspek[] getData() {
        return data;
    }
    public Anggota[] getAnggota() {
        return  anggota;
    }
}

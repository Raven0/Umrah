package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataPeriode;

public class PeriodeResponse {
    private DataPeriode[] data;

    public DataPeriode[] getData ()
    {
        return data;
    }

    public void setData (DataPeriode[] data)
    {
        this.data = data;
    }
}

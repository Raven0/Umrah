package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataPeriode;

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

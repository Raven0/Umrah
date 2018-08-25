package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataSapa;

public class SapaResponse {
    private DataSapa[] data;

    public DataSapa[] getData ()
    {
        return data;
    }

    public void setData (DataSapa[] data)
    {
        this.data = data;
    }
}

package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataProspek;

public class ProspekResponse {
    private DataProspek[] data;

    public DataProspek[] getData ()
    {
        return data;
    }

    public void setData (DataProspek[] data)
    {
        this.data = data;
    }
}

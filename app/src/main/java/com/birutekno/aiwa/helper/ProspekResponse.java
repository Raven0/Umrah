package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataProspek;

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

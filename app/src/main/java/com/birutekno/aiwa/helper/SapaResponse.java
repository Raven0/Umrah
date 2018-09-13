package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataSapa;

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

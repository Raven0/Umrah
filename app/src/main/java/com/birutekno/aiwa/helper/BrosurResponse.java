package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataBrosur;

public class BrosurResponse {
    private DataBrosur[] data;

    public DataBrosur[] getData ()
    {
        return data;
    }

    public void setData (DataBrosur[] data)
    {
        this.data = data;
    }
}

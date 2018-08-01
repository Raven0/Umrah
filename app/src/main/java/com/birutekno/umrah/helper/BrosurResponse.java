package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataBrosur;

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

package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.Data;

public class MyPojo {
    private Data[] data;

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}

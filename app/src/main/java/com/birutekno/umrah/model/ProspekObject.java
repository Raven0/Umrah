package com.birutekno.umrah.model;

public class ProspekObject {
    private DataProspek data;

    public DataProspek getData ()
    {
        return data;
    }

    public void setData (DataProspek data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}

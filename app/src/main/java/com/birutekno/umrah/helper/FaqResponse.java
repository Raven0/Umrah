package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataFaq;

public class FaqResponse {
    private DataFaq[] data;

    public DataFaq[] getData ()
    {
        return data;
    }

    public void setData (DataFaq[] data)
    {
        this.data = data;
    }
}

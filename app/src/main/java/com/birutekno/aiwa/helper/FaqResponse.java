package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataFaq;

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

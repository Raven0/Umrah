package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataKalkulasi;
import com.birutekno.aiwa.model.Links;
import com.birutekno.aiwa.model.Meta;

public class KalkulasiResponse {
    private DataKalkulasi[] data;

    private Links links;

    private Meta meta;

    public DataKalkulasi[] getKalkulasi ()
    {
        return data;
    }

    public void setData (DataKalkulasi[] data)
    {
        this.data = data;
    }

    public Links getLinks ()
    {
        return links;
    }

    public void setLinks (Links links)
    {
        this.links = links;
    }

    public Meta getMeta ()
    {
        return meta;
    }

    public void setMeta (Meta meta)
    {
        this.meta = meta;
    }
}

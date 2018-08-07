package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataHotel;
import com.birutekno.umrah.model.Links;
import com.birutekno.umrah.model.Meta;

public class HotelResponse {
    private DataHotel[] data;

    private Links links;

    private Meta meta;

    public DataHotel[] getDataHotel ()
    {
        return data;
    }

    public void setDataHotel (DataHotel[] data)
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

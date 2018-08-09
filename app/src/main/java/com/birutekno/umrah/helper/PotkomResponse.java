package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataPotkom;
import com.birutekno.umrah.model.Links;
import com.birutekno.umrah.model.Meta;

import java.util.List;

public class PotkomResponse {
    private List<DataPotkom> data;
    private DataPotkom[] dataPotkoms;

    private Links links;

    private Meta meta;

    public List<DataPotkom> getData() {
        return data;
    }
    public DataPotkom[] getKomisi() {
        return dataPotkoms;
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

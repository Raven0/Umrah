package com.birutekno.umrah.model;

public class ProspekObject {
    private DataProspek data;

    private Links links;

    private Meta meta;

    public DataProspek getData ()
    {
        return data;
    }

    public void setData (DataProspek data)
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

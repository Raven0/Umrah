package com.birutekno.aiwa.model;

public class JamaahObject {
    private DataJamaah[] data;

    private Links links;

    private Meta meta;

    public DataJamaah[] getData ()
    {
        return data;
    }

    public void setData (DataJamaah[] data)
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

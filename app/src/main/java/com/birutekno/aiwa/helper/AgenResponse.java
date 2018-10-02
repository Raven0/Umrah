package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataAgen;
import com.birutekno.aiwa.model.Links;
import com.birutekno.aiwa.model.Meta;

public class AgenResponse {
    private DataAgen[] data;

    private Links links;

    private Meta meta;

    public DataAgen[] getAgen() {
        return data;
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

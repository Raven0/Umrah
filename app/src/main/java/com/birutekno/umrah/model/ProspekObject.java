package com.birutekno.umrah.model;

public class ProspekObject {
    private DataProspek data;

<<<<<<< HEAD
    private Links links;

    private Meta meta;

=======
>>>>>>> 2b7161a532d384f67bc9e81c63136c2e27c8badf
    public DataProspek getData ()
    {
        return data;
    }

    public void setData (DataProspek data)
    {
        this.data = data;
    }

<<<<<<< HEAD
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
=======
    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
>>>>>>> 2b7161a532d384f67bc9e81c63136c2e27c8badf
    }
}

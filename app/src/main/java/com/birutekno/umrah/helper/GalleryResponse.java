package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.DataGallery;

public class GalleryResponse {
    private DataGallery[] data;

    public DataGallery[] getData ()
    {
        return data;
    }

    public void setData (DataGallery[] data)
    {
        this.data = data;
    }
}

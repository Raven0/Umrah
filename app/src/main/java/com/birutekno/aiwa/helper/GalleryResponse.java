package com.birutekno.aiwa.helper;

import com.birutekno.aiwa.model.DataGallery;

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

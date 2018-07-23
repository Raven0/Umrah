package com.birutekno.umrah.helper;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WEBInterface {
    @GET("jamaah")
    Call<WEBResponse> getJSON();
}

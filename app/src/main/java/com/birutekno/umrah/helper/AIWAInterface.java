package com.birutekno.umrah.helper;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AIWAInterface {
    @GET("aiw/jadwal/1440")
    Call<AIWAResponse> getJSON();
}

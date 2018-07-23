package com.birutekno.umrah.helper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AIWAInterface {
    @GET("jadwal/{periode}")
    Call<AIWAResponse> getJSON(@Path("periode") String periode);
}

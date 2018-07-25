package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.ProspekObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WEBInterface {
    //DATA PROSPEK

    //GET ALL PROSPEK
    @GET("prospek")
    Call<WEBResponse> getProspek();

    //GET SHOW PROSPEK
    @GET("prospek/{id}/show")
    Call<ProspekObject> showProspek(@Path("id") String id);

    //POST NEW PROSPEK
    @FormUrlEncoded
    @POST("prospek")
    Call<ResponseBody> insertProspek(@FieldMap HashMap<String, String> params);

    //PUT PROSPEK
    @FormUrlEncoded
    @PUT("prospek/{id}/edit")
    Call<ResponseBody> editProspek(@Path("id") String id, @FieldMap HashMap<String, String> params);
}

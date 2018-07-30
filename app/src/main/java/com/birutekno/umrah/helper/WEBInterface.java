package com.birutekno.umrah.helper;

<<<<<<< HEAD
import com.birutekno.umrah.model.AgenObject;
import com.birutekno.umrah.model.JamaahObject;
=======
>>>>>>> 2b7161a532d384f67bc9e81c63136c2e27c8badf
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
<<<<<<< HEAD
    Call<ProspekResponse> getProspek();
=======
    Call<WEBResponse> getProspek();
>>>>>>> 2b7161a532d384f67bc9e81c63136c2e27c8badf

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
<<<<<<< HEAD

    //DATA AGEN
    //GET ALL PROSPEK
    @GET("agen")
    Call<LoginResponse> getAgen();

    //GET SHOW PROSPEK
    @GET("agen/{id}/show")
    Call<AgenObject> showAgen(@Path("id") String id);

    //POST NEW PROSPEK
    @FormUrlEncoded
    @POST("agen")
    Call<ResponseBody> insertAgen(@FieldMap HashMap<String, String> params);

    //PUT PROSPEK
    @FormUrlEncoded
    @PUT("agen/{id}/edit")
    Call<ResponseBody> editAgen(@Path("id") String id, @FieldMap HashMap<String, String> params);

    //DATA JAMAAH
    //GET ALL PROSPEK
    @GET("jamaah")
    Call<JamaahResponse> getJamaah();

    //GET SHOW PROSPEK
    @GET("jamaah/{id}/show")
    Call<JamaahObject> showJamaah(@Path("id") String id);

    //POST NEW PROSPEK
    @FormUrlEncoded
    @POST("jamaah")
    Call<ResponseBody> insertJamaah(@FieldMap HashMap<String, String> params);

    //PUT PROSPEK
    @FormUrlEncoded
    @PUT("jamaah/{id}/edit")
    Call<ResponseBody> editJamaah(@Path("id") String id, @FieldMap HashMap<String, String> params);
=======
>>>>>>> 2b7161a532d384f67bc9e81c63136c2e27c8badf
}

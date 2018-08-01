package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.AgenObject;
import com.birutekno.umrah.model.JamaahObject;
import com.birutekno.umrah.model.LoginModel;
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
    @GET("prospek/")
    Call<ProspekResponse> getProspek();

    //GET PROSPEK BY AGEN
    @GET("prospek/{id}/agen")
    Call<ProspekResponse> getProspekAgen(@Path("id") String id);

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




    //DATA KALKULASI
    //GET ALL KALKULASI
    @GET("kalkulasi/")
    Call<KalkulasiResponse> getKalkulasi();





    //DATA AGEN
    //GET ALL AGEN
    @GET("agen")
    Call<AgenResponse> getAgen();

    //GET SHOW AGEN
    @GET("agen/{id}/show")
    Call<AgenObject> showAgen(@Path("id") String id);

    //REGISTER AGEN
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> insertAgen(@FieldMap HashMap<String, String> params);

    //LOGIN AGEN
    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> loginAgen(@FieldMap HashMap<String, String> params);

    //EDIT AGEN
    @FormUrlEncoded
    @PUT("agen/{id}/edit")
    Call<ResponseBody> editAgen(@Path("id") String id, @FieldMap HashMap<String, String> params);





    //DATA JAMAAH
    //GET ALL JAMAAH
    @GET("jamaah")
    Call<JamaahResponse> getJamaah();

    //GET SHOW JAMAAH
    @GET("jamaah/{id}/show")
    Call<JamaahObject> showJamaah(@Path("id") String id);

    //POST NEW JAMAAH
    @FormUrlEncoded
    @POST("jamaah")
    Call<ResponseBody> insertJamaah(@FieldMap HashMap<String, String> params);

    //PUT JAMAAH
    @FormUrlEncoded
    @PUT("jamaah/{id}/edit")
    Call<ResponseBody> editJamaah(@Path("id") String id, @FieldMap HashMap<String, String> params);



    //DATA FAQ
    //GET ALL FAQ
    @GET("faq/")
    Call<FaqResponse> getFaq();

    //DATA GALLERY
    //GET ALL GALLERY
    @GET("gallery/foto/")
    Call<GalleryResponse> getGalleryFoto();
    @GET("gallery/video/")
    Call<GalleryResponse> getGalleryVideo();
}

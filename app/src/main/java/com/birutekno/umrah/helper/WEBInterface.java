package com.birutekno.umrah.helper;

import com.birutekno.umrah.model.AgenObject;
import com.birutekno.umrah.model.AuthModel;
import com.birutekno.umrah.model.DashboardModel;
import com.birutekno.umrah.model.HotelObject;
import com.birutekno.umrah.model.JamaahObject;
import com.birutekno.umrah.model.ProspekObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    //BAYAR PROSPEK
    @FormUrlEncoded
    @PUT("prospek/{id}/bayar")
    Call<ResponseBody> bayarProspek(@Path("id") String id, @FieldMap HashMap<String, String> params);




    //DATA KALKULASI
    //GET ALL KALKULASI
    @GET("kalkulasi/")
    Call<KalkulasiResponse> getKalkulasi();

    //DATA PERIODE
    //GET ALL PERIODE
    @GET("periode/")
    Call<PeriodeResponse> getPeriode();





    //DATA AGEN
    //GET ALL AGEN
    @GET("agen")
    Call<AgenResponse> getAgen();

    //GET ALL AGEN
    @GET("agen/approved")
    Call<AgenResponse> getAgenApproved();

    //GET SHOW AGEN
    @GET("agen/{id}/show")
    Call<AgenObject> showAgen(@Path("id") String id);

    //GET SUB AGEN
    @GET("agen/{id}/subagen")
    Call<AgenResponse> getSubAgen(@Path("id") String id);

    //REGISTER AGEN
    @FormUrlEncoded
    @POST("register")
    Call<AuthModel> insertAgen(@FieldMap HashMap<String, String> params);

    //LOGIN AGEN
    @FormUrlEncoded
    @POST("login")
    Call<AuthModel> loginAgen(@FieldMap HashMap<String, String> params);

    //FORGOT AGEN
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("password/email")
    Call<AuthModel> passwordAgen(@FieldMap HashMap<String, String> params);

    //EDIT AGEN
    @FormUrlEncoded
    @PUT("agen/{id}/edit")
    Call<ResponseBody> editAgen(@Path("id") String id, @FieldMap HashMap<String, String> params);

    @Multipart
    @POST("agen/{id}/updatephoto")
    Call<ResponseBody> photoAgen(@Path("id") String id, @Part MultipartBody.Part image, @Part("upload") RequestBody name);





    //DATA JAMAAH
    //GET ALL JAMAAH
    @GET("jamaah")
    Call<JamaahResponse> getJamaah();

    //GET PROSPEK BY AGEN
    @GET("jamaah/{id}/agen/{tahun}/periode")
    Call<JamaahResponse> getJamaahAgen(@Path("id") String id, @Path("tahun") String tahun);

    //GET PROSPEK BERANGKAT BY AGEN
    @GET("jamaah/{id}/agen/berangkat/{tahun}/periode")
    Call<JamaahResponse> getJamaahBerangkatAgen(@Path("id") String id, @Path("tahun") String tahun);

    //GET PROSPEK PULANG BY AGEN
    @GET("jamaah/{id}/agen/pulang/{tahun}/periode")
    Call<JamaahResponse> getJamaahPulangAgen(@Path("id") String id, @Path("tahun") String tahun);

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

    //DATA Sapa
    //GET ALL FAQ
    @GET("sapaan/")
    Call<SapaResponse> getSapa();

    //DATA GALLERY
    //GET ALL GALLERY
    @GET("gallery/foto/")
    Call<GalleryResponse> getGalleryFoto();
    @GET("gallery/video/")
    Call<GalleryResponse> getGalleryVideo();

    //DATA BROSUR
    @GET("brosur/")
    Call<BrosurResponse> getBrosur();





    //DATA KOMISI
    //GET JAMAAG KOMISI
    @GET("jamaah/{id}/agen/komisi/{tahun}/periode")
    Call<PotkomResponse> getDataKomisi(@Path("id") String id, @Path("tahun") String tahun);

    //GET JAMAAH POTENSI
    @GET("jamaah/{id}/agen/potensi/{tahun}/periode")
    Call<PotkomResponse> getDataPotensi(@Path("id") String id, @Path("tahun") String tahun);

    //GET JAMAAG KOMISI KOORD
    @GET("jamaah/{id}/koordinator/komisi/{tahun}/periode")
    Call<PotkomResponse> getDataKomisiKoord(@Path("id") String id, @Path("tahun") String tahun);

    //GET JAMAAH POTENSI KOORD
    @GET("jamaah/{id}/koordinator/potensi/{tahun}/periode")
    Call<PotkomResponse> getDataPotensiKoord(@Path("id") String id, @Path("tahun") String tahun);

    //GET UANG KOMISI
    @GET("jamaah/{id}/agenfee/komisi/{tahun}/periode")
    Call<DashboardModel> getUangKomisi(@Path("id") String id, @Path("tahun") String tahun);

    //GET UANG POTENSI
    @GET("jamaah/{id}/agenfee/potensi/{tahun}/periode")
    Call<DashboardModel> getUangPotensi(@Path("id") String id, @Path("tahun") String tahun);

    //GET UANG KOMISI KOORD
    @GET("jamaah/{id}/koordinatorfee/komisi/{tahun}/periode")
    Call<DashboardModel> getUangKoordKomisi(@Path("id") String id, @Path("tahun") String tahun);

    //GET UANG POTENSI KOORD
    @GET("jamaah/{id}/koordinatorfee/potensi/{tahun}/periode")
    Call<DashboardModel> getUangKoordPotensi(@Path("id") String id, @Path("tahun") String tahun);

    //GET TOTAL JAMAAH
    @GET("jamaah/{id}/agen/total/{tahun}/periode")
    Call<DashboardModel> getTotalJamaah(@Path("id") String id, @Path("tahun") String tahun);

    //GET TOTAL PROSPEK
    @GET("prospek/{id}/agen/total")
    Call<DashboardModel> getTotalProspek(@Path("id") String id);

    //GET JAMAAH MONTHLY
    @GET("jamaah/{id}/agen/bulan/{tahun}/periode")
    Call<ChartResponse> getJamaahPerbulan(@Path("id") String id, @Path("tahun") String tahun);

    //GET KOMISI MONTHLY
    @GET("jamaah/{id}/agenfee/bulan/{tahun}/periode")
    Call<ChartResponse> getKomisiPerbulan(@Path("id") String id, @Path("tahun") String tahun);

    //GET KOMISI DARI AGEN MONTHLY
    @GET("jamaah/{id}/koordinatorfee/bulan/{tahun}/periode")
    Call<ChartResponse> getKomisiAgenPerbulan(@Path("id") String id, @Path("tahun") String tahun);





    //DATA GALLERY
    //GET ALL GALLERY
    @GET("hotel/{kota}/")
    Call<HotelResponse> getHotel(@Path("kota") String kota);

    @GET("hotel/{id}/show")
    Call<HotelObject> getHotelDetail(@Path("id") int id);

    @GET("hotel/{id}/foto")
    Call<GalleryResponse> getHotelFoto(@Path("id") int id);

    @GET("hotel/{id}/video")
    Call<GalleryResponse> getHotelVideo(@Path("id") int id);
//    @GET("gallery/video/")
//    Call<GalleryResponse> getGalleryVideo();







    //GET NOTIF BY AGEN
    @GET("notif/{id}/delivered")
    Call<NotifResponse> getNotification(@Path("id") String id);

    //GET NOTIF BY AGEN
    @PUT("notif/{id}/clear")
    Call<ResponseBody> clearNotification(@Path("id") String id);

    //PUT JAMAAH
    @FormUrlEncoded
    @PUT("notif/{id}/edit")
    Call<ResponseBody> readNotif(@Path("id") String id, @FieldMap HashMap<String, String> params);

}

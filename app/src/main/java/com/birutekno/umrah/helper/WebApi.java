package com.birutekno.umrah.helper;

public class WebApi {
//    public static final String BASE_URL_API = "http://test.heksasecurity.com/api/";
    public static final String BASE_URL_API = "http://aiwaapps.com/api/";

    public static WEBInterface getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(WEBInterface.class);
    }
}

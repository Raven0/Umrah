package com.birutekno.umrah.helper;

public class UtilsApi {
    public static final String BASE_URL_API = "http://115.124.86.218/aiw/";

    public static AIWAInterface getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(AIWAInterface.class);
    }
}

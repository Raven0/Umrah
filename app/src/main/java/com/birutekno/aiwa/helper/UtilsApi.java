package com.birutekno.aiwa.helper;

public class UtilsApi {
//    public static final String BASE_URL_API = "http://115.124.86.218/aiw/";
//    public static final String BASE_URL_API = "http://api.aiwaapps.com/";
    public static final String BASE_URL_API = "http://aiwaapps.com/api/";

    public static AIWAInterface getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(AIWAInterface.class);
    }
}

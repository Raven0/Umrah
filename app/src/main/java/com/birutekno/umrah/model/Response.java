package com.birutekno.umrah.model;

public class Response {
    private String token;
    private String status;

    public String getToken ()
    {
        return token;
    }
    public String getStatus()
    {
        return status;
    }

    public void setToken (String token)
    {
        this.token = token;
    }
}

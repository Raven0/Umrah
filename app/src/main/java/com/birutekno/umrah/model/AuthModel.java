package com.birutekno.umrah.model;

public class AuthModel {
    private User user;

    private Response response;

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public Response getResponses()
    {
        return response;
    }

    public void setResponses(Response response)
    {
        this.response = response;
    }
}

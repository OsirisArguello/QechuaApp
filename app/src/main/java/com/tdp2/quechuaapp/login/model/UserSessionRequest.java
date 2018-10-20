package com.tdp2.quechuaapp.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSessionRequest {

    @Expose
    @SerializedName("username")
    public String username;
    @Expose
    @SerializedName("password")
    public String password;

}

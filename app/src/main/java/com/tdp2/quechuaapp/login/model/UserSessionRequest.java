package com.tdp2.quechuaapp.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSessionRequest {

    @Expose
    @SerializedName("session")
    public Session session;

    public static class Session {
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("password")
        public String password;
        @Expose
        @SerializedName("device_type")
        public String deviceType;
        @Expose
        @SerializedName("device_token")
        public String deviceToken;
    }

}

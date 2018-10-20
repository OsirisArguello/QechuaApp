package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSession;
import com.tdp2.quechuaapp.login.model.UserSessionRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/api/authenticate")
    Call<UserSession> createUserSession(@Body UserSessionRequest userSessionRequest);

    @GET("/api/account")
    Call<UserLogged> getUserLogged(@Header("Authorization")String apiToken);
}

package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSession;
import com.tdp2.quechuaapp.login.model.UserSessionRequest;

import retrofit2.Call;

public interface LoginApi {
    Call<UserSession> createUserSession(UserSessionRequest userSessionRequest);

    Call<UserLogged> getUserLogged(String accessToken);
}

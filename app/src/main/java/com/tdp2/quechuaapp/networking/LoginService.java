package com.tdp2.quechuaapp.networking;

import android.support.annotation.NonNull;


import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSession;
import com.tdp2.quechuaapp.login.model.UserSessionRequest;

import retrofit2.Call;

public class LoginService {

    private LoginApi loginApi;

    public LoginService() {
        loginApi = ApiClient.getInstance().getLoginClient();
    }

    public Call<UserSession> createUserSession(String email, String password) {

        //String deviceToken = FirebaseInstanceId.getInstance().getToken();

        //UserSessionRequest userSessionRequest = getUserSessionRequest(email, password, deviceToken);

        //return loginApi.createUserSession(userSessionRequest);
        return null;
    }

    public Call<UserLogged> getUserLogged(String accessToken) {
        return loginApi.getUserLogged(accessToken);
    }

    @NonNull
    private UserSessionRequest getUserSessionRequest(String email, String password, String deviceToken) {
        UserSessionRequest userSessionRequest = new UserSessionRequest();
        userSessionRequest.session = new UserSessionRequest.Session();
        userSessionRequest.session.email = email;
        userSessionRequest.session.password = password;
        userSessionRequest.session.deviceToken = deviceToken;
        userSessionRequest.session.deviceType = "android";
        return userSessionRequest;
    }
}

package com.tdp2.quechuaapp.networking;

import android.util.Log;


import com.tdp2.quechuaapp.login.model.UserLogged;
import com.tdp2.quechuaapp.login.model.UserSession;
import com.tdp2.quechuaapp.login.model.UserSessionRequest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginService {

    private LoginApi loginApi;

    private static final String SERVICE_TAG = "LOGINSERVICE";


    public LoginService() {
        loginApi = ApiClient.getInstance().getLoginClient();
    }

    public Call<UserSession> createUserSession(String email, String password) {


        UserSessionRequest userSessionRequest = new UserSessionRequest();
        userSessionRequest.username=email;
        userSessionRequest.password=password;

        return loginApi.createUserSession(userSessionRequest);
    }

    public Call<UserLogged> getUserLogged(String accessToken) {
        return loginApi.getUserLogged(accessToken);
    }
}

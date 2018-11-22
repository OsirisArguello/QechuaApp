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

    public void updateUserLogged(UserLogged userLogged, final Client client){

        loginApi.updateUserData(userLogged).enqueue(new Callback<UserLogged>() {
            @Override
            public void onResponse(Call<UserLogged> call, Response<UserLogged> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if (response.body() != null) {
                        Log.i(SERVICE_TAG, response.body().toString());
                        client.onResponseSuccess(response.body());
                    } else {
                        Log.i(SERVICE_TAG, "NO RESPONSE");
                        client.onResponseError(null);
                    }
                } else {
                    if(response.body() != null) {
                        Log.e(SERVICE_TAG, response.body().toString());
                    }else {
                        Log.e(SERVICE_TAG, "NO RESPONSE");
                    }
                    client.onResponseError(null);
                }
            }

            @Override
            public void onFailure(Call<UserLogged> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });
    }
}

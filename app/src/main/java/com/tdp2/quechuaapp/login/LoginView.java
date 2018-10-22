package com.tdp2.quechuaapp.login;

import com.tdp2.quechuaapp.login.model.UserLogged;

public interface LoginView {
    void validateEmail(String email);
    void validatePassword(String password);
    void showProgress(boolean showLoading);
    void onError();
    void onSuccess(UserLogged body, String accessToken);
    void onServiceUnavailable();
}

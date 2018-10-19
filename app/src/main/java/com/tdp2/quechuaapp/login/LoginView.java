package com.tdp2.quechuaapp.login;

import com.tdp2.quechuaapp.login.model.UserLogged;

public interface LoginView {
    void validateEmail();
    void validatePassword();
    void showProgress(boolean showLoading);
    void onError();
    void onSuccess(UserLogged body, String accessToken);
    void onServiceUnavailable();
}

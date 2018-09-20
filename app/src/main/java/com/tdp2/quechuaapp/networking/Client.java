package com.tdp2.quechuaapp.networking;

public interface Client<T> {

    void onResponseSuccess(T responseBody);

    void onResponseError();
}

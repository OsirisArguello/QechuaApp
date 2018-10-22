package com.tdp2.quechuaapp.networking;

import android.content.Context;

public interface Client<T> {

    void onResponseSuccess(T responseBody);

    void onResponseError(String errorMessage);

    Context getContext();
}

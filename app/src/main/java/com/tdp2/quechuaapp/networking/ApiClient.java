package com.tdp2.quechuaapp.networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private final static String API_BASE_URL = "http://192.168.0.241:8080/";
    private static ApiClient instance;
    private Retrofit retrofit;

    private ApiClient() {
        buildRetrofit();
    }

    public synchronized static ApiClient getInstance() {
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }

    private void buildRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        retrofit = builder
                .client(httpClient.build())
                .build();
    }

    public EstudianteApi getEstudianteClient() {
        return retrofit.create(EstudianteApi.class);
    }

    public DocenteApi getDocenteClient() {
        return retrofit.create(DocenteApi.class);
    }

    public LoginApi getLoginClient() {
        return retrofit.create(LoginApi.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
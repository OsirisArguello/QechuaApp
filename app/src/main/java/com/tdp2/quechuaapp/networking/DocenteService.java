package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.model.Curso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocenteService {
    private DocenteApi docenteApi;

    static final String SERVICE_TAG = "DOCENTESERVICE";

    public DocenteService() {
        this.docenteApi = ApiClient.getInstance().getDocenteClient();
    }

    public void getCurso(Integer cursoId, final Client client) {
        docenteApi.getCurso(cursoId).enqueue(new Callback<Curso>() {
            @Override
            public void onResponse(Call<Curso> call, Response<Curso> response) {
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
            public void onFailure(Call<Curso> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });
    }
}

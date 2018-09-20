package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.model.Curso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstudianteService {

    private EstudianteApi estudianteApi;

    public EstudianteService() {
        this.estudianteApi = ApiClient.getInstance().getEstudianteClient();
    }

    public void getCursos(Integer idEstudiante, final Client client){
        estudianteApi.getCursos().enqueue(new Callback<ArrayList<Curso>>() {

            @Override
            public void onResponse(Call<ArrayList<Curso>> call, Response<ArrayList<Curso>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("ESTUDIANTESERVICE", response.body().toString());
                        client.onResponseSuccess(response.body());
                    }else {
                        Log.i("ESTUDIANTESERVICE", "NO RESPONSE");
                        client.onResponseError();
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("ESTUDIANTESERVICE", response.body().toString());
                    }else {
                        Log.e("ESTUDIANTESERVICE", "NO RESPONSE");
                    }
                    client.onResponseError();                }
            }

            @Override
            public void onFailure(Call<ArrayList<Curso>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError();
            }
        });
    }
}

package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.model.Profesor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocenteService {
    private DocenteApi docenteApi;

    static final String SERVICE_TAG = "DOCENTESERVICE";

    public DocenteService() {
        this.docenteApi = ApiClient.getInstance().getDocenteClient();
    }

    public static Curso getCursoMock(Integer cursoId) {
        Curso curso = new Curso();
        curso.id = cursoId;
        curso.materia = new Materia();
        curso.materia.nombre = "Materia 1";

        Alumno alum0 = new Alumno();
        alum0.nombre = "Lucia";
        alum0.apellido = "Capon";

        Alumno alum1 = new Alumno();
        alum1.nombre = "Juan";
        alum1.apellido = "Gonzalez";

        curso.est_regulares = new ArrayList<>();
        curso.est_regulares.add(alum0);

        curso.est_condicionales = new ArrayList<>();
        curso.est_condicionales.add(alum1);

        return curso;
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

package com.tdp2.quechuaapp.networking;

import android.util.Log;
import android.widget.Toast;

import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Inscripcion;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
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
                        client.onResponseError(null);
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("ESTUDIANTESERVICE", response.body().toString());
                    }else {
                        Log.e("ESTUDIANTESERVICE", "NO RESPONSE");
                    }
                    client.onResponseError(null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Curso>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void inscribirAlumno(Integer idAlumno, Integer idCurso, final Client client){
        estudianteApi.inscribirAlumno(idAlumno,idCurso).enqueue(new Callback<Inscripcion>() {

            @Override
            public void onResponse(Call<Inscripcion> call, Response<Inscripcion> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("ESTUDIANTESERVICE", response.body().toString());
                        client.onResponseSuccess(response.body());
                    }else {
                        Log.i("ESTUDIANTESERVICE", "NO RESPONSE");
                        client.onResponseError(null);
                    }
                } else {
                    if(response.body() != null) {
                        Log.e("ESTUDIANTESERVICE", response.body().toString());
                    }else {
                        Log.e("ESTUDIANTESERVICE", "NO RESPONSE");
                    }
                    if (response.code() == 400) {
                        Converter<ResponseBody, ApiError> converter =
                                ApiClient.getInstance().getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]);

                        ApiError error;

                        try {
                            error = converter.convert(response.errorBody());
                            Log.e("error message", error.message);
                            client.onResponseError(error.message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        client.onResponseError(null);
                    }

                }
            }

            @Override
            public void onFailure(Call<Inscripcion> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }
}

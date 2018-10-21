package com.tdp2.quechuaapp.networking;

import android.util.Log;
import android.widget.Toast;

import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Carrera;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.Materia;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class EstudianteService {

    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    private EstudianteApi estudianteApi;

    public EstudianteService() {
        this.estudianteApi = ApiClient.getInstance().getEstudianteClient();
    }

    public void getCarreras(final Client client){

        String accessToken = new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getCarreras(AUTHORIZATION_PREFIX +accessToken).enqueue(new Callback<ArrayList<Carrera>>() {

            @Override
            public void onResponse(Call<ArrayList<Carrera>> call, Response<ArrayList<Carrera>> response) {
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
            public void onFailure(Call<ArrayList<Carrera>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
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

    public void getMaterias(final Client client) {
        estudianteApi.getMaterias().enqueue(new Callback<ArrayList<Materia>>() {

            @Override
            public void onResponse(Call<ArrayList<Materia>> call, Response<ArrayList<Materia>> response) {
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
            public void onFailure(Call<ArrayList<Materia>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void getMateriasPorCarrera(Integer idCarrera, final Client client) {

        String accessToken = new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getMateriasPorCarrera(AUTHORIZATION_PREFIX +accessToken,idCarrera).enqueue(new Callback<ArrayList<Materia>>() {
            @Override
            public void onResponse(Call<ArrayList<Materia>> call, Response<ArrayList<Materia>> response) {
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
            public void onFailure(Call<ArrayList<Materia>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void getCursosPorMateria(Integer idMateria, final Client client){
        estudianteApi.getCursosPorMateria(idMateria).enqueue(new Callback<ArrayList<Curso>>() {

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

    public void inscribirAlumno(Integer idCurso, final Client client){
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.inscribirAlumno(AUTHORIZATION_PREFIX+apiToken,idCurso).enqueue(new Callback<Inscripcion>() {

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

    public void desinscribirAlumno(Integer idCurso, final Client client){
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.desinscribirAlumno(AUTHORIZATION_PREFIX+apiToken,idCurso).enqueue(new Callback<Inscripcion>() {

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

    public void getAlumno(final Client client){
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getAlumno(AUTHORIZATION_PREFIX+apiToken).enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
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
            public void onFailure(Call<Alumno> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });

    }
}

package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Carrera;
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.InscripcionColoquio;
import com.tdp2.quechuaapp.model.Horario;
import com.tdp2.quechuaapp.model.Periodo;
import com.tdp2.quechuaapp.model.PeriodoActividad;
import com.tdp2.quechuaapp.model.PeriodoAdministrativo;
import com.tdp2.quechuaapp.model.Profesor;
import com.tdp2.quechuaapp.model.Materia;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

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

    public void getFinalesDisponibles(Integer idCurso, final Client client) {
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getFinales(AUTHORIZATION_PREFIX + apiToken,idCurso).enqueue(new Callback<ArrayList<Coloquio>>() {
            @Override
            public void onResponse(Call<ArrayList<Coloquio>> call, Response<ArrayList<Coloquio>> response) {
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
            public void onFailure(Call<ArrayList<Coloquio>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void getMisFinales(final Client client) {
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getMisFinales(AUTHORIZATION_PREFIX+apiToken).enqueue(new Callback<ArrayList<InscripcionColoquio>>() {
            @Override
            public void onResponse(Call<ArrayList<InscripcionColoquio>> call, Response<ArrayList<InscripcionColoquio>> response) {
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
            public void onFailure(Call<ArrayList<InscripcionColoquio>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void inscribirFinal(Integer finalId,final Client client) {
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.inscribirFinal(AUTHORIZATION_PREFIX+apiToken, finalId).enqueue(new Callback<InscripcionColoquio>() {
            @Override
            public void onResponse(Call<InscripcionColoquio> call, Response<InscripcionColoquio> response) {
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
            public void onFailure(Call<InscripcionColoquio> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void desinscribirFinal(Integer finalId,final Client client) {
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.desinscribirFinal(AUTHORIZATION_PREFIX+apiToken, finalId).enqueue(new Callback<InscripcionColoquio>() {
            @Override
            public void onResponse(Call<InscripcionColoquio> call, Response<InscripcionColoquio> response) {
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
            public void onFailure(Call<InscripcionColoquio> call, Throwable t) {
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

    public void getCursadas(final Client client){
        String apiToken= new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getCursadas(AUTHORIZATION_PREFIX +apiToken).enqueue(new Callback<ArrayList<Cursada>>() {

            @Override
            public void onResponse(Call<ArrayList<Cursada>> call, Response<ArrayList<Cursada>> response) {
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
            public void onFailure(Call<ArrayList<Cursada>> call, Throwable t) {
                //Log.e("ESTUDIANTESERVICE", t.getMessage());
                Log.e("ESTUDIANTESERVICE", "No fue posible encontrar cusadas");
                client.onResponseError(null);
            }
        });
    }

    public void getAccionesPeriodo(final Client client) {
        String apiToken= new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getAccionesPeriodo(AUTHORIZATION_PREFIX + apiToken).enqueue(new Callback<ArrayList<PeriodoAdministrativo>>() {
            @Override
            public void onResponse(Call<ArrayList<PeriodoAdministrativo>> call, Response<ArrayList<PeriodoAdministrativo>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("ESTUDIANTESERVICE", response.body().toString());
                        client.onResponseSuccess(response.body());
                    }else {
                        Log.i("ESTUDIANTESERVICE", "NO RESPONSE");
                        client.onResponseError("No response");
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
            public void onFailure(Call<ArrayList<PeriodoAdministrativo>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", "Error al obtener periodos administrativos");
                client.onResponseError(null);
            }
        });
    }

    public void getInscripcionesCursos(final Client client) {
        String apiToken= new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getInscripcionesActivas(AUTHORIZATION_PREFIX + apiToken).enqueue(new Callback<ArrayList<Inscripcion>>() {
            @Override
            public void onResponse(Call<ArrayList<Inscripcion>> call, Response<ArrayList<Inscripcion>> response) {
                if (response.code() > 199 && response.code() < 300) {
                    if(response.body() != null) {
                        Log.i("ESTUDIANTESERVICE", response.body().toString());
                        client.onResponseSuccess(response.body());
                    }else {
                        Log.i("ESTUDIANTESERVICE", "NO RESPONSE");
                        client.onResponseError("No response");
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
            public void onFailure(Call<ArrayList<Inscripcion>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", "Error al obtener Inscripciones activas");
                client.onResponseError(t.getMessage());
            }
        });
    }
}

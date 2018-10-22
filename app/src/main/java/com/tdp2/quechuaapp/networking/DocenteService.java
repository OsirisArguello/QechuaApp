package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.model.Profesor;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocenteService {
    public static final String AUTHORIZATION_PREFIX = "Bearer ";
    private DocenteApi docenteApi;

    static final String SERVICE_TAG = "DOCENTESERVICE";

    public DocenteService() {
        this.docenteApi = ApiClient.getInstance().getDocenteClient();
    }

    public static Curso getCursoMock(Integer cursoId) {
        Curso curso = new Curso();
        curso.id = cursoId;
        curso.capacidadCurso = 30;


        curso.materia = new Materia();
        curso.materia.nombre = "Materia 1";

        Alumno alum0 = new Alumno();
        alum0.padron = "1234";
        alum0.nombre = "Lucia";
        alum0.apellido = "Capon";

        Alumno alum1 = new Alumno();
        alum1.padron = "345578";
        alum1.nombre = "Juan";
        alum1.apellido = "Gonzalez";

        Inscripcion inscripcion0 = new Inscripcion();
        inscripcion0.alumno = alum0;
        inscripcion0.estado = "REGULAR";

        Inscripcion inscripcion1 = new Inscripcion();
        inscripcion1.alumno = alum1;
        inscripcion1.estado = "CONDICIONAL";

        curso.inscripciones = new ArrayList<>();

        curso.inscripciones.add(inscripcion0);
        curso.inscripciones.add(inscripcion1);

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

    public void aceptarInscripcion(Integer inscripcionId, final Client client) {

        String apiToken = new UserSessionManager(client.getContext()).getAuthorizationToken();
        docenteApi.aceptar(AUTHORIZATION_PREFIX+apiToken,inscripcionId).enqueue(new Callback<Inscripcion>() {
            @Override
            public void onResponse(Call<Inscripcion> call, Response<Inscripcion> response) {
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
            public void onFailure(Call<Inscripcion> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });

    }

    public void rechazarInscripcion(Integer inscripcionId, final Client client) {

        String apiToken = new UserSessionManager(client.getContext()).getAuthorizationToken();
        docenteApi.rechazar(AUTHORIZATION_PREFIX+apiToken,inscripcionId).enqueue(new Callback<Inscripcion>() {
            @Override
            public void onResponse(Call<Inscripcion> call, Response<Inscripcion> response) {
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
            public void onFailure(Call<Inscripcion> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });

    }

    public void getCursos(final Client client) {
        String apiToken= new UserSessionManager(client.getContext()).getAuthorizationToken();

        docenteApi.getCursos(AUTHORIZATION_PREFIX +apiToken).enqueue(new Callback<ArrayList<Curso>>() {
            @Override
            public void onResponse(Call<ArrayList<Curso>> call, Response<ArrayList<Curso>> response) {
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
            public void onFailure(Call<ArrayList<Curso>> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void getColoquios(Integer cursoId, final Client client){
        String apiToken= new UserSessionManager(client.getContext()).getAuthorizationToken();

        docenteApi.getColoquios(AUTHORIZATION_PREFIX +apiToken, cursoId).enqueue(new Callback<ArrayList<Final>>() {
            @Override
            public void onResponse(Call<ArrayList<Final>> call, Response<ArrayList<Final>> response) {
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
            public void onFailure(Call<ArrayList<Final>> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void crearColoquios(Final coloquio, final Client client){
        String apiToken= new UserSessionManager(client.getContext()).getAuthorizationToken();

        docenteApi.crearColoquio(AUTHORIZATION_PREFIX +apiToken, coloquio).enqueue(new Callback<Final>() {
            @Override
            public void onResponse(Call<Final> call, Response<Final> response) {
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
            public void onFailure(Call<Final> call, Throwable t) {
                Log.e(SERVICE_TAG, t.getMessage());
                client.onResponseError(null);
            }
        });
    }


}

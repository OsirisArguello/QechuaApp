package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Horario;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.Profesor;

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

    public void getCursosPorMateria(Integer idEstudiante, Integer idMateria, final Client client){
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

    public void getCursadas(Integer idEstudiante, final Client client){
        estudianteApi.getCursadas("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcGFlekBmaXViYS5jb20iLCJhdXRoIjoiUk9MRV9BTFVNTk8iLCJleHAiOjE1NDAwNzcxNDJ9.xjPIR8tlbwa1WwFQfPa3yA7UKv0cVPBpsXIEmYcJb7WiJJ2dHou7pOWgLrGJfzNRZhPRd96zQjUdPWXkYbGlHg").enqueue(new Callback<ArrayList<Cursada>>() {

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

    public void desinscribirAlumno(Integer idCursada, final Client client){
        estudianteApi.desinscribirAlumno(idCursada).enqueue(new Callback<Inscripcion>() {

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

    public ArrayList<Curso> getCursadasMock() {
        Curso curso1 = new Curso();
        Profesor prof = new Profesor();
        Horario hor = new Horario();
        Horario hor2 = new Horario();
        hor.aula = "1";
        hor.dia = "Viernes";
        hor.horaFin = "17:00";
        hor.horaFin = "19:00";
        hor2.aula = "1";
        hor2.dia = "Viernes";
        hor2.horaInicio = "17:00";
        hor2.horaFin = "19:00";
        prof.apellido = "Perez";
        prof.nombre = "Jorge";
        curso1.id=1;
        curso1.capacidadCurso=3;
        curso1.profesor = prof;
        List<Horario> listHor = new ArrayList<>();
        listHor.add(hor);
        listHor.add(hor2);
        curso1.horarios = listHor ;


        Curso curso2 = new Curso();
        curso2.id=2;
        curso2.capacidadCurso=3;
        curso2.profesor = prof;
        curso2.horarios = listHor;


        ArrayList<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(curso1);
        listaCursos.add(curso2);

        return listaCursos;
    }
}

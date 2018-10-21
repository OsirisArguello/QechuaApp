package com.tdp2.quechuaapp.networking;

import android.util.Log;

import com.tdp2.quechuaapp.login.model.UserSessionManager;
import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Carrera;
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.InscripcionFinal;
import com.tdp2.quechuaapp.model.Horario;
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
        estudianteApi.getFinales(AUTHORIZATION_PREFIX + apiToken,idCurso).enqueue(new Callback<ArrayList<Final>>() {
            @Override
            public void onResponse(Call<ArrayList<Final>> call, Response<ArrayList<Final>> response) {
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
            public void onFailure(Call<ArrayList<Final>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void getMisFinales(final Client client) {
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.getMisFinales(AUTHORIZATION_PREFIX+apiToken).enqueue(new Callback<ArrayList<InscripcionFinal>>() {
            @Override
            public void onResponse(Call<ArrayList<InscripcionFinal>> call, Response<ArrayList<InscripcionFinal>> response) {
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
            public void onFailure(Call<ArrayList<InscripcionFinal>> call, Throwable t) {
                Log.e("ESTUDIANTESERVICE", t.getMessage());
                client.onResponseError(null);
            }
        });
    }

    public void inscribirFinal(Integer finalId,final Client client) {
        String apiToken=new UserSessionManager(client.getContext()).getAuthorizationToken();
        estudianteApi.inscribirFinal(AUTHORIZATION_PREFIX+apiToken, finalId).enqueue(new Callback<InscripcionFinal>() {
            @Override
            public void onResponse(Call<InscripcionFinal> call, Response<InscripcionFinal> response) {
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
            public void onFailure(Call<InscripcionFinal> call, Throwable t) {
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

        Materia m = new Materia();
        m.codigo = "12";
        m.nombre = "materia 1";
        curso1.materia = m;


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

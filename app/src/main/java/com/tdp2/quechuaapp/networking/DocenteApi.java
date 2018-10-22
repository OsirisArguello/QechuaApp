package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;
import com.tdp2.quechuaapp.model.Inscripcion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DocenteApi {

    @GET("/public/cursos/{cursoID}/inscripciones")
    Call<Curso> getCurso(@Path("cursoID")Integer cursoId);


    @GET("/api/profesors/cursos")
    Call<ArrayList<Curso>> getCursos(@Header("Authorization") String apiToken);


    @POST("/api/inscripcion-cursos/{inscripcionId}/accion/regularizar")
    Call<Inscripcion> aceptar(@Header("Authorization")String apiToken, @Path("inscripcionId")Integer inscripcionId);

    @POST("/api/inscripcion-cursos/{inscripcionId}/accion/rechazar")
    Call<Inscripcion> rechazar(@Header("Authorization")String apiToken, @Path("inscripcionId")Integer inscripcionId);

    @GET("/api/cursos/{cursoId}/coloquios")
    Call<ArrayList<Final>> getColoquios(@Header("Authorization") String apiToken,@Path("cursoId") Integer cursoId);

    @POST("/api/coloquios")
    Call<Final> crearColoquio(@Header("Authorization") String apiToken,@Body Final coloquio);
}

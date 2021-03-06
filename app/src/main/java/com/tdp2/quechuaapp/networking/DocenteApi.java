package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Coloquio;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.PeriodoAdministrativo;
import com.tdp2.quechuaapp.model.Profesor;
import com.tdp2.quechuaapp.networking.model.ColoquioRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DocenteApi {

    @GET("/public/cursos/{cursoID}/inscripciones")
    Call<Curso> getInscripcionesACurso(@Path("cursoID")Integer cursoId);

    @GET("/api/cursos/{cursoID}/")
    Call<Curso> getCurso(@Header("Authorization") String apiToken, @Path("cursoID")Integer cursoId);

    @GET("/api/profesors/cursos")
    Call<ArrayList<Curso>> getCursos(@Header("Authorization") String apiToken);

    @POST("/api/inscripcion-cursos/{inscripcionId}/accion/regularizar")
    Call<Inscripcion> aceptar(@Header("Authorization")String apiToken, @Path("inscripcionId")Integer inscripcionId);

    @POST("/api/inscripcion-cursos/{inscripcionId}/accion/rechazar")
    Call<Inscripcion> rechazar(@Header("Authorization")String apiToken, @Path("inscripcionId")Integer inscripcionId);

    @GET("/api/cursos/{cursoId}/coloquios")
    Call<ArrayList<Coloquio>> getColoquios(@Header("Authorization") String apiToken,@Path("cursoId") Integer cursoId);

    @GET("/api/cursos/coloquios")
    Call<ArrayList<Coloquio>> getMisFinales(@Header("Authorization") String apiToken);

    @POST("/api/coloquios")
    Call<Coloquio> crearColoquio(@Header("Authorization") String apiToken, @Body ColoquioRequest coloquio);

    @POST("/api/coloquios/{coloquioId}/eliminar")
    Call<Void> eliminarColoquio(@Header("Authorization") String apiToken, @Path("coloquioId") Integer coloquioId);

    @GET("/api/periodo-administrativos/periodos")
    Call<ArrayList<PeriodoAdministrativo>> getAccionesPeriodo(@Header("Authorization")String apiToken);

    @GET("/api/profesors/data")
    Call<Profesor> getProfData(@Header("Authorization")String apiToken);
}

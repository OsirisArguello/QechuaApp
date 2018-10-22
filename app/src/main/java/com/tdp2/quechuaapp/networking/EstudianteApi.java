package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.model.Alumno;
import com.tdp2.quechuaapp.model.Carrera;
import com.tdp2.quechuaapp.model.Curso;
import com.tdp2.quechuaapp.model.Final;
import com.tdp2.quechuaapp.model.Materia;
import com.tdp2.quechuaapp.model.Cursada;
import com.tdp2.quechuaapp.model.Inscripcion;
import com.tdp2.quechuaapp.model.InscripcionFinal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EstudianteApi {

    @GET("/api/alumnos/carreras")
    Call<ArrayList<Carrera>> getCarreras(@Header("Authorization") String apiToken);

    @GET("/public/materias")
    Call<ArrayList<Materia>> getMaterias();

    @GET("/api/materias/find")
    Call<ArrayList<Materia>> getMateriasPorCarrera(@Header("Authorization") String apiToken, @Query("carrera") Integer carreraId);

    @GET("/public/cursos")
    Call<ArrayList<Curso>> getCursos();

    @GET("/public/materias/{materiaId}/cursos")
    Call<ArrayList<Curso>> getCursosPorMateria(@Path("materiaId")Integer materiaId);

    @GET("/api/alumnos/cursadasActivas")
    Call<ArrayList<Cursada>> getCursadas(@Header("Authorization") String apiToken);

    @POST("/api/inscripcion-cursos/{cursoId}")
    Call<Inscripcion> inscribirAlumno(@Header("Authorization")String apiToken, @Path("cursoId")Integer cursoId);

    @POST("/api/inscripcion-cursos/{cursoId}/accion/desinscribir")
    Call<Inscripcion> desinscribirAlumno(@Header("Authorization")String apiToken, @Path("cursoId")Integer inscripcionId);

    @POST("/public/inscripcion-cursos/{cursoId}/{alumnoId}")
    Call<Inscripcion> inscribirAlumno(@Path("alumnoId")Integer alumnoId, @Path("cursoId")Integer cursoId);

    @GET("/api/cursos/{cursoId}/coloquios")
    Call<ArrayList<Final>> getFinales(@Header("Authorization")String apiToken, @Path("cursoId")Integer cursoId);

    @GET("/api/inscripcion-coloquios/byAlumno")
    Call<ArrayList<InscripcionFinal>> getMisFinales(@Header("Authorization")String apiToken);

    @POST("/api/inscripcion-coloquios/{coloquioId}/inscribir")
    Call<InscripcionFinal> inscribirFinal(@Header("Authorization")String apiToken, @Path("coloquioId")Integer coloquioId);

    @GET("/api/alumnos/data")
    Call<Alumno> getAlumno(@Header("Authorization")String apiToken);
}

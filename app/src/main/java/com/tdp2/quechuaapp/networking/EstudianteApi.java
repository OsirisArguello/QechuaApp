package com.tdp2.quechuaapp.networking;

import com.tdp2.quechuaapp.model.Curso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EstudianteApi {

    @GET("/public/cursos")
    Call<ArrayList<Curso>> getCursos();

}
